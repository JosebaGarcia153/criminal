package com.criminal.webapp.controller.backoffice;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.criminal.webapp.modelo.dao.impl.UsuarioDAOImpl;
import com.criminal.webapp.modelo.pojo.Usuario;

/**
 * Controlador para mostrar usuarios en el BackOffice.
 * El metodo GET se encarga de mostrar todos los usuarios
 * @see com.criminal.webapp.modelo.dao.impl.UsuarioDAOImpl
 */
@WebServlet("/views/backoffice/mostrar-usuarios")
public class TablaUsuariosBackOfficeController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(TablaUsuariosBackOfficeController.class);
	
	private static final UsuarioDAOImpl daoU = UsuarioDAOImpl.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		
		try {
			//Conseguir datos
			usuarios = daoU.conseguirTodos();
			
		} catch (Exception e) {

			LOG.error(e);
			
		} finally {
			
			//Ir a la pagina
			request.setAttribute("usuarios", usuarios);
			
			String url = "usuarios.jsp";
			LOG.debug("forward: " + url);
			request.getRequestDispatcher(url).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
