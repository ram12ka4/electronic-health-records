$(function() {
	
	var serviceList = [];
	var serviceCodeList = [];
	var tempArr = [];
	var panelServiceCode = [];
	var serviceOrderNo = 0;
	var doctorList = [];
	var soDetailList = [];
	var tempServiceNameList = [];
	var enterServiceNameList = [];

	/*
	 * Disable features
	 */
	$('.add-service').prop('disabled', true);
	
	
	
	
	$('#fromDate').datepicker().datepicker("setDate", new Date());
	var select = $(".select-box");

	select.empty().append('<option selected="selected" value="0" disabled = "disabled">Loading.....</option>');

	var req = $.ajax({
				url : '/patient.transfer',
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
		"deferLoading": 57,
	/*
	 * responsive : { details : { display :
	 * $.fn.dataTable.Responsive.display.modal({ header : function(row) { var
	 * data = row.data(); return 'Details for ' + data[0] + ' ' + data[1]; } }),
	 * renderer : $.fn.dataTable.Responsive.renderer.tableAll({ tableClass :
	 * 'table' }) } },
	 */

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
			"width" : "8%",
			"targets" : 3
		}, {
			"width" : "5%",
			"targets" : 4
		}, {
			"width" : "8%",
			"targets" : 5
		}, {
			"width" : "8%",
			"targets" : 6
		}, {
			"width" : "16%",
			"targets" : 7
		}, {
			"width" : "5%",
			"targets" : 8
		}, ]

	}).draw(false);
	
	// createRow();

	var counter = 10;
	
	
	
		for (var i = 0; i < counter; i++) {
			table.row
					.add(
							[
									'<div class="row-order" id="' + (i + 1) + '">'
											+ (i + 1) + '</div>',
									'<input type="text"	name="serviceDesc" class="serviceDesc form-control dis-auto-width dis-bottom-margin" tabindex="'
											+ (i + 1) + '"><input type="hidden" class="serviceId" name="serviceId"><input type="hidden" name="minorCode" class="minorCode"><input type="hidden" name="serviceCode" class="serviceCode">',
									'<input type="text"	name="qty"  class="text-align-center qty form-control dis-auto-width dis-bottom-margin">',
									'<input type="text"	name="rate"  class="text-align-right rate form-control dis-auto-width dis-bottom-margin" readonly>',
									'<input type="text" name="discount" class="text-align-center discount form-control dis-auto-width dis-bottom-margin">',
									'<input type="text"	name="disAmount"  class="text-align-right disAmount form-control dis-auto-width dis-bottom-margin" readonly>',
									'<input type="text"	name="netAmount"  class="text-align-right netAmount form-control dis-auto-width dis-bottom-margin" readonly>',
									'<span id="addSpecimen"></span>',
									'<div class="delete-btn"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
					.draw(false);
		}
	

	
	
	  $(document).on('click', '.row-delete', function(event) {
	  event.preventDefault();
	  
	  var x = event.which || event.keyCode;
	  
	  console.log('delete btn event : ' + x);
	  
	  var row = $(this).closest('tr');
	  
	  var serviceId = row.find('.serviceCode').val();
	  var minorCode = row.find('.minorCode').val();
	  var serviceCode = row.find('.serviceCode').val();
	  var serviceName = row.find('.serviceDesc').val();
	  
	  // console.log('Service Id -> ' + serviceId);
	  // console.log('minor Code -> ' + minorCode);
	  // console.log('Service Code -> ' + serviceCode);
	  
	  
	  tempServiceNameList.splice(tempServiceNameList.indexOf(serviceName), 1);
	 
	
	  if (minorCode.localeCompare("PANMIN") === 0){
		  // console.log('If part');
		  fetchDeletedPanelServiceCode(serviceCode);
		  serviceCodeList.splice(serviceCodeList.indexOf(serviceCode), 1);
	  } else {
		  // console.log('Else part');
		  serviceCodeList.splice(serviceCodeList.indexOf(serviceCode), 1);
	  }
	  
	 
	
	  
	  
	  
	  var id =  row.find('.row-order').attr('id'); 
	  // alert('ID : ' + id);
	  var siblings =  row.siblings(); 
	  table.row($(this).parents('tr')).remove().draw(); 
	  siblings.each(function(index) {
		  // console.log('Tab Index : ' + index);
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
						// alert(lastRowId);
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
														+ '"><input type="hidden" class="serviceId" name="serviceId"><input type="hidden" name="minorCode" class="minorCode"><input type="hidden" name="serviceCode" class="serviceCode">',
												'<input type="text" name="qty" class="text-align-center qty form-control dis-auto-width dis-bottom-margin">',
												'<input type="text" name="rate" class="text-align-right rate form-control dis-auto-width dis-bottom-margin" readonly>',
												'<input type="text" name="discount" class="text-align-center discount form-control dis-auto-width dis-bottom-margin">',
												'<input type="text"	name="disAmount" class="text-align-right disAmount form-control dis-auto-width dis-bottom-margin" readonly>',
												'<input type="text"	name="netAmount" class="text-align-right netAmount form-control dis-auto-width dis-bottom-margin" readonly>',
												'<span id="addSpecDoctor"></span>',
												'<div class="delete-btn"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
								.draw();

					});

	
	  $(document) .on( 'keyup', 'tr:last', function() { 
		 // console.log('ram');
		  var x = event.which || event.keyCode;
		  var lastRowId =  $('.row-order:last').attr('id'); 
		  // alert(lastRowId);
		  table.row .add( [ '<div class="row-order" id="' + (parseInt(lastRowId) + 1) + '">' +  (parseInt(lastRowId) + 1) + '</div>', '<input type="text"  name="serviceDesc" class="serviceDesc form-control dis-auto-width dis-bottom-margin ui-autocomplete-input" tabindex="' +	  (parseInt(lastRowId) + 1) + '">', 
			  '<input type="text" name="qty"  class="text-align-center qty form-control dis-auto-width  dis-bottom-margin">', 
			  '<input type="text" name="rate"  class="text-align-right rate form-control dis-auto-width  dis-bottom-margin" readonly>', 
			  '<input type="text" name="discount"  class="text-align-center discount form-control dis-auto-width  dis-bottom-margin">', 
			  '<input type="text" name="disAmount"  class="text-align-right disAmount form-control dis-auto-width  dis-bottom-margin" readonly>', 
			  '<input type="text" name="netAmount"  class="text-align-right netAmount form-control dis-auto-width  dis-bottom-margin" readonly>', 
			  '<span id="addSpecDoctor"></span>', 
			  '<div  class="delete-btn"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ]).draw();
	  });
	 
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
										url : '/patient.transfer',
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
											//tempServiceNameList.length = 0;
											for (var i = 0; i < data.length; i++) {

												tempServiceNameList.push(String($.trim(data[i]["serviceName"])));
												
												serviceList
														.push({
															label : String(data[i]["serviceName"]),
															value : String(data[i]["serviceName"]),
															serviceId: String(data[i]["serviceId"]),
															minorCode: String(data[i]["minorCode"]),
															serviceCode: String(data[i]["serviceCode"]),
															treatedBy: String(data[i]["treatedBy"]),
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

	previousValue = "";

	$('body')
			.on(
					'keyup',
					'.serviceDesc',
					function(e) {
						e.preventDefault();
						var serviceList1 =  $(this).autocomplete({
											autoFocus : true,
											maxShowItems : 5,
											minLength : 3,
											source : serviceList,
											delay : 500,
											focus : function(event, ui) {
												console.log('Autocomplete focus event');
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
												console.log('Autocomplete Select event');
												$(this).val(ui.item.label);
												var currentRow = $(this).closest('tr');
												var serviceDesc = ui.item.label;
												currentRow.find('.serviceId').val(ui.item.serviceId);
												var serviceCode = ui.item.serviceCode;
												currentRow.find('.serviceCode').val(serviceCode);
												var serviceId = ui.item.serviceId;
												var minorCode = ui.item.minorCode;
												currentRow.find('.minorCode').val(minorCode);
												var treatedBy = ui.item.treatedBy;
												var isTrue = duplicateCheckServiceCode(serviceDesc, minorCode, serviceCode, currentRow, e);
												console.log('IsTrue : ' + isTrue);
												if (isTrue == true){
													switchSpecimenDcotorName(serviceCode, currentRow, treatedBy);
												} 
												return false;
											}
										});
					});
	
	/*
	 * Duplicate check with service code
	 */
	function duplicateCheckServiceCode(serviceDesc, minorCode, serviceCode, currentRow, event) {
		
		
		console.log('In duplicateCheckServiceCode');
	    console.log('Minor Code : ' + minorCode);
		console.log('Service Code : ' + serviceCode);
		
		  tempArr.length = 0;
		
		
		  for (var i =0; i<serviceCodeList.length; i++){
		  console.log('ServiceCode List : ' + serviceCodeList[i]); }
		 
			
			/*
			 * Three stages duplicate checking 1. panel code with panel code. 2.
			 * panel code's sub service code with individual service code. 3.
			 * individual service code first then compare with panel's sub
			 * service code.
			 */
			var x  = parentChildServiceCodeCheck(serviceDesc, serviceCode, "", currentRow, 1);
			
			console.log('Return Value from parentChildServiceCodeCheck' + x);
			
			if (x === false){
				return false;
			} 
		
			if (minorCode.localeCompare("PANMIN") === 0){
		
				$.ajax({
					url : '/patient.transfer',
					type : 'post',
					dataType : 'text',
					data : {
						ACTION : 'GET_PANEL_SERVICE_CODE',
						serviceCode : serviceCode
					},
					success : function(data) {
						
						data = data.replace(/^\W+|\W+$/g, "");
						// console.log(data);

						if (data.length !== 0) {
							
							var arr = data.replace("[", "").replace("]", "").split(",");
							
							var i = 0;
							while (i < arr.length) {
								
								// console.log('Array data : ' + arr[i]);
								
								var childServiceCode = $.trim(arr[i]);
								var childServiceDesc = $.trim(arr[i+1]);
								
								
								var x = parentChildServiceCodeCheck(serviceDesc, childServiceCode, childServiceDesc, currentRow, 2);
								
								if (x === false){
									// console.log('step false');
									return false;
								} else {
									// console.log('step true');
									tempArr.push(childServiceCode);
								}
								i += 2;
							}
							
							serviceCodeList.push.apply(serviceCodeList, tempArr);
							serviceCodeList.push(serviceCode);
							
							/*
							 * for (var i=0; i<serviceCodeList.length; i++){
							 * console.log('After Service List : ' +
							 * serviceCodeList[i]); }
							 */
							
							
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
			
			return true;
		}
	
	
	function switchSpecimenDcotorName(serviceCode, currentRow, treatedBy){
		
		var serviceCategory = $('.select-box').val();
		
		console.log('Service Category : ' + serviceCategory);
		console.log('Service Code : ' + serviceCode);
		
		if (serviceCategory === "LABMAJ") {
			
			var req = $.ajax({
					
				type: 'post',
				url: '/patient.transfer',
				dataType : 'text',
				data : {
					ACTION : 'FETCH_SPECIMEN_LIST',
					serviceCode : serviceCode
				},
				success: function(data){
					
					data = data.replace(/^\W+|\W+$/g, "");
					console.log('Specimen List : ' + data);

					if (data.length !== 0) {
						arr = data.replace("[", "").replace("]", "").split(",");
						addSpecimenListEntry(arr, currentRow);
						
					} else {
						swal('Ohh no!', 'No Specimen found', 'info');
					}
					
					
					
				},
				error: function(data){
					var errorMsg = "There is a problem processing your request";
					swal('Sorry for inconvenience', errorMsg, 'info');
					// alert(data.responseText);
				}
				
				
				
			});
			
			
			
		} else {
			console.log('else part');
			console.log('Treated By : ' + treatedBy);
			
			
			if (treatedBy === 'Y'){
				
				var input = '<input type="text" name="doctorName"  class="doctorName form-control dis-auto-width  dis-bottom-margin">';
				currentRow.find('#addSpecDoctor').append(input);
				
			
				
				 $.ajax({
						
						type: 'post',
						url: '/patient.transfer',
						dataType : 'json',
						data : {
							ACTION : 'FETCH_DOCTOR_LIST',
						},
						success: function(data){
							
							// console.log('Doctor List : ' +
							// JSON.stringify(data));

							if (data.length !== 0) {
								doctorList.length = 0;
								
								for (var i = 0; i < data.length; i+=2) {
									doctorList
											.push({
												label : String(data[i+1]),
												value : String(data[i+1]),
												doctorId: String(data[i]),
												doctorName: String(data[i+1])
											});
								}
								
								/*
								 * for (var i=0; i<doctorList.length; i++){
								 * console.log('Doctor List : ' +
								 * JSON.stringify(doctorList[i])); }
								 */
								
							} else {
								swal('Ohh no!', 'No Doctor found', 'info');
							}
							
							
							
						},
						error: function(data){
							var errorMsg = "There is a problem processing your request";
							 swal('Sorry for inconvenience', errorMsg, 'info');
							// alert(data.responseText);
						}
						
						
						
					});
				
			}
		}
	}
	
	
	
	
	
	$('body').on('keyup', '.doctorName', function(event){
		event.preventDefault();
		$(this).autocomplete({
			autoFocus : true,
			maxShowItems : 5,
			minLength : 3,
			delay : 500,
			source: doctorList,
			select: function(event, ui){
				$(this).val(ui.item.label);
			}
		});
	});
	
	

	
	  function addSpecimenListEntry(specimenList, currentRow) {
		  
			var select = document.createElement("select");
			select.setAttribute("name", "specimen");
			select.setAttribute("class", "specimen");
			select.classList.add("form-control");
			select.classList.add("dis-auto-width");
			select.classList.add("dis-bottom-margin");
			 
			var i =0;
		  
		  while (i < specimenList.length) {

				var value = $.trim(specimenList[i]);
				var text = $.trim(specimenList[i+1]);
				
				var optionNode =  document.createElement("option");
				optionNode.value = value;
				optionNode.appendChild(document.createTextNode(text));
				select.appendChild(optionNode);
				
				i += 2;
			}
			select1 = currentRow.find('#addSpecimen');
			select1.append(select);
	    }
	
	
	

	/*
	 * Calculate updated Rate, Discount Amount, Net Amount Gross, Total Discount
	 * and Net Amount based on changes quantity
	 */

	$(document)
			.on(
					'keyup',
					'.qty',
					function(event, param) {
						event.preventDefault();
						
						console.log($(this));
						
						isNumber($(this));
						
						// console.log('Param : ' + param);
						var currentRow = $(this).closest('tr');
						// console.log('Current Row ' + currentRow);
						var quantity = $(this).val();
						// console.log('Quantity : ' + quantity);
						var baseRate = currentRow.find('.rate').val();
						// console.log('Base Rate : ' + baseRate);
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
	 * Specimen Description
	 */
	
	
	
	
	
	/*
	 * Calculate Gross Amount, Discount Amount and Net Amount
	 */

	function calculateGrossSummary() {
		// console.log('ram');
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
		// console.log('Row Object : ' + currentRow);
		// console.log('Service Name : ' + serviceName);
		// console.log(serviceNameArr);
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
	
	
	

	
  function fetchDeletedPanelServiceCode(serviceCode){
		  
		  // console.log('In fetchDeletedPanelServiceCode');
		  
		  $.ajax({
				url : '/patient.transfer',
				type : 'post',
				dataType : 'text',
				data : {
					ACTION : 'GET_PANEL_SERVICE_CODE',
					serviceCode : serviceCode
				},
				success : function(data) {
					
					data = data.replace(/^\W+|\W+$/g, "");

					if (data.length !== 0) {
						var arr = data.replace("[", "").replace("]", "").split(",");
						var i = 0;
						while (i < arr.length) {
									// console.log('Data to be deleted : ' +
									// arr[i]);
								  serviceCodeList.splice(serviceCodeList.indexOf($.trim(arr[i])), 1);
							i += 2;
						}
					}
						
					// console.log('step 1');
						
						// console.log('Service Code List Length : ' +
						// serviceCodeList.length);
					  
					/*
					 * for (var i=0; i<serviceCodeList.length; i++){
					 * console.log('After serviceCodeList : ' +
					 * serviceCodeList[i]); }
					 */
				  
					 // console.log('step 2');
					
					
				},
				error : function(data) {
					var errorMsg = "There is a problem processing your request";
					alert(errorMsg);
					alert(data.responseText);
				}
			});
		  
	  }
	
	function parentChildServiceCodeCheck(serviceDesc, serviceCode, childServiceDesc, currentRow, token) {
		
		console.log('In parentChildServiceCodeCheck');
		console.log('Given Service Code : ' + serviceCode);
		
		
		for (var i=0; i<serviceCodeList.length; i++){
			console.log('Service Code List : ' + serviceCodeList[i]);
		}
		
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
						swal("Duplicate Service", "\"" + serviceDesc + "\" service name already exists", "info");
					} else {
						swal("Duplicate Service", "\"" + childServiceDesc + "\" is present in \""+ serviceDesc +"\" service name.\n\r Please check", "info");
					}
					calculateGrossSummary();
					return false;
			}
		}

		return true;
	}
	
	
	


	/*
	 * Disable default Enter keypress form submit event
	 */

	$('.serviceDesc').on('keypress', function(event) {
		var x = event.which || event.keyCode;
		console.log('Service Desc event : ' + x);
		
		let unique = [...new Set(tempServiceNameList)];
		console.log('Total Unique : ' + unique);
		
		var currentRow = $(this).closest('tr');
	
		if (x === 13) {
			event.preventDefault();
			
			/*
			 * User entered service names
			 */
			//enterServiceNameList.push($.trim($(this).val()));
			
			var count = 0;
			
			for (var i=0; i<unique.length; i++){
				console.log('temp name :' + unique[i]);
				console.log('typed Value : ' + $(this).val());
				if ($.trim($(this).val()).localeCompare($.trim(unique[i])) === 0){
					count++;
				} 
			}
			
			console.log('total count : ' + count);
			
			if (count === 0){
				swal('Sorry!', 'Unknown Service description', 'info');
				return false;
			}
			
			
			
		} else if (x == 8){
		
			var serviceCode = currentRow.find('.serviceCode').val();
			console.log('service code to be deleted : '+ serviceCode);
			currentRow.find('.qty').val('');
			currentRow.find('.rate').val('');
			currentRow.find('.discount').val('');
			currentRow.find('.disAmount').val('');
			currentRow.find('.netAmount').val('');
			currentRow.find('.specimen').hide();
			currentRow.find('.serviceCode').val('');
			currentRow.find('.serviceId').val('');
			currentRow.find('.minorCode').val('');
			currentRow.find('.specimen').hide();
			
			var count =0;
			for (var i=0; i<serviceCodeList.length; i++){
				if (serviceCode === $.trim(serviceCodeList[i])){
					count++;
				}
			}
			
			if (count == 1){
			} else {
				serviceCodeList.splice(serviceCodeList.indexOf(serviceCode), 1);
			}
			
			// reset();
		} else if (x === 9) {
			console.log('In else part');
			var count = 0;
			
			for (var i=0; i<unique.length; i++){
				console.log('temp name : ' + unique[i]);
				console.log('typed Value : ' + $(this).val());
				
				if ($.trim($(this).val()).localeCompare($.trim(unique[i])) === 0){
					//console.log('return false');
					count++;
				} 
				
			}
			
			console.log('total count : ' + count);
			
			if (count === 0){
				swal('Sorry!', 'Unknown Service description', 'info');
				return false;
			}
			
			
			var rate = currentRow.find('.rate').val();
			console.log('rate : ' + rate);
			if (rate === ''){
				console.log('In rate if part');
				currentRow.find('.serviceDesc').val('');
				///currentRow.find('.serviceDesc').focus();
			}
			
		}
	});
	
	$('.doctorName').on('keypress', function(event) {
		var x = event.which || event.keyCode;
		if (x === 13) {
			event.preventDefault();
		}
	});

	$('.qty').on('keypress', function(event) {
		var x = event.which || event.keyCode;
		console.log('Qty event : ' + x);
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
	
	$(document).on('click', '#btn-submit', function(e){
		
		var frm = $('#service-order-frm');
		var serviceOrderId = $('#order-id').val();
		
		const swalWithBootstrapButtons = swal.mixin({
			  confirmButtonClass: 'btn btn-success',
			  cancelButtonClass: 'btn btn-danger',
			  buttonsStyling: false,
			})
			
			
		
		var truee = 0;
		var totalTrue = 0;
		
		let unique = [...new Set(tempServiceNameList)];
		console.log('total Unique : ' + unique);
		console.log('total Unique length : ' + unique.length);
		
		$('.serviceDesc').each(function(event){
			
			
			
			if ($(this).val() !== ''){
				for (var j=0; j<unique.length; j++){
					//console.log('previous typed Value : ' + unique[j]);
					console.log('entered name : ' + $(this).val());
					if ($.trim($(this).val()).localeCompare($.trim(unique[j])) === 0){
						truee++;
						console.log('This is truee value : ');
					} 
				}
				totalTrue++;
			}
			
		});
		
		console.log('Truee count : ' + truee);
		console.log('Total True count : ' + totalTrue);
		
		if (truee !== totalTrue){
			swal('Sorry!', 'Kindly rectify unknown Service description before submitting', 'info');
			return false;
		}
		
		
		
		
		swal({
			  title: 'Are you sure?',
			  text: "You won't be able to revert this!",
			  type: 'warning',
			  inputValue: serviceOrderId,
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes, Save it!'
			}).then((result) => {
			  if (result.value) {
				  
				  console.log('Result : ' + result.value);
				  console.log('Order id : ' + serviceOrderId);
				  
				  if (!serviceOrderId){
					  
					  console.log('Service Id not present');
					  
					  $.ajax({
					        type: "POST",
					        url: "/patient.transfer",
					        data:  frm.serialize() + "&ACTION=INSERT_SERVICE_ORDER",
					        cache: false,
					        success: function(response) {
					        	serviceOrderNo = response;
					        	if (serviceOrderNo === 0){
					        		swal('Ohh no!!','Data have not been saved.\n\r error code :' + serviceOrderNo,'error');
					        	} else {
					        		swal('Well done!','Data have been saved.\n\r service order id :' + serviceOrderNo,'success');
					        		reset();
					        	}
					        },
					        failure: function (response) {
					            swal(
					            "Internal Error",
					            "Oops, your note was not saved.", // had a
																	// missing
																	// comma
					            "error"
					            )
					        }
					    });
					  
				  } else {
					  
					  console.log('Service Id present');
					  $.ajax({
					        type: "POST",
					        url: "/patient.transfer",
					        data:  frm.serialize() + "&soNumber="+serviceOrderId+"&ACTION=INSERT_SERVICE_ORDER",
					        cache: false,
					        success: function(response) {
					        	serviceOrderNo = response;
					        	if (serviceOrderNo === 0){
					        		swal('Ohh no!!','Data have not been saved.\n\r error code :' + serviceOrderNo,'error');
					        	} else {
					        		swal('Well done!','Data have been updated.\n\r service order id :' + serviceOrderNo,'success');
					        		reset();
					        	}
					        },
					        failure: function (response) {
					            swal(
					            "Internal Error",
					            "Oops, your note was not saved.", // had a
																	// missing
																	// comma
					            "error"
					            )
					        }
					    });
				  }
				  
				
				  
				/*
				 * swalWithBootstrapButtons( 'Well done!', 'Your Data have been
				 * saved.', 'success' )
				 */
			  }else if (
					    // Read more about handling dismissals
					    result.dismiss === swal.DismissReason.cancel
					  ) {
					    swalWithBootstrapButtons(
					      'Cancelled',
					      'Service Order is not saved...',
					      'error'
					    )
			  }
			})
		
		
	});
	
	var inputOptions = {};
	var count = 0;
	
	$(document).on('click', '.previousBtn', function(event){
		
		/*
		 * var patId = $(this).attr('data-id'); console.log('patient Id ' +
		 * patId);
		 * 
		 * const {value: patNumber} = swal({ title: 'Enter Patient Number',
		 * input: 'text', inputValue: patId, showCancelButton: true,
		 * inputValidator: (value) => { return !value && 'You need to write
		 * something!'; } }).then(function(result){
		 * 
		 * console.log('Click value : ' + result.value);
		 * 
		 * if (result.value){
		 */
					
					console.log('Count : ' + ++count);
					
					  $('.myModal .modal-body').load( '/patient.transfer', 
							  { ACTION :  'FETCH_PREVIOUS_SERVICE_ORDER', 
					  },
					  function(response, status, xhr) {
					  if (status === 'error') { 
					  var msg = "Sorry but there was an error: "; 
					  swal( "Oh no!", msg +  xhr.status + " " + xhr.statusText, "error");
					}
					  
					var myTable = $('#example1').DataTable( 
							  { 
								searching: false,
								scrollY :  "300px", 
							    scrollX : true, 
							    scrollCollapse : true, 
							    paging :  false, 
							    info: false,
							    columnDefs: [
							    	{
							    		'targets': 0,
							    		'className': 'text-center',
							    		'orderable': false,
							    		'width': '2%',
							    		
							    	},
							    	{
							    		'targets': 1,
							    		'className': 'text-center',
							    		'orderable': false,
							    		'width': '10%',
							    		
							    	},
							    	{
							    		"targets": 2,
							    		"className": "text-right",
							    		"orderable": false,
							    		'width': '3%'
							    	},
							    	{
							    		"targets": 3,
							    		"className": "text-center",
							    		"orderable": false,
							    		'width': '10%'
							    	},
							    	{
							    		"targets": 4,
							    		"className": "text-center",
							    		"orderable": false,
							    		'width': '3%'
							    	},
							    	{
							    		"targets": 5,
							    		"className": "text-center",
							    		"orderable": false,
							    		'width': '4%'
							    	},
							    	{
							    		"targets": 6,
							    		"className": "text-center",
							    		"orderable": false,
							    		'width': '2%'
							    	}
							    	
							    	
							    ]
							    });
					  
					
					  
					$('.myModal .modal-title').html("Previous Service Order  Record");
					
					  
					  
				
					  
					  $('.myModal').on('shown.bs.modal', function(){
						  myTable.columns.adjust().draw(); 
						}).modal({show: true});
					  
					 
		
					
				});
					
					
					
				/*
				 * } else if ( result.dismiss === swal.DismissReason.cancel ){
				 * $('.myModal').modal('hide'); }
				 */
					
			
		
	
		// });
			
		
		
	});
	
	/*
	 * Function for getting Service order detail..
	 */
	
	$(document).on('click', '.view-btn', function(event){
		
		 $('.myModal').modal('hide');
		 reset();
		 
		 var soNumber = $(this).attr('so-no');
		 
		 // console.log('IP Number : ' + ipNumber);
		// console.log('SO Number : ' + soNumber);
		 
		 var req = $.ajax({
			 		
			 	url: '/patient.transfer',
			 	type: 'post',
			 	datatype: 'text',
			 	data: {
			 		ACTION: 'FETCH_PREV_SERVICE_ORDER_DETAIL',
			 		soNumber: soNumber
			 	},
			 	success: function(response){

			 	// console.log(JSON.stringify(response));
			 		
			 	
			 		var arr = response.replace("[", "").replace("]", "").split(",");
			 		// var arr = response.replace("[", "").replace("]",
					// "").match(/((?=\s*,|\s*$)\(.*?\)|[^",\s]+)(?=\s*,|\s*$)/g);

			 		
			 	/*
				 * for (var i=0; i<arr.length; i++){
				 * console.log(JSON.stringify(arr[i])); }
				 */
			 		
			 		
			 		
			 		// soDetailList.length = 0;
			 		
			 		var soNUmber = [];
			 		var siNumber = [];
			 		var serviceCode = [];
			 		var serviceDesc = [];
			 		var serviceCat = [];
			 		var minorCode = [];
			 		var qty = [];
			 		var rate = [];
			 		var discount = [];
			 		var doctorCode = [];
			 		var isBilled = [];
			 		var totalRow = 0;
			 	
			 		tempServiceNameList.length = 0;

			 		for (var i=0; i<arr.length; i+=11){
			 			
			 			// console.log('Sevice Description ' +
						// String(arr[i+3]));
			 			
			 			soNUmber.push(String(arr[i]));
			 			siNumber.push(String(arr[i+1]));
			 			serviceCodeList.push(String($.trim(arr[i+2]))); 
			 			tempServiceNameList.push(String(arr[i+3]).replace(/\|/g, ',')); 
			 			serviceCode.push(String(arr[i+2]));
			 			serviceDesc.push(String(arr[i+3]).replace(/\|/g, ','));
			 			serviceCat.push(String(arr[i+4]));
			 			minorCode.push(String(arr[i+5]));
			 			qty.push(String(arr[i+6]));
			 			rate.push(String(arr[i+7]));
			 			discount.push(String(arr[i+8]));
			 			doctorCode.push(String(arr[i+9]));
			 			isBilled.push(String(arr[i+10]));
			 			++totalRow;
			 		}

			 		var i = 0;
			 		var initialRow = 10;
			 		
			 		// console.log('Initial Row : ' + initialRow + ' Total Row :
					// ' + totalRow);
			 		
			 		if (totalRow > initialRow){
			 			
			 			var remainRow = totalRow - initialRow;
			 			
			 		// console.log('Remaining row : ' + remainRow);
			 			
			 			var l = 0;
			 			
			 			while(l<remainRow){
			 				
			 				// console.log('No of row : ' + l);
			 				
			 				table.row
							.add(
									[
											'<div class="row-order" id="' + (initialRow + 1) + '">'
													+ (initialRow + 1) + '</div>',
											'<input type="text"	name="serviceDesc" class="serviceDesc form-control dis-auto-width dis-bottom-margin" tabindex="'
													+ (initialRow + 1) + '"><input type="hidden" class="serviceId" name="serviceId"><input type="hidden" name="minorCode" class="minorCode"><input type="hidden" name="serviceCode" class="serviceCode">',
											'<input type="text"	name="qty" class="text-align-center qty form-control dis-auto-width dis-bottom-margin">',
											'<input type="text"	name="rate"  class="text-align-right rate form-control dis-auto-width dis-bottom-margin" readonly>',
											'<input type="text" name="discount" class="text-align-center discount form-control dis-auto-width dis-bottom-margin">',
											'<input type="text"	name="disAmount"  class="text-align-right disAmount form-control dis-auto-width dis-bottom-margin" readonly>',
											'<input type="text"	name="netAmount"  class="text-align-right netAmount form-control dis-auto-width dis-bottom-margin" readonly>',
											'<span id="addSpecDoctor"></span>',
											'<div class="delete-btn"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
							.draw();
			 				l++;
			 				initialRow++;
			 			}
			 		}
			 		
			 		if ($.trim(isBilled[0]) === 'NOT_BILLED'){
			 			// console.log('Not Billed');
			 			
			 			$('#btn-submit').prop('disabled', false);
			 			
			 			$('.serviceDesc').each(function(event){
			 				if (i < totalRow){
				 			$(this).val(serviceDesc[i]);
				 			$(this).prop('readonly', false);
			 				}
				 			i++;
				 		});
				 		
				 		i =0;
				 		$('.rate').each(function(event){
				 			
				 			var serviceId = siNumber[i];
				 			var patientNumber = $('#patient-no');
				 			var rateObject = $(this);
				 			
				 			// console.log('initial Value : ' + i);
				 			// console.log('Total Row : ' + totalRow);
				 			
				 			if (i < totalRow){
				 				// console.log('In if part');
				 				var req = $.ajax({
					 				url: '/patient.transfer',
					 				type: 'post',
					 				datatype: 'text',
					 				data: {
					 					ACTION: 'FETCH_SERVICE_ID_RATE',
					 					serviceId: serviceId
					 				},
					 				success: function(data){
					 					rateObject.val($.trim(data));
					 					$('.qty').trigger('keyup', [totalRow]);
					 					
					 				},
					 				failure: function (response) { 
								 		alert(response.responseText);
								 		return;
											  },
											  error: function(response){
												  alert(response.responseText);
												  return;
											  }
					 			});
				 			}
				 		i++;
				 		});
				 		
				 		i =0;
				 		
				 		$('.discount').each(function(event){
				 			if (i < totalRow){
				 			$(this).val($.trim(discount[i]));
				 			$(this).prop('readonly', false);
				 			}
				 			i++;
				 		});

				 		i =0;
				 		
				 		$('.serviceCode').each(function(event){
				 			if (i < totalRow){
				 				$(this).val($.trim(serviceCode[i]));
				 				$(this).prop('readonly', false);
				 			}
				 			i++;
				 		});
				 		
				 		i =0;
				 		
				 		$('.minorCode').each(function(event){
				 			if (i < totalRow){
				 				$(this).val($.trim(minorCode[i]));
				 				$(this).prop('readonly', false);
				 			}
				 			i++;
				 		});
				 		
				 		i =0;
				 		
				 		$('.serviceId').each(function(event){
				 			if (i < totalRow){
				 				$(this).val($.trim(siNumber[i]));
				 				$(this).prop('readonly', false);
				 			}
				 			i++;
				 		});
				 		
				 		$('#order-id').val(soNUmber[0]);
				 		
				 		i =0;
				 		
				 		$('.qty').each(function(event){
				 			if (i < totalRow){
				 			$(this).val($.trim(qty[i]));
				 			$(this).prop('readonly', false);
				 			}
				 			i++;
				 		});
			 			
			 			
			 		} else {
			 			// console.log('Billed');
			 			
			 			$('#btn-submit').prop('disabled', true);
			 			
			 			$('.serviceDesc').each(function(event){
			 				if (i < totalRow){
			 					$(this).val($.trim(serviceDesc[i]));
					 			$(this).prop('readonly', true);
			 				}
				 			
				 			i++;
				 		});
				 		
				 		i =0;
				 		$('.rate').each(function(event){
				 			if (i < totalRow){
				 				console.log('In if part');
				 				$(this).val($.trim(rate[i]));
				 				$(this).prop('readonly', true);
			 					
				 			}
				 		i++;
				 		});
				 		
				 		i =0;
				 		
				 		$('.discount').each(function(event){
				 			if (i < totalRow){
				 			$(this).val($.trim(discount[i]));
				 			$(this).prop('readonly', true);
				 			}
				 			i++;
				 		});
				 		
				 		i =0;
				 		
				 		$('.serviceCode').each(function(event){
				 			if (i < totalRow){
				 				$(this).val($.trim(serviceCode[i]));
				 				$(this).prop('readonly', true);
				 			}
				 			i++;
				 		});
				 		
				 		i =0;
				 		
				 		$('.minorCode').each(function(event){
				 			if (i < totalRow){
				 				$(this).val($.trim(minorCode[i]));
				 				$(this).prop('readonly', true);
				 			}
				 			i++;
				 		});
				 		
				 		i =0;
				 		
				 		$('.serviceId').each(function(event){
				 			if (i < totalRow){
				 				$(this).val($.trim(siNumber[i]));
				 				$(this).prop('readonly', true);
				 			}
				 			i++;
				 		});
				 		
				 		$('#order-id').val(soNUmber[0]);
				 		
				 		i =0;
				 		
				 		$('.qty').each(function(event){
				 			if (i < totalRow){
				 			$(this).val(qty[i]);
				 			$(this).trigger('keyup', [totalRow]);
				 			$(this).prop('readonly', true);
				 			}
				 			i++;
				 		});
			 		}
			 	},
			 	failure: function (response) { 
			 		alert(response.responseText);
						  },
						  error: function(response){
							  alert(response.responseText);
						  }
		 });
	});
	
	
	$(document).on('click', '#btn-reset', function(event){
		
		// console.log('Reset Calling');
		reset();
		
		
		
	});
	
	
	function reset(){
		// console.log('Reset Called');
		
		$('#order-id').val('');
		$('.serviceDesc').val('');
		$('.serviceDesc').prop('readonly', false);
		$('.serviceId').val('');
		$('.minorCode').val('');
		$('.serviceCode').val('');
		$('.qty').val('');
		$('.qty').prop('readonly', false);
		$('.rate').val('');
		$('.discount').val('');
		$('.discount').prop('readonly', false);
		$('.disAmount').val('');
		$('.netAmount').val('');
		$('#sum-gross-amount').val('');
		$('#sum-discount-amount').val('');
		$('#sum-net-amount').val('');
		serviceCodeList.length = 0;
		$('.specimen').hide();
		
		var totalRow = 0;
		
		$('.serviceDesc').each(function(event){
			totalRow++;
		});
		
		
		var i = 0;
 		var initialRow = 10;
 		
 		console.log('Initial Row : ' + initialRow + ' Total Row :' + totalRow);
 		
 		if (totalRow < initialRow){
 			
 			var remainRow = initialRow - totalRow ;
 			
 		// console.log('Remaining row : ' + remainRow);
 			
 			var l = 0;
 			
 			while(l<remainRow){
 				
 				// console.log('No of row : ' + l);
 				
 				table.row
				.add(
						[
								'<div class="row-order" id="' + (totalRow + 1) + '">'
										+ (totalRow + 1) + '</div>',
								'<input type="text"	name="serviceDesc" class="serviceDesc form-control dis-auto-width dis-bottom-margin" tabindex="'
										+ (totalRow + 1) + '"><input type="hidden" class="serviceId" name="serviceId"><input type="hidden" name="minorCode" class="minorCode"><input type="hidden" name="serviceCode" class="serviceCode">',
								'<input type="text"	name="qty" class="text-align-center qty form-control dis-auto-width dis-bottom-margin">',
								'<input type="text"	name="rate"  class="text-align-right rate form-control dis-auto-width dis-bottom-margin" readonly>',
								'<input type="text" name="discount" class="text-align-center discount form-control dis-auto-width dis-bottom-margin">',
								'<input type="text"	name="disAmount"  class="text-align-right disAmount form-control dis-auto-width dis-bottom-margin" readonly>',
								'<input type="text"	name="netAmount"  class="text-align-right netAmount form-control dis-auto-width dis-bottom-margin" readonly>',
								'<span id="addSpecDoctor"></span>',
								'<div class="delete-btn"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
				.draw();
 				l++;
 				totalRow++;
 			}
 		} else {
 			
 			table.rows().remove().draw();
 			
 			
 			//var counter = 10;
 			
 			
 			
 			for (var i = 0; i < counter; i++) {
 				table.row
 						.add(
 								[
 										'<div class="row-order" id="' + (i + 1) + '">'
 												+ (i + 1) + '</div>',
 										'<input type="text"	name="serviceDesc" class="serviceDesc form-control dis-auto-width dis-bottom-margin" tabindex="'
 												+ (i + 1) + '"><input type="hidden" class="serviceId" name="serviceId"><input type="hidden" name="minorCode" class="minorCode"><input type="hidden" name="serviceCode" class="serviceCode">',
 										'<input type="text"	name="qty"  class="text-align-center qty form-control dis-auto-width dis-bottom-margin">',
 										'<input type="text"	name="rate"  class="text-align-right rate form-control dis-auto-width dis-bottom-margin" readonly>',
 										'<input type="text" name="discount" class="text-align-center discount form-control dis-auto-width dis-bottom-margin">',
 										'<input type="text"	name="disAmount"  class="text-align-right disAmount form-control dis-auto-width dis-bottom-margin" readonly>',
 										'<input type="text"	name="netAmount"  class="text-align-right netAmount form-control dis-auto-width dis-bottom-margin" readonly>',
 										'<span id="addSpecimen"></span>',
 										'<div class="delete-btn"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
 						.draw(false);
 			}
 			

 			
 			
 			
 			
 		}
		
		
	}
	



	// This code solves the problem, at least in IE and Firefox (haven't tested
	// any other, but I give it a reasonable chance of working if the problem
	// even exists in other browsers).

	// Prevent the backspace key from navigating back.
/*
 * $(document).unbind('keydown').bind('keydown', function (event) { if
 * (event.keyCode === 8) { var doPrevent = true; var types = ["text",
 * "password", "file", "search", "email", "number", "date", "color", "datetime",
 * "datetime-local", "month", "range", "search", "tel", "time", "url", "week"];
 * var d = $(event.srcElement || event.target); var disabled =
 * d.prop("readonly") || d.prop("disabled"); if (!disabled) { if
 * (d[0].isContentEditable) { doPrevent = false; } else if (d.is("input")) { var
 * type = d.attr("type"); if (type) { type = type.toLowerCase(); } if
 * (types.indexOf(type) > -1) { doPrevent = false; } } else if
 * (d.is("textarea")) { doPrevent = false; } } if (doPrevent) {
 * event.preventDefault(); return false; } } });
 */

	
	

});

/*
 * Style 1 not working properly
 */
function isNumber1(event) {
	
	var char = String.fromCharCode(event.which || event.keyCode);
	console.log(char);
	if (!(/[0-9]/.test(char))){
		console.log('test failed');
		// event.preventDefault();
		return false;
	}
	return true;
}
/*
 * Style 2 not working
 */

function isNumber2(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
    	console.log('test failed');
        return false;
    }
    return true;
}

/*
 * Style 3
 */
function isNumber(input) {
	console.log(input.val());
	var replaceInput = input.val();
	var regex = /[^0-9]/;
	replaceInput = replaceInput.replace(regex, "");
	input.val(replaceInput);
}






