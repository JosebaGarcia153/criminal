// ejecuta la funcion cuando todo el documento de html DOM este listo y cargado

$(document).ready(function() {
    // seleccion por id => #example y ejecuta el plugin .DataTable();
	$('#table').DataTable();
});

function confirmar(nombre) {
	
	// El metodo confirm() devuelve verdadero si el usuario ha pulsado "OK" y falso de lo contrario. 
	if ( confirm('Are you sure you want to delete ' + nombre + '?') ){
		
		console.debug(' continua el evento por defecto del ancla ');
		
	}else {
		console.debug(' prevenimos o detenemos el evento del ancla ');
		event.preventDefault();
	}	
}