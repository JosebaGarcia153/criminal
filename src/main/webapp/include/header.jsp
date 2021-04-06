<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="es">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Todas las rutas relativas comienzan por el href indicado -->
<!--  ${pageContext.request.contextPath} == http://localhost:8080/games -->
<base href="${pageContext.request.contextPath}/" />

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

<!-- Custom CSS -->
<link rel="stylesheet" href="css/styles.css?d=<%=System.currentTimeMillis()%>">

<title>${param.title} | Criminal</title>
</head>
<body>

	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark mb-5">

		<!-- lista enlaces -->
		<div class="collapse navbar-collapse" id="navbarsExampleDefault">
			<ul class="navbar-nav mr-auto">
				<c:if test="${ not empty sessionScope.usuario_login }">
					<c:if test="${not (sessionScope.usuario_login.rol.nombre eq 'admin')}">
						<li class="nav-item ${('index' eq param.page) ? 'active' : ''}">
							<a class="nav-link" href="views/frontoffice/inicio">Front Office</a>
						</li>
					</c:if>
					<c:if test="${sessionScope.usuario_login.rol.nombre eq 'admin'}">
						<li class="nav-item ${('index' eq param.page) ? 'active' : ''}">
							<a class="nav-link" href="views/backoffice/inicio">Back Office</a>
						</li>
					</c:if>
				</c:if>
			</ul>


			<span class="form-inline"> <c:if test="${ empty sessionScope.usuario_login }">
					<a class="nav-link  btn btn-outline-warning" href="views/login/login.jsp">Iniciar sesión</a>
				</c:if> <c:if test="${ not empty sessionScope.usuario_login }">
					<div class="mr-2" style="color: white">${usuario_login.nombre}</div>
					<span class="badge badge-pill badge-light mr-3">${sessionScope.usuario_login.nombre}</span>
					<a class="nav-link  btn btn-outline-light" href="logout">Cerrar sesión</a>
				</c:if>
			</span>

		</div>
	</nav>

	<main role="main" class="container pt-5 mt-5">

		<jsp:include page="alerts.jsp"></jsp:include>