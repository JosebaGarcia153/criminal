<jsp:include page ="../../include/header.jsp">
	<jsp:param name="pagina" value="login" />
	<jsp:param name="title" value="Login" />
</jsp:include>

<form action="login" method="post">
	<div class="form-group">
	<label for="nombre">Nombre:</label>
	<input type="text" name="nombre" value="${nombre}" autofocus placeholder="Inserta tu usuario">
	</div>
	<br/>
	<div class="form-group">
	<label for="password">Contraseña:</label>
	<input type="password" id="password" name="password" placeholder="Insterta tu contraseña">
	</div>
	<br/>
        
	<input type="submit" value="Login" class="btn btn-primary">
</form>
<br/>
<a href="views/login/sign.jsp">Crear Usuario</a>

<%@include file="../../include/footer.jsp" %>