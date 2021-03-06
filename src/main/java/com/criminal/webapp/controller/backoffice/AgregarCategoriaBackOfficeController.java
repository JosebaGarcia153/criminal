package com.criminal.webapp.controller.backoffice;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.criminal.webapp.modelo.dao.impl.CategoriaDAOImpl;
import com.criminal.webapp.controller.Alert;
import com.criminal.webapp.modelo.pojo.Categoria;


/**
 * Controlador para el formulario de categorias.
 * El metodo GET se encarga de entrar al formulario por primera vez y guardar los datos y seguir mostrándolos en el formulario en el caso de que halla habido algún error al enviarlos.
 * El metodo POST se encarga de recibir los datos del formulario y enviarlos a la implementación DAO donde se realizarán las llamadas SQL.
 * @see com.criminal.webapp.modelo.dao.impl.CategoriaaDAOImpl
 */
@WebServlet("/views/backoffice/agregar-categoria")
@MultipartConfig
public class AgregarCategoriaBackOfficeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(AgregarCategoriaBackOfficeController.class);
	private static final CategoriaDAOImpl daoC = CategoriaDAOImpl.getInstance();
	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private static Validator validator = factory.getValidator();
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Categoria categoria = new Categoria();
		
		try {
			
			//Recoger parametros
			String idParameter = request.getParameter("id");
					
			//Recoger categoria si la ID no esta vacía
			if (idParameter != null) {
				
				int id = Integer.parseInt(idParameter);
				categoria = daoC.conseguirPorId(id);
			}	
			
		} catch (Exception e) {

			LOG.error(e);

		} finally {
			
			//Ir al formulario
			request.setAttribute("categoria", categoria);
			
			String url = "formulariocategorias.jsp";	
			LOG.debug("forward: " + url);
			
			request.getRequestDispatcher(url).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String titulo = "";
		String url = "";

		Categoria categoria = new Categoria();
		
		Alert alert = new Alert();
		
		//Recoger parametros del formulario
		String idParam = request.getParameter("id");
		String nombre = request.getParameter("nombre");

		
		try {
			
			int id = Integer.parseInt(idParam);
			
			//Crear objeto con esos parametros			
			categoria.setId(id);
			categoria.setNombre(nombre);
			
				
			//Validar pojos
			Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);
			
			if ( violations.isEmpty() ) {
				
				if (id == 0) {
					
					daoC.crear(categoria);
					alert = new Alert ("success", "Categoria añadida");
					request.setAttribute("titulo", titulo);
					url = "inicio";
				
				} else {
					
					daoC.actualizar(categoria);
					alert = new Alert ("success", "Categoria actualizada");
					request.setAttribute("titulo", titulo);
					url = "formulariocategorias.jsp";
				}
				
			} else {
				
				String errors = "";
				
				for (ConstraintViolation<Categoria> v : violations) {	
					errors += "<p><b>" + v.getPropertyPath() + "</b>: "  + v.getMessage() + "</p>";		
				}
				alert = new Alert("warning", errors);
				url = "formulariocategorias.jsp";
			}//if

		} catch (Exception e) {
			
			LOG.error(e);
			alert = new Alert("warning", "Una categoria identica ya esta registrada");
			url = "formulariocategorias.jsp";
		
		} finally {
			
			//Volver al formulario
			request.setAttribute("alert", alert);
			request.setAttribute("categoria", categoria);
			request.setAttribute("titulo", titulo);
			
			
			LOG.debug("forward: " + url);
			
			request.getRequestDispatcher(url).forward(request, response);
		}//try
	}//dopost
}
