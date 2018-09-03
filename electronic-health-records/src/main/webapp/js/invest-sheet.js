$(document)
		.ready(
				function() {

					var arr = new Array();
					var arr1 = new Array();
					var select = $("#blood-group");
					var select1 = $(".sel-prev-record");
					select1.css("display", "inline");
					$('.update-btn').remove();
					
					select.empty().append('<option selected="selected" value="0" disabled = "disabled">Loading.....</option>');
					select1.empty().append('<option selected="selected" value="0" disabled = "disabled">Loading.....</option>');
					
					
					var req = $.ajax({
						
							url: "invest.do",
							datatype: "text",
							type: "post",
							data: {
								ACTION : 'GET_PREV_ED_NO',
								paramType : "IS",
							},
							success: function(data){
								
								data = data.replace(/^\W+|\W+$/g, "");
								
								
								if (data.length !== 0) {
									arr = data.replace("[", "").replace("]", "").split(",");
									select1
											.empty()
											.append(
													'<option selected="selected" value="0">Select Previous Record</option>');
									var i = 0;

									while (i < arr.length) {

										select1.append($("<option></option>").val(arr[i])
												.html(arr[i] + " <------> " + arr[i + 1]));
										i += 3;
									}
								} else {
									select1.css("display", "none");
								}
							},
							failure : function(data) {
								// alert("failure");
								alert(data.responseText);
							},
							error : function(data) {
								// alert("error");
								alert(data.responseText);
							}
						
						
						
					});
					

					var req = $
							.ajax({
								url : "invest.do",
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

															$('.circleModal').modal('hide');

															var table = $('#fixedColumnExample').DataTable(
																			{
																				scrollY : "300px",
																				scrollX : true,
																				scrollCollapse : true,
																				paging : false,
																				fixedColumns: true																			
																			});
															
																			$('.myModal .modal-title').html("Previous Investigation Record");

															$('.myModal').on('shown.bs.modal', function(){
																table.fixedColumns().update();
																table.fixedColumns().relayout();
															}).modal({show: true});
																
														});
									});
					
					
					$(document).on('change', '.sel-prev-record', function(){
						
						var edNumber = $.trim($(this).val());
						alert(edNumber);
						
						var req = $.ajax({
							
							url: 'invest.do',
							type: 'post',
							datatype: 'text',
							data: {
								ACTION : 'GET_PREV_INVEST_RECORD',
								paramType : 'IS',
								edNo : edNumber
							},
							success: function(data){
								alert(data);
								arr = data.replace("{", "").replace("}", "").split(",");

								// alert("Length : " + arr.length);

								if (arr.length === 1) {
									resetValue();
									//$('.remove_field').trigger('click');
									$('.update-btn').remove();
									$('.add-btn').append('<button type="button" class="btn btn-primary submit-btn">Submit</button>');

								} else {
									// alert(" UPdate div exists : " +
									// $(".update-btn").length);

									if ($(".update-btn").length === 0) {
										$('.submit-btn').remove();
										$('.add-btn').append('<button type="button" class="btn btn-primary update-btn">Update</button>');
									}

									$('.form-control').each(function() {

														var string = $(this).attr('name');

														// alert(string);

														if (string !== undefined && string !== null	&& string.length) {

															var i;

															for (i = 0; i < arr.length; i++) {

																var keyValue = arr[i].split("=");

																var key = keyValue[0].trim();
																var value = keyValue[1].trim();
																var expr = string.trim();

																if (key.localeCompare(expr) == 0) {
																	//alert("Compared Key : " + key);

																	var otherValue = new Array();

																	if (key.localeCompare("IS016") == 0) {
																		alert("In If clause");
																		alert("Value : " + value.length);
																		
																		$('select[name="IS016"] option[value="'+ $.trim(value) +'"]').attr("selected","selected");
																
																	} else {
																		//alert("In else clause");
																		$(this).val(value);
																	}

																}
															}
														}
													});

								}
							},
							failure: function(data){
								
							},
							error: function(data){
								
							}
							
							
							
							
						});
						
						
						
					});
					
					function resetValue() {
						$('#invest-frm').trigger('reset');
					}
					

				});


