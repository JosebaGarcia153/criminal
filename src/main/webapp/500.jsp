<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page isErrorPage="true" %>

<jsp:include page="include/header.jsp" >
  <jsp:param name="pagina" value="500" />
  <jsp:param name="title" value="500" /> 
</jsp:include>


<div class="jumbotron">
  <h1 class="display-4">500</h1>
  <p class="lead">Disculpas. Ha habido un ERROR.</p>
  <hr class="my-4">
  <p>Por favor contacta con un administrador.</p>
  <hr class="my-4">
  <a class="btn btn-primary btn-lg" href="index.jsp" role="button">Volver al Index</a>
</div>

<%@include file="include/footer.jsp" %>