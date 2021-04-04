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
}
