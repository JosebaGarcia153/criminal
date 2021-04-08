<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<jsp:include page="../../include/backoffice-header.jsp" />
<jsp:include page="../../include/backoffice-navbar.jsp" />
                        
	<h2 class="mt-2">${titulo}</h2>
	
	<form action="views/backoffice/agregar-categoria" method="post">
	
	<div class="form-group">
			<label for="id">ID:</label>
			<input type="text" name="id" id="id" value="${categoria.id}" readonly class="categoria-formulario-control">
		</div>
		
		<div class="form-group">
			<label for="nombre">Nombre:</label>
			<input type="text" name="nombre" id="nombre" value="${categoria.nombre}" class="categoria-formulario-control" autofocus placeholder="Escribe el nombre de la categoria">
		</div>
        
	<input type="submit" id="btnSign" value="Crear" class="btn btn-primary">
</form>
    
 <jsp:include page="../../include/office-footer.jsp" />