package com.criminal.webapp.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.criminal.webapp.controller.Alert;


	
/**
 * Listener que se ejecuta cada vez que se inicia el servidor
 * Setea los usuarios logueados a 0
 *
 */
@WebListener
public class IndexListener implements ServletContextListener {
	
	private static final Logger LOG = Logger.getLogger(IndexListener.class);
	
	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	//Cuando paramos la App
    	LOG.info("Cerrando el servidor");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	
    	//Cuando ejecutamos la App en el Servidor, al arrancar la 1º vez
    	
    	//Evento de log
    	LOG.info("Ejecutando la APP");
    	
    	// Este contexto es para toda la Aplicacion y es accesible desde cualquier JSP o Servlet    	
    	ServletContext contextoAplicacion = sce.getServletContext();
    	
    	try {
    		
    		contextoAplicacion.setAttribute("usuarios_logeados", 0);

    	} catch (Exception e) {
    		
    		LOG.fatal(e);
    		contextoAplicacion.setAttribute("alert", new Alert("danger", "Tenemos un problema desconocido") );
		}	
    }
}
