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
												.getElementById("doctor-note-frm").action = "docnote.do";
										document.getElementById("doctor-note-frm").submit();

									});
									
						/*
						 * Doctor Previos record modal 
						 */
									
					$(document).on('click', '.previousBtn', function(){
						
						var id = $(this).data('id');
						//alert(id);
						
						$('.myModal .modal-body').load('docnote.do?ACTION=FETCH_DOCTOR_NOTE&ip_no='+id, function(){
							
							$('.myModal').modal({show:true});
						});
					});
					
					$(document).on('click', '.doctor-edit-button', function(){
						
						var id = $(this).attr('data-id');
						alert(id);
						$('.editModal .update-note').attr('data-id', id);
						
						$('.editModal .modal-body').load('docnote.do?ACTION=FETCH_DOCTOR_NOTE_BY_ED_ID&ed_no='+id, function(){
							$('.myModal').modal('hide');
							$('.editModal').modal({show:true});
						});
					});
					

					$(document).on('click','.doctor-del-button', function() {
						var id = $(this).data('id');
						//alert("ED ID :" + id);
						$('.deleteModal .delete_note').attr('ed_id', id);
						$('.myModal').modal('hide');
						$('.deleteModal').modal({show:true});
					});
					
					$(document).on('click', '.deleteModal .close-note', function(){
						
						$('.deleteModal').modal('hide');
						$('.previousBtn').trigger('click');
					});
					
					$(document).on('click', '.editModal .close-note', function(){
						
						$('.editModal').modal('hide');
						$('.previousBtn').trigger('click');
					});

					
					$(document).on('click', '.delete_note', function() {
						
						//alert("Delete Doctor Note");
						var id = $(this).attr('ed_id');
						//alert(id);

						req = $.ajax({
							dataType: "html",
							url : 'docnote.do',
							type : 'POST',
							data : {
								emrDetNo : id,
								ACTION : "DEL_NOTE"
							},
							success: function(data){
								//alert(data);
								swal("Well done!", 'Doctor note deleted successfully.', "success");
								$('.deleteModal').modal('hide');
								$('.previousBtn').trigger('click');
							},
							error: function(data) {
								//alert(data);
								swal("Oh no!", 'something went wrong.', "error");
							}
						});
					});
					
					$(document).on('click', '.editModal .update-note', function() {
						
						alert("Update Doctor Note");
						var id = $(this).attr('data-id');
						var note = $('.updated-note').val();
						//var note = $('#updated-note').val();
						alert(id + " " + note);
						

						req = $.ajax({
							dataType: "html",
							url : 'docnote.do',
							type : 'POST',
							data : {
								emrDetNo : id,
								doctor_note: note,
								ACTION : "UPDATE_NOTE"
							},
							success: function(data){
								//alert(data);
								swal("Well done!", 'Doctor note updated successfully.', "success");
								$('.editModal').modal('hide');
								$('.previousBtn').trigger('click');
							},
							error: function(data) {
								//alert(data);
								swal("Oh no!", 'something went wrong.', "error");
							}
						});
					});

					$(document).on('click', '.doctor-edit-button', function() {
						var id = $(this).data('id');
						//alert(id);

					});

				});
