package com.criminal.webapp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.criminal.webapp.modelo.dao.impl.UsuarioDAOImpl;
import com.criminal.webapp.modelo.pojo.Rol;
import com.criminal.webapp.modelo.pojo.Usuario;

/**
 * Servlet encargado de recibir el nombre y contraseña insertados en login.jsp para logear a un usuario en la pagina web.
 * Si el logeo falla, vuelve una vez mas al la jsp, sino lo redirecciona al controlador del area del usuario o administrador.
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * Recive el nombre y contraseña de login.jsp, y comprueba si son correctos para redireccionar o quedarse en el login.
	 * @see BackOfficeController com.games.webapp.controller.backoffice.BackOfficeController
	 * @see FrontOfficeController com.games.webapp.controller.frontoffice.FrontOfficeController
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nombre = request.getParameter("nombre");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession();
		
		UsuarioDAOImpl dao = UsuarioDAOImpl.getInstance();
		Usuario usuario = dao.existe(nombre, password);
		
		if (usuario == null) {
			
			request.setAttribute("alert", new Alert("danger", "Datos incorrectos"));
			request.setAttribute("nombre", nombre);
			request.getRequestDispatcher("views/login/login.jsp").forward(request, response);
			
			
		} else {			
			
			session.setMaxInactiveInterval(60 * 60 * 2); //la sesion dura 2 horas si inactivo
			session.setAttribute("usuario_login", usuario); // @see UsuarioLogeadoListener => attributeAdded
			
			session.setAttribute("alert", new Alert("success", "Has iniciado sesión"));		
			
			//Seguir una ruta de carpetas dependiendo del tipo de usuario
			if (usuario.getRol().getId() == Rol.ADMIN) {
				response.sendRedirect("views/backoffice/inicio");				
			} else {
				response.sendRedirect("views/frontoffice/inicio");
			}
		}
	}
}