<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<jsp:include page="../../include/backoffice-header.jsp" />
<jsp:include page="../../include/backoffice-navbar.jsp" />
                        
	<h2>${titulo}</h2>
    
    <table class="tabla table table-striped">
		<thead>
			<tr>
				<td>ID</td>
				<td>Nombre</td>
				<td>Contraseña</td>
				<td>Rol</td>
				<td>Operaciones</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${usuarios}" var="u">
				<tr>
					<td>${u.id}</td>
					<td>${u.nombre}</td>
					<td>${u.password}</td>
					<td>${u.rol.nombre}</td>
					<td>
						<a href="views/backoffice/editar-usuario?id=${u.id}" class="mr-4">
							<i class="far fa-edit fa-2x" title="Editar Usuario"></i>
						</a>
						<a href="views/backoffice/borrar-usuario?id=${u.id}" onclick="confirmar('${u.nombre}')">
							<i class="fas fa-trash fa-2x" title="Borrar Usuario"></i>
						</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    
 <jsp:include page="../../include/office-footer.jsp" />