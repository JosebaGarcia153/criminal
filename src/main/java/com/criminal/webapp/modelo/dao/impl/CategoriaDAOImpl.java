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
//	private final String SQL_GET_ALL_WITH_GAMES = "SELECT c.id 'category_id', c.name 'category_name', g.id 'game_id', g.name 'game_name', price FROM games g, categories c WHERE g.category_id = c.id ORDER BY c.name ASC LIMIT 500; ";
//	
//	private final String SQL_READ_BY_ID = "SELECT id, name FROM categories WHERE id = ? LIMIT 500; ";
//
//	private final String SQL_CREATE = "INSERT INTO categories (name) VALUES (?); ";
//	private final String SQL_UPDATE = "UPDATE categories SET name = ? WHERE id = ?; ";
//	private final String SQL_DELETE = "DELETE FROM categories WHERE id = ?; ";
	
	//private final String PA_GET_ALL = " { CALL pa_category_list() }  ";
	//private final String SQL_GET_ALL_WITH_GAMES = " SELECT c.id 'category_id', c.name 'category_name', g.id 'game_id', g.name 'game_name', image, price FROM games g, categories c WHERE g.category_id = c.id AND g.approval_date IS NOT NULL ORDER BY c.name ASC LIMIT 500; ";
	
	//private final String PA_GET_BY_ID = " { CALL pa_category_by_id(?) } ";
	
	//private final String PA_INSERT = " { CALL pa_category_insert(?,?) } ";
	//private final String PA_UPDATE = " { CALL pa_category_update(?,?) } ";	
	//private final String PA_DELETE = " { CALL pa_category_delete(?) } ";
	
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
	
	private Categoria mapper(ResultSet rs) throws SQLException {
		
		Categoria c = new Categoria();
		
		c.setId(rs.getInt("id"));
		c.setNombre(rs.getString("nombre"));
		
		return c;
	}

	@Override
	public Categoria crear(Categoria pojo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categoria actualizar(Categoria pojo, int usuarioId) throws Exception, SecurityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categoria borrar(int id, int usuarioId) throws Exception, SecurityException {
		// TODO Auto-generated method stub
		return null;
	}
}
