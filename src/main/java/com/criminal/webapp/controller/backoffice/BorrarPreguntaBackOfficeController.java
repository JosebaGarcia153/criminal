package com.criminal.webapp.controller.backoffice;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl;
import com.criminal.webapp.controller.Alert;
import com.criminal.webapp.modelo.pojo.Pregunta;

/**
* Controlador para usuarios comunes para borrar preguntas de la base de datos que aún no han sido aprobadas.
* El metodo GET se encarga de indicar a la implementación DAO cual es la ID de la pregunta a borrar.
* @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
*/
@WebServlet("/views/backoffice/borrar")
public class BorrarPreguntaBackOfficeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(BorrarPreguntaBackOfficeController.class);
	private static final PreguntaDAOImpl  dao = PreguntaDAOImpl.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Alert alert = new Alert();
		
		//Recoger parametros
		String idParam = request.getParameter("id");
		
		LOG.trace("ID de la pregunta a borrar: " + idParam);
		
		try {

			int id = Integer.parseInt(idParam);
			
			//Borra la pregunta si no ha sido aprobada aún
			Pregunta pregunta = dao.borrarPorAdmin(id);
			alert = new Alert ("success" , pregunta.getNombre() + " borrado");
				
		} catch (Exception e) {	
			LOG.error(e);
			
		} finally {
			
			//Volver al panel del usuario
			String url = "inicio";
			LOG.debug("forward: " + url);
			session.setAttribute("alert", alert);
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
