package com.criminal.webapp.modelo.dao.impl;

import org.apache.log4j.Logger;

import com.criminal.webapp.modelo.dao.UsuarioDAO;
import com.criminal.webapp.modelo.pojo.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {
	
	private static final Logger LOG = Logger.getLogger(UsuarioDAOImpl.class);
	private static UsuarioDAOImpl instance = null;
	
	private UsuarioDAOImpl() {
		super();	
	}
	
	public static synchronized UsuarioDAOImpl getInstance() {
		
		if (instance == null) {
			
			instance = new UsuarioDAOImpl();
		}	
		return instance;
	}
	
	@Override
	public Usuario existe(String nombre, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario crear(Usuario usuario) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean buscarPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return false;
	}

}
