package com.criminal.webapp.controller.backoffice;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.criminal.webapp.modelo.dao.impl.CategoriaDAOImpl;
import com.criminal.webapp.modelo.pojo.Categoria;

/**
 * Controlador para mostrar categorias en el BackOffice.
 * El metodo GET se encarga de mostrar todas las categorias
 * @see com.criminal.webapp.modelo.dao.impl.CategoriaDAOImpl
 */
@WebServlet("/views/backoffice/mostrar-categorias")
public class TablaCategoriasBackOfficeController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(TablaCategoriasBackOfficeController.class);
	
	private static final CategoriaDAOImpl daoC = CategoriaDAOImpl.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();
		
		try {
			//Conseguir datos
			categorias = daoC.conseguirTodas();
			
		} catch (Exception e) {

			LOG.error(e);
			
		} finally {
			
			//Ir a la pagina
			request.setAttribute("categorias", categorias);
			
			String url = "categorias.jsp";
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
