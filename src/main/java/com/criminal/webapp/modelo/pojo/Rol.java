package com.criminal.webapp.modelo.pojo;

/**
 * Clase para generar instancias de rols
 * id guarda el ID del rol, si el ID es 1 es un usuario normal, si es 51 (no 2 por motivos de seguridad) es un administrador
 * nombre guarda el nombre del rol
 * @see Rol
 *
 */
public class Rol {
	
	public static final int ADMIN = 51;
	public static final int USER = 1;
	
	private int id;
	private String nombre;
	
	public Rol() {
		super();
		this.id = 1;
		this.nombre = "";
	}
	
	public Rol( int id ) {
		this();
		this.id = id;		
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
		return "Rol [id=" + id + ", nombre=" + nombre + "]";
	}
}