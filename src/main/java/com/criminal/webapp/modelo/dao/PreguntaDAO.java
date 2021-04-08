package com.criminal.webapp.modelo.dao;

import java.util.ArrayList;

import com.criminal.webapp.modelo.Crudable;
import com.criminal.webapp.modelo.pojo.ContadorPreguntas;
import com.criminal.webapp.modelo.pojo.Pregunta;
import com.criminal.webapp.modelo.dao.SecurityException;

public interface PreguntaDAO extends Crudable<Pregunta> {
	
	/**
	 * Busca todos las pregunas ingresadas en la BBDD
	 * @return preguntas Todas las entradas
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	ArrayList<Pregunta> conseguirTodas();
	
	/**
	 * Busca todos las pregunas aprobadas ingresadas en la BBDD
	 * @return preguntas Todas las entradas
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	ArrayList<Pregunta> conseguirTodasAprobadas();
	
	/**
	 * Busca todos las preguntas de una categoria
	 * @param categoriaId ID de la categoria a mostrar
	 * @return preguntas Entradas encontradas
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	ArrayList<Pregunta> conseguirPorCategoria (int categoriaId);
	
	/**
	 * Busca todos las preguntas aprobadas de una categoria a usuarios normales
	 * @param categoriaId ID de la categoria a mostrar
	 * @return preguntas Entradas encontradas
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	ArrayList<Pregunta> conseguirPorCategoriaPorUsuario (int categoriaId);
	
	/**
	 * Busca las preguntas agregadas a la BBDD por un usuario especifico
	 * @param usuarioId Usuario que ha ingresado las preguntas
	 * @return preguntas Entradas encontradas
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	ArrayList<Pregunta> conseguirPorUsuario(int usuarioId);
	
	/**
	 * Busca las preguntas agregadas a la BBDD por un usuario especifico en base a si han sido aprobadas
	 * @param usuarioId Usuario que ha ingresado las preguntas
	 * @param estaAprobada confirma si la pregunta ha sido aprobada
	 * @return preguntas Entradas encontradas
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	ArrayList<Pregunta> conseguirPorUsuario(int usuarioId, boolean estaAprobada);
			
	/**
	 * Cuenta cuantas preguntas hay subidas en total
	 * @return numero Objeto con datos contados
	 * @see ContadorPreguntas com.criminal.webapp.modelo.pojo.ContadorPreguntas
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	ContadorPreguntas contar();
	
	/**
	 * Cuenta cuantos preguntas hay subidas por el usuario
	 * @param usuarioId Usuario al que pertenecen las preguntas
	 * @return numero Objeto con datos contados
	 * @see ContadorPreguntas com.criminal.webapp.modelo.pojo.ContadorPreguntas
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	ContadorPreguntas contarPorUsuario(int usuarioId);
	
	/**
	 * Agrega una pregunta a la BBDD
	 * @param pregunta datos de la pregunta a agregar
	 * @return pregunta datos agregados
	 * @throws Exception si la pregunta ya esta en la BBDD
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	Pregunta crear(Pregunta pregunta) throws Exception;
	
	/**
	 * Edita una pregunta de la BBDD despues de comprobar que pertenezca al usuario intentando editarla
	 * @param pregunta datos que modificar a la pregunta
	 * @param usuarioId Usuario al que pertenece la pregunta
	 * @return pregunta Datos de al pregunta modificada
	 * @throws Exception Si la ID de la pregunta no esta en la BBDD
	 * @throws SecurityException Si no puede eliminar la pregunta porque no pertenece al usuario
	 * @see com.criminal.webapp.modelo.dao.SecurityException
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	Pregunta actualizar(Pregunta pregunta, int usuarioId) throws Exception, SecurityException;
	
	/**
	 * Edita una pregunta de la BBDD por un administrador
	 * @param pregunta datos que modificar a la pregunta
	 * @return pregunta Datos de al pregunta modificada
	 * @throws Exception Si la ID de la pregunta no esta en la BBDD
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	Pregunta actualizarPorAdmin(Pregunta pregunta) throws Exception;
	
	/**
	 * Elimina una pregunta de la BBDD despues de comprobar que pertenezca al usuario intentando borrarla y no este aprobada
	 * @param id ID de la pregunta a borrar
	 * @param usuarioId Usuario al que pertenece la pregunta
	 * @param fecha_aprobada mira si ha sido aprobada para evitar borrarla por un usuario normal
	 * @return preguntas Datos de la pregunta eliminada
	 * @throws Exception Si la ID de la pregunta no esta en la BBDD
	 * @throws SecurityException Si no puede eliminar la pregunta porque no pertenece al usuario
	 * @see com.criminal.webapp.modelo.dao.SecurityException
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	Pregunta borrarPorUsuario(int id, int usuarioId, String fecha_aprobada) throws Exception, SecurityException;
	
	/**
	 * Elimina una pregunta de la BBDD por un administrador
	 * @param id ID de la pregunta a borrar
	 * @return preguntas Datos de la pregunta eliminada
	 * @throws Exception Si la ID de la pregunta no esta en la BBDD
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	Pregunta borrarPorAdmin(int id) throws Exception;
	
	/**
	 * Busca todos las preguntas agregadas a la BBDD por un usuario especifico
	 * @param id ID de la pregunta a encontrar
	 * @return pregunta Datos de la pregunta encontrada
	 * @throws Exception Si la ID de la pregunta no esta en la BBDD
	 * @throws SecurityException Si no puede eliminar la pregunta porque no pertenece al usuario
	 * @see com.criminal.webapp.modelo.dao.SecurityException
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	Pregunta conseguirPorId(int id, int usuarioId) throws Exception, SecurityException;
	
	/**
	 * Busca una pregunta específica para expandirla en la lista de preguntas por categoria
	 * @param id ID de la pregunta a encontrar
	 * @return pregunta Datos de la pregunta encontrada
	 * @throws Exception Si la ID de la pregunta no esta en la BBDD
	 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
	 */
	public Pregunta conseguirPorId(int id) throws Exception;
	
	/**
	 * Un administrador valida una pregunta en la BBDD
	 * @param id ID de la pregunta a validar
	 * @throws Exception Si la ID de la pregunta no esta en la BBDD
	 * @see com.games.webapp.modelo.dao.impl.GameDAOImpl
	 */
	public void validar(int id) throws Exception;
}
