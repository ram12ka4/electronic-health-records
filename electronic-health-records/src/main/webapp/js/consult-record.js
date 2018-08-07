$(function() {

	$('.date-of-consult').datepicker().datepicker('setDate', new Date());

	$(document)
			.on(
					'click',
					'.submit-btn',
					function(e) {

						e.preventDefault();

						if ($('input[name=CR002]').val() === "") {

							$('#myAlert')
									.addClass(
											'alert alert-danger alert-dismissible')
									.html(
											'<Strong>Required!</Strong> Please mention Doctor Name');
							$('.alertModal').modal();

						} else if ($('textarea[name=CR006]').val() === "") {

							$('#myAlert')
									.addClass(
											'alert alert-danger alert-dismissible')
									.html(
											'<Strong>Required!</Strong> Please mention clinical notes');
							$('.alertModal').modal();

						} else if ($('textarea[name=CR007]').val() === "") {

							$('#myAlert')
									.addClass(
											'alert alert-danger alert-dismissible')
									.html(
											'<Strong>Required!</Strong> Please mention consultant\'s opinion');
							$('.alertModal').modal();
						} else {
							$('.confirmModal').modal();
						}

					});

	$(document).on('click', '.confirm-btn', function() {

		alert("Final confirm");

		$('.circleModal').modal({
			backdrop : 'static',
			keyboard : false
		});

		$('.confirmModal').modal('hide');

		document.getElementById("consult-record-frm").method = "post";
		document.getElementById("consult-record-frm").action = "/consult.do";
		document.getElementById("consult-record-frm").submit();

	});

	$(document).on(
			'click',
			'.previous-btn',
			function() {

				alert("previous Btn");

				$('.myModal .modal-body').load(
						'/consult.do?ACTION=PREVIOUS_CONSULT_RECORDS',
						function() {

							$('.myModal').modal({
								show : true
							});
						});
			});

	$(document).on('click', '.del-link', function() {
		var id = $(this).data('id');
		alert("ED ID :" + id);
		$('.deleteModal .delete-btn').attr('ed-id', id);
		$('.myModal').modal('hide');
		$('.deleteModal').modal({
			show : true
		});
	});

	$(document).on('click', '.deleteModal .close-btn', function() {

		$('.deleteModal').modal('hide');
		$('.previous-btn').trigger('click');
	});
	
	$(document).on('click', '.deleteModal .delete-btn', function() {
		
		alert("Delete Doctor Order");
		var id = $(this).attr('ed-id');
		alert(id);

		req = $.ajax({
			dataType: "html",
			url : '/consult.do',
			type : 'POST',
			data : {
				emrDetNo : id,
				ACTION : "DEL_ORDER"
			},
			success: function(data){
				//alert(data);
				swal("Well done!", 'Doctor order deleted successfully.', "success");
				$('.deleteModal').modal('hide');
				$('.previous-btn').trigger('click');
			},
			error: function(data) {
				//alert(data);
				swal("Oh no!", 'something went wrong.', "error");
			}
		});
	});

});
