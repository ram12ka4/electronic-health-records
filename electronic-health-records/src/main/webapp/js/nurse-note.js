$(function() {
	
	/*
	 * Initial Set Value
	 */
		$('#fromDate').datepicker().datepicker("setDate", new Date());
		$('#fromDate').datepicker().datepicker("option", "disabled", true);
		var doctorList = [];
		var errorMsg;
		
		 $.ajax({
				
				type: 'post',
				url: 'doctor.note',
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
			
		
	

	$(document).on('click', '#btn-submit', function(e){

		var frm = $('#nurse-note-frm');
		var nurseNoteId = $('#note-id').val();
		
		const swalWithBootstrapButtons = swal.mixin({
			  confirmButtonClass: 'btn btn-success',
			  cancelButtonClass: 'btn btn-danger',
			  buttonsStyling: false,
			})
			
		
			/*
			 * data validation
			 */
		/*
		 * var x = dataValidation(); console.log('data validation return value : ' +
		 * x); if (x === false){ swalWithBootstrapButtons('Ohh no!', errorMsg ,
		 * 'info'); return false; }
		 */
			
		
		
		swal({
			  title: 'Are you sure?',
			  text: "You won't be able to revert this!",
			  type: 'warning',
			  inputValue: nurseNoteId,
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes, Save it!'
			}).then((result) => {
			  if (result.value) {
				  
				 console.log('Result : ' + result.value);
				 console.log('Order id : ' + nurseNoteId);
				  
				  if (!nurseNoteId){
					  
					  console.log('Doctor Note Id not present');
					  
					  $.ajax({
					        type: "POST",
					        url: "nurse.note",
					        data:  frm.serialize() + "&ACTION=INSERT_UPDATE_NURSE_NOTE",
					        cache: false,
					        success: function(response) {
					        	var nurseNoteNo = response;
					        	if (nurseNoteNo === 0){
					        		swal('Ohh no!!','Data have not been saved.<br> ERROR CODE :' + nurseNoteNo,'error');
					        	} else {
					        		swal('Well done!','Data have been saved.<br> NURSE NOTE ID :' + nurseNoteNo,'success');
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
					        url: "nurse.note",
					        data:  frm.serialize() + "&noteNumber="+nurseNoteId+"&ACTION=INSERT_UPDATE_NURSE_NOTE",
					        cache: false,
					        success: function(response) {
					        	nurseNoteNo = response;
					        	if (nurseNoteNo === 0){
					        		swal('Ohh no!!','Data have not been saved.<br> ERROR CODE :' + nurseNoteNo,'error');
					        	} else {
					        		swal('Well done!','Data have been updated.<br> NURSE NOTE ID :' + nurseNoteNo,'success');
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
			  }else if (
					    // Read more about handling dismissals
					    result.dismiss === swal.DismissReason.cancel
					  ) {
					    swalWithBootstrapButtons(
					      'Cancelled',
					      'Doctor Note / Order is not saved...',
					      'error'
					    )
			  }
			})
		
		
	});
	
	var inputOptions = {};
	var count = 0;
	
	$(document).on('click', '.previousBtn', function(event){
		// console.log('Count : ' + ++count);
		
		  $('.myModal .modal-body').load( 'nurse.note', 
				  { ACTION :  'FETCH_PREVIOUS_NURSE_NOTE', 
		  },
		  function(response, status, xhr) {
			  
			  console.log('status : ' + status);
			  
		  if (status === 'error') { 
		  var msg = "Sorry but there was an error: "; 
		  // swal( "Oh no!", msg + xhr.status + " " + xhr.statusText,
			// "error");
		  swal('Info', 'No previous Nurse notes are available', 'info');
		  $('.myModal').modal('hide');
		}
		  
		  if (status === 'success'){
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
						    		"className": "text-center",
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
								  'width': '10%'
						    	}
						    ]
						    });
				$('.myModal .modal-title').html("Patient History Record : " + $('#patient-no').val());
				$('.myModal .modal-title').css('text-align', 'center');
				  $('.myModal').on('shown.bs.modal', function(){
					  myTable.columns.adjust().draw(); 
					}).modal({show: true});
		  }
	
	});
	});
	
	/*
	 * Function for getting previous Service order detail..
	 */
	
	$(document).on('click', '.pat-view-btn', function(event){
		
		 $('.myModal').modal('hide');
		
		var nurseNoteId =  $(this).attr('nr-no');
		console.log('Nurse Note Id. ' + nurseNoteId);
		 reset();
		
		 if (nurseNoteId){
			 
			  $.ajax({
				    url: "nurse.note",
			        type: "POST",
			        datatype: 'text',
			        data:  {
			        	ACTION: 'FETCH_NURSE_NOTE_HISTORY',
			        	noteNo: nurseNoteId
			        },
			        success: function(data) {
			        	
			        	data = data.replace(/^\W+|\W+$/g, "");
						console.log(data);
			        

						if (data.length !== 0) {
							
							arr = data.replace("[", "").replace("]", "").split(",");
							console.log('Arrray is : ' + arr);
							console.log('Array length is : ' + arr.length);
							
							var i = 0;
							var nurseNoteId;
							var nurseNoteDate;
							var referdoctorName;
							var referDoctorId;

							while (i < arr.length) {
								
								nurseNoteId = $.trim(arr[i]);
								nurseNoteDate = $.trim(arr[i+1]);
								referDoctorId = $.trim(arr[i+2]);
								referdoctorName = $.trim(arr[i+3]);
								var noteDetail = $.trim(arr[i+4]).replace(/\|/g, ',');
								
									$('textarea#note').val(noteDetail);
								i += 5;
							}
							
							$('#note-id').val(nurseNoteId);
							$('#fromDate').datepicker().datepicker("setDate", nurseNoteDate);
							$('#refer-doctor').val(referdoctorName);
							$('#refDocId').val(referDoctorId);
							
							
						} else {
							swal("Internal Error",  "No previous Nurse note is available", "error");
						}
			        },
			        failure: function (data) {
			            swal("Internal Error",  "Oops, your note was not saved.", "error");
			        },
			        error: function(data){
			        	// console.log(data.responseText);
			        	swal("Internal Error",  "No previous Nurse note is available", "error");
			        }
			        
			    });
		 } else {
			 swal("Internal Error",  "Nurse Note id not present", "error");
		 }
		
	
		
	});
	
	
	$(document).on('click', '#btn-reset', function(event){
		reset();
	});
	
	
	function dataValidation(){
		var referDoctorName = $('#refer-doctor').val();
		console.log('referDoctorName length ' + referDoctorName.length);
		if (referDoctorName.length === 0){
			errorMsg = 'Doctor name is not present';
			return false;
		} else {
			return true;
		} 
	}
	
	function trimField(str){
		return str.replace(/^\s+|\s+$/g, '');
	}
		
	
	function reset(){
		$('#fromDate').datepicker().datepicker("setDate", new Date());
		$('#note').val('');
		$('#note-id').val('');
		$('#refDocId').val('');
		$('#refer-doctor').val('');
		$('#btn-submit').prop('disabled', false);
	}
	
	/*window.onbeforeunload = WindowCloseHanlder;
	function WindowCloseHanlder()
	{
	window.console.log('My Window is closing');
	}*/
	

});


if (window.history && window.history.pushState) {
    window.history.pushState('', 'nurse-note', 'nurse.note?moduleName=' + document.getElementById('frm-name').value);
    $(window).on('popstate', function() {
        document.location.href = '';
    });
} 



