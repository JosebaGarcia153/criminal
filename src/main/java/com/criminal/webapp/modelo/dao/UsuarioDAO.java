package com.criminal.webapp.modelo.dao;

import com.criminal.webapp.modelo.pojo.Usuario;

public interface UsuarioDAO {
	/**
	 * Comprueba si un usuario existe en la BBDD
	 * @param nombre Nombre del usuario
	 * @param password Contraseña del usuario
	 * @return usuario encontrado
	 * @see com.criminal.webapp.modelo.dao.impl.UsuarioDAOImpl
	 */
	Usuario existe (String nombre, String password);
	
	/**
	 * Crea un usuario en la BBDD
	 * @param usuario objeto creado en un controlador
	 * @return usuario generado
	 * @throws Exception Si el usuario ya existe o los datos son incorrectos
	 * @see com.criminal.webapp.modelo.dao.impl.UsuarioDAOImpl
	 */
	Usuario crear(Usuario usuario) throws Exception;
	
	/**
	 * Busca el nombre exacto de un usuario en la BBDD
	 * @param nombre Nombre a buscar
	 * @return true si encuentra al usuario, false si no lo encuentra
	 * @see com.criminal.webapp.modelo.dao.impl.UsuarioDAOImpl
	 */
	boolean buscarPorNombre(String nombre);
}
