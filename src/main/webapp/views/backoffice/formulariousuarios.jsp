<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<jsp:include page="../../include/backoffice-header.jsp" />
<jsp:include page="../../include/backoffice-navbar.jsp" />
                        
	<h2 class="mt-2">${titulo}</h2>
	
	<form action="views/backoffice/editar-usuario" method="post">
	
	<div class="form-group">
			<label for="id">ID:</label>
			<input type="text" name="id" id="id" value="${usuario.id}" readonly class="usuario-formulario-control">
		</div>
		
		<div class="form-group">
			<label for="nombre">Nombre:</label>
			<input type="text" name="nombre" id="nombre" value="${usuario.nombre}" class="usuario-formulario-control" autofocus placeholder="Escribe el nuevo nombre del usuario">
		</div>
		
		<div class="form-group">
			<label for="password">Contraseña:</label> <small class="form-text"><a href="https://md5hashing.net/hash/md5">* Conviertelo de MD5 antes de continuar</a></small>
			<input type="text" name="password" id="password" value="${usuario.password}" class="usuario-formulario-control" placeholder="Escribe la nueva contraseña del usuario">
		</div>
		
		<div class="form-group">
			<label for="tipo">Tipo de usuario:</label> <small class="form-text text-muted">* El campo se resetea al entrar al formulario</small>
			<select class="custom-select" value="${usuario.rol.id}" name="tipo" id="tipo">
				<option value="1">Usuario</option>
				<option value="51">Administrador</option>
			</select>
		</div>
        
	<input type="submit" id="btnSign" value="Crear" class="btn btn-primary">
</form>
    
 <jsp:include page="../../include/office-footer.jsp" />