<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<jsp:include page="../../include/backoffice-header.jsp" />
<jsp:include page="../../include/backoffice-navbar.jsp" />

<h2 class="mt-2">${titulo}</h2>
	
	<div class="dropdown show">
		<a class="btn btn-secondary dropdown-toggle mt-4 mb-4" href="mostrar-categorias" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			Categorias
		</a>
		<div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
		    <a class="dropdown-item" href="views/backoffice/mostrar-por-categorias?categoriaId=-1">Todas</a>
			<c:forEach items="${lista_categorias}" var="c">
				<a class="dropdown-item" href="views/backoffice/mostrar-por-categorias?categoriaId=${c.id}&categoriaNombre=${c.nombre}">${c.nombre}</a>	
			</c:forEach>
		</div>
	</div> 
	
	<c:if test="${pregunta.id != 0}">
	  <table class="tabla table table-striped">
			<thead>
				<tr>
					<td>Nombre</td>
					<td>Dificultad</td>
					<td>Tiempo</td>
					<td>Imagen</td>
					<td>Respuesta 1</td>
					<td></td>
					<td>Respuesta 2</td>
					<td></td>
					<td>Respuesta 3</td>
					<td></td>
					<td>Comentario</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>${pregunta.nombre}</td>
					<td>${pregunta.dificultad}</td>
					<td>${pregunta.tiempo}</td>
					<td><img src="${pregunta.imagen}"  class="img-thumbnail" alt="imagen..."></td>
					<c:forEach items="${pregunta.respuestas}" var="r">
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
					<td>${pregunta.comentario}</td>
				</tr>
			</tbody>
		</table>
	</c:if>	
	
	<c:if test="${not empty categorias}">
		<c:forEach items="${categorias}" var="c">
		<h4>${c.nombre}</h4>
		
			<div class="row-card">
				<c:forEach items="${c.preguntas}" var="p">
					<div class="card">
						<div class="card-body">
							<h4>${p.nombre}</h4>
							<a href="views/backoffice/mostrar-por-categorias?preguntaId=${p.id}">Ver detalles</a>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
	</c:if>
	
		<c:if test="${not empty preguntas}">
		<c:forEach items="${preguntas}" var="p">
			<div class="row-card">
				<div class="card">
					<div class="card-body">
						<h4>${p.nombre}</h4>
						<a href="views/backoffice/mostrar-por-categorias?preguntaId=${p.id}">Ver detalles</a>
					</div>
				</div>
			</div>
		</c:forEach>
	</c:if>
	
<jsp:include page="../../include/office-footer.jsp" />