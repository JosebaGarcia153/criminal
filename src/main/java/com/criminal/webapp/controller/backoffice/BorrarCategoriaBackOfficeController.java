package com.criminal.webapp.controller.backoffice;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.criminal.webapp.modelo.dao.impl.CategoriaDAOImpl;
import com.criminal.webapp.modelo.pojo.Categoria;
import com.criminal.webapp.controller.Alert;


/**
* Controlador para usuarios comunes para borrar categorias de la base de datos.
* El metodo GET se encarga de indicar a la implementación DAO cual es la ID de la categoria a borrar.
* @see com.criminal.webapp.modelo.dao.impl.CategoriaDAOImpl
*/
@WebServlet("/views/backoffice/borrar-categoria")
public class BorrarCategoriaBackOfficeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(BorrarCategoriaBackOfficeController.class);
	private static final CategoriaDAOImpl  daoC = CategoriaDAOImpl.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Alert alert = new Alert();
		
		//Recoger parametros
		String idParam = request.getParameter("id");
		
		LOG.trace("ID de la categoria a borrar: " + idParam);
		
		try {

			int id = Integer.parseInt(idParam);
			
			//Borra la categoria si no ha sido aprobada aún
			Categoria categoria = daoC.borrar(id);
			alert = new Alert ("success" , categoria.getNombre() + " borrada");
				
		} catch (Exception e) {	
			LOG.error(e);
			
		} finally {
			
			//Volver a la lista de categorias
			String url = "mostrar-categorias";
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
