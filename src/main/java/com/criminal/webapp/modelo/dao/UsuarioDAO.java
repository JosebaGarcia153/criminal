package com.criminal.webapp.modelo.dao;

import java.util.ArrayList;

import com.criminal.webapp.modelo.pojo.Usuario;

public interface UsuarioDAO {
	
	/**
	* Obtiene la lista de usuarios
	* @return ArrayList{@code <}Usuario{@code >} devuelve las usuarios alfbeticamente
	* @see com.criminal.webapp.modelo.dao.impl.UsuarioDAOImpl
	*/
	public ArrayList<Usuario> conseguirTodos();
	
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
	 * Actualizar el nombre o rol de un usuario de la BBDD por un administrador
	 * @param id ID del usuario a actualizar
	 * @return categoria Datos del usuario actualizdo
	 * @throws Exception Si el ID del usuario no esta en la BBDD
	 * @see com.criminal.webapp.modelo.dao.impl.UsuarioDAOImpl
	 * @see com.criminal.webapp.modelo.pojo.Rol
	 */
	public Usuario actualizar(Usuario usuario) throws Exception;
	
	/**
	 * Elimina un usuario de la BBDD por un administrador
	 * @param id ID de la categoria a borrar
	 * @return categoria Datos del usuario categoria eliminado
	 * @throws Exception Si el ID ddel usuario no esta en la BBDD
	 * @see com.criminal.webapp.modelo.dao.impl.UsuarioDAOImpl
	 */
	public Usuario borrar(int id) throws Exception;
	
	/**
	 * Busca el nombre exacto de un usuario en la BBDD
	 * @param nombre Nombre a buscar
	 * @return true si encuentra al usuario, false si no lo encuentra
	 * @see com.criminal.webapp.modelo.dao.impl.UsuarioDAOImpl
	 */
	boolean buscarPorNombre(String nombre);
	
	/**
	 * Busca un usuario específico por ID
	 * @param id ID de un usuario a encontrar
	 * @return categoria Datos del usuario encontrada
	 * @throws Exception Si la ID del usuario no esta en la BBDD
	 * @see com.criminal.webapp.modelo.dao.impl.UsuarioDAOImpl
	 */
	public Usuario conseguirPorId(int id) throws Exception;
}
