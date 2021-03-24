package com.criminal.webapp.modelo;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManager {
	
	/*
	private final static String URL = "jdbc:mysql://localhost/criminal";
	private final static String USUARIO = "debian-sys-maint";
	private final static String PASS = "o8lAkaNtX91xMUcV";
	*/
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException, NamingException {
		
		Connection con = null;
		
		// comprobar que tengamos el .jar de MySQL
		Class.forName("com.mysql.jdbc.Driver");
		
		InitialContext initCtx= new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource dataSource = (DataSource)envCtx.lookup("jdbc/super");
		
		//establecer conexion
		//con = DriverManager.getConnection(URL, USUARIO, PASS);
		
		con = dataSource.getConnection(); 
		
		return con;
	};
}
