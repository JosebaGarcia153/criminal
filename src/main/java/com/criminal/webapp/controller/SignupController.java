package com.criminal.webapp.controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
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
import com.criminal.webapp.modelo.pojo.Usuario;

/**
 * Servlet implementation class SignupController
 */
@WebServlet("/signup")
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(SignupController.class);
	private static final UsuarioDAOImpl dao = UsuarioDAOImpl.getInstance();
	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private static Validator validator = factory.getValidator();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario usuario = new Usuario();
		Alert alert = new Alert();
		
		String url = "";
		
		//Recoger parametros del formulario
		String nombre = request.getParameter("nombre");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		
		try {
			//Crear objeto con esos parametros	
			usuario.setNombre(nombre);
			usuario.setPassword(password);
			
			//Comprobar que la contrasenia sea repetida correctamente
			if (password.equals(repassword)) {
				
				//Validar pojo
				Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
				
				if ( violations.isEmpty() ) {
					
					dao.crear(usuario);
					alert = new Alert ("success", "Has creado un usuario correctamente");
					url = "views/login/login.jsp";

				} else {
					
					String errors = "";
					
					for (ConstraintViolation<Usuario> v : violations) {	
						errors += "<p><b>" + v.getPropertyPath() + "</b>: "  + v.getMessage() + "</p>";		
					}
					alert = new Alert("warning", errors);
					request.setAttribute("name", nombre);
					url = "views/login/sign.jsp";
				}//if
				
			} else {
				
				alert = new Alert("warning", "La contraseña y la confirmación no encajan");
				request.setAttribute("alert", alert);
				request.setAttribute("name", nombre);
				
				LOG.debug("forward: " + url);
				
				request.getRequestDispatcher(url).forward(request, response);
			}//if		
			
		} catch (Exception e) {
			
			LOG.error(e);
			alert = new Alert("warning", e.getMessage());
			request.setAttribute("name", nombre);
			url = "views/login/sign.jsp";
		
		} finally {
			
			//Volver al formulario o iniciar sesión
			request.setAttribute("alert", alert);			
			
			LOG.debug("forward: " + url);		
			request.getRequestDispatcher(url).forward(request, response);
		}//try
	}
}
