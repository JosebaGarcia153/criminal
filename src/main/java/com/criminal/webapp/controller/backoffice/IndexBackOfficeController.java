package com.criminal.webapp.controller.backoffice;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl;
import com.criminal.webapp.modelo.pojo.Usuario;

/**
 * Controlador para ir al index del BackOffice.
 * El metodo GET se encarga de contar los juegos añadidos por el usuario separados por aprobados, pendientes y el numero total.
 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
 */
@WebServlet("/views/backoffice/inicio")
public class IndexBackOfficeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(IndexBackOfficeController.class);
	private static final PreguntaDAOImpl dao = PreguntaDAOImpl.getInstance();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario sesionUsuario = (Usuario) request.getSession().getAttribute("usuario_login");
		int usuarioId = sesionUsuario.getId();
		
		request.setAttribute("aprobadas", dao.contarPorUsuario(usuarioId).getAprobadas());
		request.setAttribute("pendientes", dao.contarPorUsuario(usuarioId).getPendientes());
		request.setAttribute("total", dao.contarPorUsuario(usuarioId).getTotal());
		
		// CUIDADO: mirar la URL del servlet "/views/backoffice/inicio"
		// Cuando hacemos forward se pierde lo ultimo de la url y se le suma la variable pagina
		//------------------------
		// El forward resuelve la URL de la siguiente manera:
		//	 "/views/backoffice/inicio" + "index.jsp" = "/views/backoffice/index.jsp"
		//------------------------
 		String url = "index.jsp";
		LOG.debug("forward: " + url);
		
		request.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
