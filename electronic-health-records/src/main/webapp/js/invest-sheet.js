$(document)
		.ready(
				function() {

					var arr = new Array();
					var select = $("#blood-group");
					select
							.empty()
							.append(
									'<option selected="selected" value="0" disabled = "disabled">Loading.....</option>');

					var req = $
							.ajax({
								url : "/invest.do",
								type : "post",
								datatype : "text",
								/*
								 * contentType : "application/json;
								 * charset=utf-8", mimeType :
								 * 'application/json',
								 */
								// data :
								// JSON.stringify("GET_BLOOD_GRP_DATA&BG"),
								data : {
									ACTION : "GET_BLOOD_GRP_DATA",
									paramType : "BG"
								},
								success : function(data) {

									// alert("success");
									arr = data.replace("[", "")
											.replace("]", "").split(",");
									// alert(arr.length);

									select
											.empty()
											.append(
													'<option selected="selected" value="0">Please select</option>');

									var i;

									for (i = 0; i < arr.length; i++) {
										/*
										 * var option = document
										 * .createElement("option"); option.text =
										 * option.value = arr[i];
										 * select.add(option)
										 */
										select.append($("<option></option>")
												.val(arr[i]).html(arr[i]));
									}

								},
								failure : function(data) {
									alert("failure");
									alert(data.responseText);
								},
								error : function(data) {
									alert("error");
									alert(data.responseText);
								}
							});

					$(document).on('click', '.submit-btn', function(e) {

						alert('Submit Button');

						e.preventDefault();

						$('.confirmModal').modal();

					});

					$(document)
							.on(
									'click',
									'.confirm-btn',
									function() {

										alert("Final confirm");

										$('.circleModal').modal({
											backdrop : 'static',
											keyboard : false
										});

										$('.confirmModal').modal('hide');

										document.getElementById("invest-frm").method = "post";
										document.getElementById("invest-frm").action = "/invest.do";
										document.getElementById("invest-frm")
												.submit();

									});

					$(document)
							.on(
									'click',
									'.previous-btn',
									function() {
										
										$('.form-control').each(function(){
											
											var value = $(this).attr('name');
											alert("Input Element name " + value);
											
											if ()
										});
										
										
										

										$('.circleModal').modal({
											backdrop : 'static',
											keyboard : false
										});

										// alert("previous Btn");

										$('.myModal .modal-body')
												.load(
														'/invest.do?ACTION=PREVIOUS_INVEST_RECORDS',
														function() {

															$('.circleModal')
																	.modal(
																			'hide');
															// alert("paginationHandler
															// function
															// called");
															$(
																	'.myModal .modal-title')
																	.html(
																			"Previous Investigation Record");
															paginationHandler();
															$('.myModal')
																	.modal(
																			{
																				show : true
																			});
														});
									});

				});