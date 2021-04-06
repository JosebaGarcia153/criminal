package com.criminal.webapp.modelo.dao.impl;

import java.sql.CallableStatement;
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
	
//	private final String SQL_READ_BY_ID = "SELECT id, name FROM categories WHERE id = ? LIMIT 500; ";

//	private final String SQL_CREATE = "INSERT INTO categories (name) VALUES (?); ";
//	private final String SQL_UPDATE = "UPDATE categories SET name = ? WHERE id = ?; ";
//	private final String SQL_DELETE = "DELETE FROM categories WHERE id = ?; ";
	
	
	@Override
	public ArrayList<Categoria> conseguirTodas() {
		
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();

		try(
			Connection connection = ConnectionManager.getConnection();
			//PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL);
			CallableStatement cs = connection.prepareCall(SQL_GET_ALL);
			ResultSet rs = cs.executeQuery();
			){
			
			LOG.debug(cs);
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
	public Categoria crear(Categoria pojo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Categoria actualizar(Categoria pojo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Categoria borrar(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
