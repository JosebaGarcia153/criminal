package com.criminal.webapp.modelo.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Clase para generar instancias de categorias
 * 
 * id guarda la ID de la categoria
 * nombre guarda el nombre
 */
public class Categoria {
	
	private int id;
	
	@NotNull(message = "El nombre no puede estar vacío")
	@Size(min = 3, max = 20, message = "La categoria tiene que tener entre 3 y 20 caracteres")
	private String nombre;
	
	public Categoria() {
		super();
		this.id = 0;
		this.nombre = "";
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

	
	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + "]";
	}
}
