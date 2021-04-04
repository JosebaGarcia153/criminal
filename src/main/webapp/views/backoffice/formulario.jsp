<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<jsp:include page="../../include/backoffice-header.jsp" />
<jsp:include page="../../include/backoffice-navbar.jsp" />
    
	<h1 class="mt-2">Crea una nueva pregunta</h1>
	             
	<form action="views/backoffice/agregar-pregunta" method="post" enctype="multipart/form-data">
	
		<div class="form-group">
			<label for="id">ID:</label>
			<input type="text" name="id" id="id" value="${pregunta.id}" readonly class="pregunta-formulario-control">
		</div>
		
		<div class="form-group">
			<label for="nombre">Nombre:</label>
			<input type="text" name="nombre" value="${pregunta.nombre}" class="pregunta-formulario-control" autofocus placeholder="Escribe la pregunta">
		</div>
		
		<div class="form-group">
			<label for="dificultad">Dificultad:</label>
			<select class="custom-select" name="dificultad">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
			</select>
		</div>
		
		<div class="form-group">
			<label for="tiempo">Tiempo:</label>
			<input type="text" name="tiempo" value="${pregunta.tiempo}" class="pregunta-formulario-control" placeholder="Segundos para responder a la pregunta">
		</div>
		
		<div class="form-group">
			<label for="imagen">Imagen:</label>
			<input type="text" name="imagen" id="image" value="${pregunta.imagen}" class="formulario-control" placeholder="URL de imagen (.jpg or .png)" >
		</div>
 
		<div class="form-group">
			<select class="custom-select" name="categoria_id">
				<c:forEach items="${categorias}" var="c">
					<option value="${c.id}" ${(c.id eq categoria.id) ? "selected" : ""}>${c.nombre}</option>
				</c:forEach>					  					  
			</select>
		</div>

		<div class="form-group">
			<label for="resp1">Respuesta 1:</label>
			<input type="text" name="resp1" value="${pregunta.respuestas[0].nombre}" class="pregunta-formulario-control" placeholder="Respuesta 1">
		</div>

		<div class="form-group">
			<label for="resp2">Respuesta 2:</label>
			<input type="text" name="resp2" value="${pregunta.respuestas[1].nombre}" class="pregunta-formulario-control" placeholder="Respuesta 2">
		</div>

		
		<div class="form-group">
			<label for="resp2">Respuesta 3:</label>
			<input type="text" name="resp3" value="${pregunta.respuestas[2].nombre}" class="pregunta-formulario-control" placeholder="Respuesta 3">
		</div>
		
		<div class="form-group">
			<label for="respuesta_correcta">Respuesta Correcta:</label>
			<select class="custom-select" name="respuesta_correcta">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
			</select>
		</div>
		
		<div class="form-group">
			<label for="comentario">Comentario:</label>
			<textarea name="comentario" rows="5" cols="20" placeholder="Comentario sobre la respuesta correcta"></textarea>
		</div>
		
		<input type="submit" value="Guardar" class="btn btn-primary btn-block">
	</form>
                        
  
 <jsp:include page="../../include/office-footer.jsp" />              