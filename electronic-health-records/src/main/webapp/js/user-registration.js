$(document).ready(function() {

	
	
	
	
	$.post('gnrc-user-reg-control.jsp',
	{
		token : 'TI',
		msg : 'TITLE'
	}, function(data, status, jqXHR) {
		alert('status:' + status + ', data: ' + data);
	});

});