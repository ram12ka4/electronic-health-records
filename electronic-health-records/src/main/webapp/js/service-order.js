$(function() {

	$('#fromDate').datepicker().datepicker("setDate", new Date());
	var select = $(".select-box");
	// select.css("display", "inline");

	// alert(id);

	select
			.empty()
			.append(
					'<option selected="selected" value="0" disabled = "disabled">Loading.....</option>');

	var req = $
			.ajax({
				url : 'patient.transfer',
				type : 'post',
				datatype : 'text',
				data : {
					ACTION : 'GET_SERVICE_LIST'
				},
				success : function(data) {

					data = data.replace(/^\W+|\W+$/g, "");
					// alert(data.length);

					if (data.length !== 0) {
						arr = data.replace("[", "").replace("]", "").split(",");
						select
								.empty()
								.append(
										'<option selected="selected" value="0">Select Service Category</option>');
						var i = 0;

						while (i < arr.length) {

							select.append($("<option></option>").val(
									$.trim(arr[i])).html($.trim(arr[i + 1])));
							i += 2;
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

	var table = $('#myTable').DataTable({

		"paging" : false,
		"ordering" : false,
		"info" : false,
		"searching" : false,
		"autoWidth" : false,
		"scrollY" : '50vh',
		"scrollCollapse" : true,
		responsive : {
			details : {
				display : $.fn.dataTable.Responsive.display.modal({
					header : function(row) {
						var data = row.data();
						return 'Details for ' + data[0] + ' ' + data[1];
					}
				}),
				renderer : $.fn.dataTable.Responsive.renderer.tableAll({
					tableClass : 'table'
				})
			}
		},

		"columnDefs" : [ {
			"width" : "2%",
			"targets" : 0

		}, {
			"width" : "27%",
			"targets" : 1
		}, {
			"width" : "7%",
			"targets" : 2
		}, {
			"width" : "10%",
			"targets" : 3
		}, {
			"width" : "5%",
			"targets" : 4
		}, {
			"width" : "10%",
			"targets" : 5
		}, {
			"width" : "10%",
			"targets" : 6
		}, {
			"width" : "10%",
			"targets" : 7
		}, {
			"width" : "5%",
			"targets" : 8
		}, ]

	}).draw(false);

	var counter = 6;

	for (var i = 0; i < counter; i++) {

		table.row
				.add(
						[
								'<div class="pull-right row-order" id="'
										+ (i + 1) + '">' + (i + 1) + '</div>',
								'<input type="text"	name="serviceDesc" class="serviceDesc form-control dis-auto-width dis-bottom-margin" tabindex="'
										+ (i + 1) + '">',
								'<input type="text"	name="qty" class="qty form-control dis-auto-width dis-bottom-margin">',
								'<input type="text"	name="rate"  class="rate form-control dis-auto-width dis-bottom-margin">',
								'<input type="text"	name="discount"  class="discount form-control dis-auto-width dis-bottom-margin">',
								'<input type="text"	name="disAmount"  class="disAmount form-control dis-auto-width dis-bottom-margin">',
								'<input type="text"	name="netAmount"  class="netAmount form-control dis-auto-width dis-bottom-margin">',
								'<input type="text"	name="specDesc"  class="specDesc form-control dis-auto-width dis-bottom-margin">',
								'<button class="btn btn-warning btn-sm pull-right row-delete">X</button>' ])
				.draw();
	}

	$('#myTable tbody').on(
			'click',
			'.row-delete',
			function() {

				var row = $(this).closest('tr')
				var id = row.find('.row-order').attr('id');
				alert('ID : ' + id);
				var siblings = row.siblings();
				table.row($(this).parents('tr')).remove().draw();
				// row.remove(id).draw();
				siblings.each(function(index) {
					$(this).children().first().addClass('pull-right row-order')
							.attr('id', index + 1).text(index + 1);
				});

			});

	$(document)
			.on(
					'click',
					'.row-add',
					function() {
						var lastRowId = $('.row-order:last').attr('id');
						alert(lastRowId);
						table.row
								.add(
										[
												'<div class="pull-right row-order" id="'
														+ (parseInt(lastRowId) + 1)
														+ '">'
														+ (parseInt(lastRowId) + 1)
														+ '</div>',
												'<input autocomplete="off" type="text"	name="serviceDesc" class="serviceDesc form-control dis-auto-width dis-bottom-margin ui-autocomplete-input" tabindex="'
														+ (parseInt(lastRowId) + 1)
														+ '">',
												'<input type="text"	name="qty" class="qty form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="rate" class="rate form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="discount" class="discount form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="disAmount" class="disAmount form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="netAmount" class="netAmount form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="specDesc" class="specDesc form-control dis-auto-width dis-bottom-margin">',
												'<button class="btn btn-warning btn-sm pull-right row-delete">X</button>' ])
								.draw();

					});

	$(document)
			.on(
					'keyup',
					'tr:last',
					function() {
						var lastRowId = $('.row-order:last').attr('id');
						alert(lastRowId);
						table.row
								.add(
										[
												'<div class="pull-right row-order" id="'
														+ (parseInt(lastRowId) + 1)
														+ '">'
														+ (parseInt(lastRowId) + 1)
														+ '</div>',
												'<input type="text"	name="serviceDesc" class="serviceDesc form-control dis-auto-width dis-bottom-margin ui-autocomplete-input" tabidex="'
														+ (parseInt(lastRowId) + 1)
														+ '">',
												'<input type="text"	name="qty" class="qty form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="rate" class="rate form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="discount" class="discount form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="disAmount" class="disAmount form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="netAmount" class="netAmount form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="specDesc" class="specDesc form-control dis-auto-width dis-bottom-margin">',
												'<button class="btn btn-warning btn-sm pull-right row-delete">X</button>' ])
								.draw();

					});

	$(document).on(
			'click',
			'.previousBtn',
			function() {

				var id = $(this).data('id');
				// alert(id);

				$('.myModal .modal-body').load(
						'/patient.transfer?ACTION=FETCH_SERVICE_ORDER&ip_no='
								+ id, function() {

							$('.myModal').modal({
								show : true
							});
						});
			});

	var projects = [ {
		"value" : "java",
		"label" : "Java",
		"desc" : "write once run anywhere",
	}, {
		"value" : "jquery-ui",
		"label" : "jQuery UI",
		"desc" : "the official user interface library for jQuery",
	}, {
		"value" : "Bootstrap",
		"label" : "Twitter Bootstrap",
		"desc" : "popular front end frameworks ",
	} ];

	var serviceList = [];

	$(document)
			.on(
					'keyup',
					'.serviceDesc',
					function() {

						var serviceDesc = $(this).val();
						var patNumber = $('#patient-no').val();
						// alert(patNumber);
						var serviceCat = $('.select-box').val();
						// alert(serviceCat);

						if (serviceDesc.length > 2) {

							$
									.ajax({
										url : 'patient.transfer',
										type : 'post',
										dataType : 'json',
										data : {
											ACTION : 'GET_SERVICE_RATE_LIST',
											serviceDesc : serviceDesc,
											ip_no : patNumber,
											serviceCat : serviceCat
										},
										success : function(data) {
											// alert(data);
											// alert(JSON.stringify(data));
											// serviceList.push(data);
											// alert(serviceList);

											// alert(data[0]["serviceName"]);

											for (var i = 0; i < data.length; i++) {

												serviceList
														.push({
															label : String(data[i]["serviceName"]),
															value : String(data[i]["serviceName"]),
															qty : String(data[i]["qty"]),
															rate : String(data[i]["rate"]),
															discount : String(data[i]["discount"]),
															discountAmount : String(data[i]["discountAmount"]),
															netAmount : String(data[i]["netAmount"])
														});
											}

											// alert("Data" + serviceList);
										},
										error : function(data) {
											var errorMsg = "There is a problem processing your request";
											alert(errorMsg);
											alert(data.responseText);
										}

									});
						}

					});

	/* Service Description autocomple features */
	$('body').on('focus', '.serviceDesc', function() {

		$(this).autocomplete({
			autoFocus : true,
			maxShowItems : 5,
			minLength : 3,
			source : serviceList,
			focus : function(event, ui) {
				var currentRow = $(this).closest('tr');
				currentRow.find('.qty').val(ui.item.qty);
				currentRow.find('.rate').val(ui.item.rate);
				currentRow.find('.discount').val(ui.item.discount);
				currentRow.find('.disAmount').val(ui.item.disAmount);
				currentRow.find('.netAmount').val(ui.item.netAmount);
				return false;
			}
		});

	});
	$('body').on('click', '.serviceDesc', function() {

		$(this).autocomplete({
			autoFocus : true,
			maxShowItems : 5,
			minLength : 3,
			source : serviceList,
			focus : function(event, ui) {
				var currentRow = $(this).closest('tr');
				currentRow.find('.qty').val(ui.item.qty);
				currentRow.find('.rate').val(ui.item.rate);
				currentRow.find('.discount').val(ui.item.discount);
				currentRow.find('.disAmount').val(ui.item.disAmount);
				currentRow.find('.netAmount').val(ui.item.netAmount);
				return false;
			}
		});

	});

});
