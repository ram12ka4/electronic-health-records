$(function() {

	$("#txtEditor").Editor();

	$('#timepicker').mdtimepicker().on('timechanged', function(e) {

		console.log(e.value);

		console.log(e.time);

	});

});