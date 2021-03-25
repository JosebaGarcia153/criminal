package com.criminal.webapp.modelo.pojo;

/**
 * Clase para contar las preguntas agregadas por un usuario y el numero de preguntas en total.
 * usuario_id guarda la ID del usuario.
 * total cuenta todos las preguntas
 */
public class ContadorPreguntas {
	
	private int user_id;
	private int total;
	
	
	public ContadorPreguntas() {
		super();
	}

	
	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}


	@Override
	public String toString() {
		return "GameCount [user_id=" + user_id + ", total=" + total + "]";
	}
}
