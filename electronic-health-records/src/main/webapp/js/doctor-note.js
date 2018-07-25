$(document)
		.ready(
				function() {
					
					$('#fromDate').datepicker().datepicker("setDate", new Date());
					
					$('button[name=doctor_note_submit]')
							.click(
									function(e) {

										//alert("ram");

										e.preventDefault();

										if ($('input[name=DN003]').val() === "") {
											var id = $('input[name=DN003]')
													.attr("id");

											//alert(id);
											$('#' + id).focus();
											$('#myAlert')
													.addClass(
															'alert alert-danger alert-dismissible')
													.html(
															'<Strong>Required!</Strong> Please pick current date');
											$('#alert-box').modal();

										} else if ($('input[name=DN001]').val() === "") {
											var id = $('input[name=DN001]')
													.attr("id");

											$('#myAlert')
													.addClass(
															'alert alert-danger alert-dismissible')
													.html(
															'<Strong>Required!</Strong> Please mention doctor name');
											$('#alert-box').modal();

											$('#' + id).focus();

										} else if ($('textarea[name=DN004]')
												.val() === "") {
											$('textarea[name=DN004]').focus();

											$('#myAlert')
													.addClass(
															'alert alert-danger alert-dismissible')
													.html(
															'<Strong>Required!</Strong> Please mention note');
											$('#alert-box').modal();
										} else {
											$('#confirm-box').modal();
										}

									});
					
					$('button[name=confirm]').click(function(){
						//alert('Button');
					
						$('#circle').modal({
							backdrop: 'static',
							keyboard: false
						});
						
						$('#confirm-box').modal('hide');
						
						document.getElementById("doctor-note-frm").method = "post";
						document.getElementById("doctor-note-frm").action = "/docnote.do";
						document.getElementById("doctor-note-frm").submit(); 
						
					});
					

				});
