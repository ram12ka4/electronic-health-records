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

					/*
					 * $('#fixedColumnExample').DataTable({ scrollY : "300px",
					 * scrollX : true, scrollCollapse : true, paging : false,
					 * fixedColumns : { leftColumns : 1 } });
					 */

					function CSSDone() {
						alert("CSS loaded");
						$("th, td").css({
							"white-space" : "nowrap"
						});
						alert("next loaded");
						$("div.dataTables_wrapper").css({
							"width" : "800px",
							"margin" : "0 auto"
						});
					}

					$(document)
							.on(
									'click',
									'.previous-btn',
									function() {

										var myArr = new Array();

										myArr = [];

										$('.form-control')
												.each(
														function() {

															var string = $(this)
																	.attr(
																			'name');

															// alert(string);

															if (string !== undefined
																	&& string !== null
																	&& string.length) {

																if (string
																		.indexOf("IS") >= 0) {
																	// var value
																	// =
																	// string.includes("IS");
																	myArr
																			.push(string);
																}
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

															var table = $(
																	'#fixedColumnExample')
																	.DataTable(
																			{
																				scrollY : "50vh",
																				scrollX : true,
																				scrollCollapse : true,
																				paging : false,
																				fixedColumns : true

																			});
															/*
															 * table
															 * .fixedColumns(true);
															 */

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