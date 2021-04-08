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

import com.criminal.webapp.modelo.dao.impl.UsuarioDAOImpl;
import com.criminal.webapp.controller.Alert;
import com.criminal.webapp.modelo.pojo.Usuario;


/**
 * Controlador para usuarios comunes para el formulario de juegos.
 * El metodo GET se encarga de entrar al formulario por primera vez y guardar los datos y seguir mostrándolos en el formulario en el caso de que halla habido algún error al enviarlos.
 * El metodo POST se encarga de recibir los datos del formularios y enviarlos a la implementación DAO donde se realizarán las llamadas SQL.
 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
 */
@WebServlet("/views/backoffice/editar-usuario")
@MultipartConfig
public class EditarUsuarioBackOfficeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(EditarUsuarioBackOfficeController.class);
	private static final UsuarioDAOImpl daoU = UsuarioDAOImpl.getInstance();
	
	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private static Validator validator = factory.getValidator();
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario usuario = new Usuario();
		
		try {
			
			//Recoger parametros
			String idParameter = request.getParameter("id");
					
			//Recoger usuario si la ID no esta vacía
			if (idParameter != null) {
				
				int id = Integer.parseInt(idParameter);
				usuario = daoU.conseguirPorId(id);
			}	
			
		} catch (Exception e) {

			LOG.error(e);

		} finally {
			
			//Ir al formulario
			request.setAttribute("usuario", usuario);
			
			String url = "formulariousuarios.jsp";	
			LOG.debug("forward: " + url);
			
			request.getRequestDispatcher(url).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String titulo = "";

		Usuario usuario = new Usuario();
		
		Alert alert = new Alert();
		
		//Recoger parametros del formulario
		String idParam = request.getParameter("id");
		String nombre = request.getParameter("nombre");
		String password = request.getParameter("password");
		String tipoParam = request.getParameter("tipo");

		
		try {
			
			int id = Integer.parseInt(idParam);
			int tipo = Integer.parseInt(tipoParam);
			
			//Crear objeto con esos parametros			
			usuario.setId(id);
			usuario.setNombre(nombre);
			usuario.setPassword(password);
			usuario.getRol().setId(tipo);
			
				
			//Validar pojos
			Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
			
			if ( violations.isEmpty() ) {			
					
				daoU.actualizar(usuario);
				alert = new Alert ("success", "Usuario actualizado");
				request.setAttribute("titulo", titulo);
				
			} else {
				
				String errors = "";
				
				for (ConstraintViolation<Usuario> v : violations) {	
					errors += "<p><b>" + v.getPropertyPath() + "</b>: "  + v.getMessage() + "</p>";		
				}
				alert = new Alert("warning", errors);
			}//if

		} catch (Exception e) {
			
			LOG.error(e);
			alert = new Alert("warning", "Un usuario identico ya esta registrado");
		
		} finally {
			
			//Volver al formulario
			request.setAttribute("alert", alert);
			request.setAttribute("usuario", usuario);
			request.setAttribute("titulo", titulo);
			
			String url = "formulariousuarios.jsp";
			LOG.debug("forward: " + url);
			
			request.getRequestDispatcher(url).forward(request, response);
		}//try
	}//dopost
}
