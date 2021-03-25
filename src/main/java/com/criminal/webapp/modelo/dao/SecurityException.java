package com.criminal.webapp.modelo.dao;

/**
 * Excepción customizada para cuando un usuario intenta alterar cosas que no le pertenezen en la BBDD
 */
public class SecurityException extends Exception {

	private static final long serialVersionUID = 1L;
	public static final String MESSAGE = "No tienes privilegios para alterar esta entrada";

	public SecurityException() {
		super(MESSAGE);
	}
}