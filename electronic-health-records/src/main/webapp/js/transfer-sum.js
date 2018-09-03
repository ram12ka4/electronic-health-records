$(function() {

	var arr = new Array();

	var select = $(".sel-prev-record");
	select.css("display", "inline");

	$('.update-btn').remove();

	select
			.empty()
			.append(
					'<option selected="selected" value="0" disabled = "disabled">Loading.....</option>');

	var req = $
			.ajax({
				url : 'transfer.do',
				type : 'post',
				datatype : 'text',
				data : {
					ACTION : 'GET_PREV_ED_NO',
					paramType : "TSNS",

				},
				success : function(data) {
					
					data = data.replace(/^\W+|\W+$/g, "");
					//alert(data.length);

					if (data.length !== 0) {
						arr = data.replace("[", "").replace("]", "").split(",");
						select
								.empty()
								.append(
										'<option selected="selected" value="0">Select Previous Record</option>');
						var i = 0;

						while (i < arr.length) {

							select.append($("<option></option>").val(arr[i])
									.html(arr[i] + " <------> " + arr[i + 1]));
							i += 3;
						}
					} else {
						select.css("display", "none");
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

	$(document).on('click', '.submit-btn', function(e) {

		// alert('Submit Button');

		e.preventDefault();

		$('.confirmModal').modal();

	});

	$(document).on('click', '.confirm-btn', function() {
		// alert("Final confirm");
		$('.circleModal').modal({
			backdrop : 'static',
			keyboard : false
		});
		$('.confirmModal').modal('hide');
		document.getElementById("transfer-frm").method = "post";
		document.getElementById("transfer-frm").action = "transfer.do";
		document.getElementById("transfer-frm").submit();
	});

	$(document).on('click', '.previous-btn', function() {

		document.getElementById("transfer-frm").method = "get";
		document.getElementById("transfer-frm").action = "transfer.report";
		document.getElementById("transfer-frm").target = "_blank";
		document.getElementById("transfer-frm").submit();

	});

	$(document)
			.on(
					'change',
					'.sel-prev-record',
					function() {
						var edId = $.trim($(this).val());
						var arr = new Array();
						var index = 0;

						var req = $
								.ajax({
									url : 'transfer.do',
									type : 'post',
									datatype : 'json',
									data : {
										ACTION : 'GET_PREV_TRANSFER_RECORD',
										paramType : 'TSNS',
										edNo : edId
									},
									success : function(data) {

										// alert(data);
										arr = data.replace("{", "").replace(
												"}", "").split(",");

										// alert("Length : " + arr.length);

										if (arr.length === 1) {

											resetValue();
											$('.remove_field').trigger('click');

											$('.update-btn').remove();
											$('.add-btn')
													.append(
															'<button type="button" class="btn btn-primary submit-btn">Submit</button>');

										} else {

											// alert(" UPdate div exists : " +
											// $(".update-btn").length);

											if ($(".update-btn").length === 0) {
												$('.submit-btn').remove();
												$('.add-btn')
														.append(
																'<button type="button" class="btn btn-primary update-btn">Update</button>');
											}

											$('.form-control')
													.each(
															function() {

																var string = $(
																		this)
																		.attr(
																				'name');

																// alert(string);

																if (string !== undefined
																		&& string !== null
																		&& string.length) {

																	var i;

																	for (i = 0; i < arr.length; i++) {

																		var keyValue = arr[i]
																				.split("=");

																		var key = keyValue[0]
																				.trim();
																		var value = keyValue[1]
																				.trim();

																		var expr = string
																				.trim();

																		if (key
																				.localeCompare(expr) == 0) {
																			// alert("Compared
																			// Key
																			// : "
																			// +
																			// key);

																			var otherValue = new Array();

																			if (key
																					.localeCompare("TS017") == 0) {
																				// alert("In
																				// If
																				// clause");
																				otherValue = value
																						.replace(
																								"[",
																								"")
																						.replace(
																								"]",
																								"")
																						.split(
																								"-");
																				var j;

																				$(
																						'.remove_field')
																						.trigger(
																								'click');

																				for (j = 0; j < otherValue.length; j++) {
																					// alert("Counter
																					// : "
																					// +
																					// j);

																					$(
																							'.add-field-button')
																							.trigger(
																									'click',
																									[ otherValue[j] ]);

																				}
																			} else {
																				// alert("In
																				// else
																				// clause");
																				$(
																						this)
																						.val(
																								value);
																			}

																		}
																	}
																}
															});

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

					});

	var y = 1;

	$('.add-field-button')
			.on(
					'click',
					function(e, value) {
						// alert("Value is : " + value);
						e.preventDefault();
						if (y < 10) {
							y++;
							if (value != undefined) {
								$('.input-field-wrap')
										.append(

												'<div class="form-group "><div class="col-xs-3"></div><div class="col-xs-3"><input type="text" class="form-control input-sm" value=\"'
														+ value.trim()
														+ '\" id="speciality" name="TS017" placeholder="Medical Problems"></div><a class="btn btn-warning btn-sm remove_field">Remove</a></div>');

								$('.remove_field').attr('disabled', true);

							} else {
								$('.input-field-wrap')
										.append(

												'<div class="form-group "><div class="col-xs-3"></div><div class="col-xs-3"><input type="text" class="form-control input-sm" id="speciality" name="TS017" placeholder="Medical Problems"></div><a href="#" class="btn btn-warning btn-sm remove_field">Remove</a></div>'); // add

							}
						}
					});

	$('.input-field-wrap').on("click", '.remove_field', function(e) {
		// alert("remove done");
		e.preventDefault();
		$(this).parent('div').remove();
		y--;
	});

	function resetValue() {
		$('#transfer-frm').trigger('reset');
	}

	$(document)
			.on(
					'click',
					'.reset-btn',
					function(e) {
						e.preventDefault();
						resetValue();

						// alert("No of submit button : " +
						// $('.submit-btn').length);

						if ($('.submit-btn').length === 0) {
							$('.update-btn').remove();
							$('.add-btn')
									.append(
											'<button type="button" class="btn btn-primary submit-btn">Submit</button>');
						}

						$('.remove_field').trigger('click');
					});

	$(document)
			.on(
					'click',
					'.update-btn',
					function(e) {
						// transfer-frm
						e.preventDefault();

						var frm = $('#transfer-frm');

						// alert(frm);

						var selectVal = $.trim($('.sel-prev-record').val());

						// alert(selectVal.trim());

						var container = document
								.getElementById("hidden-container");
						var input1 = document.createElement("input");
						var input2 = document.createElement("input");

						input1.type = "hidden";
						input1.name = "ACTION";
						input1.setAttribute("value", "UPDATE_RECORD");

						input2.type = "hidden";
						input2.name = "edNo";
						input2.setAttribute("value", selectVal);

						container.appendChild(input1);
						container.appendChild(input2);

						var req = $
								.ajax({

									url : 'transfer.do',
									datatype : 'text',
									type : 'post',
									data : frm.serialize(),

								})
								.done(
										function(data) {

											// log data to the console
											// alert(data);

											if ($.trim(data).localeCompare(
													'true') == 0) {
												swal(
														"Well done!",
														'Data have been updated successfully',
														"success");
												resetValue();
												$('.remove_field').trigger(
														'click');
											} else {
												swal("Oh no!",
														'Something went wrong',
														"error");
											}

										});

						/*
						 * var container =
						 * document.getElementById("hidden-container"); var
						 * input1 = document.createElement("input"); var input2 =
						 * document.createElement("input"); input1.type =
						 * "hidden"; input1.name = "ACTION";
						 * input1.setAttribute("value", "UPDATE_RECORD");
						 * 
						 * input2.type = "hidden"; input2.name = "edNo";
						 * input2.setAttribute("value", selectVal);
						 * 
						 * container.appendChild(input1);
						 * container.appendChild(input2);
						 * 
						 * 
						 * document.getElementById("transfer-frm").method =
						 * "post";
						 * document.getElementById("transfer-frm").action =
						 * "transfer.do";
						 * document.getElementById("transfer-frm").submit();
						 */

					});

});