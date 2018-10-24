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
		"scrollY" : '60vh',
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
			"width" : "29%",
			"targets" : 1
		}, {
			"width" : "5%",
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

	var counter = 10;

	for (var i = 0; i < counter; i++) {

		table.row
				.add(
						[
								'<div class="row-order" id="'
										+ (i + 1) + '">' + (i + 1) + '</div>',
								'<input type="text"	name="serviceDesc" class="serviceDesc form-control dis-auto-width dis-bottom-margin" tabindex="'
										+ (i + 1) + '">',
								'<input type="text"	name="qty" class="text-align-center qty form-control dis-auto-width dis-bottom-margin">',
								'<input type="text"	name="rate"  class="text-align-right rate form-control dis-auto-width dis-bottom-margin"></div>',
								'<input type="text" name="discount" class="text-align-center discount form-control dis-auto-width dis-bottom-margin">',
								'<input type="text"	name="disAmount"  class="text-align-right disAmount form-control dis-auto-width dis-bottom-margin">',
								'<input type="text"	name="netAmount"  class="text-align-right netAmount form-control dis-auto-width dis-bottom-margin">',
								'<input type="text"	name="specDesc"  class="specDesc form-control dis-auto-width dis-bottom-margin">',
								'<div class="delete-btn"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
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
												'<div class="row-order" id="'
														+ (parseInt(lastRowId) + 1)
														+ '">'
														+ (parseInt(lastRowId) + 1)
														+ '</div>',
												'<input type="text"	name="serviceDesc" class="serviceDesc form-control dis-auto-width dis-bottom-margin" tabindex="'
														+ (parseInt(lastRowId) + 1)
														+ '">',
												'<input type="text" name="qty" class="text-align-center qty form-control dis-auto-width dis-bottom-margin">',
												'<input type="text" name="rate" class="text-align-right rate form-control dis-auto-width dis-bottom-margin">',
												'<input type="text" name="discount" class="text-align-center discount form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="disAmount" class="text-align-right disAmount form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="netAmount" class="text-align-right netAmount form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="specDesc" class="specDesc form-control dis-auto-width dis-bottom-margin">',
												'<div class="delete-btn"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
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
												'<div class="row-order" id="'
														+ (parseInt(lastRowId) + 1)
														+ '">'
														+ (parseInt(lastRowId) + 1)
														+ '</div>',
												'<input type="text"	name="serviceDesc" class="serviceDesc form-control dis-auto-width dis-bottom-margin ui-autocomplete-input" tabindex="'
														+ (parseInt(lastRowId) + 1)
														+ '">',
												'<input type="text"	name="qty" class="text-align-center qty form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="rate" class="text-align-right rate form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="discount" class="text-align-center discount form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="disAmount" class="text-align-right disAmount form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="netAmount" class="text-align-right netAmount form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="specDesc" class="specDesc form-control dis-auto-width dis-bottom-margin">',
												'<div class="delete-btn"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
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
											
											//serviceList.length = 0;

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
	$('body').on('click', '.serviceDesc', function() {

		$(this).autocomplete({
			autoFocus : true,
			maxShowItems : 5,
			minLength : 3,
			source : serviceList,
			focus : function(event, ui) {
				$(this).val(ui.item.label);
				var currentRow = $(this).closest('tr');
				var quatity = ui.item.qty;
				currentRow.find('.qty').val(quatity);
				var baseRate = ui.item.rate;
				var totalRateAsQuantity = (quatity * baseRate);
				var itemRate = parseFloat(Math.round(baseRate * 100) / 100).toFixed(2);
				currentRow.find('.rate').val(itemRate);
				var baseDiscount = ui.item.discount;
				currentRow.find('.discount').val(baseDiscount);
				var discountInDec = baseDiscount / 100;
				var totalDiscountAmount =   totalRateAsQuantity * discountInDec;
				var itemDiscountAmount = parseFloat(Math.round(totalDiscountAmount * 100) / 100).toFixed(2);
				currentRow.find('.disAmount').val(itemDiscountAmount);
				var totalNetAmount = totalRateAsQuantity - totalDiscountAmount;
				var itemNetAmount = parseFloat(Math.round(totalNetAmount * 100) / 100).toFixed(2);
				currentRow.find('.netAmount').val(itemNetAmount);
				calculateGrossSummary();
				return false;
			},
			select : function(event, ui) {
				$(this).val(ui.item.label);
				return false;
			}
		});

	});

	$('.serviceDesc').autocomplete({
		autoFocus : true,
		maxShowItems : 5,
		minLength : 3,
		source : serviceList,
		delay : 1000,
		focus : function(event, ui) {
			$(this).val(ui.item.label);
			var currentRow = $(this).closest('tr');
			var quatity = ui.item.qty;
			currentRow.find('.qty').val(quatity);
			var baseRate = ui.item.rate;
			var totalRateAsQuantity = (quatity * baseRate);
			var itemRate = parseFloat(Math.round(baseRate * 100) / 100).toFixed(2);
			currentRow.find('.rate').val(itemRate);
			var baseDiscount = ui.item.discount;
			currentRow.find('.discount').val(baseDiscount);
			var discountInDec = baseDiscount / 100;
			var totalDiscountAmount =   totalRateAsQuantity * discountInDec;
			var itemDiscountAmount = parseFloat(Math.round(totalDiscountAmount * 100) / 100).toFixed(2);
			currentRow.find('.disAmount').val(itemDiscountAmount);
			var totalNetAmount = totalRateAsQuantity - totalDiscountAmount;
			var itemNetAmount = parseFloat(Math.round(totalNetAmount * 100) / 100).toFixed(2);
			currentRow.find('.netAmount').val(itemNetAmount);
			calculateGrossSummary();
		},
		select : function(event, ui) {
			$(this).val(ui.item.label);
			return false;
		}
	});

	$('body').on('keydown','.serviceDesc',	function() {
				$(this).autocomplete('option', 'delay',	1000 / ($(this).val().length + 1));

			});
	
	$(document).on('keyup', '.qty', function(){

		var currentRow = $(this).closest('tr');
		
		var quantity = $(this).val();
		var baseRate = currentRow.find('.rate').val();
		var baseDiscount = currentRow.find('.discount').val();
		var discountInDec = baseDiscount / 100;
		var totalRateAsQuantity = (quantity * baseRate);
		//alert(totalRateAsQuantity);
		var totalDiscountAmount =   totalRateAsQuantity * discountInDec;
		var totalNetAmount = totalRateAsQuantity - totalDiscountAmount;
		var itemDiscountAmount = parseFloat(Math.round(totalDiscountAmount * 100) / 100).toFixed(2);
		var itemNetAmount = parseFloat(Math.round(totalNetAmount * 100) / 100).toFixed(2);
		//alert(itemDiscountAmount);
		currentRow.find('.disAmount').val(itemDiscountAmount);
		currentRow.find('.netAmount').val(itemNetAmount);
		
		
		calculateGrossSummary();
		
		
		
	});
	
	function calculateGrossSummary(){
		
		var sumDisAmount = 0.0;
		var sumNetAmount = 0.0;
		var sumGrossAmount = 0.0;
		
		$('.disAmount').each(function(){
			
			if ($(this).val() === ''){
				var disAmt = 0.0;
			} else {
				var disAmt = $(this).val();
			}
			sumDisAmount += parseFloat(disAmt);
		});
		
		$('.netAmount').each(function(){
			if ($(this).val() === ''){
				var netAmt = 0.0;
			} else {
				var netAmt = $(this).val();
			}
			sumNetAmount += parseFloat(netAmt);
		});
		
		//alert(sumDisAmount);
		//alert(sumNetAmount);
		//sumGrossAmount = (parseFloat(sumDisAmount) + parseFloat(sumNetAmount));
		sumGrossAmount = (parseFloat(sumDisAmount) + parseFloat(sumNetAmount));
		
		
		
		$('#sum-gross-amount').val(parseFloat(Math.round(sumGrossAmount * 100) / 100).toFixed(2));
		$('#sum-discount-amount').val(sumDisAmount);
		$('#sum-net-amount').val(sumNetAmount);
		 
		
		
	}
	
	
	
	
	
	

});
