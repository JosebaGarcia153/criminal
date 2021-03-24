package com.criminal.webapp.modelo.pojo;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Clase para generar instancias de respuestas
 * 
 * id guarda la ID de la respuesta
 * respuesta guarda la respuesta
 * esCorrecta confirma si la respuesta es verdadera o falsa
 */
public class Respuesta {
	
	private int id;
	
	@NotEmpty(message = "La respuesta no puede estar vacía")
	@Size(min = 1, max = 100, message = "La respuesta tiene que tener entre 1 y 100 caracteres")
	private String respuesta;
	
	private boolean esCorrecta;
	
	
	public Respuesta() {
		super();
		this.id = 0;
		this.respuesta = "";
		this.esCorrecta = false;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}


	public boolean isEsCorrecta() {
		return esCorrecta;
	}

	public void setEsCorrecta(boolean esCorrecta) {
		this.esCorrecta = esCorrecta;
	}


	@Override
	public String toString() {
		return "Respuesta [id=" + id + ", respuesta=" + respuesta + ", esCorrecta=" + esCorrecta + "]";
	}
}
