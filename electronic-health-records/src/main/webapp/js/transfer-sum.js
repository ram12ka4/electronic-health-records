$(function() {

	$(document).on('click', '.submit-btn', function(e) {

		alert('Submit Button');

		e.preventDefault();

		$('.confirmModal').modal();

	});

	$(document).on('click', '.confirm-btn', function() {
		alert("Final confirm");
		$('.circleModal').modal({
			backdrop : 'static',
			keyboard : false
		});
		$('.confirmModal').modal('hide');
		document.getElementById("transfer-frm").method = "post";
		document.getElementById("transfer-frm").action = "transfer.do";
		document.getElementById("transfer-frm").submit();
	});

	$(document).on('click', '.previous-btn', function() {

		
		document.getElementById("transfer-frm").method = "get";
		document.getElementById("transfer-frm").action = "transfer.report";
		document.getElementById("transfer-frm").target = "_blank";
		document.getElementById("transfer-frm").submit();

	});

});