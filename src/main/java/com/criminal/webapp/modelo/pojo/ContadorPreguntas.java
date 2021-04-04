package com.criminal.webapp.modelo.pojo;

/**
 * Clase para contar las preguntas agregadas por un usuario y el numero de preguntas en total.
 * usuario_id guarda la ID del usuario.
 * total cuenta todos las preguntas
 * aprobadas cuenta las preguntas aprobadas por un administrador
 * pendientes cuenta las preguntas sin aprobar
 */
public class ContadorPreguntas {
	
	private int usuarioId;
	private int total;
	private int aprobadas;
	private int pendientes;
	
	
	public ContadorPreguntas() {
		super();
	}

	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	
	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	
	public int getAprobadas() {
		return aprobadas;
	}

	public void setAprobadas(int aprobadas) {
		this.aprobadas = aprobadas;
	}

	
	public int getPendientes() {
		return pendientes;
	}

	public void setPendientes(int pendientes) {
		this.pendientes = pendientes;
	}

	
	@Override
	public String toString() {
		return "ContadorPreguntas [usuarioId=" + usuarioId + ", total=" + total + ", aprobadas=" + aprobadas
				+ ", pendientes=" + pendientes + "]";
	}
}
