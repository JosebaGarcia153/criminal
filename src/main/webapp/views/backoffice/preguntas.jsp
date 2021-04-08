<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<jsp:include page="../../include/backoffice-header.jsp" />
<jsp:include page="../../include/backoffice-navbar.jsp" />
                        
	<h2>${titulo}</h2>
    
    <table class="tabla table table-striped">
		<thead>
			<tr>
				<td>ID</td>
				<td>ID Usuario</td>
				<td>Nombre</td>
				<td>Dificultad</td>
				<td>Tiempo</td>
				<td>Imagen</td>
				<td>Categoria</td>
				<td>Respuesta 1</td>
				<td></td>
				<td>Respuesta 2</td>
				<td></td>
				<td>Respuesta 3</td>
				<td></td>
				<td>Comentario</td>
				<td>Operaciones</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${preguntas}" var="p">
				<tr>
					<td>${p.id}</td>
					<td>${p.usuario_id}</td>
					<td>${p.nombre}</td>
					<td>${p.dificultad}</td>
					<td>${p.tiempo}</td>
					<td><img src="${p.imagen}"  class="img-thumbnail" alt="imagen..."></td>
					<td>${p.categoria.nombre}</td>
					<c:forEach items="${p.respuestas}" var="r">
						<td>${r.nombre}</td>
						<td>
							<c:choose>
								<c:when test="${r.esCorrecta=='true'}">
									<span class="fas fa-check-square"  style='font-size:24px;color:green'></span>
									<br/>
								</c:when>    
								<c:otherwise>
									<span  class="fas fa-window-close"  style='font-size:24px;color:red'></span>
									<br/>
								</c:otherwise>
							</c:choose>   
						</td>
					</c:forEach>
					<td>${p.comentario}</td>
					<td>
						<c:if test="${empty p.fecha_aprobada}">
						<a href="views/backoffice/validar?id=${p.id}" class="mr-4">
							<i class="fas fa-check fa-2x" title="Validar Pregunta"></i>
						</a>
						</c:if>
						<a href="views/backoffice/agregar-pregunta?id=${p.id}" class="mr-4">
							<i class="far fa-edit fa-2x" title="Editar Pregunta"></i>
						</a>
						<a href="views/backoffice/borrar?id=${p.id}&aprobada=${p.fecha_aprobada}" onclick="confirmar('${p.nombre}')">
							<i class="fas fa-trash fa-2x" title="Borrar Pregunta"></i>
						</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    
 <jsp:include page="../../include/office-footer.jsp" />