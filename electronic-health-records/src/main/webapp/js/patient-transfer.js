$(function() {
	
	
	var doctorList = [];
	var wardBedList = [];
	var errorMsg;
	
	
	
	  $('a[data-toggle="tab"]').on( 'shown.bs.tab', function (e) {
	        $($.fn.dataTable.tables( true ) ).css('width', '100%');
	        $($.fn.dataTable.tables( true ) ).DataTable().columns.adjust().draw();
	    } ); 
	

	
	function changeTime() {
	  var monthNameList = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];	
	  var d = new Date();	
	  var hours = d.getHours();
	  var minutes = d.getMinutes();
	  var seconds = d.getSeconds();
	  var ampm = hours >= 12 ? 'PM' : 'AM';
	  hours = hours % 12;
	  hours = hours ? hours : 12; // the hour '0' should be '12'
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  var strTime = hours + ':' + minutes + ':' +  seconds + ' ' + ampm;
	  var dateformat = [ d.getDate(), (monthNameList[d.getMonth()]), d.getFullYear()].join('-') + ' ' + strTime;
	  $('#transferDate').val(dateformat);
	  $('#transferDate').html(dateformat);
	}


	
	
		/*
		 * Initial Set Value
		 */
 		 $('#fromDate').datepicker().datepicker("setDate", new Date());
		 setInterval(changeTime, 1);
		 $('#transferDate').prop('disabled', true);
		 $('#refer-doctor').prop('disabled', true);
		 
		 	
			/*
			 * Datatable for fetching bed number under ward
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
			/*
			 * responsive : { details : { display :
			 * $.fn.dataTable.Responsive.display.modal({ header : function(row) {
			 * var data = row.data(); return 'Details for ' + data[0] + ' ' +
			 * data[1]; } }), renderer :
			 * $.fn.dataTable.Responsive.renderer.tableAll({ tableClass :
			 * 'table' }) } },
			 */

				"columnDefs" : [ {
					"width" : "2%",
					"targets" : 0

				}, {
					"width" : "5%",
					"targets" : 1
				}, {
					"width" : "5%",
					"targets" : 2
				}, {
					"width" : "5%",
					"targets" : 3
				}, {
					"width" : "5%",
					"targets" : 4
				}
				]

			}).draw(false);
			
		 	
		
		
		
		
		$('.circleModal').modal({
			backdrop : 'static',
			keyboard : false
		});	
	
		/*
		 * Fetching Ward Name List
		 */
	var select = $(".ward-name");

	select.empty().append('<option selected="selected" value="0" disabled = "disabled">Loading.....</option>');

	var req = $.ajax({
				url : 'patient.transfer',
				type : 'post',
				datatype : 'text',
				data : {
					ACTION : 'FETCH_WARD_LIST'
				},
				success : function(data) {

					$('.circleModal').modal('hide');
					
					data = data.replace(/^\W+|\W+$/g, "");
					// alert(data.length);

					if (data.length !== 0) {
						arr = data.replace("[", "").replace("]", "").split(",");
						select
								.empty()
								.append(
										'<option selected="selected" value="0">Select Ward Name</option>');
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
					console.log("failure");
					console.log(data.responseText);
					
				},
				error : function(data) {
					console.log("error");
					console.log(data.responseText);
				
				}
				
				

			});
	
	
	$(document).on('change', '.ward-name', function(event, param){
		
		var wardCode;
		console.log('Ward Code : ' + wardCode);
		
		if(param === undefined){
			wardCode = $(this).val();
		} else {
			wardCode = param;
		}
		
		$('.circleModal').modal({
			backdrop : 'static',
			keyboard : false
		});	
	
		
		
		var req = $.ajax({
			url : 'patient.transfer',
			type : 'post',
			datatype : 'json',
			data : {
				ACTION : 'FETCH_WARD_BED_LIST',
				toWard: wardCode
			},
			success : function(response) {

				$('.circleModal').modal('hide');
				
				console.log(response);
				console.log('Data Length : ' + response.length);
				wardBedList.length = 0;
				//console.log('Vital Chart length : ' + wardBedList.length);
				
		
					
					for (var i=0; i<response.length; i +=3){
						 wardBedList
							.push({
								roomCode: String($.trim(response[i])),
								bedCode: String($.trim(response[i+1])),
								bedType: String($.trim(response[i+2])),
							});
					 }
			
				 
				 

				if ($.fn.dataTable.isDataTable('#myTable')) {	
					// alert('object already exists');
					table = $('#myTable').DataTable();
					table.clear().destroy();
				}
				
				var i = 0;
				
				var	table = $('#myTable')
				.DataTable(
						{
							"paging" : false,
							"ordering" : false,
							"info" : false,
							"searching" : false,
							"autoWidth" : false,
							"scrollY" : '60vh',
							"scrollCollapse" : true,
							"deferLoading": 57,
							"createdRow": function( row, data, dataIndex){
				                // console.log('Row : ' + row + ' ' + '
								// data' + JSON.stringify(data) + '
								// dataIndex ' + dataIndex);
								if (dataIndex % 2 === 0){
									$(row).addClass('orangeClass');
								} else {
									$(row).addClass('blueClass');
								}
								
				            },
							data : wardBedList,
							columns : [
									{
										'render' : function(object){
											return ++i;
										}
									},
									{
										'data' : 'roomCode'
									},
									{
										'data' : 'bedCode'
									},
									{
										'data' : 'bedType'
									},
									{
										'render' : function(data, type, full, meta){
											return '<input type="radio" class="vacant-bed-ward" name="vacanctWardBed" value="'+ full.roomCode + '@' + full.bedCode + '@' +full.bedType +'">';
										}
									}
							],
							"columnDefs" : [ {
								"width" : "2%",
								"targets" : 0,
								'className': 'text-center'

							}, {
								"width" : "5%",
								"targets" : 1,
								'className': 'text-center'
							}, {
								"width" : "5%",
								"targets" : 2,
								'className': 'text-center'
							}, {
								"width" : "5%",
								"targets" : 3,
								'className': 'text-center'
							}, {
								"width" : "5%",
								"targets" : 4,
								'className': 'text-center'
							}
							]
						});
				
				
				
				
				

			},
			failure : function(data) {
				// alert("failure");
				//alert(data.responseText);
			},
			error : function(data) {
				// alert("error");
				//alert(data.responseText);
			}

		});
		
		
		$('.circleModal').modal('hide');
		
		
	});
	

	
	
	
	/*
	 * Fetching Doctor List with doctor id
	 */
	
	 $.ajax({
			type: 'post',
			url: 'patient.transfer',
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
					
			
				} else {
					swal('Ohh no!', 'No Doctor found', 'info');
				}
			},
			error: function(data){
				var errorMsg = "There is a problem processing your request";
				 swal('Sorry for inconvenience', errorMsg, 'info');
				// alert(data.responseText);
			},
			failure: function(data){
				var errorMsg = "There is a problem processing your request";
				 swal('Sorry for inconvenience', errorMsg, 'info');
			}
		});

		$('body').on('keyup', '#refer-doctor', function(event){
			event.preventDefault();
			$(this).autocomplete({
				autoFocus : true,
				maxShowItems : 5,
				minLength : 3,
				delay : 500,
				source: doctorList,
				select: function(event, ui){
					// $(this).val(ui.item.label);
					console.log('Doctor Id ' + ui.item.doctorId);
					$('#refDocId').val(ui.item.doctorId);
					
				}
			});
		});
	
		$('body').on('keyup', '#recomm-doctor', function(event){
			event.preventDefault();
			$(this).autocomplete({
				autoFocus : true,
				maxShowItems : 5,
				minLength : 3,
				delay : 500,
				source: doctorList,
				select: function(event, ui){
					console.log('Recommend Doctor Id ' + ui.item.doctorId);
					$('#recomm-doctor-id').val(ui.item.doctorId);
					
				}
			});
		});

	  

	
	

	
	
	
	
	
	
	$(document).on('click', '#btn-submit', function(e){
		
		var frm = $('#patient-transfer-frm');
		var transferId = $('#transfer-id').val();

		const swalWithBootstrapButtons = swal.mixin({
			  confirmButtonClass: 'btn btn-success',
			  cancelButtonClass: 'btn btn-danger',
			  buttonsStyling: false,
			})
			
			
			  var x = dataValidation(); 
			  console.log('data validation return value : ' +  x); 
			  if (x === false){ 
				  swalWithBootstrapButtons('Ohh no!', errorMsg , 'info'); 
				  return false; 
			  }
			
			
	
		swal({
			  title: 'Are you sure?',
			  text: "You won't be able to revert this!",
			  type: 'warning',
			  inputValue: transferId,
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes, Save it!'
			}).then((result) => {
			  if (result.value) {
				  
				 console.log('Result : ' + result.value);
				 console.log('Order id : ' + transferId);
				 
				 $('.circleModal').modal({
						backdrop : 'static',
						keyboard : false
					});

				  
				  if (!transferId){
					  
					  console.log('Service Id not present and not billed');
					  $('#voucher-id').val('');
					 
					  
					  $.ajax({
						    url: "patient.transfer",
					        type: "POST",
					        datatype: 'text',
					        data:  frm.serialize() + "&ACTION=INSERT_UPDATE_PATIENT_TRANSFER",
					        success: function(response) {
					        	
					        	
					        	
					        	patientTransferNo = response;
					        	console.log('Response : '  + response);
					        	if (patientTransferNo.includes("F")){
					        		swal('Ohh no!!','Data have not been saved.<br> ERROR MSG. :' + patientTransferNo.slice(1),'error');
					        	} else {
					        		swal('Well done!','Data have been saved.<br> SERVICE ORDER ID :' + patientTransferNo,'success');
					        		reset();
					        	}
					        },
					        failure: function (response) {
					            swal("Internal Error","Oops, your note was not saved.", "error" );
					        }
					    });
					  
				  } else {
					  console.log('Service Id present but not billed only updation');
					  $.ajax({
					        type: "POST",
					        url: "patient.transfer",
					        data:  frm.serialize() + "&ptNumber="+transferId+"&ACTION=INSERT_UPDATE_PATIENT_TRANSFER",
					        cache: false,
					        success: function(response) {
					        	$('.circleModal').modal('hide');
					        	patientTransferNo = response;
					        	if (patientTransferNo.includes("F")){
					        		swal('Ohh no!!','Data have not been saved.<br> ERROR MSG. : ' + patientTransferNo.slice(1),'error');
					        	} else {
					        		swal('Well done!','Data have been updated.<br> SERVICE ORDER ID :' + patientTransferNo,'success');
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
	

	
	
	
	
	
	
	
	$(document).on('click', '.btn-previous', function(event){
		
							
					// console.log('Count : ' + ++count);
					
					  $('.myModal .modal-body').load( 'patient.transfer', 
							  { ACTION :  'FETCH_PREVIOUS_PATIENT_TRANSFER', 
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
						  
							var myTable = $('#example1').DataTable( 
									  { 
										searching: false,
										scrollY :  "300px", 
										autoWidth: false,
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
									    		"className": "text-center",
									    		"orderable": false,
									    		'width': '3%'
									    	},
									    	{
									    		"targets": 3,
									    		"className": "text-left",
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
									    		"className": "text-left",
									    		"orderable": false,
									    		'width': '4%'
									    	},
									    	{
									    		"targets": 6,
									    		"className": "text-center",
									    		"orderable": false,
									    		'width': '2%'
									    	},
									    	{
									    		"targets": 7,
									    		"className": "text-left",
									    		"orderable": false,
									    		'width': '10px'
									    	},
									    	{
									    		"targets": 8,
									    		"className": "text-center",
									    		"orderable": false,
									    		'width': '5%'
									    	}
									    	
									    	
									    ]
									    });
							  
							
							  
							$('.myModal .modal-title').html("Previous Patient Transfer Record : " + $('#patient-no').val());
							$('.myModal .modal-title').css('text-align', 'center');
							
							  
							  
						
							  
							  $('.myModal').on('shown.bs.modal', function(){
								  myTable.columns.adjust().draw(); 
								}).modal({show: true});
						  
					  }
					  
		
				});
	});
	
	
	
	
	
	
	
	
	
	$(document).on('click', '#btn-reset', function(event){
		
		// console.log('Reset Calling');
		reset();
		
		
		
	});
	
	
	function dataValidation(){
		var toWardNumber = $('.ward-name').val();
		var recommDoc = $('#recomm-doctor').val();
		var remark = $('#remark').val();
		var count = 0;
		
		$('.vacant-bed-ward').each(function(){
			if ($(this).is(':checked')){
				count++;
			}
		});
		
		
		if (toWardNumber == 0){
			errorMsg = "To Ward is not present";
			return false;
			
		} else if (count === 0){
			errorMsg = "Please select one room to be proceed";
			return false;
		}else if ( recommDoc.length == 0) {
			errorMsg = "recommendation doctor is not present";
			return false;
		} else if ((trimField(remark)).length ===0) {
			errorMsg = "Remark is not present";
			return false;
		} 
	}
	
	function trimField(str){
		return str.replace(/^\s+|\s+$/g, '');
	}
		
	
	function reset(){
		// console.log('Reset Called');
		
		$('#fromDate').datepicker().datepicker("setDate", new Date());
		$('.ward-name').prop('selectedIndex',0);
		$('#recomm-doctor').val('');
		$('#remark').val('');
		doctorList.length = 0;
		wardBedList.length = 0;
		$('.ward-name').trigger('change', [0]);
		setTimeout(reloadPage, 2000);
	}
	
	function reloadPage(){
		location.reload();
	}
});






