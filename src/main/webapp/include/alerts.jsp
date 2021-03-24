<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    

<c:if test="${not empty alert}">  
	<div class="alert alert-${alert.type} alert-dismissible fade show" role="alert">
		${alert.text}
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
	
	<%
		// Eliminar en sesion el atributo despues de pintarlo
		session.setAttribute("alert", null);
	%>
</c:if>	