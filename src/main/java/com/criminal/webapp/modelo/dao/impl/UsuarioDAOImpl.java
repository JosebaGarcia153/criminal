package com.criminal.webapp.modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.criminal.webapp.modelo.dao.SecurityException;
import com.criminal.webapp.modelo.dao.UsuarioDAO;
import com.criminal.webapp.modelo.pojo.Usuario;
import com.criminal.webapp.modelo.pojo.Rol;
import com.criminal.webapp.modelo.ConnectionManager;

public class UsuarioDAOImpl implements UsuarioDAO {
	
	private static final Logger LOG = Logger.getLogger(UsuarioDAOImpl.class);
	private static UsuarioDAOImpl instance = null;
	
	private UsuarioDAOImpl() {
		super();	
	}
	
	public static synchronized UsuarioDAOImpl getInstance() {
		
		if (instance == null) {
			
			instance = new UsuarioDAOImpl();
		}	
		return instance;
	}
	
	private final String SQL_GET_ALL = "SELECT u.id, u.nombre, password, r.id, r.nombre FROM usuarios u, rol r WHERE rol_id = r.id ORDER BY u.nombre ASC; ";
	
	private final String SQL_EXISTS = "SELECT u.id, u.nombre, password, r.id, r.nombre FROM usuarios u, rol r WHERE u.nombre = ? AND password = MD5(?) AND rol_id = r.id; ";
	
	private final String SQL_CREATE = "INSERT INTO usuarios (nombre, password, rol_id) VALUES (?, MD5(?), 1); ";
	
	private final String SQL_UPDATE = "UPDATE usuarios SET nombre = ?, password = MD5(?), rol_id = ?  WHERE id = ?; ";
	
	private final String SQL_DELETE = "DELETE FROM usuarios WHERE id = ?; ";
	
	private final String SQL_FIND = "SELECT nombre from usuarios WHERE nombre = ?; ";
	
	private final String SQL_GET_BY_ID = "SELECT u.id, u.nombre, password, r.id, r.nombre FROM usuarios u, rol r WHERE rol_id = r.id AND u.id = ?; ";
	
	@Override
	public ArrayList<Usuario> conseguirTodos() {
		
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		
		try(
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery();
				){
				
				LOG.debug(pst);
				while( rs.next() ) {
					usuarios.add(mapper(rs));					
				}
					
			} catch (Exception e) {
				LOG.error(e);
			}
		
			return usuarios;
	}
	
	@Override
	public Usuario existe(String nombre, String password) {
		
		Usuario usuario = null;
		
		try(	
			Connection conexion = ConnectionManager.getConnection();
			PreparedStatement pst = conexion.prepareStatement(SQL_EXISTS);	
			) {
			
			pst.setString(1, nombre);
			pst.setString(2, password);
			
			LOG.debug(pst);
			try (ResultSet rs = pst.executeQuery()) {
				
				if (rs.next()) {
					
					usuario = mapper(rs);
				}
			} //try 2
		} catch (Exception e) {
			
			LOG.error(e);
		}//try
		
		return usuario;
	}
	
	
	@Override
	public Usuario crear(Usuario usuario) throws Exception {
		
		try ( 
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
			){
			
			pst.setString(1, usuario.getNombre());
			pst.setString(2, usuario.getPassword());
		
			int affectedRows = pst.executeUpdate();
			
			LOG.debug(pst);
			if (affectedRows == 1) {
				
				//Conseguir el ID
				try ( ResultSet rsKeys = pst.getGeneratedKeys(); ) {
					
					if (rsKeys.next()) {

						usuario.setId(rsKeys.getInt(1));
					}
				}//try 2
			} else {

				throw new Exception ("No se pudo crear una nueva entrada para " + usuario.getNombre());
			}//if
		} catch (SQLException e) {
			
			throw new SQLException("El email ya existe");
		}//try
		
		return usuario;
	}
	
	
	@Override
	public boolean buscarPorNombre(String nombre) {
		
		boolean check = false;
		
		try(	
			Connection conexion = ConnectionManager.getConnection();
			PreparedStatement pst = conexion.prepareStatement(SQL_FIND);	
			) {
			
			pst.setString(1, nombre);
			
			LOG.debug(pst);
			try (ResultSet rs = pst.executeQuery()) {
				
				if (rs.next()) {
					
					check = true;
				}
			}//try2
		} catch (Exception e) {
			
			LOG.error(e);
		}//try
		
		return check;
	}
	
	@Override
	public Usuario actualizar(Usuario usuario) throws Exception {
		
		if (usuario.getNombre() == null) {
			
			throw new Exception("Tienes que insertar un nombre");
		}
		
		try ( 
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_UPDATE);
			){
			
			pst.setString(1,usuario.getNombre());
			pst.setString(2, usuario.getPassword());
			pst.setInt(3, usuario.getRol().getId());
			pst.setInt(4, usuario.getId());
			
			LOG.debug(pst);
			int affectedRows = pst.executeUpdate();
			
			if (affectedRows != 1) {				
				throw new Exception ("No se ha podido actualizar: " + usuario.getNombre());
			}		
		} catch (SQLException e) {	
			LOG.error(e);
			throw new SQLException("El usuario ya existe");
		} 
		return usuario;
	}

	
	@Override
	public Usuario borrar(int id) throws Exception {
		
		Usuario usuario = null;
		
		try (	
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_DELETE);
			){
			
			pst.setInt(1, id);
			
			LOG.debug(pst);
				pst.executeUpdate();

			} catch (Exception e) {
				throw new SecurityException();
			}
		
		return usuario;
	}
	
	@Override
	public Usuario conseguirPorId(int id) throws Exception {
		
		Usuario usuario = new Usuario();

		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_GET_BY_ID);
			){

			pst.setInt(1, id);
			
			try ( ResultSet rs = pst.executeQuery() ){
				
				LOG.debug(pst);
				while (rs.next()) {

					usuario = mapper(rs);
				}
				
			} catch (Exception e) {

				throw new SecurityException();
			}//try2
		} //try1
		
		return usuario;
	}
	
	private Usuario mapper( ResultSet rs ) throws SQLException {
		
		Usuario usuario = new Usuario();
		
		usuario.setId(rs.getInt("u.id"));
		usuario.setNombre(rs.getString("u.nombre"));
		usuario.setPassword(rs.getString("password"));

		
		//Rol
		Rol rol = new Rol();
		rol.setId(rs.getInt("r.id"));
		rol.setNombre(rs.getString("r.nombre"));
		
		//Setear el rol al usuario
		usuario.setRol(rol);
		
		return usuario;	
	}
}
