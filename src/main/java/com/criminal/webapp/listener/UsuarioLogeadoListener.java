package com.criminal.webapp.listener;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.criminal.webapp.modelo.pojo.Usuario;

/**
 * Listener para guardar los datos de sesión de un usuario cada vez que se loguea y borrarlos una vez se desconecta.
 *
 */
@WebListener
public class UsuarioLogeadoListener implements HttpSessionListener, HttpSessionAttributeListener {
	
	private static HashMap<String, Usuario> hashm = null;
	private static final  Logger LOG = Logger.getLogger(UsuarioLogeadoListener.class);
	
    /**
     * Constructor por defecto. 
     */
    public UsuarioLogeadoListener() {
        
    	LOG.trace("constructor");
    	hashm = new HashMap<String, Usuario>();
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
    	LOG.trace("Nueva sesión");
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	LOG.trace("Sesión eliminada");
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent event)  { 
         String attributeNombre = event.getName();
         ServletContext sc = event.getSession().getServletContext();
         String sessionId = event.getSession().getId();
         
         LOG.trace("Nuevo atributo de sesión " + attributeNombre);
         
         //Se acaba de hacer el login en LoginController
         if ("usario_login".equals(attributeNombre)) {
        	 Usuario usuario = (Usuario)event.getValue();
        	 LOG.trace("Usuario logeado " + usuario);
        	 
        	 hashm = (HashMap<String, Usuario>)sc.getAttribute("usuariosLogeados");
        	 
        	 if (null == hashm) {
        		 
        		 hashm = new HashMap<String, Usuario>();
        	 }
        	 
        	 hashm.put(sessionId, usuario);
        	 sc.setAttribute("usuariosLogeados", hashm);
        	 LOG.trace("Usuario guardado en el HashMap");
         }
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent event)  { 
         
    	String attributeNombre = event.getName();
    	LOG.trace("Removiendo atributo de sesión");
        ServletContext sc = event.getSession().getServletContext();
        String sessionId = event.getSession().getId();
        
        if ("usuario_login".equals(attributeNombre)) {
        	hashm = (HashMap<String, Usuario>)sc.getAttribute("usuariosLogeados");
        	
        	if (null == hashm) {
       		 
        		hashm = new HashMap<String, Usuario>();
       	 	}
        	
        hashm.remove(sessionId);
       	sc.setAttribute("usuariosLogeados", hashm);        	
        }
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent event)  { 
    	LOG.trace("Atributo de sesión modificado");
    }
}
