$(document).ready(function(e) {

	e.preventDefault();

	var noteDate = $('input[name=note_date]').val();
	var doctorName = $('input[name=doctor_name]').val();
	var note = $('input[name=note]').val();

	if ($("input[name=note_date]").val() === "") {
		alert("Sorry enter a date");
		return false;
	} else if ($("input[name=doctor_name]").val() === "") {
		// alert(password);
		alert("Sorry enter a doctor name");
		return false;
	} else if ($("input[name=note]").val() === "") {
		// alert(password);
		alert("Sorry enter a some on note box");
		return false;
	}

	document.getElementById("doctor_note_form").method = "post";
	document.getElementById("doctor_note_form").action = "/login.do";
	document.getElementById("doctor_note_form").submit();

});
