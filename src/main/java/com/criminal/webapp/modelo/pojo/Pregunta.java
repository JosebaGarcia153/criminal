package com.criminal.webapp.modelo.pojo;

import java.util.ArrayList;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;


/**
 * Clase para generar instancias de respuestas
 * 
 * id guarda la ID de la pregunta
 * pregunta guarda el texto de la pregunta
 * dificultad guarda la dificultad en un rango del 1 al 5
 * tiempo guarda el tiempo que se da para responder
 * comentario añade un comentario sobre la respuesta correcta
 * imagen guarda una imagen para acompañar a la pregunta
 * usuario guarda una instancia del usuario que lo ha subido
 * categoria guarda una instancia de la categoria al a que pertenece la pregunta
 * repuestas guarda un arraylist de posibles respuestas a la pregunta
 * id_usuario comprueba el ID del usuario para violaciones de seguridad
 * @see Usuario
 * @see Categoria
 * @see Respuesta
 */
public class Pregunta {
	private int id;
	
	@NotEmpty(message = "La preguna no puede estar vacía")
	@Size(min = 5, max = 100, message = "La pregunta tiene que tener entre 5 y 100 caracteres")
	private String pregunta;
	
	private int dificultad;
	private int tiempo;
	
	@NotEmpty(message = "El comentario no puede estar vacío")
	@Size(min = 5, max = 500, message = "El comentario tiene que tener entre 5 y 500 caracteres")
	private String comentario;
	private String imagen;
	private Usuario usuario;
	private Categoria categoria;
	private ArrayList<Respuesta> respuestas;
	private int id_usuario;
	
	
	public Pregunta() {
		super();
		this.id = 0;
		this.pregunta = "";
		this.dificultad = 0;
		this.tiempo = 0;
		this.comentario = "";
		this.imagen = "";
		this.usuario = new Usuario();
		this.categoria = new Categoria();
		this.respuestas = new ArrayList<Respuesta>();
		this.id_usuario = 0;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}


	public int getDificultad() {
		return dificultad;
	}

	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}


	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}


	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public ArrayList<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(ArrayList<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}


	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}


	@Override
	public String toString() {
		return "Pregunta [id=" + id + ", pregunta=" + pregunta + ", dificultad=" + dificultad + ", tiempo=" + tiempo
				+ ", comentario=" + comentario + ", imagen=" + imagen + ", usuario=" + usuario + ", categoria="
				+ categoria + ", respuestas=" + respuestas + ", id_usuario=" + id_usuario + "]";
	}
}
