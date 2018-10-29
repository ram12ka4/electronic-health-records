$(function() {
	
	var serviceList = [];
	var serviceCodeList = [];
	var tempArr = [];
	var panelServiceCode = [];

	$('#fromDate').datepicker().datepicker("setDate", new Date());
	var select = $(".select-box");
	// select.css("display", "inline");

	select.empty().append('<option selected="selected" value="0" disabled = "disabled">Loading.....</option>');

	var req = $.ajax({
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
								'<div class="row-order" id="' + (i + 1) + '">'
										+ (i + 1) + '</div>',
								'<input type="text"	name="serviceDesc" class="serviceDesc form-control dis-auto-width dis-bottom-margin" tabindex="'
										+ (i + 1) + '"><input type="hidden" class="serviceId" name="serviceId"><input type="hidden" name="minorCode" class="minorCode"><input type="hidden" name="serviceCode" class="serviceCode">',
								'<input type="text"	name="qty" class="text-align-center qty form-control dis-auto-width dis-bottom-margin">',
								'<input type="text"	name="rate"  class="text-align-right rate form-control dis-auto-width dis-bottom-margin" readonly>',
								'<input type="text" name="discount" class="text-align-center discount form-control dis-auto-width dis-bottom-margin">',
								'<input type="text"	name="disAmount"  class="text-align-right disAmount form-control dis-auto-width dis-bottom-margin" readonly>',
								'<input type="text"	name="netAmount"  class="text-align-right netAmount form-control dis-auto-width dis-bottom-margin" readonly>',
								'<input type="text"	name="specDesc"  class="specDesc form-control dis-auto-width dis-bottom-margin">',
								'<div class="delete-btn"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
				.draw();
	}

	
	  $('.delete-btn').on( 'click', function(e) {
	  e.preventDefault();
	  var row = $(this).closest('tr');
	  
	  var serviceId = row.find('.serviceCode').val();
	  var minorCode = row.find('.minorCode').val();
	  var serviceCode = row.find('.serviceCode').val();
	  
	  console.log('Service Id -> ' + serviceId);
	  console.log('minor Code -> ' + minorCode);
	  console.log('Service Code -> ' + serviceCode);
	  
	  
	  
	 
	  for (var i=0; i<serviceCodeList.length; i++){
		  console.log('Before serviceCodeList : ' + serviceCodeList[i]);
	  }
	  
	  if (minorCode.localeCompare("PANMIN") === 0){
		  console.log('If part');
		  fetchDeletedPanelServiceCode(serviceCode);
		  serviceCodeList.splice(serviceCodeList.indexOf(serviceCode), 1);
	  } else {
		  console.log('Else part');
		  serviceCodeList.splice(serviceCodeList.indexOf(serviceCode), 1);
	  }
	  
	 
	
	  
	  
	  
	  var id =  row.find('.row-order').attr('id'); alert('ID : ' + id); 
	  var siblings =  row.siblings(); 
	  table.row($(this).parents('tr')).remove().draw(); 
	  siblings.each(function(index) {
		  console.log('Tab Index : ' + index);
	  $(this).children().first().addClass('row-order').attr('id', index + 1).text(index + 1); 
	  });
	  calculateGrossSummary();
	  alignTabIndex();
	  
	  });
	  
	
	  
	
	 

	$(document)
			.on(
					'click',
					'.row-add',
					function(e) {
						e.preventDefault();
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
												'<input type="text" name="rate" class="text-align-right rate form-control dis-auto-width dis-bottom-margin" readonly>',
												'<input type="text" name="discount" class="text-align-center discount form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="disAmount" class="text-align-right disAmount form-control dis-auto-width dis-bottom-margin" readonly>',
												'<input type="text"	name="netAmount" class="text-align-right netAmount form-control dis-auto-width dis-bottom-margin" readonly>',
												'<input type="text"	name="specDesc" class="specDesc form-control dis-auto-width dis-bottom-margin">',
												'<div class="delete-btn"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
								.draw();

					});

	
	  $(document) .on( 'keyup', 'tr:last', function() { 
		 // console.log('ram');
		  var x = event.which || event.keyCode;
		  var lastRowId =  $('.row-order:last').attr('id'); 
		  alert(lastRowId); 
		  table.row .add( [ '<div class="row-order" id="' + (parseInt(lastRowId) + 1) + '">' +  (parseInt(lastRowId) + 1) + '</div>', '<input type="text"  name="serviceDesc" class="serviceDesc form-control dis-auto-width dis-bottom-margin ui-autocomplete-input" tabindex="' +	  (parseInt(lastRowId) + 1) + '">', 
			  '<input type="text" name="qty"  class="text-align-center qty form-control dis-auto-width  dis-bottom-margin">', 
			  '<input type="text" name="rate"  class="text-align-right rate form-control dis-auto-width  dis-bottom-margin" readonly>', 
			  '<input type="text" name="discount"  class="text-align-center discount form-control dis-auto-width  dis-bottom-margin">', 
			  '<input type="text" name="disAmount"  class="text-align-right disAmount form-control dis-auto-width  dis-bottom-margin" readonly>', 
			  '<input type="text" name="netAmount"  class="text-align-right netAmount form-control dis-auto-width  dis-bottom-margin" readonly>', 
			  '<input type="text" name="specDesc"  class="specDesc form-control dis-auto-width dis-bottom-margin">', 
			  '<div  class="delete-btn"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ]).draw();
	  });
	 

	$(document).on(
			'click',
			'.previousBtn',
			function(e) {
				e.preventDefault();

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



	$(document)
			.on(
					'keyup',
					'.serviceDesc',
					function(e) {
						e.preventDefault();
						var serviceDesc = $(this).val();
						var patNumber = $('#patient-no').val();
						var serviceCat = $('.select-box').val();

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
											serviceList.length = 0;
											for (var i = 0; i < data.length; i++) {

												serviceList
														.push({
															label : String(data[i]["serviceName"]),
															value : String(data[i]["serviceName"]),
															serviceId: String(data[i]["serviceId"]),
															minorCode: String(data[i]["minorCode"]),
															serviceCode: String(data[i]["serviceCode"]),
															qty : String(data[i]["qty"]),
															rate : String(data[i]["rate"]),
															discount : String(data[i]["discount"]),
															discountAmount : String(data[i]["discountAmount"]),
															netAmount : String(data[i]["netAmount"])
														});
											}
										},
										error : function(data) {
											var errorMsg = "There is a problem processing your request";
											alert(errorMsg);
											alert(data.responseText);
										}

									});
						}

					});

	

	$('body')
			.on(
					'keyup',
					'.serviceDesc',
					function(e) {
						e.preventDefault();
						$(this).autocomplete({
											autoFocus : true,
											maxShowItems : 5,
											minLength : 3,
											source : serviceList,
											delay : 500,
											focus : function(event, ui) {
												
												$(this).val(ui.item.label);
												var currentRow = $(this).closest('tr');
												currentRow.find('.serviceId').val(ui.item.serviceId);
												var serviceCode = ui.item.serviceCode;
												currentRow.find('.serviceCode').val(serviceCode);
												var minorCode = ui.item.minorCode;
												currentRow.find('.minorCode').val(minorCode);
												var quatity = ui.item.qty;
												currentRow.find('.qty').val(quatity);
												var baseRate = ui.item.rate;
												var totalRateAsQuantity = (quatity * baseRate);
												var itemRate = parseFloat(Math.round(baseRate * 100) / 100).toFixed(2);
												currentRow.find('.rate').val(itemRate);
												var baseDiscount = ui.item.discount;
												currentRow.find('.discount').val(baseDiscount);
												var discountInDec = baseDiscount / 100;
												var totalDiscountAmount = totalRateAsQuantity * discountInDec;
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
												var serviceDesc = ui.item.label;
												var currentRow = $(this).closest('tr');
												currentRow.find('.serviceId').val(ui.item.serviceId);
												var serviceCode = ui.item.serviceCode;
												currentRow.find('.serviceCode').val(serviceCode);
												var minorCode = ui.item.minorCode;
												currentRow.find('.minorCode').val(minorCode);
												duplicateCheckServiceCode(serviceDesc, minorCode, serviceCode, currentRow, e);
												return false;
											}
										});

					});

	/*
	 * Calculate updated Rate, Discount Amount, Net Amount Gross, Total Discount
	 * and Net Amount based on changes quantity
	 */

	$(document)
			.on(
					'keyup',
					'.qty',
					function(e) {
						e.preventDefault();
						var currentRow = $(this).closest('tr');
						var quantity = $(this).val();
						var baseRate = currentRow.find('.rate').val();
						var baseDiscount = currentRow.find('.discount').val();
						var discountInDec = baseDiscount / 100;
						var totalRateAsQuantity = (quantity * baseRate);
						// alert(totalRateAsQuantity);
						var totalDiscountAmount = totalRateAsQuantity
								* discountInDec;
						var totalNetAmount = totalRateAsQuantity
								- totalDiscountAmount;
						var itemDiscountAmount = parseFloat(
								Math.round(totalDiscountAmount * 100) / 100)
								.toFixed(2);
						var itemNetAmount = parseFloat(
								Math.round(totalNetAmount * 100) / 100)
								.toFixed(2);
						currentRow.find('.disAmount').val(itemDiscountAmount);
						currentRow.find('.netAmount').val(itemNetAmount);
						calculateGrossSummary();
					});
	
	/*
	 * Calculate Gross Amount, Discount Amount and Net Amount 
	 */

	function calculateGrossSummary() {
		//console.log('ram');
		var sumDisAmount = 0.0;
		var sumNetAmount = 0.0;
		var sumGrossAmount = 0.0;
		$('.disAmount').each(function() {
			if ($(this).val() === '') {
				var disAmt = 0.0;
			} else {
				var disAmt = $(this).val();
			}
			sumDisAmount += parseFloat(disAmt);
		});

		$('.netAmount').each(function() {
			if ($(this).val() === '') {
				var netAmt = 0.0;
			} else {
				var netAmt = $(this).val();
			}
			sumNetAmount += parseFloat(netAmt);
		});
		sumGrossAmount = (parseFloat(sumDisAmount) + parseFloat(sumNetAmount));
		$('#sum-gross-amount').val(
				parseFloat(Math.round(sumGrossAmount * 100) / 100).toFixed(2));
		$('#sum-discount-amount').val(
				parseFloat(Math.round(sumDisAmount * 100) / 100).toFixed(2));
		$('#sum-net-amount').val(
				parseFloat(Math.round(sumNetAmount * 100) / 100).toFixed(2));
	}

	/*
	 * Method for duplicate checking not more than one service description will
	 * be assigned
	 */

	function duplicateCheck(serviceName, currentRow) {
		var serviceNameArr = [];
		var count = 0;
		$('.serviceDesc').each(function(e) {
			if ($(this).val() !== '') {
				serviceNameArr.push($(this).val());
			}
		});
		console.log('Row Object : ' + currentRow);
		console.log('Service Name : ' + serviceName);
		console.log(serviceNameArr);
		for (var i = 0; i < serviceNameArr.length; i++) {
			if (serviceName == serviceNameArr[i]) {
				if (count === 1) {
					currentRow.find('.serviceDesc').val('');
					currentRow.find('.qty').val('');
					currentRow.find('.rate').val('');
					currentRow.find('.discount').val('');
					currentRow.find('.disAmount').val('');
					currentRow.find('.netAmount').val('');
					calculateGrossSummary();
					return true;
				}
				count++;
			}
		}
	}
	
	var count = 0;
	
	$('.serviceDesc').each(function(){
		if ($(this).val() === ''){
			count++;
		}
		if (count === counter){
			serviceCodeList.length = 0;
		}
	});
	
	
	
	/*
	 * Duplicate check with service code 
	 */
	function duplicateCheckServiceCode(serviceDesc, minorCode, serviceCode, currentRow, event) {
		
		
		console.log('Minor Code : ' + minorCode);
		console.log('Service Code : ' + serviceCode);
		
		  tempArr.length = 0;
		
		for (var i =0; i<serviceCodeList.length; i++){
			console.log('ServiceCode List : ' + serviceCodeList[i]);
		}
			
			/*
			 * Three stages duplicate checking 
			 * 	1.	panel code with panel code.
			 * 	2.  panel code's sub service code with individual service code.
			 *  3.  individual service code first then compare with panel's sub service code.
			 */
			var x  = parentChildServiceCodeCheck(serviceDesc, serviceCode, "", currentRow, 1);
			
			if (x === false){
				return false;
			} 
		
			//console.log('Else Section');
			
			if (minorCode.localeCompare("PANMIN") === 0){
		
				$.ajax({
					url : 'patient.transfer',
					type : 'post',
					dataType : 'text',
					data : {
						ACTION : 'GET_PANEL_SERVICE_CODE',
						serviceCode : serviceCode
					},
					success : function(data) {
						
						data = data.replace(/^\W+|\W+$/g, "");
						console.log(data);

						if (data.length !== 0) {
							
							var arr = data.replace("[", "").replace("]", "").split(",");
							
							var i = 0;
							while (i < arr.length) {
								
								console.log('Array data : ' + arr[i]);
								
								var childServiceCode = $.trim(arr[i]);
								var childServiceDesc = $.trim(arr[i+1]);
								
								
								var x = parentChildServiceCodeCheck(serviceDesc, childServiceCode, childServiceDesc, currentRow, 2);
								
								if (x === false){
									console.log('step false');
									return false;
								} else {
									console.log('step true');
									tempArr.push(childServiceCode);
								}
								i += 2;
							}
							
							serviceCodeList.push.apply(serviceCodeList, tempArr);
							serviceCodeList.push(serviceCode);
							
							for (var i=0; i<serviceCodeList.length; i++){
								console.log('After Service List : ' + serviceCodeList[i]);
							}
							
							
						}
					},
					error : function(data) {
						var errorMsg = "There is a problem processing your request";
						alert(errorMsg);
						alert(data.responseText);
					}
				});
				
			} else {
				serviceCodeList.push(serviceCode);
			}
		}
	
  function fetchDeletedPanelServiceCode(serviceCode){
		  
		  console.log('In fetchDeletedPanelServiceCode');
		  //panelServiceCode.length = 0;
		
		  
		  $.ajax({
				url : 'patient.transfer',
				type : 'post',
				dataType : 'text',
				data : {
					ACTION : 'GET_PANEL_SERVICE_CODE',
					serviceCode : serviceCode
				},
				success : function(data) {
					
					data = data.replace(/^\W+|\W+$/g, "");
					//console.log(data);

					if (data.length !== 0) {
						var arr = data.replace("[", "").replace("]", "").split(",");
						var i = 0;
						while (i < arr.length) {
									console.log('Data to be deleted : ' + arr[i]);
								  serviceCodeList.splice(serviceCodeList.indexOf($.trim(arr[i])), 1);
							i += 2;
						}
					}
						
						console.log('step 1');
						
						console.log('Service Code List Length : ' + serviceCodeList.length);
					  
					  for (var i=0; i<serviceCodeList.length; i++){
						  console.log('After serviceCodeList : ' + serviceCodeList[i]);
					  }
				  
					  console.log('step 2');
					
					
				},
				error : function(data) {
					var errorMsg = "There is a problem processing your request";
					alert(errorMsg);
					alert(data.responseText);
				}
			});
		  
	  }
	
	function parentChildServiceCodeCheck(serviceDesc, serviceCode, childServiceDesc, currentRow, token) {
		
		for (var i = 0; i < serviceCodeList.length; i++) {
			
			if (serviceCode === serviceCodeList[i]) {
					currentRow.find('.qty').val('');
					currentRow.find('.rate').val('');
					currentRow.find('.discount').val('');
					currentRow.find('.disAmount').val('');
					currentRow.find('.netAmount').val('');
					currentRow.find('.serviceDesc').val('');
					currentRow.find('.serviceId').val('');
					currentRow.find('.minorCode').val('');
					currentRow.find('.serviceCode').val('');
					if (token === 1){
						alert("\"" + serviceDesc + "\" service name already exists");
					} else {
						alert("......................................Duplicate Service Name..............................\n\r\"" + childServiceDesc + "\" is present in \""+ serviceDesc +"\" service name.\n\r Please check");
					}
					calculateGrossSummary();
					return false;
			}
		}

		//return true;
	}
	


	/*
	 * Disable default Enter keypress form submit event 
	 */

	$('.serviceDesc').on('keypress', function(event) {
		var x = event.which || event.keyCode;
		if (x === 13) {
			event.preventDefault();
		}
	});

	$('.qty').on('keypress', function(event) {
		var x = event.which || event.keyCode;
		if (x === 13) {
			event.preventDefault();
		}
	});
	
	$('.discount').on('keypress', function(event) {
		var x = event.which || event.keyCode;
		if (x === 13) {
			event.preventDefault();
		}
	});
	
	/*
	 * Realign TabIndex of ServiceDesc input field
	 */
	function alignTabIndex(){
		var counter = 0;
		$('.serviceDesc').each(function(){
			var tabValue = $(this).attr('tabindex', counter + 1);
			counter++;
		});
	}

});
