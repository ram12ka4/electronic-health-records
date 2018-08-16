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

					$('#fixedColumnExample').DataTable({
						fixedColumns : {
							leftColumns: 0
						}, 
						scrollY : 300,
						scrollX : true,
						scrollCollapse : true,
						paging : false,
						fixedColumns:   true
					});


					

					
					$(document)
							.on(
									'click',
									'.previous-btn',
									function() {

										var myArr = new Array();

										$('.form-control').each(function() {

											var value = $(this).attr('name');

											if (value.includes('IS')) {
												myArr.push(value);
											}
										});

										alert("Arry List is : " + myArr);

										$('.myModal .modal-body')
												.load(
														'/invest.do',
														{
															ACTION : 'FETCH_PARAM_NAME',
															paramList : JSON
																	.stringify(myArr)
														},
														function(response,
																status, xhr) {

															if (status === 'error') {
																var msg = "Sorry but there was asn error: ";
																alert(response);
																swal(
																		"Oh no!",
																		msg
																				+ xhr.status
																				+ " "
																				+ xhr.statusText,
																		"error");
															}
															
														
															$('.circleModal')
																	.modal(
																			'hide');

															$(
																	'.myModal .modal-title')
																	.html(
																			"Previous Investigation Record");
															
															$('.myModal')
																	.modal(
																			{
																				show : true
																			});

														});
									});

				});