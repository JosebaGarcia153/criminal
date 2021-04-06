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
	

	public Categoria crear(Categoria pojo) throws Exception;
	public Categoria actualizar(Categoria pojo) throws Exception;
	public Categoria borrar(int id) throws Exception;
}
