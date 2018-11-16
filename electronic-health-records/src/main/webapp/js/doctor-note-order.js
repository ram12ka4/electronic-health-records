$(function() {
	
	/*
	 * Initial Set Value
	 */
		$('#fromDate').datepicker().datepicker("setDate", new Date());

	
	/*
	 * Enable super notes
	 */
	 $('#treatment').summernote({
		 placeholder: 'Treatment',
			tabsize: 2,
	        height: 300,
	        focus: true,
	        minHeight: null,             // set minimum height of editor
	        maxHeight: null,             // set maximum height of editor
	        /*
			 * toolbar: [ // [groupName, [list of button]] ['style', ['bold',
			 * 'italic', 'underline', 'clear']], ['font', ['strikethrough',
			 * 'superscript', 'subscript']], ['fontsize', ['fontsize']],
			 * ['color', ['color']], ['para', ['ul', 'ol', 'paragraph']],
			 * ['height', ['height']] ]
			 */

		 });
	 $('#medication').summernote({
		 placeholder: 'Medication',
		 tabsize: 2,
		 height: 300,
		 focus: true,
		 minHeight: null,             // set minimum height of editor
		 maxHeight: null,             // set maximum height of editor
		 /*
		  * toolbar: [ // [groupName, [list of button]] ['style', ['bold',
		  * 'italic', 'underline', 'clear']], ['font', ['strikethrough',
		  * 'superscript', 'subscript']], ['fontsize', ['fontsize']],
		  * ['color', ['color']], ['para', ['ul', 'ol', 'paragraph']],
		  * ['height', ['height']] ]
		  */
		 
	 });
	 $('#laboratory').summernote({
		 placeholder: 'Laboratory',
		 tabsize: 2,
		 height: 300,
		 focus: true,
		 minHeight: null,             // set minimum height of editor
		 maxHeight: null,             // set maximum height of editor
		 /*
		  * toolbar: [ // [groupName, [list of button]] ['style', ['bold',
		  * 'italic', 'underline', 'clear']], ['font', ['strikethrough',
		  * 'superscript', 'subscript']], ['fontsize', ['fontsize']],
		  * ['color', ['color']], ['para', ['ul', 'ol', 'paragraph']],
		  * ['height', ['height']] ]
		  */
		 
	 });
	 $('#diet').summernote({
		 placeholder: 'Diet',
		 tabsize: 2,
		 height: 300,
		 focus: true,
		 minHeight: null,             // set minimum height of editor
		 maxHeight: null,             // set maximum height of editor
		 /*
		  * toolbar: [ // [groupName, [list of button]] ['style', ['bold',
		  * 'italic', 'underline', 'clear']], ['font', ['strikethrough',
		  * 'superscript', 'subscript']], ['fontsize', ['fontsize']],
		  * ['color', ['color']], ['para', ['ul', 'ol', 'paragraph']],
		  * ['height', ['height']] ]
		  */
		 
	 });
	 $('#others').summernote({
		 placeholder: 'Others',
		 tabsize: 2,
		 height: 300,
		 focus: true,
		 minHeight: null,             // set minimum height of editor
		 maxHeight: null,             // set maximum height of editor
		 /*
		  * toolbar: [ // [groupName, [list of button]] ['style', ['bold',
		  * 'italic', 'underline', 'clear']], ['font', ['strikethrough',
		  * 'superscript', 'subscript']], ['fontsize', ['fontsize']],
		  * ['color', ['color']], ['para', ['ul', 'ol', 'paragraph']],
		  * ['height', ['height']] ]
		  */
		 
	 });
	

	 
	 $(document).on('click', '.btn-medic', function(event){
		
		 
		 
	 })
	 

	
	
		
	

	$(document).on('click', '#btn-submit', function(e){
		
		var frm = $('#service-order-frm');
		var serviceOrderId = $('#order-id').val();
		
		const swalWithBootstrapButtons = swal.mixin({
			  confirmButtonClass: 'btn btn-success',
			  cancelButtonClass: 'btn btn-danger',
			  buttonsStyling: false,
			})
			
		
			/*
			 * data validation
			 */
			var x = dataValidation();
			if (x === false){
				swalWithBootstrapButtons('Ohh no!', 'No Data available <br> Or <br> There is something wrong', 'info');
				return false;
			}
			
			
		
		var trueCount = 0;
		var totalCount = 0;
		
		let unique = [...new Set(serviceNameList)];
		console.log('total Unique : ' + unique);
		console.log('total Unique length : ' + unique.length);
		
		$('.serviceDesc').each(function(event){
			console.log('entered name : ' + $(this).val());
			if ($(this).val()){
				console.log('step 1');
				for (var j=0; j<unique.length; j++){
						
					// if
					// ($.trim($(this).val()).localeCompare($.trim(unique[j]))
					// === 0){
						if ($.trim($(this).val()) === $.trim(unique[j])){
						console.log('Stored Value : ' + unique[j]);
						console.log('entered name : ' + $(this).val());
						trueCount++;
						console.log('True');
					} 
				}
				totalCount++;
			}
			
		});
		
		console.log('Truee count : ' + trueCount);
		console.log('Total True count : ' + totalCount);
		
		if (trueCount !== totalCount){
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
					        url: "service.order",
					        data:  frm.serialize() + "&ACTION=INSERT_SERVICE_ORDER",
					        cache: false,
					        success: function(response) {
					        	serviceOrderNo = response;
					        	if (serviceOrderNo === 0){
					        		swal('Ohh no!!','Data have not been saved.<br> ERROR CODE :' + serviceOrderNo,'error');
					        	} else {
					        		swal('Well done!','Data have been saved.<br> SERVICE ORDER ID :' + serviceOrderNo,'success');
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
					  
					  // console.log('Service Id present');
					  $.ajax({
					        type: "POST",
					        url: "service.order",
					        data:  frm.serialize() + "&soNumber="+serviceOrderId+"&ACTION=INSERT_SERVICE_ORDER",
					        cache: false,
					        success: function(response) {
					        	serviceOrderNo = response;
					        	if (serviceOrderNo === 0){
					        		swal('Ohh no!!','Data have not been saved.<br> ERROR CODE :' + serviceOrderNo,'error');
					        	} else {
					        		swal('Well done!','Data have been updated.<br> SERVICE ORDER ID :' + serviceOrderNo,'success');
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
		
							
					// console.log('Count : ' + ++count);
					
					  $('.myModal .modal-body').load( 'service.order', 
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
					  
					
					  
					$('.myModal .modal-title').html("Previous Service Order  Record : " + $('#patient-no').val());
					$('.myModal .modal-title').css('text-align', 'center');
					
					  
					  
				
					  
					  $('.myModal').on('shown.bs.modal', function(){
						  myTable.columns.adjust().draw(); 
						}).modal({show: true});
					  
					 
		
					
				});
					
					
					
			
			
		
		
	});
	
	/*
	 * Function for getting previous Service order detail..
	 */
	
	$(document).on('click', '.view-btn', function(event){
		
		 $('.myModal').modal('hide');
		 reset();
		 
		 var soNumber = $(this).attr('so-no');
		
		 
		 // console.log('IP Number : ' + ipNumber);
		// console.log('SO Number : ' + soNumber);
		 
		 var req = $.ajax({
			 		
			 	url: 'service.order',
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
			 		var specimen = [];
			 		var treatedBy = [];
			 		var totalRow = 0;
			 		var soDate;
			 	
			 		serviceNameList.length = 0;

			 		for (var i=0; i<arr.length; i+=14){
			 			
			 			soNUmber.push(String(arr[i]));
			 			siNumber.push(String(arr[i+1]));
			 			serviceCodeList.push(String($.trim(arr[i+2]))); 
			 			serviceNameList.push(String($.trim(arr[i+3])).replace(/\|/g, ',')); 
			 			serviceCode.push(String(arr[i+2]));
			 			serviceDesc.push(String(arr[i+3]).replace(/\|/g, ','));
			 			serviceCat.push(String(arr[i+4]));
			 			minorCode.push(String(arr[i+5]));
			 			qty.push(String(arr[i+6]));
			 			rate.push(String(arr[i+7]));
			 			discount.push(String(arr[i+8]));
			 			doctorCode.push(String(arr[i+9]));
			 			isBilled.push(String(arr[i+10]));
			 			specimen.push(String(arr[i+11]));
			 			treatedBy.push(String(arr[i+12]));
			 			soDate = String($.trim(arr[i+13]));
			 			++totalRow;
			 		}
			 		
			 		console.log('Service Order date : ' + soDate);
			 		$('#fromDate').datepicker().datepicker("setDate", soDate);
			 		
			 		/*
					 * for (x in serviceNameList){ console.log('Service Name
					 * List after fetching service order : ' +
					 * serviceNameList[x]); }
					 */

			 		var i = 0;
			 		var initialRow = 10;
			 		
			 		if (totalRow > initialRow){
			 			
			 			var remainRow = totalRow - initialRow;
			 			
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
											'<span class="addSpecDoctor"></span>',
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
				 			// var $this = $(this);
			 				
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
					 				url: 'service.order',
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
				 				var currentRow = $(this).parents('tr');
				 				switchSpecimenDcotorName($.trim(serviceCode[i]), currentRow, $.trim(treatedBy[i]), $.trim(serviceCat[i]), $.trim(doctorCode[i]), $.trim(specimen[i]));
				 				
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
			 			console.log('Billed');
			 			
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
				 				// console.log('In if part');
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
	
	
	function dataValidation(){
		
		var serviceCount = 0;
		var qtyCount = 0;
		var discountCount = 0;
		
		const swalWithBootstrapButtons = swal.mixin({
			  confirmButtonClass: 'btn btn-success',
			  cancelButtonClass: 'btn btn-danger',
			  buttonsStyling: false,
			})
		
		$('.serviceDesc').each(function(){
			if ($(this).val()){
				serviceCount++;
			}
		});
		
		$('.qty').each(function(){
			if ($(this).val()){
				qtyCount++;
			}
		});
		
		$('.discount').each(function(){
			if ($(this).val()){
				discountCount++;
			}
		});	

		console.log('Total Data : ' + serviceCount);
		console.log('Total Data : ' + qtyCount);
		console.log('Total Data : ' + discountCount);
		console.log(discountCount == 0 && qtyCount == 0 && serviceCount == 0);
		
		if (discountCount == 0 && qtyCount == 0 && serviceCount == 0){
			return false;
		} else {
			return true;
		}
	}
	
	function reset(){
		// console.log('Reset Called');
		
		$('#fromDate').datepicker().datepicker("setDate", new Date());
		$('.select-box').prop('selectedIndex',0);
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
		$('.addSpecDoctor').children().remove();
		serviceCodeList.length = 0;
		serviceNameList.length = 0;
		$('.specimen').hide();
		
		var totalRow = 0;
		
		$('.serviceDesc').each(function(event){
			totalRow++;
		});
		
		
		var i = 0;
 		var initialRow = 10;
 		
 		// console.log('Initial Row : ' + initialRow + ' Total Row :' +
		// totalRow);
 		
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
								'<span class="addSpecDoctor"></span>',
								'<div class="delete-btn"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
				.draw();
 				l++;
 				totalRow++;
 			}
 		} else {
 			
 			table.rows().remove().draw();
 			
 			
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
 										'<span class="addSpecDoctor"></span>',
 										'<div class="delete-btn"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
 						.draw(false);
 			}
  		}
		
		
	}
	
	

});






