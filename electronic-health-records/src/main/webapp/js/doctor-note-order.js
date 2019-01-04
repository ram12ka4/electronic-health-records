$(function() {
	
	/*
	 * Initial Set Value
	 */
		$('#fromDate').datepicker().datepicker("setDate", new Date());
		$('#fromDate').datepicker().datepicker("option", "disabled", true);
		$('#medication').hide();
		$('#laboratory').hide();
		$('#diet').hide();
		$('#progress').hide();
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
		

		$(document).on('click', '.btn-medic', function(event){
				$('#treatment').hide();
				$('#medication').show();
				$('#medication').focus();
				$('#laboratory').hide();
				$('#diet').hide();
				$('#progress').hide();
			});
			
			$(document).on('click', '.btn-treat', function(event){
				$('#treatment').show();
				$('#treatment').focus();
				$('#medication').hide();
				$('#laboratory').hide();
				$('#diet').hide();
				$('#progress').hide();
			});	 
			$(document).on('click', '.btn-lab', function(event){
				$('#treatment').hide();
				$('#medication').hide();
				$('#laboratory').show();
				$('#laboratory').focus();
				$('#diet').hide();
				$('#progress').hide();
			});	 
			$(document).on('click', '.btn-diet', function(event){
				$('#treatment').hide();
				$('#medication').hide();
				$('#laboratory').hide();
				$('#diet').show();
				$('#diet').focus();
				$('#progress').hide();
			});	 
			
			$(document).on('click', '.btn-progress', function(event){
				$('#treatment').hide();
				$('#medication').hide();
				$('#laboratory').hide();
				$('#diet').hide();
				$('#progress').show();
				$('#progress').focus();
			
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
						//$(this).val(ui.item.label);
						console.log('Doctor Id ' + ui.item.doctorId);
						$('#refDocId').val(ui.item.doctorId);
						
					}
				});
			});
			
			$(document).on('click', '.btn-treat', function(){
				$(this).css('background', 'yellow');
				$(this).css('color', 'red');
				$('.btn-progress').css('background', '');
				$('.btn-progress').css('color', '');
				$('.btn-medic').css('background', '');
				$('.btn-medic').css('color', '');
				$('.btn-lab').css('background', '');
				$('.btn-lab').css('color', '');
				$('.btn-diet').css('background', '');
				$('.btn-diet').css('color', '');
			});
			
			$(document).on('click', '.btn-medic', function(){
				$(this).css('background', 'yellow');
				$(this).css('color', 'red');
				$('.btn-progress').css('background', '');
				$('.btn-progress').css('color', '');
				$('.btn-treat').css('background', '');
				$('.btn-treat').css('color', '');
				$('.btn-lab').css('background', '');
				$('.btn-lab').css('color', '');
				$('.btn-diet').css('background', '');
				$('.btn-diet').css('color', '');
			});
			
			$(document).on('click', '.btn-lab', function(){
				$(this).css('background', 'yellow');
				$(this).css('color', 'red');
				$('.btn-progress').css('background', '');
				$('.btn-progress').css('color', '');
				$('.btn-treat').css('background', '');
				$('.btn-treat').css('color', '');
				$('.btn-medic').css('background', '');
				$('.btn-medic').css('color', '');
				$('.btn-diet').css('background', '');
				$('.btn-diet').css('color', '');
			});
			
			$(document).on('click', '.btn-diet', function(){
				$(this).css('background', 'yellow');
				$(this).css('color', 'red');
				$('.btn-progress').css('background', '');
				$('.btn-progress').css('color', '');
				$('.btn-treat').css('background', '');
				$('.btn-treat').css('color', '');
				$('.btn-medic').css('background', '');
				$('.btn-medic').css('color', '');
				$('.btn-lab').css('background', '');
				$('.btn-lab').css('color', '');
			});
			
			$(document).on('click', '.btn-progress', function(){
				$(this).css('background', 'yellow');
				$(this).css('color', 'red');
				$('.btn-diet').css('background', '');
				$('.btn-diet').css('color', '');
				$('.btn-treat').css('background', '');
				$('.btn-treat').css('color', '');
				$('.btn-medic').css('background', '');
				$('.btn-medic').css('color', '');
				$('.btn-lab').css('background', '');
				$('.btn-lab').css('color', '');
			});
			
		
			
		
	

	$(document).on('click', '#btn-submit', function(e){
		
		var treatment = $('#treatment').val();
		var medication = $('#medication').val();
		var laboratory = $('#laboratory').val();
		var diet = $('#diet').val();
		
		
		console.log('Treatment : ' + treatment);
		console.log('Medication : ' + medication);
		console.log('Laboratory : ' + laboratory);
		console.log('Diet : ' + diet);
		
		
		var frm = $('#doctor-note-order-frm');
		var doctorNoteId = $('#note-order-id').val();
		
		const swalWithBootstrapButtons = swal.mixin({
			  confirmButtonClass: 'btn btn-success',
			  cancelButtonClass: 'btn btn-danger',
			  buttonsStyling: false,
			})
			
		
			/*
			 * data validation
			 */
			var x = dataValidation();
			console.log('data validation return value : ' + x);
			if (x === false){
				swalWithBootstrapButtons('Ohh no!', errorMsg , 'info');
				return false;
			}
			
		
		
		swal({
			  title: 'Are you sure?',
			  text: "You won't be able to revert this!",
			  type: 'warning',
			  inputValue: doctorNoteId,
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes, Save it!'
			}).then((result) => {
			  if (result.value) {
				  
				 console.log('Result : ' + result.value);
				 console.log('Order id : ' + doctorNoteId);
				  
				  if (!doctorNoteId){
					  
					  console.log('Doctor Note Id not present');
					  
					  $.ajax({
					        type: "POST",
					        url: "doctor.note",
					        data:  frm.serialize() + "&ACTION=INSERT_UPDATE_DOCTOR_ORDER",
					        cache: false,
					        success: function(response) {
					        	var doctorOrderNo = response;
					        	if (doctorOrderNo === 0){
					        		swal('Ohh no!!','Data have not been saved.<br> ERROR CODE :' + doctorOrderNo,'error');
					        	} else {
					        		swal('Well done!','Data have been saved.<br> DOCTOR ORDER ID :' + doctorOrderNo,'success');
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
					        url: "doctor.note",
					        data:  frm.serialize() + "&noteNumber="+doctorNoteId+"&ACTION=INSERT_UPDATE_DOCTOR_ORDER",
					        cache: false,
					        success: function(response) {
					        	serviceOrderNo = response;
					        	if (serviceOrderNo === 0){
					        		swal('Ohh no!!','Data have not been saved.<br> ERROR CODE :' + serviceOrderNo,'error');
					        	} else {
					        		swal('Well done!','Data have been updated.<br> DOCTOR ORDER ID :' + serviceOrderNo,'success');
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
		
		  $('.myModal .modal-body').load( 'doctor.note', 
				  { ACTION :  'FETCH_PREVIOUS_DOCTOR_ORDER', 
		  },
		  function(response, status, xhr) {
			  
			  console.log('status : ' + status);
			  
		  if (status === 'error') { 
		  var msg = "Sorry but there was an error: "; 
		  //swal( "Oh no!", msg +  xhr.status + " " + xhr.statusText, "error");
		  swal('Info', 'No previous Doctor notes are available', 'info');
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
						    	},
						    	{
						    		"targets": 5,
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
		
		var drNoteId =  $(this).attr('dr-no');
		console.log('Dr. Note Id. ' + drNoteId);
		 reset();
		
		 if (drNoteId){
			 
			  $.ajax({
				    url: "doctor.note",
			        type: "POST",
			        datatype: 'text',
			        data:  {
			        	ACTION: 'FETCH_PATIENT_HISTORY',
			        	noteNumber: drNoteId
			        },
			        success: function(data) {
			        	
			        	data = data.replace(/^\W+|\W+$/g, "");
						console.log(data);
			        

						if (data.length !== 0) {
							
							arr = data.replace("[", "").replace("]", "").split(",");
							console.log('Arrray is : ' + arr);
							console.log('Array length is : ' + arr.length);
							
							var i = 0;
							var doctorNoteId;
							var doctorNoteDate;
							var referdoctorName;
							var referDoctorId;

							while (i < arr.length) {
								
								doctorNoteId = $.trim(arr[i]);
								doctorNoteDate = $.trim(arr[i+1]);
								referdoctorName = $.trim(arr[i+2]);
								referDoctorId = $.trim(arr[i+5]);
								var noteType = $.trim(arr[i+3]);
								var noteDetail = $.trim(arr[i+4]).replace(/\|/g, ',');
								
								console.log('Note Type : ' + noteType);
								
								if (noteType === 'ADVC'){
									console.log('In ADVC');
									$('textarea#treatment').val(noteDetail);
								} 
								
								if (noteType === 'DIET'){
									console.log('In DIET');
									$('textarea#diet').val(noteDetail);
								} 
								
								if (noteType === 'MDCN'){
									console.log('In MDCN');
									$('textarea#medication').val(noteDetail);
								} 
								
								if (noteType === 'SERV'){
									console.log('In SERV');
									$('textarea#laboratory').val(noteDetail);
								} 
								
								if (noteType === 'PROG'){
									console.log('In PROG');
									$('textarea#progress').val(noteDetail);
								} 
								
								i += 6;
							}
							
							$('#note-order-id').val(doctorNoteId);
							$('#fromDate').datepicker().datepicker("setDate", doctorNoteDate);
							$('#refer-doctor').val(referdoctorName);
							$('#refDocId').val(referDoctorId);
							
							
						} else {
							swal("Internal Error",  "No previous Doctor note is available", "error");
						}
			        },
			        failure: function (data) {
			            swal("Internal Error",  "Oops, your note was not saved.", "error");
			        },
			        error: function(data){
			        	//console.log(data.responseText);
			        	swal("Internal Error",  "No previous Doctor note is available", "error");
			        }
			        
			    });
		 }
		
	
		
	});
	
	
	$(document).on('click', '#btn-reset', function(event){
		reset();
	});
	
	
	function dataValidation(){
		
		var advice = document.getElementById('treatment');
		var medication = document.getElementById('medication');
		var laboratory = document.getElementById('laboratory');
		var diet = document.getElementById('diet');
		var progress = document.getElementById('progress');
		var referDoctorName = $('#refer-doctor').val();
		
		console.log('advice length ' + advice.value.length);
		console.log('medication length ' + medication.value.length);
		console.log('laboratory length ' + laboratory.value);
		console.log('diet length ' + diet.value);
		console.log('progress length ' + progress.value);
		console.log('referDoctorName length ' + referDoctorName.length);
		
		
		if (referDoctorName.length === 0){
			errorMsg = 'Doctor name is not present';
			return false;
		} else {
			return true;
		} 
		
		if (trimField(advice.value).length == 0){
			errorMsg = 'Atleast One Doctor Advice should be filled up!!!';
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
		$('#treatment').val('');
		$('#medication').val('');
		$('#laboratory').val('');
		$('#progress').val('');
		$('#diet').val('');
		$('#note-order-id').val('');
		$('#refDocId').val('');
		$('#refer-doctor').val('');
		$('#btn-submit').prop('disabled', false);
	}
	
	

});

if (window.history && window.history.pushState) {
	window.history.pushState('', 'ram', 'doctor.note?moduleName=' + document.getElementById('frm-name').value);
	$(window).on('popstate', function() {
		document.location.href = '';
	});
}




