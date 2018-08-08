$(function() {

	/*
	 * Pagination Code
	 */

	function paginationHandler() {
		// alert("Ram");
		// store pagination container so we only select it once
		var $paginationContainer = $(".pagination-container"), $pagination = $paginationContainer
				.find('.pagination ul');
		// click event
		$pagination
				.find("li a")
				.on(
						'click.pageChange',
						function(e) {
							e.preventDefault();
							// get parent li's data-page attribute and current
							// page
							var parentLiPage = $(this).parent('li')
									.data("page"), currentPage = parseInt($(
									".pagination-container div[data-page]:visible")
									.data('page')), numPages = $paginationContainer
									.find("div[data-page]").length;
							// make sure they aren't clicking the current page
							if (parseInt(parentLiPage) !== parseInt(currentPage)) {
								// hide the current page
								$paginationContainer.find(
										"div[data-page]:visible").hide();
								if (parentLiPage === '+') {
									// next page
									$paginationContainer
											.find(
													"div[data-page="
															+ (currentPage + 1 > numPages ? numPages
																	: currentPage + 1)
															+ "]").show();
								} else if (parentLiPage === '-') {
									// previous page
									$paginationContainer.find(
											"div[data-page="
													+ (currentPage - 1 < 1 ? 1
															: currentPage - 1)
													+ "]").show();
								} else {
									// specific page
									$paginationContainer.find(
											"div[data-page="
													+ parseInt(parentLiPage)
													+ "]").show();
								}
							}
						});
	};

	// $(document).ready(paginationHandler);

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
				
				$('.circleModal').modal({
					backdrop : 'static',
					keyboard : false
				});

				// alert("previous Btn");

				$('.myModal .modal-body').load(
						'/consult.do?ACTION=PREVIOUS_CONSULT_RECORDS',
						function() {
							
							$('.circleModal').modal('hide');
							// alert("paginationHandler function called");
							$('.myModal .modal-title').html(
									"Previous Consultation Record");
							paginationHandler();
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

	$(document)
			.on(
					'click',
					'.deleteModal .delete-btn',
					function() {

						alert("Delete Doctor Order");
						var id = $(this).attr('ed-id');
						alert(id);

						req = $
								.ajax({
									dataType : "html",
									url : '/consult.do',
									type : 'POST',
									data : {
										emrDetNo : id,
										ACTION : "DEL_CONSULTANT"
									},
									success : function(data) {
										// alert(data);
										swal(
												"Well done!",
												'Doctor\'s consultation deleted successfully.',
												"success");
										$('.deleteModal').modal('hide');
										$('.previous-btn').trigger('click');
									},
									error : function(data) {
										// alert(data);
										swal("Oh no!", 'something went wrong.',
												"error");
									}
								});
					});
	
	
	
	

});
