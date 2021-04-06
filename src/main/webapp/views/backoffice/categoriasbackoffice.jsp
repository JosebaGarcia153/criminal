<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<jsp:include page="../../include/backoffice-header.jsp" />
<jsp:include page="../../include/backoffice-navbar.jsp" />
                        
	<h2>Lista de categorias</h2>
    
    <table class="tabla table table-striped">
		<thead>
			<tr>
				<td>ID</td>
				<td>Nombre</td>
				<td>Operaciones</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${categorias}" var="c">
				<tr>
					<td>${c.id}</td>
					<td>${c.nombre}</td>
					<td>
						<a href="views/backoffice/agregar-categoria?id=${c.id}" class="mr-4">
							<i class="far fa-edit fa-2x" title="Editar Categoria"></i>
						</a>
						<a href="views/backoffice/borrar-categoria?id=${c.id}" onclick="confirmar('${c.nombre}')">
							<i class="fas fa-trash fa-2x" title="Borrar Categoria"></i>
						</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    
 <jsp:include page="../../include/office-footer.jsp" />