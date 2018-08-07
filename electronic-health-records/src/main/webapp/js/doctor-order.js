$(function() {

	$('#fromDate').datepicker().datepicker('setDate', new Date());
	var medicine = document.getElementsByName('DO003');
	var lab = document.getElementsByName('DO004');

	function medicineValidation() {

		var flag = false;

		for (i = 0; i < medicine.length; i++) {

			if (medicine[i].value !== "") {
				alert('Completed All fields');
				alert(medicine[i].value);
				flag = true;
			} else {
				alert('Misssing data');
				flag = false;
			}
		}

		if (flag === true) {
			return true;
		} else {
			return false;
		}

	}

	function laboratoryValidation() {

		var flag = false;

		for (i = 0; i < lab.length; i++) {

			if (lab[i].value !== "") {
				alert('Completed All fields');
				flag = true;
			} else {
				alert('Misssing data');
				flag = false;
			}
		}

		if (flag === true) {
			return true;
		} else {
			return false;
		}

	}

	$(document)
			.on(
					'click',
					'.submit-btn',
					function(e) {

						alert('Submit Button');

						e.preventDefault();

						if ($('input[name=DO001]').val() === "") {

							$('#myAlert')
									.addClass(
											'alert alert-danger alert-dismissible')
									.html(
											'<Strong>Required!</Strong> Please mention OCCU');
							$('#alert-box').modal();

						} else if ($('input[name=DN001]').val() === "") {

							$('#myAlert')
									.addClass(
											'alert alert-danger alert-dismissible')
									.html(
											'<Strong>Required!</Strong> Please mention doctor name');
							$('#alert-box').modal();

						} else if ($('textarea[name=DO002]').val() === "") {

							$('#myAlert')
									.addClass(
											'alert alert-danger alert-dismissible')
									.html(
											'<Strong>Required!</Strong> Please mention treatment');
							$('#alert-box').modal();
						} else {
							$('#confirm-box').modal();
						}

					});

	/*
	 * Submit Form
	 */
	$(document).on('click', '.confirm', function() {

		alert("Final confirm");

		$('#circle').modal({
			backdrop : 'static',
			keyboard : false
		});

		$('#confirm-box').modal('hide');

		var isVisitDoc = $('input[name=DO008]').is(':checked');
		var container = document.getElementById("hidden-container");
		var input = document.createElement("input");
		input.type = "hidden";
		input.name = "DO008";
		if (isVisitDoc === true) {
			input.setAttribute("value", "YES");
			container.appendChild(input);
		} else {
			input.setAttribute("value", "NO");
			container.appendChild(input);
		}

		document.getElementById("doctor-order-frm").method = "post";
		document.getElementById("doctor-order-frm").action = "/docorder.do";
		document.getElementById("doctor-order-frm").submit();

	});

	$(document).on(
			'click',
			'.previousBtn',
			function() {

				alert("previous Btn");

				$('.myModal .modal-body').load(
						'/docorder.do?ACTION=DOCTOR_PREVIOUS_ORDERS',
						function() {

							$('.myModal').modal({
								show : true
							});
						});
			});

	$(document).on('click', '.order-del', function() {
		var id = $(this).data('id');
		alert("ED ID :" + id);
		$('.deleteModal .delete-order').attr('ed-id', id);
		$('.myModal').modal('hide');
		$('.deleteModal').modal({
			show : true
		});
	});
	
	$(document).on('click', '.deleteModal .close-order', function(){
		
		$('.deleteModal').modal('hide');
		$('.previousBtn').trigger('click');
	});
	
	$(document).on('click', '.delete-order', function() {
		
		alert("Delete Doctor Order");
		var id = $(this).attr('ed-id');
		alert(id);

		req = $.ajax({
			dataType: "html",
			url : '/docorder.do',
			type : 'POST',
			data : {
				emrDetNo : id,
				ACTION : "DEL_ORDER"
			},
			success: function(data){
				//alert(data);
				swal("Well done!", 'Doctor order deleted successfully.', "success");
				$('.deleteModal').modal('hide');
				$('.previousBtn').trigger('click');
			},
			error: function(data) {
				//alert(data);
				swal("Oh no!", 'something went wrong.', "error");
			}
		});
	});
	
	

});