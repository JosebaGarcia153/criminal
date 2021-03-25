package com.criminal.webapp.modelo.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.criminal.webapp.modelo.dao.PreguntaDAO;
import com.criminal.webapp.modelo.dao.SecurityException;
import com.criminal.webapp.modelo.pojo.ContadorPreguntas;
import com.criminal.webapp.modelo.pojo.Pregunta;

public class PreguntaDAOImpl implements PreguntaDAO{
	
	private static final Logger LOG = Logger.getLogger(PreguntaDAOImpl.class);
	public static PreguntaDAOImpl INSTANCE = null;
	
	private PreguntaDAOImpl() {
		super();
	}

	public static synchronized PreguntaDAOImpl getInstance() {
		
		if ( INSTANCE == null ){
			INSTANCE = new PreguntaDAOImpl();
		}
		
		return INSTANCE;		
	}

	private final String SQL_GET_ALL = "SELECT g.id 'game_id', g.name 'game_name', price, c.id 'category_id', c.name 'category_name', user_id"
										+ " FROM games g, categories c" 
										+ " WHERE g.category_id = c.id"
										+ " ORDER BY g.id DESC LIMIT 500; ";
	
	private final String SQL_GET_BY_CATEGORY = "SELECT g.id 'game_id', g.name 'game_name', price, c.id 'category_id', c.name 'category_name', user_id" 
			+ " FROM games g, categories c" 
			+ " WHERE g.category_id  = c.id AND approval_date IS NOT NULL"
			+ " AND c.id = ? " // filtramos por el id de la categoria
			+ " ORDER BY g.id DESC LIMIT ? ; ";
	
	private final String SQL_GET_BY_USER = "SELECT g.id 'game_id', g.name 'game_name', price, image, c.id 'category_id', c.name 'category_name', user_id"
										+ " FROM games g, categories c" 
										+ " WHERE g.category_id  = c.id AND g.user_id = ?"  
										+ " ORDER BY g.id DESC LIMIT 500; ";
	
	private final String SQL_COUNT_ALL = "SELECT * FROM count_games; ";
	
	private final String SQL_COUNT_BY_USER = "SELECT * FROM count_games WHERE user_id = ?; ";
	
	private final String SQL_CREATE = "INSERT INTO games (name, price, image, user_id, category_id) VALUES (?, ?, ?, ?, ?); ";
	
	private final String SQL_UPDATE = "UPDATE games SET name = ?, price = ?, image = ?, user_id = ?, category_id = ?, approval_date = ? WHERE id = ?; ";
	
	private final String SQL_DELETE = "DELETE FROM games WHERE id = ?; ";
	
	private final String SQL_GET_BY_ID = "SELECT g.id 'game_id', g.name 'game_name', price, c.id 'category_id', g.user_id, c.name 'category_name', user_id"
										+ " FROM games g, categories c"
										+ " WHERE g.category_id = c.id AND g.id = ? AND g.user_id = ? LIMIT 500; ";
	
	
	@Override
	public ArrayList<Pregunta> conseguirTodas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Pregunta> conseguirPorCategoria(int categoriaId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Pregunta> conseguirPorUsario(int usuarioId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContadorPreguntas contar() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ContadorPreguntas contarPorUsuario(int usuarioId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pregunta crear(Pregunta pregunta) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pregunta actualizar(Pregunta pregunta, int usuarioId) throws Exception, SecurityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pregunta borrar(int id, int usuarioId) throws Exception, SecurityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pregunta getById(int id, int usuarioId) throws Exception, SecurityException {
		// TODO Auto-generated method stub
		return null;
	}
}
