package com.criminal.webapp.controller.frontoffice;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl;
import com.criminal.webapp.modelo.pojo.Pregunta;
import com.criminal.webapp.modelo.pojo.Usuario;

/**
 * Controlador para usuarios comunes para mostrar preguntas.
 * El metodo GET se encarga de pedir a la implementación DAO la lista de preguntas pendientes de aprobar, aprobadas o todas dependiendo de la petición hecha.
 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
 */
@WebServlet("/views/frontoffice/preguntas")
public class PreguntasFrontOfficeController extends HttpServlet {
		private static final long serialVersionUID = 1L;
		
		private static final Logger LOG = Logger.getLogger(PreguntasFrontOfficeController.class);
		private static final PreguntaDAOImpl dao = PreguntaDAOImpl.getInstance();
		
		
		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			//Recoger parametros
			String aprobadas = request.getParameter("aprobadas");
			String total = request.getParameter("total");
			String titulo = "";
			
			ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
			
			try {
				
				//Recuperar usuario de session
				Usuario sesionUsuario = (Usuario) request.getSession().getAttribute("usuario_login");
				int usuarioId = sesionUsuario.getId();
				
				//if para decidir que datos mostrar
				if (total == null) {
					
					if (aprobadas == null) {
						
						titulo = "Preguntas Aprobadas";
						preguntas = dao.conseguirPorUsuario(usuarioId, true);
						
					} else {
						
						titulo = "Preguntas Pendientes de Aprobar";
						preguntas = dao.conseguirPorUsuario(usuarioId, false);
					}
				
				} else if (total.equals("1")) {
					
					titulo = "Todas las Preguntas";
					preguntas = dao.conseguirTodasAprobadas();
					
				}else {
					
					titulo = "Todas tus Preguntas";
					preguntas = dao.conseguirPorUsuario(usuarioId);
				}//if
				
			} catch (Exception e) {
				
				LOG.error(e);
				
			} finally {
				
				//Ir a la pagina donde muestra los resultados
				request.setAttribute("preguntas", preguntas);
				request.setAttribute("titulo", titulo);
				
				String url = "preguntas.jsp";
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
