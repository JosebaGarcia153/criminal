package com.criminal.webapp.modelo.pojo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Clase para generar instancias de respuestas
 * 
 * id guarda la ID de la respuesta
 * nombre guarda la respuesta
 * esCorrecta confirma si la respuesta es verdadera o falsa
 */
public class Respuesta {
	
	private int id;
	
	@NotEmpty(message = "La respuesta no puede estar vacía")
	@Size(min = 1, max = 100, message = "La respuesta tiene que tener entre 1 y 100 caracteres")
	private String nombre;
	
	private boolean esCorrecta;
	
	@Min(value = 1, message = "Tiene que haber entre 1 y 3 preguntas")
	@Max(value = 3, message = "Tiene que haber entre 1 y 3 preguntas")
	private int num_respuesta;
	
	
	public Respuesta() {
		super();
		this.id = 0;
		this.nombre = "";
		this.esCorrecta = false;
		this.num_respuesta = 0;
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


	public boolean isEsCorrecta() {
		return esCorrecta;
	}

	public void setEsCorrecta(boolean esCorrecta) {
		this.esCorrecta = esCorrecta;
	}

	
	public int getNum_respuesta() {
		return num_respuesta;
	}

	public void setNum_respuesta(int num_respuesta) {
		this.num_respuesta = num_respuesta;
	}


	@Override
	public String toString() {
		return "Respuesta [id=" + id + ", nombre=" + nombre + ", esCorrecta=" + esCorrecta + ", num_respuesta="
				+ num_respuesta + "]";
	}
}
