<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<jsp:include page="../../include/frontoffice-header.jsp">
	<jsp:param name="page" value="index" />
	<jsp:param name="title" value="Index" />
</jsp:include>
<jsp:include page="../../include/frontoffice-navbar.jsp" />
    

	<h2>Mis preguntas</h2>
	<div class="row">
	
	    <div class="col-xl-3 col-md-6">
	        <div class="card bg-primary text-white mb-4">
	            <div class="card-body">Aprobadas <span class="big-number">${aprobadas}</span></div>
	            <div class="card-footer d-flex align-items-center justify-content-between">
	                <a class="small text-white stretched-link" href="views/frontoffice/preguntas">Ver detalles</a>
	                <div class="small text-white"><i class="fas fa-angle-right"></i></div>
	            </div>
	        </div>
	    </div>
	    
	    <div class="col-xl-3 col-md-6">
	        <div class="card bg-danger text-white mb-4">
	            <div class="card-body">Pendientes <span class="big-number">${pendientes}</span></div>
	            <div class="card-footer d-flex align-items-center justify-content-between">
	                <a class="small text-white stretched-link" href="views/frontoffice/preguntas?aprobadas=0">Ver detalles</a>
	                <div class="small text-white"><i class="fas fa-angle-right"></i></div>
	            </div>
	        </div>
	    </div> 
	    
	   <div class="col-xl-3 col-md-6">
	        <div class="card bg-secondary teesxt-white mb-4">
	            <div class="card-body">Total <span class="big-number">${total}</span></div>
	            <div class="card-footer d-flex align-items-center justify-content-between">
	                <a class="small text-white stretched-link" href="views/frontoffice/preguntas?total=0">Ver detalles</a>
	                <div class="small text-white"><i class="fas fa-angle-right"></i></div>
	            </div>
	        </div>
	    </div>
	</div>
	
	<h2>Preguntas de todos los usuarios</h2></br>
	<a href="views/frontoffice/preguntas?total=1">Ver todas las preguntas aprobadas</a>
                        
  
<jsp:include page="../../include/office-footer.jsp" />