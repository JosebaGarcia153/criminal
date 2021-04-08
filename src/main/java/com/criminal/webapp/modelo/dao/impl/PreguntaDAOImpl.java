package com.criminal.webapp.modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.criminal.webapp.modelo.dao.PreguntaDAO;
import com.criminal.webapp.modelo.dao.SecurityException;
import com.criminal.webapp.modelo.pojo.Categoria;
import com.criminal.webapp.modelo.pojo.ContadorPreguntas;
import com.criminal.webapp.modelo.pojo.Pregunta;
import com.criminal.webapp.modelo.pojo.Respuesta;
import com.criminal.webapp.modelo.ConnectionManager;

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

	private final String SQL_GET_ALL = "SELECT p.id 'pregunta_id', p.nombre 'pregunta_nombre', dificultad, imagen,"
										+ " tiempo, comentario, fecha_aprobada, usuario_id, c.id 'categoria_id', c.nombre 'categoria_nombre',"
										+ " r.id 'respuesta_id', r.nombre 'respuesta_nombre', esCorrecta, num_respuesta"
										+ " FROM preguntas p, respuestas r, categorias c"
										+ " WHERE p.categoria_id = c.id AND r.pregunta_id = p.id"
										+ " ORDER BY p.id DESC; ";
	
	private final String SQL_GET_ALL_APPROVED = "SELECT p.id 'pregunta_id', p.nombre 'pregunta_nombre', dificultad, imagen,"
										+ " tiempo, comentario, fecha_aprobada, usuario_id, c.id 'categoria_id', c.nombre 'categoria_nombre',"
										+ " r.id 'respuesta_id', r.nombre 'respuesta_nombre', esCorrecta, num_respuesta"
										+ " FROM preguntas p, respuestas r, categorias c"
										+ " WHERE p.categoria_id = c.id AND r.pregunta_id = p.id"
										+ " AND fecha_aprobada IS NOT NULL " //filtramos por la aprobacion
										+ " ORDER BY p.id DESC; ";
	
	private final String SQL_GET_BY_CATEGORY = "SELECT p.id 'pregunta_id', p.nombre 'pregunta_nombre', dificultad, imagen,"
										+ " tiempo, comentario, fecha_aprobada, usuario_id, c.id 'categoria_id', c.nombre 'categoria_nombre',"
										+ " r.id 'respuesta_id', r.nombre 'respuesta_nombre', esCorrecta, num_respuesta"
										+ " FROM preguntas p, respuestas r, categorias c"
										+ " WHERE p.categoria_id = c.id AND r.pregunta_id = p.id"
										+ " AND c.id = ? " // filtramos por el id de la categoria
										+ " ORDER BY p.id DESC; ";
	
	private final String SQL_GET_BY_CATEGORY_APPROVED = "SELECT p.id 'pregunta_id', p.nombre 'pregunta_nombre', dificultad, imagen,"
										+ " tiempo, comentario, fecha_aprobada, usuario_id, c.id 'categoria_id', c.nombre 'categoria_nombre',"
										+ " r.id 'respuesta_id', r.nombre 'respuesta_nombre', esCorrecta, num_respuesta"
										+ " FROM preguntas p, respuestas r, categorias c"
										+ " WHERE p.categoria_id = c.id AND r.pregunta_id = p.id"
										+ " AND c.id = ? " // filtramos por el id de la categoria
										+ " AND fecha_aprobada IS NOT NULL " //filtramos por la aprobacion
										+ " ORDER BY p.id DESC; ";
	
	private final String SQL_GET_ALL_BY_USER = "SELECT p.id 'pregunta_id', p.nombre 'pregunta_nombre', dificultad, imagen,"
										+ " tiempo, comentario, fecha_aprobada, usuario_id, c.id 'categoria_id', c.nombre 'categoria_nombre',"
										+ " r.id 'respuesta_id', r.nombre 'respuesta_nombre', esCorrecta, num_respuesta"
										+ " FROM preguntas p, respuestas r, categorias c"
										+ " WHERE p.categoria_id = c.id AND r.pregunta_id = p.id"
										+ " AND p.usuario_id = ?" // filtramos por el id del usuario
										+ " ORDER BY p.id DESC; ";
	
	private final String SQL_GET_BY_USER_APPROVED = "SELECT p.id 'pregunta_id', p.nombre 'pregunta_nombre', dificultad, imagen,"
										+ " tiempo, comentario, fecha_aprobada, usuario_id, c.id 'categoria_id', c.nombre 'categoria_nombre',"
										+ " r.id 'respuesta_id', r.nombre 'respuesta_nombre', esCorrecta, num_respuesta"
										+ " FROM preguntas p, respuestas r, categorias c"
										+ " WHERE p.categoria_id = c.id AND r.pregunta_id = p.id"
										+ " AND fecha_aprobada IS NOT NULL " //filtramos por la aprobacion
										+ " AND p.usuario_id = ?" // filtramos por el id del usuario
										+ " ORDER BY p.id DESC; ";

	
	private final String SQL_GET_BY_USER_NON_APPROVED = "SELECT p.id 'pregunta_id', p.nombre 'pregunta_nombre', dificultad, imagen,"
										+ " tiempo, comentario, fecha_aprobada, usuario_id, c.id 'categoria_id', c.nombre 'categoria_nombre',"
										+ " r.id 'respuesta_id', r.nombre 'respuesta_nombre', esCorrecta, num_respuesta"
										+ " FROM preguntas p, respuestas r, categorias c"
										+ " WHERE p.categoria_id = c.id AND r.pregunta_id = p.id"
										+ " AND fecha_aprobada IS NULL " //filtramos por la aprobacion
										+ " AND p.usuario_id = ?" // filtramos por el id del usuario
										+ " ORDER BY p.id DESC; ";
	
	private final String SQL_COUNT_ALL = "SELECT * FROM contar_preguntas; ";
	
	private final String SQL_COUNT_BY_USER = "SELECT * FROM contar_preguntas WHERE usuario_id = ?; ";
	
	private final String SQL_CREATE_QUESTION = "INSERT INTO preguntas (nombre, dificultad, tiempo, comentario, imagen, usuario_id, categoria_id)"
										+ " VALUES (?, ?, ?, ?, ?, ?, ?); ";
	
	private final String SQL_CREATE_ANSWER = "INSERT INTO respuestas (nombre, esCorrecta, pregunta_id, num_respuesta)"
										+ " VALUES (?, ?, ?, ?); ";
	
	private final String SQL_UPDATE_QUESTION = "UPDATE preguntas SET nombre = ?, dificultad = ?, tiempo = ?,"
										+ " comentario = ?, imagen = ?, usuario_id = ?, categoria_id = ?"
										+ " WHERE id = ?; ";
	
	private final String SQL_UPDATE_ANSWER = "UPDATE respuestas SET nombre = ?, esCorrecta = ?, pregunta_id = ?, num_respuesta = ?"
										+ " WHERE id = ?; ";
	
	private final String SQL_DELETE = "DELETE FROM preguntas WHERE id = ?; ";
	
	private final String SQL_GET_QUESTION_BY_ID = "SELECT p.id 'pregunta_id', p.nombre 'pregunta_nombre', dificultad, imagen, tiempo, comentario, "
										+ " fecha_aprobada, usuario_id, c.id 'categoria_id', c.nombre 'categoria_nombre', r.id 'respuesta_id', r.nombre 'respuesta_nombre'"
										+ " FROM preguntas p, respuestas r, categorias c"
										+ " WHERE p.categoria_id = c.id AND r.pregunta_id = p.id AND p.id = ? AND p.usuario_id = ?; ";
	
	private final String SQL_GET_ONE_QUESTION_BY_ID = "SELECT p.id 'pregunta_id', p.nombre 'pregunta_nombre', dificultad, imagen,"
										+ " tiempo, comentario, fecha_aprobada, usuario_id, r.esCorrecta,"
										+ " c.id 'categoria_id', c.nombre 'categoria_nombre', r.nombre 'respuesta_nombre'"
										+ " FROM preguntas p, respuestas r, categorias c"
										+ " WHERE p.categoria_id = c.id AND r.pregunta_id = p.id AND p.id = ?; ";
	
	private final String SQL_VALIDATE = "UPDATE preguntas SET fecha_aprobada = NOW() WHERE id = ?; ";
	
	@Override
	public ArrayList<Pregunta> conseguirTodas() {
		
		//La clave Integer es el ID de la pregunta
		HashMap<Integer, Pregunta> preguntas = new HashMap<Integer, Pregunta>();
		
		try(
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
			ResultSet rs = pst.executeQuery();
			){
			
			LOG.debug(pst);
			while( rs.next() ) {
				
				int preguntaId = rs.getInt("pregunta_id"); //clave del hashmap
				Pregunta pregunta = preguntas.get(preguntaId);
				
				//Si la pregunta no existe, crea una nueva y guarda los datos
				if (pregunta == null) {
					
					pregunta = mapper(rs);
				}
				
				//Crea nueva respuesta y guarda los datos
				Respuesta respuesta = new Respuesta();
				
				respuesta.setId(rs.getInt("respuesta_id"));
				respuesta.setNombre(rs.getString("respuesta_nombre"));
				
				if (rs.getInt("esCorrecta") == 1) {
					respuesta.setEsCorrecta(false);
				} else {
					respuesta.setEsCorrecta(true);
				}
				
				respuesta.setNum_respuesta(rs.getInt("num_respuesta"));
				
				//Agrega respuesta a la pregunta
				//Valor del hashmap
				pregunta.getRespuestas().add(respuesta);
				
				//Guarda la pregunta en el hashmap
				preguntas.put(preguntaId, pregunta);
			}//while	
		} catch (Exception e) {
			
			LOG.error(e);
		}//try
		//Devuelve un arraylist de preguntas, cada una con su lista de respuestas
		return new ArrayList<Pregunta>(preguntas.values());
	}
	
	
	@Override
	public ArrayList<Pregunta> conseguirTodasAprobadas() {
		
		//La clave Integer es el ID de la pregunta
		HashMap<Integer, Pregunta> preguntas = new HashMap<Integer, Pregunta>();
		
		try(
			Connection con = ConnectionManager.getConnection();
			PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_APPROVED);
			ResultSet rs = pst.executeQuery();
			){
			
			LOG.debug(pst);
			while( rs.next() ) {
				
				int preguntaId = rs.getInt("pregunta_id"); //clave del hashmap
				Pregunta pregunta = preguntas.get(preguntaId);
				
				//Si la pregunta no existe, crea una nueva y guarda los datos
				if (pregunta == null) {
					
					pregunta = mapper(rs);
				}
				
				//Crea nueva respuesta y guarda los datos
				Respuesta respuesta = new Respuesta();
				
				respuesta.setId(rs.getInt("respuesta_id"));
				respuesta.setNombre(rs.getString("respuesta_nombre"));
				
				if (rs.getInt("esCorrecta") == 1) {
					respuesta.setEsCorrecta(false);
				} else {
					respuesta.setEsCorrecta(true);
				}
				
				respuesta.setNum_respuesta(rs.getInt("num_respuesta"));
				
				//Agrega respuesta a la pregunta
				//Valor del hashmap
				pregunta.getRespuestas().add(respuesta);
				
				//Guarda la pregunta en el hashmap
				preguntas.put(preguntaId, pregunta);
			}//while	
		} catch (Exception e) {
			
			LOG.error(e);
		}//try
		//Devuelve un arraylist de preguntas, cada una con su lista de respuestas
		return new ArrayList<Pregunta>(preguntas.values());
	}

	
	@Override
	public ArrayList<Pregunta> conseguirPorCategoria(int categoriaId) {
		
		//La clave Integer es el ID de la pregunta
		HashMap<Integer, Pregunta> preguntas = new HashMap<Integer, Pregunta>();
		
		try (
			Connection conexion = ConnectionManager.getConnection();
			PreparedStatement pst = conexion.prepareStatement(SQL_GET_BY_CATEGORY);
			){
			
			pst.setNull(1, java.sql.Types.NULL);
			pst.setInt(1, categoriaId);
			
			LOG.debug(pst);
			try (ResultSet rs = pst.executeQuery()) {
				while( rs.next() ) {
					
					int preguntaId = rs.getInt("pregunta_id"); //clave del hashmap
					Pregunta pregunta = preguntas.get(preguntaId);
					
					//Si la pregunta no existe, crea una nueva y guarda los datos
					if (pregunta == null) {
						
						pregunta = mapper(rs);
					}
					
					//Crea nueva respuesta y guarda los datos
					Respuesta respuesta = new Respuesta();
					
					respuesta.setId(rs.getInt("respuesta_id"));
					respuesta.setNombre(rs.getString("respuesta_nombre"));
					
					if (rs.getInt("esCorrecta") == 1) {
						respuesta.setEsCorrecta(false);
					} else {
						respuesta.setEsCorrecta(true);
					}
					
					respuesta.setNum_respuesta(rs.getInt("num_respuesta"));
					
					//Agrega respuesta a la pregunta
					//Valor del hashmap
					pregunta.getRespuestas().add(respuesta);
					
					//Guarda la pregunta en el hashmap
					preguntas.put(preguntaId, pregunta);
				}//while
			}//try2
		} catch (Exception e) {
			
			LOG.error(e);
		}//try1
		//Devuelve un arraylist de preguntas, cada una con su lista de respuestas
		return new ArrayList<Pregunta>(preguntas.values());
	}
	
	
	@Override
	public ArrayList<Pregunta> conseguirPorCategoriaPorUsuario(int categoriaId) {
		
		//La clave Integer es el ID de la pregunta
		HashMap<Integer, Pregunta> preguntas = new HashMap<Integer, Pregunta>();
		
		try (
			Connection conexion = ConnectionManager.getConnection();
			PreparedStatement pst = conexion.prepareStatement(SQL_GET_BY_CATEGORY_APPROVED);
			){
			
			pst.setNull(1, java.sql.Types.NULL);
			pst.setInt(1, categoriaId);
			
			LOG.debug(pst);
			try (ResultSet rs = pst.executeQuery()) {
				while( rs.next() ) {
					
					int preguntaId = rs.getInt("pregunta_id"); //clave del hashmap
					Pregunta pregunta = preguntas.get(preguntaId);
					
					//Si la pregunta no existe, crea una nueva y guarda los datos
					if (pregunta == null) {
						
						pregunta = mapper(rs);
					}
					
					//Crea nueva respuesta y guarda los datos
					Respuesta respuesta = new Respuesta();
					
					respuesta.setId(rs.getInt("respuesta_id"));
					respuesta.setNombre(rs.getString("respuesta_nombre"));
					
					if (rs.getInt("esCorrecta") == 1) {
						respuesta.setEsCorrecta(false);
					} else {
						respuesta.setEsCorrecta(true);
					}
					
					respuesta.setNum_respuesta(rs.getInt("num_respuesta"));
					
					//Agrega respuesta a la pregunta
					//Valor del hashmap
					pregunta.getRespuestas().add(respuesta);
					
					//Guarda la pregunta en el hashmap
					preguntas.put(preguntaId, pregunta);
				}//while
			}//try2
		} catch (Exception e) {
			
			LOG.error(e);
		}//try1
		//Devuelve un arraylist de preguntas, cada una con su lista de respuestas
		return new ArrayList<Pregunta>(preguntas.values());
	}

	
	@Override
	public ArrayList<Pregunta> conseguirPorUsuario(int usuarioId) {
		
		//La clave Integer es el ID de la pregunta
		HashMap<Integer, Pregunta> preguntas = new HashMap<Integer, Pregunta>();
		
		try (
			Connection conexion = ConnectionManager.getConnection();
			PreparedStatement pst = conexion.prepareStatement(SQL_GET_ALL_BY_USER);
			){
			
			pst.setNull(1, java.sql.Types.NULL);
			pst.setInt(1, usuarioId);
			
			LOG.debug(pst);
			try (ResultSet rs = pst.executeQuery()) {
				while( rs.next() ) {
					
					int preguntaId = rs.getInt("pregunta_id"); //clave del hashmap
					Pregunta pregunta = preguntas.get(preguntaId);
					
					//Si la pregunta no existe, crea una nueva y guarda los datos
					if (pregunta == null) {
						
						pregunta = mapper(rs);
					}
					
					//Crea nueva respuesta y guarda los datos
					Respuesta respuesta = new Respuesta();
					
					respuesta.setId(rs.getInt("respuesta_id"));
					respuesta.setNombre(rs.getString("respuesta_nombre"));
					
					if (rs.getInt("esCorrecta") == 1) {
						respuesta.setEsCorrecta(false);
					} else {
						respuesta.setEsCorrecta(true);
					}
					
					respuesta.setNum_respuesta(rs.getInt("num_respuesta"));
					
					//Agrega respuesta a la pregunta
					//Valor del hashmap
					pregunta.getRespuestas().add(respuesta);
					
					//Guarda la pregunta en el hashmap
					preguntas.put(preguntaId, pregunta);
				}//while
			}//try2
		} catch (Exception e) {
			
			LOG.error(e);
		}//try1
		//Devuelve un arraylist de preguntas, cada una con su lista de respuestas
		return new ArrayList<Pregunta>(preguntas.values());
	}
	
	@Override
	public ArrayList<Pregunta> conseguirPorUsuario(int usuarioId, boolean estaAprobada) {
		
		//La clave Integer es el ID de la pregunta
		HashMap<Integer, Pregunta> preguntas = new HashMap<Integer, Pregunta>();

		String sql = (estaAprobada) ? SQL_GET_BY_USER_APPROVED : SQL_GET_BY_USER_NON_APPROVED;
		
		try (
			Connection conexion = ConnectionManager.getConnection();
			PreparedStatement pst = conexion.prepareStatement(sql);
			){
			
			pst.setNull(1, java.sql.Types.NULL);
			pst.setInt(1, usuarioId);
			
			LOG.debug(pst);
			try (ResultSet rs = pst.executeQuery()) {
				while( rs.next() ) {
					
					int preguntaId = rs.getInt("pregunta_id"); //clave del hashmap
					Pregunta pregunta = preguntas.get(preguntaId);
					
					//Si la pregunta no existe, crea una nueva y guarda los datos
					if (pregunta == null) {
						
						pregunta = mapper(rs);
					}
					
					//Crea nueva respuesta y guarda los datos
					Respuesta respuesta = new Respuesta();
					
					respuesta.setId(rs.getInt("respuesta_id"));
					respuesta.setNombre(rs.getString("respuesta_nombre"));
					
					if (rs.getInt("esCorrecta") == 1) {
						respuesta.setEsCorrecta(false);
					} else {
						respuesta.setEsCorrecta(true);
					}
					
					respuesta.setNum_respuesta(rs.getInt("num_respuesta"));
					
					//Agrega respuesta a la pregunta
					//Valor del hashmap
					pregunta.getRespuestas().add(respuesta);
					
					//Guarda la pregunta en el hashmap
					preguntas.put(preguntaId, pregunta);
				}//while
			}//try2
		} catch (Exception e) {
			
			LOG.error(e);
		}//try1
		//Devuelve un arraylist de preguntas, cada una con su lista de respuestas
		return new ArrayList<Pregunta>(preguntas.values());
	}

	
	@Override
	public ContadorPreguntas contar() {
		
		ContadorPreguntas contador = new ContadorPreguntas();
		
		try(
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_COUNT_ALL);
			){
			
			try( ResultSet rs = pst.executeQuery() ){
				if (rs.next()) {
					contador.setTotal(rs.getInt("total"));
					contador.setAprobadas(rs.getInt("aprobadas"));
					contador.setPendientes(rs.getInt("pendientes"));
				}//if
		}//try2
		} catch (Exception e) {
			LOG.error(e);
		}//try1
		
		return contador;
	}
	
	
	@Override
	public ContadorPreguntas contarPorUsuario(int usuarioId) {
		
		ContadorPreguntas contador = new ContadorPreguntas();
		
		try(
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_COUNT_BY_USER);
			){
			
			pst.setInt(1, usuarioId);
			LOG.debug(pst);
			
			try( ResultSet rs = pst.executeQuery() ){
				if (rs.next()) {
					contador.setUsuarioId(usuarioId);
					contador.setTotal(rs.getInt("total"));
					contador.setAprobadas(rs.getInt("aprobadas"));
					contador.setPendientes(rs.getInt("pendientes"));
				}//if
			}//try2
		}catch (Exception e) {
			LOG.error(e);
		}//try1
		
		return contador;
	}

	
	@Override
	public Pregunta crear(Pregunta pregunta) throws Exception {
		
		if (pregunta.getNombre() == null) {
			throw new Exception("No se ha escrito una pregunta");
		}
		
		try ( 
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_CREATE_QUESTION, PreparedStatement.RETURN_GENERATED_KEYS);
			){
			
			pst.setString(1, pregunta.getNombre());
			pst.setInt(2, pregunta.getDificultad());
			pst.setInt(3, pregunta.getTiempo());
			pst.setString(4, pregunta.getComentario());
			pst.setString(5, pregunta.getImagen());
			pst.setInt(6, pregunta.getUsuario().getId());
			pst.setInt(7, pregunta.getCategoria().getId());
			
			int affectedRows = pst.executeUpdate();
			
			LOG.debug(pst);
			if (affectedRows == 1) {
				
				//conseguir el ID
				try ( ResultSet rsKeys = pst.getGeneratedKeys(); ) {
					
					if (rsKeys.next()) {

						pregunta.setId(rsKeys.getInt(1));
					}
				}//try2
			} else {

				throw new Exception ("No se ha podido agregar " + pregunta.getNombre());
			}//if
			
			try ( 
				Connection connection2 = ConnectionManager.getConnection();
				PreparedStatement pst2 = connection.prepareStatement(SQL_CREATE_ANSWER, PreparedStatement.RETURN_GENERATED_KEYS);
				){
				
				for (int i = 0; i < pregunta.getRespuestas().size(); i++) {
					
					pst2.setString(1, pregunta.getRespuestas().get(i).getNombre());
					pst2.setInt(3, pregunta.getId());
					pst2.setInt(4, pregunta.getRespuestas().get(i).getNum_respuesta());
					
					if (pregunta.getRespuestas().get(i).isEsCorrecta() == false) {
						pst2.setInt(2, 1);
					} else {
						pst2.setInt(2, 2);
					}
					
					int affectedRows2 = pst2.executeUpdate();
					
					LOG.debug(pst2);
					if (affectedRows2 == 1) {
						
						//conseguir el ID
						try ( ResultSet rsKeys = pst2.getGeneratedKeys(); ) {
							
							if (rsKeys.next()) {

								pregunta.getRespuestas().get(i).setId(rsKeys.getInt(1));
							}
						}//try4
					} else {

						throw new Exception ("No se ha podido agregar " + pregunta.getRespuestas().get(i).getNombre());
					}//if
				}//for
			}//try3
		} catch (SQLException e) {
			
			throw new SQLException("Ha ocurrido un error. Es posible que la pregunta ya exista o una respuesta este duplicada");
		}//try1
		
		return pregunta;
	}

	
	@Override
	public Pregunta actualizar(Pregunta pregunta, int usuarioId) throws Exception, SecurityException {
		
		if (pregunta.getNombre() == null) {
			throw new Exception("No se ha escrito una pregunta");
		}
		
		if (pregunta.getFecha_aprobada() != "") {
			throw new Exception("La pregunta ha sido aprobada. No se puede modificar");
		}
		
		try ( 
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUESTION, PreparedStatement.RETURN_GENERATED_KEYS);
			){
			
			pst.setString(1, pregunta.getNombre());
			pst.setInt(2, pregunta.getDificultad());
			pst.setInt(3, pregunta.getTiempo());
			pst.setString(4, pregunta.getComentario());
			pst.setString(5, pregunta.getImagen());
			pst.setInt(6, pregunta.getUsuario().getId());
			pst.setInt(7, pregunta.getCategoria().getId());
			pst.setInt(8, pregunta.getId());
			
			int affectedRows = pst.executeUpdate();
			
			LOG.debug(pst);
			if (affectedRows == 1) {
				
				//conseguir el ID
				try ( ResultSet rsKeys = pst.getGeneratedKeys(); ) {
					
					if (rsKeys.next()) {

						pregunta.setId(rsKeys.getInt(1));
					}
				}//try2
			} else {

				throw new Exception ("No se ha podido actualizar " + pregunta.getNombre());
			}//if
			
			try ( 
				Connection connection2 = ConnectionManager.getConnection();
				PreparedStatement pst2 = connection.prepareStatement(SQL_UPDATE_ANSWER, PreparedStatement.RETURN_GENERATED_KEYS);
				){
				
				for (int i = 0; i < pregunta.getRespuestas().size(); i++) {
					
					pst2.setString(1, pregunta.getRespuestas().get(i).getNombre());
					pst2.setInt(3, pregunta.getId());
					pst2.setInt(4, pregunta.getRespuestas().get(i).getNum_respuesta());
					pst2.setInt(5, pregunta.getRespuestas().get(i).getId());
					
					if (pregunta.getRespuestas().get(i).isEsCorrecta() == false) {
						pst2.setInt(2, 1);
					} else {
						pst2.setInt(2, 2);
					}
					
					int affectedRows2 = pst2.executeUpdate();
					
					LOG.debug(pst2);
					if (affectedRows2 == 1) {
						
						//conseguir el ID
						try ( ResultSet rsKeys = pst2.getGeneratedKeys(); ) {
							
							if (rsKeys.next()) {

								pregunta.setId(rsKeys.getInt(1));
							}
						}//try4
					} else {

						throw new Exception ("No se ha podido actualizar " + pregunta.getNombre());
					}//if
				}//for
			}//try3
		} catch (SQLException e) {
			
			throw new SQLException("Ha ocurrido un error. Es posible que la pregunta ya exista");
		}//try1
		
		return pregunta;
	}
	
	
	@Override
	public Pregunta actualizarPorAdmin(Pregunta pregunta) throws Exception {
		
		if (pregunta.getNombre() == null) {
			throw new Exception("No se ha escrito una pregunta");
		}
		
		try ( 
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUESTION, PreparedStatement.RETURN_GENERATED_KEYS);
			){
			
			pst.setString(1, pregunta.getNombre());
			pst.setInt(2, pregunta.getDificultad());
			pst.setInt(3, pregunta.getTiempo());
			pst.setString(4, pregunta.getComentario());
			pst.setString(5, pregunta.getImagen());
			pst.setInt(6, pregunta.getUsuario().getId());
			pst.setInt(7, pregunta.getCategoria().getId());
			pst.setInt(8, pregunta.getId());
			
			int affectedRows = pst.executeUpdate();
			
			LOG.debug(pst);
			if (affectedRows == 1) {
				
				//conseguir el ID
				try ( ResultSet rsKeys = pst.getGeneratedKeys(); ) {
					
					if (rsKeys.next()) {

						pregunta.setId(rsKeys.getInt(1));
					}
				}//try2
			} else {

				throw new Exception ("No se ha podido actualizar " + pregunta.getNombre());
			}//if
			
			try ( 
				Connection connection2 = ConnectionManager.getConnection();
				PreparedStatement pst2 = connection.prepareStatement(SQL_UPDATE_ANSWER, PreparedStatement.RETURN_GENERATED_KEYS);
				){
				
				for (int i = 0; i < pregunta.getRespuestas().size(); i++) {
					
					pst2.setString(1, pregunta.getRespuestas().get(i).getNombre());
					pst2.setInt(3, pregunta.getId());
					pst2.setInt(4, pregunta.getRespuestas().get(i).getNum_respuesta());
					pst2.setInt(5, pregunta.getRespuestas().get(i).getId());
					
					if (pregunta.getRespuestas().get(i).isEsCorrecta() == false) {
						pst2.setInt(2, 1);
					} else {
						pst2.setInt(2, 2);
					}
					
					int affectedRows2 = pst2.executeUpdate();
					
					LOG.debug(pst2);
					if (affectedRows2 == 1) {
						
						//conseguir el ID
						try ( ResultSet rsKeys = pst2.getGeneratedKeys(); ) {
							
							if (rsKeys.next()) {

								pregunta.setId(rsKeys.getInt(1));
							}
						}//try4
					} else {

						throw new Exception ("No se ha podido actualizar " + pregunta.getRespuestas().get(i));
					}//if
				}//for
			}//try3
		} catch (SQLException e) {
			
			throw new SQLException("Ha ocurrido un error. Es posible que la pregunta ya exista");
		}//try1
		
		return pregunta;
	}
	
	@Override
	public Pregunta borrarPorUsuario(int id, int usuarioId, String fecha_aprobada) throws Exception, SecurityException {
		
		Pregunta pregunta = new Pregunta();
		
		//Este metodo lanza una SecurityException por lo que no hace falta usarla en el try
		pregunta = conseguirPorId(id, usuarioId);
		
		if (pregunta.getFecha_aprobada() == "") {
			
			try (	
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement pst = connection.prepareStatement(SQL_DELETE);
				){
				
				pst.setInt(1, id);
	
				pst.executeUpdate();
				LOG.debug(pst);
			}//try
		} else {
			//El usuario esta intentando borrar una pregunta aprobada
			throw new SecurityException();
		}//if
		
		return pregunta;
	}
	
	
	@Override
	public Pregunta borrarPorAdmin(int id) throws Exception {
		
		Pregunta pregunta = new Pregunta();
		pregunta = conseguirPorId(id);
		
		try (	
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_DELETE);
			){
			
			pst.setInt(1, id);

			pst.executeUpdate();
			LOG.debug(pst);
			
		} catch (Exception e) {
			
			throw new SecurityException();
		}//try
		
		return pregunta;
	}
	

	@Override
	public Pregunta conseguirPorId(int id, int usuarioId) throws Exception, SecurityException {
		
		Pregunta pregunta = new Pregunta();
		
		ArrayList<Respuesta> respuestas = new ArrayList<Respuesta>();

		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_GET_QUESTION_BY_ID);
			){

			pst.setInt(1, id);
			pst.setInt(2, usuarioId);
			
			try ( ResultSet rs = pst.executeQuery() ){
				
				LOG.debug(pst);
				while (rs.next()) {

					pregunta = mapper(rs);
					
					Respuesta respuesta = new Respuesta();
					respuesta.setId(rs.getInt("respuesta_id"));
					respuesta.setNombre(rs.getString("respuesta_nombre"));
					respuestas.add(respuesta);
				}
				
				pregunta.setRespuestas(respuestas);
				
			} catch (Exception e) {

				throw new SecurityException();
			}//try2
		} //try1
		
		return pregunta;
	}
	
	
	@Override
	public Pregunta conseguirPorId(int id) throws Exception {
		
		Pregunta pregunta = new Pregunta();
		
		ArrayList<Respuesta> respuestas = new ArrayList<Respuesta>();

		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_GET_ONE_QUESTION_BY_ID);
			){

			pst.setInt(1, id);
			
			try ( ResultSet rs = pst.executeQuery() ){
				
				LOG.debug(pst);
				while (rs.next()) {

					pregunta = mapper(rs);
					
					Respuesta respuesta = new Respuesta();
					respuesta.setNombre(rs.getString("respuesta_nombre"));
					
					if (rs.getInt("esCorrecta") == 1) {
						respuesta.setEsCorrecta(false);
					} else {
						respuesta.setEsCorrecta(true);
					}
					
					respuestas.add(respuesta);
				}
				
				pregunta.setRespuestas(respuestas);
				
			} catch (Exception e) {

				throw new Exception();
			}//try2
		} //try1
		
		return pregunta;
	}
	
	
	@Override
	public void validar(int id) throws Exception {
		
		try ( 
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement pst = connection.prepareStatement(SQL_VALIDATE);
			){
			
			pst.setInt(1, id);
			
			int affectedRows = pst.executeUpdate();
			
			LOG.debug(pst);
			if (affectedRows != 1) {
				
				throw new Exception ("No se pudo validar la pregunta con ID: " + id);
			}
			
		} catch (SQLException e) {
			
			LOG.error(e);
			throw new SQLException("Ha habido un error");
		}
	}
	
	
	private Pregunta mapper( ResultSet rs ) throws SQLException {
		
		Pregunta p = new Pregunta();
		Categoria c = new Categoria();
		
		p.setId(rs.getInt("pregunta_id"));
		p.setNombre(rs.getString("pregunta_nombre"));
		p.setDificultad(rs.getInt("dificultad"));
		p.setTiempo(rs.getInt("tiempo"));
		p.setComentario(rs.getString("comentario"));
		p.setImagen(rs.getString("imagen"));
		p.setUsuario_id(rs.getInt("usuario_id"));
		
		if (rs.getString("fecha_aprobada") != "") {
			p.setFecha_aprobada(rs.getString("fecha_aprobada"));
		}
		
		c.setId(rs.getInt("categoria_id"));
		c.setNombre(rs.getString("categoria_nombre"));
		p.setCategoria(c);
		
		return p;
	}

	
	
	@Override
	public Pregunta borrar(int id, int usuarioId) throws Exception, SecurityException {
		return null;
	}
}
