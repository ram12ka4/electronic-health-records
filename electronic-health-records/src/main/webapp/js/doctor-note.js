$(document)
		.ready(
				function() {

					var now = moment();
					// $('.days-dash').text(now.fromNow());
					$('.days-dash').text(now.format("lll"));
					$('#fromDate').datepicker().datepicker("setDate",
							new Date());

					/*
					 * for (var i = 0; i < 200; i++) { $('.modal-body').append(i + '<br>'); }
					 */

					$('button[name=doctor_note_submit]')
							.click(
									function(e) {

										// alert("ram");

										e.preventDefault();

										if ($('input[name=DN003]').val() === "") {
											var id = $('input[name=DN003]')
													.attr("id");

											// alert(id);
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

					$('button[name=confirm]')
							.click(
									function() {
										// alert('Button');

										$('#circle').modal({
											backdrop : 'static',
											keyboard : false
										});

										$('#confirm-box').modal('hide');

										document
												.getElementById("doctor-note-frm").method = "post";
										document
												.getElementById("doctor-note-frm").action = "/docnote.do";
										document.getElementById(
												"doctor-note-frm").submit();

									});
					
		
					
					$('.openBtn').on('click', function(){
						
						var id = $(this).attr('data-id');
						alert(id);
						
						$('#myModal .modal-body').load('/docnote.do?ACTION=FETCH_DOCTOR_NOTE&ip_no='+id, function(){
							
							$('#myModal').modal({show:true});
							
						});
						
						
					});
					

					$('#myButton').on('click', function() {
						var myId = $(this).attr('data-id');
						alert("ED ID :" + myId);
						$('.delete_note').attr('ed_id', myId);
					});

					$('.delete_note').on('click', function() {
						alert("Delete Doctor Note");
						var ed_id = $(this).attr('ed_id');
						alert(ed_id);

						req = $.ajax({
							url : '/docnote.do',
							type : 'POST',
							data : {
								emrDetNo : ed_id,
								ACTION : "DEL_NOTE"
							},
							dataType: "text"
						});
						
						req.done(function(){
							 
							swal("Well done!", 'Doctor note deleted successfully.', "success");
							$('#delete-doctor-note').modal('hide');

							window.location.reload();
							
							
						});
						
						req.error(function(){
							swal("Oh no!", 'something went wrong.', "error");
						});

					});

					$('.doctor-edit-button').on('click', function() {
						var myId = $(this).data('id');
						$('#ed_name').val(myId);

					});

				});
