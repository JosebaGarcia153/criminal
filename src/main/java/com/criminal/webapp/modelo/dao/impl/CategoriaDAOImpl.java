package com.criminal.webapp.modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.criminal.webapp.modelo.dao.CategoriaDAO;
import com.criminal.webapp.modelo.dao.SecurityException;
import com.criminal.webapp.modelo.ConnectionManager;
import com.criminal.webapp.modelo.pojo.Categoria;
import com.criminal.webapp.modelo.pojo.Pregunta;


public class CategoriaDAOImpl implements CategoriaDAO{
	
	private static final Logger LOG = Logger.getLogger(CategoriaDAOImpl.class);
	private static CategoriaDAOImpl INSTANCE = null;
	
	private CategoriaDAOImpl() {
		super();
	}
	
	public static synchronized CategoriaDAOImpl getInstance() {
		
		if (INSTANCE == null) {
			INSTANCE = new CategoriaDAOImpl();
		}
		
		return INSTANCE;
	}
	
	private final String SQL_GET_ALL = "SELECT id, nombre FROM categorias ORDER BY nombre ASC; ";
	
	private final String SQL_GET_ALL_WITH_QUESTIONS = " SELECT c.id 'categoria_id', c.nombre 'categoria_nombre',"
													+ " p.id 'pregunta_id', p.nombre 'pregunta_nombre'"
													+ " FROM preguntas p, categorias c"
													+ " WHERE p.categoria_id = c.id"
													+ " ORDER BY c.nombre ASC; ";
	
	
	private final String SQL_GET_ALL_APPROVED_WITH_QUESTIONS = " SELECT c.id 'categoria_id', c.nombre 'categoria_nombre',"
													+ " p.id 'pregunta_id', p.nombre 'pregunta_nombre'"
													+ " FROM preguntas p, categorias c"
													+ " WHERE p.categoria_id = c.id"
													+ " AND fecha_aprobada IS NOT NULL " //filtramos por la aprobacion
													+ " ORDER BY c.nombre ASC; ";

	private final String SQL_CREATE = "INSERT INTO categorias (nombre) VALUES (?); ";
	
	private final String SQL_UPDATE = "UPDATE categorias SET nombre = ? WHERE id = ?; ";
	
	private final String SQL_DELETE = "DELETE FROM categorias WHERE id = ?; ";
	
	private final String SQL_GET_BY_ID = "SELECT id, nombre FROM categorias WHERE id = ?; ";
	
	
	@Override
	public ArrayList<Categoria> conseguirTodas() {
		
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();

		try(
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL);
			ResultSet rs = pst.executeQuery();
			){
			
			LOG.debug(pst);
			while( rs.next() ) {
				categorias.add(mapper(rs));					
			}
				
		} catch (Exception e) {
			LOG.error(e);
		}
		return categorias;
	}
	
	
	@Override
	public ArrayList<Categoria> conseguirTodasConPreguntas() {
		
		//La clave Integer es el ID de la categoria
		HashMap<Integer, Categoria> categorias = new HashMap<Integer, Categoria>();

		try(
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_WITH_QUESTIONS);
			ResultSet rs = pst.executeQuery();
			){
			
			LOG.debug(pst);
			while( rs.next() ) {
					
				int categoriaId = rs.getInt("categoria_id"); //hashmap key
				
				Categoria categoria = categorias.get(categoriaId);
				
				//Si la categoria no existe, crea una nueva y guarda los datos
				if (categoria == null) {
					
					categoria = new Categoria();
					categoria.setId(categoriaId);
					categoria.setNombre(rs.getString("categoria_nombre"));
				}
				
				//Crea nueva pregunta y guarda los datos
				Pregunta pregunta = new Pregunta();
				pregunta.setId(rs.getInt("pregunta_id"));
				pregunta.setNombre(rs.getString("pregunta_nombre"));
				
				//Agrega pregunta a la categoria
				//Value del hashmap
				categoria.getPreguntas().add(pregunta);
				
				//Guarda la categoria en el hashmap
				categorias.put(categoriaId, categoria);					
			}
				
		} catch (Exception e) {
			
			LOG.error(e);
		}
		//Devuelve un arraylist de categorias, cada una con su lista de preguntas
		return new ArrayList<Categoria>(categorias.values());
	}
	
	
	@Override
	public ArrayList<Categoria> conseguirTodasConPreguntasPorUsuario() {
		
		//La clave Integer es el ID de la categoria
		HashMap<Integer, Categoria> categorias = new HashMap<Integer, Categoria>();

		try(
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_APPROVED_WITH_QUESTIONS);
			ResultSet rs = pst.executeQuery();
			){
			
			LOG.debug(pst);
			while( rs.next() ) {
					
				int categoriaId = rs.getInt("categoria_id"); //hashmap key
				
				Categoria categoria = categorias.get(categoriaId);
				
				//Si la categoria no existe, crea una nueva y guarda los datos
				if (categoria == null) {
					
					categoria = new Categoria();
					categoria.setId(categoriaId);
					categoria.setNombre(rs.getString("categoria_nombre"));
				}
				
				//Crea nueva pregunta y guarda los datos
				Pregunta pregunta = new Pregunta();
				pregunta.setId(rs.getInt("pregunta_id"));
				pregunta.setNombre(rs.getString("pregunta_nombre"));		
				
				//Agrega pregunta a la categoria
				//Value del hashmap
				categoria.getPreguntas().add(pregunta);
				
				//Guarda la categoria en el hashmap
				categorias.put(categoriaId, categoria);					
			}
				
		} catch (Exception e) {
			
			LOG.error(e);
		}
		//Devuelve un arraylist de categorias, cada una con su lista de preguntas
		return new ArrayList<Categoria>(categorias.values());
	}

	
	@Override
	public Categoria crear(Categoria categoria) throws Exception {
		
		if (categoria.getNombre() == null) {

			throw new Exception("Tienes que insertar un nombre");
		}
		
		try ( 
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
			
			){
			
			pst.setString(1, categoria.getNombre());

			
			int affectedRows = pst.executeUpdate();
			
			LOG.debug(pst);
			if (affectedRows == 1) {
				
				//conseguir el ID
				try ( ResultSet rsKeys = pst.getGeneratedKeys(); ) {
					
					if (rsKeys.next()) {

						categoria.setId(rsKeys.getInt(1));
					}
				}//try2
			} else {

				throw new Exception ("No se ha podido agregar " + categoria.getNombre());
			}//if
			
		} catch (SQLException e) {
			LOG.error(e);
			throw new SQLException("La categoria ya existe");
		}//try
		
		return categoria;
	}
	
	
	@Override
	public Categoria actualizar(Categoria categoria) throws Exception {
		
		if (categoria.getNombre() == null) {
			
			throw new Exception("Tienes que insertar un nombre");
		}
		
		try ( 
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_UPDATE);
			){
			
			pst.setString(1,categoria.getNombre());				
			pst.setInt(2, categoria.getId());
			
			LOG.debug(pst);
			int affectedRows = pst.executeUpdate();
			
			if (affectedRows != 1) {				
				throw new Exception ("No se ha podido actualizar: " + categoria.getNombre());
			}		
		} catch (SQLException e) {	
			LOG.error(e);
			throw new SQLException("La categoria ya existe");
		} 
		return categoria;
	}

	
	@Override
	public Categoria borrar(int id) throws Exception {
		
		Categoria categoria = null;
		
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
		
		return categoria;
	}
	
	
	@Override
	public Categoria conseguirPorId(int id) throws Exception {
		
		Categoria categoria = new Categoria();

		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_GET_BY_ID);
			){

			pst.setInt(1, id);
			
			try ( ResultSet rs = pst.executeQuery() ){
				
				LOG.debug(pst);
				while (rs.next()) {

					categoria = mapper(rs);
				}
				
			} catch (Exception e) {

				throw new SecurityException();
			}//try2
		} //try1
		
		return categoria;
	}
	
	
	private Categoria mapper(ResultSet rs) throws SQLException {
		
		Categoria c = new Categoria();
		
		c.setId(rs.getInt("id"));
		c.setNombre(rs.getString("nombre"));
		
		return c;
	}

	
	
	@Override
	public Categoria borrar(int id, int usuarioId) throws Exception, SecurityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categoria actualizar(Categoria pojo, int usuarioId) throws Exception, SecurityException {
		// TODO Auto-generated method stub
		return null;
	}
}
