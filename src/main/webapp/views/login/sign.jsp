<jsp:include page ="../../include/header.jsp">
	<jsp:param name="pagina" value="sign up" />
	<jsp:param name="title" value="Sign Up" />
</jsp:include>

<form action="signup" method="post">
	
	<div class="form-group">
	<label for="nombre">Nombre:</label>
	<input type="text" id="nombre" name="nombre" autofocus value="${nombre}" onkeyUp="searchUser(event)" class="form-control" placeholder="Inserta tu email" required>	
	</div>
	<div class="form-group">
	<label for="password">Contraseña:</label>
	<input type="text" id="password" name="password" class="form-control" placeholder="Inserta una contraseña enter 6 y 15 caracteres" required>
	</div>
	<div class="form-group">
	<label for="repassword">Confirma tu contraseña:</label>
	<input type="text" id="repassword" name="repassword" class="form-control" placeholder="Confirma tu contraseña" required>
	</div>
        
	<input type="submit" id="btnSign" value="Crear" class="btn btn-primary">
</form>


<%@include file="../../include/footer.jsp" %>