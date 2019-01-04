$(function() {
	
	var itemList = [];
	var batchList  = [];
	var doctorList  = [];
	var dataList = [];
	
	
	/*
	 * Initial Set Value
	 */
	$('#voucher-id').val();
		$('#fromDate').datepicker().datepicker("setDate", new Date());
		$('#fromDate').datepicker().datepicker("option", "disabled", true);
		$('#refer-doctor').prop('disabled', true);
		$('#order-id').prop('disabled', true);

		
		  $('a[data-toggle="tab"]').on( 'shown.bs.tab', function (e) {
		        $($.fn.dataTable.tables( true ) ).css('width', '100%');
		        $($.fn.dataTable.tables( true ) ).DataTable().columns.adjust().draw();
		    }); 
	/*
	 * $('.circleModal').modal({ backdrop : 'static', keyboard : false });
	 */
		
	


	var table = $('#myTable').DataTable({

		"paging" : false,
		"ordering" : false,
		"info" : false,
		"searching" : false,
		"autoWidth" : false,
		"scrollY" : '60vh',
		"scrollCollapse" : true,
		"deferLoading": 57,
	
		"columnDefs" : [ {
			"width" : "1%",
			"targets" : 0

		}, {
			"width" : "10%",
			"targets" : 1
		}, {
			"width" : "5%",
			"targets" : 2
		}, {
			"width" : "1%",
			"targets" : 3
		}, {
			"width" : "1%",
			"targets" : 4
		}, {
			"width" : "1%",
			"targets" : 5
		},
		 {
			"width" : "5%",
			"targets" : 6
		},
		{
			"width" : "5%",
			"targets" : 7
		},
		{
			"width" : "2%",
			"targets" : 8
		}
		]

	}).draw(false);
	
	var counter = 10;
	
		for (var i = 0; i < counter; i++) {
			table.row
					.add(
							[
									'<div class="row-order" id="' + (i + 1) + '">'
											+ (i + 1) + '</div>',
									'<input type="text"	name="itemName" class="itemName form-control dis-auto-width dis-bottom-margin" tabindex="'
											+ (i + 1) + '"><input type="hidden" class="itemCode" name="itemCode">',
									'<select class="form-control batch dis-auto-width" name="batch"></select>',
									'<input type="text"	name="issueQty"  class="text-align-center issueQty form-control dis-auto-width dis-bottom-margin" readonly>',
									'<input type="text"	name="prevRetQty"  class="text-align-center prevRetQty form-control dis-auto-width dis-bottom-margin" readonly>',
									'<input type="text"	name="qty"  class="text-align-center qty form-control dis-auto-width dis-bottom-margin" tabindex="'+ (i + 1) +'">',
									'<input type="text"	name="voucherNo"  class="text-align-center voucherNo form-control dis-auto-width dis-bottom-margin" readonly>',
									'<input type="text"	name="remark"  class="text-align-center remark form-control dis-auto-width dis-bottom-margin" tabindex="'+ (i + 1) +'">',
									'<div class="delete-btn"><input type="hidden" class="chk-box" name="spcimenChkBox" value="N"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
					.draw(false);
		}
	

		var select = $(".batch");
		select.empty().append('<option selected="selected" value="0" disabled = "disabled">Loading.....</option>');
	
	  $(document).on('click', '.row-delete', function(event) {
	  event.preventDefault();
	  
	  var x = event.which || event.keyCode;
	  
	  console.log('delete btn event : ' + x);
	  
	  var row = $(this).closest('tr');
	  
	  var itemCode = row.find('.itemCode').val();
	  batchList.splice(batchList.indexOf($.trim(itemCode)), 1);
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
					'.btn-row-add',
					function(e) {
						e.preventDefault();
						var lastRowId = $('.row-order:last').attr('id');
						// alert(lastRowId);
						table.row
						.add(
								[
										'<div class="row-order" id="' + (parseInt(lastRowId) + 1) + '">'
												+ (parseInt(lastRowId) + 1) + '</div>',
										'<input type="text"	name="itemName" class="itemName form-control dis-auto-width dis-bottom-margin" tabindex="'
												+ (parseInt(lastRowId) + 1) + '"><input type="hidden" class="itemCode" name="itemCode">',
												'<select class="form-control batch dis-auto-width" name="batch"></select>',
												'<input type="text"	name="issueQty"  class="text-align-center issueQty form-control dis-auto-width dis-bottom-margin" readonly>',
												'<input type="text"	name="prevRetQty"  class="text-align-center prevRetQty form-control dis-auto-width dis-bottom-margin" readonly>',
												'<input type="text"	name="qty"  class="text-align-center qty form-control dis-auto-width dis-bottom-margin" tabindex="'+ (parseInt(lastRowId) + 1) +'">',
												'<input type="text"	name="voucherNo"  class="text-align-center voucherNo form-control dis-auto-width dis-bottom-margin" readonly>',
												'<input type="text"	name="remark"  class="text-align-center remark form-control dis-auto-width dis-bottom-margin" tabindex="'+ (parseInt(lastRowId) + 1) +'">',
												'<div class="delete-btn"><input type="hidden" class="chk-box" name="spcimenChkBox" value="N"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
						.draw(false);

					});

	
	  $(document) .on( 'keyup', 'tr:last', function() { 
		 // console.log('ram');
		  var x = event.which || event.keyCode;
		  var lastRowId =  $('.row-order:last').attr('id'); 
		  // alert(lastRowId);
			table.row.add(
					[
							'<div class="row-order" id="' + (parseInt(lastRowId) + 1) + '">'
									+ (parseInt(lastRowId) + 1) + '</div>',
							'<input type="text"	name="itemName" class="itemName form-control dis-auto-width dis-bottom-margin" tabindex="'
									+ (parseInt(lastRowId) + 1) + '"><input type="hidden" class="itemCode" name="itemCode">',
									'<select class="form-control batch dis-auto-width" name="batch"></select>',
									'<input type="text"	name="issueQty"  class="text-align-center issueQty form-control dis-auto-width dis-bottom-margin" readonly>',
									'<input type="text"	name="prevRetQty"  class="text-align-center prevRetQty form-control dis-auto-width dis-bottom-margin" readonly>',
									'<input type="text"	name="qty"  class="text-align-center qty form-control dis-auto-width dis-bottom-margin" tabindex="'+ (parseInt(lastRowId) + 1) +'">',
									'<input type="text"	name="voucherNo"  class="text-align-center voucherNo form-control dis-auto-width dis-bottom-margin" readonly>',
									'<input type="text"	name="remark"  class="text-align-center remark form-control dis-auto-width dis-bottom-margin" tabindex="'+ (parseInt(lastRowId) + 1) +'">',
							'<div class="delete-btn"><input type="hidden" class="chk-box" name="spcimenChkBox" value="N"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ]).draw(false); 
			});
	 
							/*
							 * Fetch all patient related previous medicine
							 * purchase detail
							 */

							$.ajax({
										url : 'pharma.return.request',
										type : 'post',
										dataType : 'json',
										data : {
											ACTION : 'GET_ITEM_LIST_DRUG_RETURN'
										},
										success : function(data) {
											itemList.length = 0;
											for (var i = 0; i < data.length; i+=7) {
												itemList
														.push({
															label : String(data[i+1]),
															value : String(data[i+1]),
															itemCode: String(data[i]),
															batch: String(data[i+2]),
															issueQty: String(data[i+3]),
															prevRetQty: String(data[i+4]),
															qty: String(data[i+5]),
															voucherNo: String(data[i+6]),
															searchPattern: String(data[i])+String(data[i+2])+String(data[i+6])
														});
											}
										},
										error : function(data) {
											var errorMsg = "There is a problem processing your request";
											console.log(errorMsg);
											console.log(data.responseText);
										},
										failure: function(data){
											var errorMsg = "There is a problem processing your request";
											console.log(errorMsg);
											console.log(data.responseText);
										}
									});


	previousValue = "";

	$('body')
			.on(
					'keyup',
					'.itemName',
					function(e) {
						e.preventDefault();
						var itemNames =  $(this).autocomplete({
											autoFocus : true,
											maxShowItems : 5,
											minLength : 3,
											source : itemList,
											delay : 500,
											focus : function(event, ui) {
												console.log('Autocomplete focus event');
												var currentRow = $(this).closest('tr');
												currentRow.find('.itemCode').val(ui.item.itemCode);
												return false;
											},
											select : function(event, ui) {
												console.log('Autocomplete Select event');
												$(this).val(ui.item.label);
												var currentRow = $(this).closest('tr');
												var itemCode = $.trim(ui.item.itemCode);
												// console.log('Item Code List :
												// ' + batchList);
												currentRow.find('.itemCode').val(ui.item.itemCode);
												currentRow.find('.batch').empty().append('<option selected="selected" value="0">Select Batch</option>');
												for(var i=0; i<itemList.length; i++){
													//console.log('Given Item Code : ' + itemCode);
													//console.log('Search Item Code : ' + JSON.stringify(itemList[i]));
													if (itemCode === itemList[i]['itemCode']){
														currentRow.find('.batch').append($("<option></option>").val(
																$.trim(itemList[i]['voucherNo']+itemList[i]['batch'])).html($.trim(itemList[i]['batch'])));
													}
												}
												return false;
											}
										});
					});
	
	var previousItemCode;
	var previousBatch;
	var previousVoucher; 
	
	$('.batch').on('focus', function(event){
		
		var currentRow = $(this).closest('tr');
		previousItemCode = currentRow.find('.itemCode').val();
		previousBatch =  $(this).val();
		previousVoucher =  currentRow.find('.voucherNo').val();
		console.log('Previous ItemCode : ' + previousItemCode);
		console.log('Previous Batch : ' + previousBatch);
		console.log('Previous Voucher : ' + previousVoucher);
		console.log('Data List in focus : ' + dataList);
		
	}).change(function(event){
		var batchNo = $(this).val();
		var currentRow = $(this).closest('tr');
		var itemCode = currentRow.find('.itemCode').val();
		var voucherNo;
		
		deletePreviousData(previousItemCode, previousBatch, previousVoucher);
		
		for(var i=0; i<itemList.length; i++){
			
			if ($.trim(batchNo) === itemList[i]['voucherNo']+itemList[i]['batch']){

					voucherNo = itemList[i]['voucherNo'];
					console.log('Item Code : ' + itemCode); 
					console.log('Batch No : ' +	  batchNo); 
					console.log('Voucher No : ' + voucherNo);
				
					
					
					var isDuplicateBatch = duplicateBatchNoCheck(itemCode, batchNo, voucherNo);
					console.log('Data duplicate : ' + isDuplicateBatch);
					
					if (isDuplicateBatch === true){
						swal('Info!', 'Duplicate batch number is not allowed', 'warning');
						currentRow.find('.itemCode').val('');
						currentRow.find('.batch').val('');
						currentRow.find('.itemName').val('').focus();
						currentRow.find('.issueQty').val('');
						currentRow.find('.prevRetQty').val('');
						currentRow.find('.qty').val('');
						currentRow.find('.voucherNo').val('');
						currentRow.find('.batch').empty().append('<option selected="selected" value="0" disabled = "disabled">Loading.....</option>');
						break;
					} else {
							dataList.push($.trim(itemCode)+$.trim(batchNo)+$.trim(voucherNo));
							currentRow.find('.issueQty').val(itemList[i]['issueQty']);
							currentRow.find('.prevRetQty').val(itemList[i]['prevRetQty']);
							currentRow.find('.qty').val(itemList[i]['qty']);
							currentRow.find('.voucherNo').val(itemList[i]['voucherNo']);
							break;
					}
				}
		}
		
		console.log('Data List after deleting data : ' + dataList);
		
	
	});
	
	
	function duplicateBatchNoCheck(itemCode, batchNo, voucherNo){
		var data = $.trim(itemCode)+$.trim(batchNo)+$.trim(voucherNo);
		console.log('Duplicate Data : ' + data);
		if (dataList.length > 0){
			for (var i=0; i<dataList.length; i++){
				if (data === dataList[i]){
					return true;
				}
			}
		}
		return false;
	}
	
	function deletePreviousData(preItemCode, preBatchNo, preVoucherNo){
		console.log('In delete previous data');
		console.log('Data List before deleting data : ' + dataList);
		console.log('previous Item Code : ' + preItemCode); 
		console.log('previous Batch No : ' +	  preBatchNo); 
		console.log('previous Voucher No : ' + preVoucherNo);
		console.log('Index of deleted data : ' + dataList.indexOf($.trim(preItemCode)+$.trim(preBatchNo)+$.trim(preVoucherNo)));
		var index = dataList.indexOf($.trim(preItemCode)+$.trim(preBatchNo)+$.trim(preVoucherNo));
		if (Math.sign(index) !== -1){
			console.log('Delete previous data : ' + dataList.splice(index, 1));
		}
		
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
						isNumber($(this));
						var currentRow = $(this).closest('tr');
						var quantity = $(this).val();
						var stock = currentRow.find('.stock').val();
						currentRow.find('.balQty').val(quantity);
					});
	

	/*
	 * Disable default Enter keypress form submit event
	 */

	$(document).on('keypress', '.itemName', function(event) {
		var x = event.which || event.keyCode;
		
		var currentRow = $(this).closest('tr');
		var itemCode = currentRow.find('.itemCode').val();
		
		if (x === 13) {
			console.log('itemName event : ' + x);
			event.preventDefault();
			
			var flag = false;
			
			if (batchList.length > 0){
				for(var i =0; i<batchList.length; i++){
					if (itemCode === batchList[i]){
						flag = false;
						break;
					} else {
						flag = true;
					} 
				}
				
				if (flag === false){
					swal('Info!', 'Duplicate Item Name', 'warning');
					currentRow.find('.itemCode').val('');
					currentRow.find('.itemName').val('');
					currentRow.find('.molecule').val('');
					currentRow.find('.stock').val('');
					currentRow.find('.qty').val('');
					return false;
				}
			}
			
		} else if (x == 8){ // Backspace event number = 8
			console.log('itemName event : ' + x);
			
			batchList.splice(batchList.indexOf($.trim(currentRow.find('.itemCode').val())), 1);
			currentRow.find('.itemCode').val('');
			currentRow.find('.itemName').val('');
			currentRow.find('.molecule').val('');
			currentRow.find('.stock').val('');
			currentRow.find('.qty').val('');
			
		} else if (x === 9) {
			console.log('itemName event : ' + x);
		}
	});
	
	

	$(document).on('keypress', '.qty', function(event) {
		var x = event.which || event.keyCode;
		if (x === 13) {
			event.preventDefault();
		}
	});
	
	
	
	
	$(document).on('click', '#btn-submit', function(e){
		
		var frm = $('#pharmacy-order-frm');
		var pharmaOrderId = $('#order-id').val();
		console.log('Pharmacy Order Id : ' + pharmaOrderId);
		
		
		const swalWithBootstrapButtons = swal.mixin({
			  confirmButtonClass: 'btn btn-success',
			  cancelButtonClass: 'btn btn-danger',
			  buttonsStyling: false,
			})
			
		if (!$('#refer-doctor').val()){
			swal('Oh no!', 'Refer doctor name is not present', 'warning');
			return false;
		}	
			
			
		var x = dataValidation();
		console.log('Result : ' + x);
		if (x === false){
			swal('Oh no!', 'There is is something wrong', 'warning');
			return false;
		}
		
		swal({
			  title: 'Are you sure?',
			  text: "You won't be able to revert this!",
			  type: 'warning',
			  inputValue: pharmaOrderId,
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes, Save it!'
			}).then((result) => {
			  if (result.value) {
				  
				 console.log('Result : ' + result.value);
				 console.log('Order id : ' + pharmaOrderId);
				 
				 $('.circleModal').modal({
						backdrop : 'static',
						keyboard : false
					});

				  
				  if (!pharmaOrderId){
					  console.log('Pharmacy Id not present and not billed');
					  $.ajax({
						    url: "pharma.return",
					        type: "POST",
					        datatype: 'text',
					        data:  frm.serialize() + "&ACTION=INSERT_UPDATE_PHARMA_ORDER",
					        success: function(response) {
					        	
					        	$('.circleModal').modal('hide');
					        	
					        	pharmaOrderNo = response;
					        	console.log('Response : '  + response);
					        	if (pharmaOrderNo.includes("F")){
					        		swal('Ohh no!!','Data have not been saved.<br> ERROR MSG. :' + pharmaOrderNo.slice(1),'error');
					        	} else {
					        		swal('Well done!','Data have been saved.<br> PHARMACY ORDER ID :' + pharmaOrderNo,'success');
					        		reset();
					        	}
					        },
					        failure: function (response) {
					            swal("Internal Error","Oops, your note was not saved.", "error" );
					        }
					    });
					  
				  } else {
					  console.log('Pharmacy Id present but not billed only updation');
					  $.ajax({
					        type: "POST",
					        url: "pharma.return",
					        data:  frm.serialize() + "&poNumber="+pharmaOrderId+"&ACTION=INSERT_UPDATE_PHARMA_ORDER",
					        cache: false,
					        success: function(response) {
					        	$('.circleModal').modal('hide');
					        	pharmaOrderNo = response;
					        	if (pharmaOrderNo.includes("F")){
					        		swal('Ohh no!!','Data have not been saved.<br> ERROR MSG. : ' + pharmaOrderNo.slice(1),'error');
					        	} else {
					        		swal('Well done!','Data have been updated.<br> PHARMACY ORDER ID :' + pharmaOrderNo,'success');
					        		reset();
					        	}
					        },
					        failure: function (response) {
					            swal("Internal Error","Oops, your note was not saved.", "error");
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
	
	
	var count = 0;
	
	$(document).on('click', '.btn-previous', function(event){
		
							
					// console.log('Count : ' + ++count);
		
					$('.circleModal').modal({ backdrop : 'static', keyboard : false });
					
					
					
					  $('.myModal .modal-body').load( 'pharma.return', 
							  { ACTION :  'FETCH_PREVIOUS_PHARMA_ORDER_LIST', 
					  },
					  function(response, status, xhr) {
					  
						  
						  if (status === 'error') { 
				      $('.myModal').modal('hide');
					  var msg = "Sorry but there was an error: "; 
					  // swal( "Info!", msg + xhr.status + " " +
						// xhr.statusText, "error");
					  swal( "Info!", 'No previous service order is available ', 'info');
					}
					  
					  if (status === 'success'){
						  
						  $('.circleModal').modal('hide');
						  
							var myTable = $('#pharmaTable').DataTable( 
									  { 
										searching: false,
										scrollY :  "300px", 
									    scrollX : true, 
									    scrollCollapse : true, 
									    paging :  false, 
									    info: false,
									    order: [],
									    columnDefs: [
									    	{
									    		'targets': 0,
									    		'className': 'text-center',
									    		'orderable': false,
									    		'width': '1%',
									    		
									    	},
									    	{
									    		'targets': 1,
									    		'className': 'text-center',
									    		'orderable': false,
									    		'width': '15%',
									    		
									    	},
									    	{
									    		"targets": 2,
									    		"className": "text-center",
									    		"orderable": false,
									    		'width': '8%'
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
									    		'width': '10%'
									    	},
									    	{
									    		"targets": 6,
									    		"className": "text-center",
									    		"orderable": false,
									    		'width': '2%'
									    	},
									    	{
											  "targets": 7,
											  "className": "text-center",
											  "orderable": false,
											  'width': '2%'
									    	}
									    ]
									    });
							$('.myModal .modal-title').html("Previous Pharmacy Order Record : " + $('#patient-no').val());
							$('.myModal .modal-title').css('text-align', 'center');
							  $('.myModal').on('shown.bs.modal', function(){
								  myTable.columns.adjust().draw(); 
								}).modal({show: true});
					  }
				});
	});
	
	
	/*
	 * Function for getting previous Pharmacy order detail..
	 */
	
	$(document).on('click', '.view-btn', function(event){
		
		 $('.myModal').modal('hide');
		 reset();
		 $('.circleModal').modal({ backdrop : 'static', keyboard : false });
		 
		 var pharmaOrderNumber = $(this).attr('po-no');
		 var refDocCode = $(this).attr('ref-doc-code');
		 var refDocName = $(this).attr('ref-doc-name');
		 var status = $(this).attr('status');
		 var pharmaDate = $(this).attr('req-date');
		
		 
		 console.log('IP Number : ' + pharmaOrderNumber);
		 
		 var req = $.ajax({
			 		
			 	url: 'pharma.return',
			 	type: 'post',
			 	datatype: 'text',
			 	data: {
			 		ACTION: 'FETCH_PHARMA_ORDER_DETAIL',
			 		poNumber: pharmaOrderNumber
			 	},
			 	success: function(response){

			 		$('.circleModal').modal('hide');
			 		 
			 		var arr = response.replace("[", "").replace("]", "").split(",");
			 		
			 		var poDate = [];
			 		var itemCode = [];
			 		var itemName = [];
			 		var moleculeName = [];
			 		var stock = [];
			 		var reqQty = [];
			 		var balQty = [];
			 		var totalRow = 0;
			 	
			 		for (var i=0; i<arr.length; i+=7){
			 			poDate.push(String(arr[i]));
			 			itemCode.push(String(arr[i+1]));
			 			batchList.push($.trim(String(arr[i+1])));
			 			itemName.push(String(arr[i+2]).replace(/\|/g, ','));
			 			stock.push(String(arr[i+6]));
			 			moleculeName.push(String(arr[i+3]).replace(/\|/g, ','));
			 			reqQty.push(String(arr[i+4]));
			 			balQty.push(String(arr[i+5]));
			 			++totalRow;
			 		}

			 		var i = 0;
			 		var initialRow = 10;
			 		
			 		if (totalRow > initialRow){
			 			
			 			var remainRow = totalRow - initialRow;
			 			
			 			var l = 0;
			 			
			 			while(l<remainRow){
			 				table.row
							.add(
									[
											'<div class="row-order" id="' + (initialRow + 1) + '">'
													+ (initialRow + 1) + '</div>',
											'<input type="text"	name="itemName" class="itemName form-control dis-auto-width dis-bottom-margin" tabindex="'
													+ (initialRow + 1) + '"><input type="hidden" class="itemCode" name="itemCode">',
													'<select class="form-control batch dis-auto-width" name="batch"></select>',
													'<input type="text"	name="issueQty"  class="text-align-center issueQty form-control dis-auto-width dis-bottom-margin" readonly>',
													'<input type="text"	name="prevRetQty"  class="text-align-center prevRetQty form-control dis-auto-width dis-bottom-margin" readonly>',
													'<input type="text"	name="qty"  class="text-align-center qty form-control dis-auto-width dis-bottom-margin" tabindex="'+ (initialRow + 1) +'">',
													'<input type="text"	name="voucherNo"  class="text-align-center voucherNo form-control dis-auto-width dis-bottom-margin" readonly>',
													'<input type="text"	name="remark"  class="text-align-center remark form-control dis-auto-width dis-bottom-margin" tabindex="'+ (initialRow + 1) +'">',
													'<div class="delete-btn"><input type="hidden" class="chk-box" name="spcimenChkBox" value="N"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
							.draw(false);
			 				l++;
			 				initialRow++;
			 			}
			 		}
			 		
			 		
			 		console.log('Pharmacy Order date : ' + pharmaDate);
			 		$('#fromDate').datepicker().datepicker("setDate", pharmaDate);
			 		
			 		$('#order-id').val(poDate[0]);
			 		$('#refDocId').val(refDocCode);
			 		$('#refer-doctor').val(refDocName);
			 		
			 		$('.itemCode').each(function(event){
		 				if (i < totalRow){
		 					$(this).val($.trim(itemCode[i]));
		 				}
			 			i++;
			 		});
			 		
					i =0;
			 		$('.itemName').each(function(event){
		 				if (i < totalRow){
		 					$(this).val($.trim(itemName[i]));
		 				}
			 			i++;
			 		});

			 		i =0;
			 		$('.qty').each(function(event){
		 				if (i < totalRow){
		 					$(this).val($.trim(reqQty[i]));
		 				}
			 			i++;
			 		});
			 		
			 		i =0;
			 		$('.molecule').each(function(event){
		 				if (i < totalRow){
		 					$(this).val($.trim(moleculeName[i]));
		 				}
			 			i++;
			 		});
			 		
			 		i =0;
			 		$('.stock').each(function(event){
		 				if (i < totalRow){
		 					$(this).val($.trim(stock[i]));
		 				}
			 			i++;
			 		});

			 		
			 		
			 		i =0;
			 		$('.balQty').each(function(event){
		 				if (i < totalRow){
		 					$(this).val($.trim(balQty[i]));
		 				}
			 			i++;
			 		});
			 		
			 		if ($.trim(status) === 'RQSC' || $.trim(status) === 'RQSP'){
				 		i =0;
				 		$('.itemName').each(function(event){
			 					$(this).prop('readonly', true);
				 		});

				 		i =0;
				 		$('.qty').each(function(event){
			 					$(this).prop('readonly', true);
				 		});
				 		
				 		$('#btn-submit').prop('disabled', true);
				 		$('.btn-row-add').prop('disabled', true);
				 		$('.row-delete').prop('disabled', true);
				 		$('#refer-doctor').prop('disabled', true);
				 		
				 		
			 		} else {
			 			i =0;
				 		$('.itemName').each(function(event){
			 					$(this).prop('readonly', false);
				 		});

				 		i =0;
				 		$('.qty').each(function(event){
			 					$(this).prop('readonly', false);
				 		});
				 		
				 		$('#btn-submit').prop('disabled', false);
				 		$('.btn-row-add').prop('disabled', false);
				 		$('.row-delete').prop('disabled', false);
				 		$('#refer-doctor').prop('disabled', false);
			 			
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
		reset();
	});
	
	
	function dataValidation(){
		console.log('In dataValidation');
		var itemCount = 0;
		var qtyCount = 0;
		$('.itemName').each(function(){
			if ($(this).val()){
				itemCount++;
			}
		});
		
		$('.qty').each(function(){
			if ($(this).val()){
				qtyCount++;
			}
		});
		console.log('Total item Count : ' + itemCount);
		console.log('Total quantity Count : ' + qtyCount);
		
		if (qtyCount == 0 && itemCount == 0){
			return false;
		} else {
			return true;
		}
	}
	
	function reset(){
		
		console.log('Reset Called');
		$('#fromDate').datepicker().datepicker("setDate", new Date());
		$('#order-id').val('');
		$('.itemCode').val('');
		$('.itemName').prop('readonly', false);
		$('.itemName').val('');
		$('.qty').val('');
		$('.molecule').val('');
		$('.qty').val('');
		$('.stock').val('');
		$('.balQty').val('');
		$('#refer-doctor').val('');
		$('#refer-doctor').text('');
		$('#refDocId').val('');
		$('#btn-submit').prop('disabled', false);
		$('.row-delete').prop('disabled', false);
		$('.btn-row-add').prop('disabled', false);
		$('.row-delete').prop('disabled', false);
		$('#refer-doctor').prop('disabled', false);
		batchList.length = 0;
		
		
		
		
		var totalRow = 0;
		$('.itemName').each(function(event){
			totalRow++;
		});
		
		
		var i = 0;
 		var initialRow = 10;
 		
 		if (totalRow < initialRow){
 			
 			var remainRow = initialRow - totalRow ;
 			
 		 console.log('Remaining row : ' + remainRow);
 			
 			var l = 0;
 			
 			while(l<remainRow){
 				
 				// console.log('No of row : ' + l);
 				
 				table.row
				.add(
						[
								'<div class="row-order" id="' + (totalRow + 1) + '">'
										+ (totalRow + 1) + '</div>',
								'<input type="text"	name="itemName" class="itemName form-control dis-auto-width dis-bottom-margin" tabindex="'
										+ (totalRow + 1) + '"><input type="hidden" class="itemId" name="serviceId">',
										'<select class="form-control batch dis-auto-width" name="batch"></select>',
										'<input type="text"	name="issueQty"  class="text-align-center issueQty form-control dis-auto-width dis-bottom-margin" readonly>',
										'<input type="text"	name="prevRetQty"  class="text-align-center prevRetQty form-control dis-auto-width dis-bottom-margin" readonly>',
										'<input type="text"	name="qty"  class="text-align-center qty form-control dis-auto-width dis-bottom-margin" tabindex="'+ (totalRow + 1) +'">',
										'<input type="text"	name="voucherNo"  class="text-align-center voucherNo form-control dis-auto-width dis-bottom-margin" readonly>',
										'<input type="text"	name="remark"  class="text-align-center remark form-control dis-auto-width dis-bottom-margin" tabindex="'+ (totalRow + 1) +'">',
								'<div class="delete-btn"><input type="hidden" class="chk-box" name="spcimenChkBox" value="N"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
				.draw(false);
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
								'<input type="text"	name="itemName" class="itemName form-control dis-auto-width dis-bottom-margin" tabindex="'
										+ (i + 1) + '"><input type="hidden" class="itemCode" name="itemCode">',
										'<select class="form-control batch dis-auto-width" name="batch"></select>',
										'<input type="text"	name="issueQty"  class="text-align-center issueQty form-control dis-auto-width dis-bottom-margin" readonly>',
										'<input type="text"	name="prevRetQty"  class="text-align-center prevRetQty form-control dis-auto-width dis-bottom-margin" readonly>',
										'<input type="text"	name="qty"  class="text-align-center qty form-control dis-auto-width dis-bottom-margin" tabindex="'+ (i + 1) +'">',
										'<input type="text"	name="voucherNo"  class="text-align-center voucherNo form-control dis-auto-width dis-bottom-margin" readonly>',
										'<input type="text"	name="remark"  class="text-align-center remark form-control dis-auto-width dis-bottom-margin" tabindex="'+ (i + 1) +'">',
								'<div class="delete-btn"><input type="hidden" class="chk-box" name="spcimenChkBox" value="N"><button class="btn btn-warning btn-sm row-delete">X</button></div>' ])
				.draw(false);
 			}
  		}
		
		
	}
	
	
	
	

});

/*
 * Style 1 not working properly
 */
function isNumber1(event) {
	
	var char = String.fromCharCode(event.which || event.keyCode);
	// console.log(char);
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
    	// console.log('test failed');
        return false;
    }
    return true;
}

/*
 * Style 3
 */
function isNumber(input) {
	// console.log(input.val());
	var replaceInput = input.val();
	var regex = /[^0-9]/;
	replaceInput = replaceInput.replace(regex, "");
	input.val(replaceInput);
}



if (window.history && window.history.pushState) {
    window.history.pushState('', null, 'pharma.return.request?moduleName=' + document.getElementById('frm-name').value);
    $(window).on('popstate', function() {
        document.location.href = '';
    });
} 


