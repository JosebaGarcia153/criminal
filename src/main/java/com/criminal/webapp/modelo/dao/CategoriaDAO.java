package com.criminal.webapp.modelo.dao;

import java.util.ArrayList;

import com.criminal.webapp.modelo.Crudable;
import com.criminal.webapp.modelo.pojo.Categoria;


public interface CategoriaDAO extends Crudable<Categoria> {
	
	/**
	* Obtiene la lista de categorias
	* @return ArrayList{@code <}Categoria{@code >} devuelve las categorias alfbeticamente
	* @see com.criminal.webapp.modelo.dao.impl.CategoriaDAOImpl
	*/
	public ArrayList<Categoria> conseguirTodas();
	
	/**
	* Obtiene la lista de categorias con sus preguntas
	* @return ArrayList{@code <}Categoria{@code >} devuelve las categorias alfbeticamente
	* @see com.criminal.webapp.modelo.dao.impl.CategoriaDAOImpl
	*/
	public ArrayList<Categoria> conseguirTodasConPreguntas();
	
	/**
	* Obtiene la lista de categorias con sus preguntas. Muesta solo las aprobadas para usuarios normales
	* @return ArrayList{@code <}Categoria{@code >} devuelve las categorias alfbeticamente
	* @see com.criminal.webapp.modelo.dao.impl.CategoriaDAOImpl
	*/
	public ArrayList<Categoria> conseguirTodasConPreguntasPorUsuario();
	
	/**
	 * Crea una categoria de la BBDD por un administrador
	 * @param id ID de la categoria a crear
	 * @return categoria Datos de la categoria creada
	 * @throws Exception Si la categoria ya esta en la BBDD
	 * @see com.criminal.webapp.modelo.dao.impl.CategoriaDAOImpl
	 */
	public Categoria crear(Categoria pojo) throws Exception;
	
	/**
	 * Actualizar una categoria de la BBDD por un administrador
	 * @param id ID de la categoria a actualizar
	 * @return categoria Datos de la categoria actualizda
	 * @throws Exception Si el ID de la categoria no esta en la BBDD
	 * @see com.criminal.webapp.modelo.dao.impl.CategoriaDAOImpl
	 */
	public Categoria actualizar(Categoria pojo) throws Exception;
	
	/**
	 * Elimina una categoria de la BBDD por un administrador
	 * @param id ID de la categoria a borrar
	 * @return categoria Datos de la categoria eliminada
	 * @throws Exception Si el ID de la categoria no esta en la BBDD
	 * @see com.criminal.webapp.modelo.dao.impl.CategoriaDAOImpl
	 */
	public Categoria borrar(int id) throws Exception;
	
	/**
	 * Busca una categoria específica por ID
	 * @param id ID de la categoria a encontrar
	 * @return categoria Datos de la categoria encontrada
	 * @throws Exception Si la ID de la categoria no esta en la BBDD
	 * @see com.criminal.webapp.modelo.dao.impl.CategoriaDAOImpl
	 */
	public Categoria conseguirPorId(int id) throws Exception;
}
