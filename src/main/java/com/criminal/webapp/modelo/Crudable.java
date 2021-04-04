package com.criminal.webapp.modelo;

import java.util.ArrayList;

import com.criminal.webapp.modelo.dao.SecurityException;

public interface Crudable<P> {
		
	ArrayList<P> conseguirTodas();

	P crear(P pojo) throws Exception;
	P actualizar(P pojo, int usuarioId) throws Exception, SecurityException;
	P borrar(int id, int usuarioId) throws Exception, SecurityException;	
}
