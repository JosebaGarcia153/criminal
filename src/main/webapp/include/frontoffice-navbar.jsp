<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
	<a class="navbar-brand" href="views/frontoffice/inicio">My Panel</a>
	<button class="btn btn-link btn-sm order-1 order-lg-0"
		id="sidebarToggle" type="button" data-toggle="collapse"
		data-target="#sidenavAccordion" aria-controls="sidenavAccordion"
		aria-expanded="false" aria-label="Toggle navigation">
		<i class="fas fa-bars"></i>
	</button>
	<!-- Navbar-->
	<span class="form-inline">
		<c:if
			test="${ empty sessionScope.usuario_login }">
			<a class="nav-link  btn btn-outline-warning" href="views/login/login.jsp">Iniciar sesión</a>
		</c:if>
		<c:if test="${ not empty sessionScope.usuario_login }">
			<span class="badge badge-pill badge-light mr-3">${sessionScope.usuario_login.nombre}</span>
			<a class="nav-link  btn btn-outline-light" href="logout">Cerrar sesión</a>
		</c:if>
	</span>
</nav>

<div id="layoutSidenav">
	<div id="layoutSidenav_nav">
		<nav class="sb-sidenav accordion sb-sidenav-dark"
			id="sidenavAccordion">
			<div class="sb-sidenav-menu">
				<div class="nav">

					<a class="nav-link" href="index.jsp">
						<div class="sb-nav-link-icon">
							<i class="fa fa-globe"></i>
						</div> Index
					</a> <a class="nav-link" href="views/frontoffice/inicio">
						<div class="sb-nav-link-icon">
							<i class="fa fa-tachometer-alt"></i>
						</div> Mi Panel
					</a> <a class="nav-link" href="views/frontoffice/preguntas">
						<div class="sb-nav-link-icon">
							<i class="fa fa-check-square-o"></i>
						</div> Preguntas Aprobadas
					</a> <a class="nav-link" href="views/frontoffice/preguntas?aprobadas=0">
						<div class="sb-nav-link-icon">
							<i class="fa fa-check-circle-o"></i>
						</div> Pendientes de Aprobar
					</a> <a class="nav-link" href="views/frontoffice/agregar-pregunta">
						<div class="sb-nav-link-icon">
							<i class="fa fa-plus-circle"></i>
						</div> Agregar Pregunta
					</a>
				</div>
			</div>
		</nav>
	</div>


	<!-- CONTENIDO PRINCIPAL -->
	<div id="layoutSidenav_content">
		<main>
			<div class="container-fluid mb-3">
				<%@ include file="alerts.jsp"%>