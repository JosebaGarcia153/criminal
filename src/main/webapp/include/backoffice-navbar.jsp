<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
 
 		<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand" href="views/backoffice/inicio">My Panel</a>
            <button class="btn btn-link btn-sm order-1 order-lg-0" id="sidebarToggle" type="button" data-toggle="collapse" data-target="#sidenavAccordion" aria-controls="sidenavAccordion" aria-expanded="false" aria-label="Toggle navigation"><i class="fas fa-bars"></i></button>
            <!-- Navbar-->
            <ul class="navbar-nav ml-auto ml-md-0">
                <li class="nav-item">
                      <a class="dropdown-item text-white" href="logout">Close Session</a>                 
                </li>
            </ul>
        </nav>
        
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            
                            <a class="nav-link" href="inicio">
                                <div class="sb-nav-link-icon"><i class="fa fa-globe"></i></div>
                                Index
                            </a>
                            <a class="nav-link" href="views/backoffice/inicio">
                                <div class="sb-nav-link-icon"><i class="fa fa-tachometer-alt"></i></div>
                                Mi Panel
                            </a>
                            <a class="nav-link" href="views/backoffice/preguntas">
                                <div class="sb-nav-link-icon"><i class="fa fa-check-square-o"></i></div>
                                Preguntas Aprobadas
                            </a>
                            <a class="nav-link" href="views/backoffice/preguntas?aprobadas=0">
                                <div class="sb-nav-link-icon"><i class="fa fa-check-circle-o"></i></div>
                                Pendientes de Aprobar
                            </a>
                            <a class="nav-link" href="views/backoffice/agregar-pregunta">
                                <div class="sb-nav-link-icon"><i class="fa fa-plus-circle"></i></div>
                                Agregar Pregunta
                            </a>
                            <c:if test="${sessionScope.user_login.name eq 'admin'}">
			            		<a class="nav-link" href="views/backoffice/category-table-control">
				            		<div class="sb-nav-link-icon"><i class="fa fa-plus-circle"></i></div>
				            		Tabla de Categorias
			            		</a>
			            	</c:if>
                         </div>   
                    </div>       
                </nav>
            </div>
            
            
            <!-- CONTENIDO PRINCIPAL -->
             <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid mb-3">
                    	<%@ include file="alerts.jsp" %>
					