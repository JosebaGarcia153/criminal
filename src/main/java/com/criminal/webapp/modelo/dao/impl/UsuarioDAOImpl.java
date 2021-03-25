package com.criminal.webapp.modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

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
	
	
	private final String SQL_EXISTE = "SELECT u.id, u.nombre, password, r.id, r.nombre FROM usuarios u, rol r WHERE u.nombre = ? AND password = MD5(?) AND rol_id = r.id; ";
	
	private final String SQL_CREAR = "INSERT INTO usuarios (nombre, password, rol_id) VALUES (?, MD5(?), 1); ";
	
	private final String SQL_ENCONTRAR = "SELECT nombre from usuarios WHERE nombre = ?; ";
	
	
	@Override
	public Usuario existe(String nombre, String password) {
		
		Usuario usuario = null;
		
		try(	
			Connection conexion = ConnectionManager.getConnection();
			PreparedStatement pst = conexion.prepareStatement(SQL_EXISTE);	
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
			PreparedStatement pst = connection.prepareStatement(SQL_CREAR, PreparedStatement.RETURN_GENERATED_KEYS);
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
			PreparedStatement pst = conexion.prepareStatement(SQL_ENCONTRAR);	
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
