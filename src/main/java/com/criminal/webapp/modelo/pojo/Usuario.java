package com.criminal.webapp.modelo.pojo;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase para generar instancias de usuarios
 * 
 * nombre guarda el nombre del usuario
 * password guarda la contraseña
 * rol es un objeto usado para indicar si el usuario es un administrador o no
 * @see Rol
 *
 */
public class Usuario {
	
	private int id;
	
	@Email(message = "El email tiene que ser valido")
	private String nombre;
	
	@NotEmpty(message = "La contraseña no puede estar vacia")
	@Size(min = 6, max = 15, message = "La contraseña debe tener entre 6 y 15 caracteres")
	private String password;
	private Rol rol;
	
	
	public Usuario() {
		super();
		this.id = 0;
		this.nombre = "";
		this.password = "";
		this.rol = new Rol();
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", password=" + password + ", rol=" + rol + "]";
	}
}
