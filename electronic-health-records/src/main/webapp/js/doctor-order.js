$(function() {

	$('#fromDate').datepicker().datepicker('setDate', new Date());
	var medicine = document.getElementsByName('DO003[]');
	var lab = document.getElementsByName('DO004[]');

	function medicineValidation() {

		var flag = false;

		for (i = 0; i < medicine.length; i++) {

			if (medicine[i].value !== "") {
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

						}/*
							 * else if (medicineValidation() === false) {
							 * $('#myAlert') .addClass( 'alert alert-danger
							 * alert-dismissible') .html( '<Strong>Required!</Strong>
							 * Please mention medicine');
							 * $('#alert-box').modal(); } else if
							 * (laboratoryValidation() === false) {
							 * $('#myAlert') .addClass( 'alert alert-danger
							 * alert-dismissible') .html( '<Strong>Required!</Strong>
							 * Please mention lab test');
							 * $('#alert-box').modal(); } else if
							 * ($('textarea[name=DO005]').val() === "") {
							 * 
							 * $('#myAlert') .addClass( 'alert alert-danger
							 * alert-dismissible') .html( '<Strong>Required!</Strong>
							 * Please mention diet'); $('#alert-box').modal(); }
							 */else if ($('textarea[name=DO002]').val() === "") {

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
		alert('Submit form section')

		document.getElementById("doctor-order-frm").method = "post";
		document.getElementById("doctor-order-frm").action = "/docorder.do";
		document.getElementById("doctor-order-frm").submit();

	});

});