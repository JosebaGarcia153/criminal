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
import com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl;
import com.criminal.webapp.modelo.pojo.Categoria;
import com.criminal.webapp.modelo.pojo.Pregunta;

/**
 * Controlador para mostrar categorias en el BackOffice.
 * El metodo GET se encarga de mostrar todas las preguntas ordenadas por la categoria seleccionada
 * @see com.criminal.webapp.modelo.dao.impl.CategoriaDAOImpl
 */
@WebServlet("/views/backoffice/mostrar-por-categorias")
public class CategoriasBackOfficeController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(CategoriasBackOfficeController.class);
	
	private static final PreguntaDAOImpl dao = PreguntaDAOImpl.getInstance();
	private static final CategoriaDAOImpl daoC = CategoriaDAOImpl.getInstance();
	
	private static final String TODAS_CATEGORIAS = "-1";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();
		ArrayList<Categoria> lista_categorias = new ArrayList<Categoria>();
		Pregunta pregunta = new Pregunta();
		
		//Recoger parametros
		String categoriaIdParam = request.getParameter("categoriaId");
		String preguntaIdParam = request.getParameter("preguntaId");
		String categoriaNombreParam = ( request.getParameter("categoriaNombre") == null ) ? "Todas las categorias" : request.getParameter("categoriaNombre");
		
		String titulo = "";
		
		try {
			
			//Poder mostrar siempre las posibles categorias a elegir
			lista_categorias = daoC.conseguirTodas();
		
			//Si se le dice que muestre todas las categorias
			if (TODAS_CATEGORIAS.equals(categoriaIdParam)) {
				
				//Recoge la lista de preguntas por categorias
				categorias = daoC.conseguirTodasConPreguntasPorUsuario();
				
				titulo = "Todas las preguntas por categoria";
			
			//Si se le pide los detalles de una pregunta	
			} else if (preguntaIdParam != null) {
				
				int preguntaId = Integer.parseInt(preguntaIdParam);
				//Recoge los datos de la pregunta
				pregunta = dao.conseguirPorId(preguntaId);
				
				titulo = "Pregunta detallada";
				
			//Si se le pide una categoria especifica	
			} else {
				
				int categoriaId = Integer.parseInt(categoriaIdParam);
				
				//Recoge la lista de preguntas por la categoria indicada
				preguntas = dao.conseguirPorCategoriaPorUsuario(categoriaId);
				
				titulo = "<b>" + preguntas.size() + "</b> preguntas para <b>" + categoriaNombreParam + "</b>";
			}
		} catch (Exception e) {

			LOG.error(e);
			
		} finally {
			
			//Volver a la pagina
			request.setAttribute("preguntas", preguntas);
			request.setAttribute("pregunta", pregunta);
			request.setAttribute("categorias", categorias);
			request.setAttribute("lista_categorias", lista_categorias);
			
			request.setAttribute("titulo", titulo);
			
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
