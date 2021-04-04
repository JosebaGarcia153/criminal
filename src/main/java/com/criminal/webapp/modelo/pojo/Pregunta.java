package com.criminal.webapp.modelo.pojo;

import java.util.ArrayList;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;


/**
 * Clase para generar instancias de respuestas
 * 
 * id guarda la ID de la pregunta
 * nombre guarda el texto de la pregunta
 * dificultad guarda la dificultad en un rango del 1 al 5
 * tiempo guarda el tiempo que se da para responder
 * comentario añade un comentario sobre la respuesta correcta
 * imagen guarda una imagen para acompañar a la pregunta
 * usuario guarda una instancia del usuario que lo ha subido
 * categoria guarda una instancia de la categoria al a que pertenece la pregunta
 * repuestas guarda un arraylist de posibles respuestas a la pregunta
 * id_usuario comprueba el ID del usuario para violaciones de seguridad
 * fecha_aprobada guarda la fecha en la que un administrador la a aprobado
 * @see Usuario
 * @see Categoria
 * @see Respuesta
 */
public class Pregunta {
	private int id;
	
	@NotEmpty(message = "La preguna no puede estar vacía")
	@Size(min = 5, max = 100, message = "La pregunta tiene que tener entre 5 y 100 caracteres")
	private String nombre;
	
	private int dificultad;
	private int tiempo;
	
	@NotEmpty(message = "El comentario no puede estar vacío")
	@Size(min = 5, max = 500, message = "El comentario tiene que tener entre 5 y 500 caracteres")
	private String comentario;
	private String imagen;
	private Usuario usuario;
	private Categoria categoria;
	private ArrayList<Respuesta> respuestas;
	private int usuario_id;
	private String fecha_aprobada;
	
	
	public Pregunta() {
		super();
		this.id = 0;
		this.nombre = "";
		this.dificultad = 0;
		this.tiempo = 0;
		this.comentario = "";
		this.imagen = "";
		this.usuario = new Usuario();
		this.categoria = new Categoria();
		this.respuestas = new ArrayList<Respuesta>();
		this.usuario_id = 0;
		this.fecha_aprobada = "";
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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


	public int getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}
	
	
	public String getFecha_aprobada() {
		return fecha_aprobada;
	}

	public void setFecha_aprobada(String fecha_aprobada) {
		this.fecha_aprobada = fecha_aprobada;
	}


	@Override
	public String toString() {
		return "Pregunta [id=" + id + ", nombre=" + nombre + ", dificultad=" + dificultad + ", tiempo=" + tiempo
				+ ", comentario=" + comentario + ", imagen=" + imagen + ", usuario=" + usuario + ", categoria="
				+ categoria + ", respuestas=" + respuestas + ", usuario_id=" + usuario_id + ", fecha_aprobada="
				+ fecha_aprobada + "]";
	}
}
