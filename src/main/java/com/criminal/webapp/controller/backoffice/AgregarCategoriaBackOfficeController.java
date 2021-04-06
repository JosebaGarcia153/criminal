package com.criminal.webapp.controller.backoffice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.criminal.webapp.modelo.dao.impl.CategoriaDAOImpl;
import com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl;
import com.criminal.webapp.controller.Alert;
import com.criminal.webapp.modelo.pojo.Categoria;
import com.criminal.webapp.modelo.pojo.Pregunta;
import com.criminal.webapp.modelo.pojo.Respuesta;
import com.criminal.webapp.modelo.pojo.Usuario;


/**
 * Controlador para usuarios comunes para el formulario de juegos.
 * El metodo GET se encarga de entrar al formulario por primera vez y guardar los datos y seguir mostrándolos en el formulario en el caso de que halla habido algún error al enviarlos.
 * El metodo POST se encarga de recibir los datos del formularios y enviarlos a la implementación DAO donde se realizarán las llamadas SQL.
 * @see com.criminal.webapp.modelo.dao.impl.PreguntaDAOImpl
 */
@WebServlet("/views/backoffice/agregar-pregunta")
@MultipartConfig
public class AgregarCategoriaBackOfficeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(AgregarCategoriaBackOfficeController.class);
	private static final PreguntaDAOImpl dao = PreguntaDAOImpl.getInstance();
	private static final CategoriaDAOImpl daoC = CategoriaDAOImpl.getInstance();
	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private static Validator validator = factory.getValidator();
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Usuario usuario = new Usuario();
		Pregunta pregunta = new Pregunta();
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();
		
		try {
			
			//Recuperar usuario de session
			usuario = (Usuario)session.getAttribute("usuario_login");
			int usuarioId = usuario.getId();
			
			//Recoger parametros
			categorias = daoC.conseguirTodas();
			
			String idParameter = request.getParameter("id");
					
			//Recoger pregunta si la ID no esta vacía
			if (idParameter != null) {
				
				int id = Integer.parseInt(idParameter);
				pregunta = dao.conseguirPorId(id, usuarioId);
			}	
			
		} catch (Exception e) {

			LOG.error(e);

		} finally {
			
			//Ir al formulario
			request.setAttribute("pregunta", pregunta);
			request.setAttribute("categorias", categorias);
			
			String url = "formulario.jsp";	
			LOG.debug("forward: " + url);
			
			request.getRequestDispatcher(url).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String titulo = "";
		
		HttpSession session = request.getSession();
		
		Usuario usuario = new Usuario();
		Pregunta pregunta = new Pregunta();

		ArrayList<Respuesta> respuestas = new ArrayList<Respuesta>();
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();
		
		Alert alert = new Alert();
		
		//Recoger parametros del formulario
		String idParam = request.getParameter("id");
		String nombre = request.getParameter("nombre");
		String dificultadParam = request.getParameter("dificultad");
		String tiempoParam = request.getParameter("tiempo");
		String imagen = request.getParameter("image");
		String comentario = request.getParameter("comentario");
		
		String categoriaIdParam = request.getParameter("categoria_id");
		
		String respuesta1idParam = request.getParameter("resp1id");
		String respuesta1nombre = request.getParameter("resp1");
		String respuesta2idParam = request.getParameter("resp2id");
		String respuesta2nombre = request.getParameter("resp2");
		String respuesta3idParam = request.getParameter("resp3id");
		String respuesta3nombre = request.getParameter("resp3");
		String respuesta_correctaParam = request.getParameter("respuesta_correcta");
		
		try {
			
			//Recuperar usuario de sesión y setearlo en la pregunta
			usuario = (Usuario)session.getAttribute("usuario_login");
			
			categorias = daoC.conseguirTodas();
			
			int id = Integer.parseInt(idParam);
			int idCategoria = Integer.parseInt(categoriaIdParam);
			int dificultad = Integer.parseInt(dificultadParam);
			int tiempo = Integer.parseInt(tiempoParam);
			int respuesta_correcta = Integer.parseInt(respuesta_correctaParam);
			int respuesta1id = Integer.parseInt(respuesta1idParam);
			int respuesta2id = Integer.parseInt(respuesta2idParam);
			int respuesta3id = Integer.parseInt(respuesta3idParam);
			
			
			//Crear objeto con esos parametros			
			pregunta.setId(id);
			pregunta.setNombre(nombre);
			pregunta.setDificultad(dificultad);
			pregunta.setTiempo(tiempo);
			pregunta.setImagen(imagen);
			pregunta.setComentario(comentario);
			
			
			Categoria categoria = new Categoria();
			categoria.setId(idCategoria);
			
			pregunta.setCategoria(categoria);
			
			
			Respuesta respuesta1 = new Respuesta();
			Respuesta respuesta2 = new Respuesta();
			Respuesta respuesta3 = new Respuesta();
			
			respuesta1.setId(respuesta1id);
			respuesta2.setId(respuesta2id);
			respuesta3.setId(respuesta3id);
			respuesta1.setNombre(respuesta1nombre);
			respuesta2.setNombre(respuesta2nombre);
			respuesta3.setNombre(respuesta3nombre);
			respuesta1.setNum_respuesta(1);
			respuesta2.setNum_respuesta(2);
			respuesta3.setNum_respuesta(3);
			
			switch (respuesta_correcta) {
			case 1:
				respuesta1.setEsCorrecta(true);
				respuesta2.setEsCorrecta(false);
				respuesta3.setEsCorrecta(false);
				break;
			case 2:
				respuesta1.setEsCorrecta(false);
				respuesta2.setEsCorrecta(true);
				respuesta3.setEsCorrecta(false);
				break;
			case 3:
				respuesta1.setEsCorrecta(false);
				respuesta2.setEsCorrecta(false);
				respuesta3.setEsCorrecta(true);
				break;
			}
			
			respuestas.add(respuesta1);
			respuestas.add(respuesta2);
			respuestas.add(respuesta3);
			
			pregunta.setRespuestas(respuestas);
			
			//Recuperar usuario de sesion y setearlo en la pregunta
			pregunta.setUsuario(usuario);
			
				
			//Validar pojos
			Set<ConstraintViolation<Pregunta>> violations = validator.validate(pregunta);
			
			if ( violations.isEmpty() ) {
				
				if (id == 0) {
					
					dao.crear(pregunta);
					alert = new Alert ("success", "Pregunta añadida. Dentro de poco será revisada para aprobarla");
					request.setAttribute("titulo", titulo);
				
				} else {
					
					dao.actualizarPorAdmin(pregunta);
					alert = new Alert ("success", "Pregunta actualizada. Pendiente de aprobación");
					request.setAttribute("titulo", titulo);
				}
				
			} else {
				
				String errors = "";
				
				for (ConstraintViolation<Pregunta> v : violations) {	
					errors += "<p><b>" + v.getPropertyPath() + "</b>: "  + v.getMessage() + "</p>";		
				}
				alert = new Alert("warning", errors);
			}//if

		} catch (Exception e) {
			
			LOG.error(e);
			alert = new Alert("warning", "Una pregunta idéntica ya esta registrada");
		
		} finally {
			
			//Volver al formulario
			request.setAttribute("alert", alert);
			request.setAttribute("pregunta", pregunta);
			request.setAttribute("categorias", categorias);
			request.setAttribute("titulo", titulo);
			
			String url = "formulario.jsp";
			LOG.debug("forward: " + url);
			
			request.getRequestDispatcher(url).forward(request, response);
		}//try
	}//dopost
}
