$(function() {
	'use strict';
	
	
	/*
	 * Initial Set Value
	 */
		$('#fromDate').datepicker().datepicker("setDate", new Date());
		var doctorList = [];
		var vitalChartList = [];
		var errorMsg;
		$('#vital-no').prop('disabled', true);
		$('#refer-doctor').prop('disabled', true);
		
	
		
		  $('a[data-toggle="tab"]').on( 'shown.bs.tab', function (e) {
		        $($.fn.dataTable.tables( true ) ).css('width', '100%');
		        $($.fn.dataTable.tables( true ) ).DataTable().columns.adjust().draw();
		    } ); 
		
		/*
		 * Fetching Eyes open previous list
		 */
		
		var ajaxReq = function(){
			$.ajax({
			url : 'vital.chart',
			method : 'post',
			dataType : 'json',
			data : {
				ACTION : 'FETCH_PREVIOUS_VITAL_CHART',
			},
			success : function(data) {

				//alert(data);
				console.log('Vital Chart length : ' + vitalChartList.length);
				vitalChartList.length = 0;
				//location.reload();
				 for (var i=0; i<data.length; i +=11){
					 vitalChartList
						.push({
							date: String($.trim(data[i])),
							time: String($.trim(data[i+1])),
							today: String($.trim(data[i+2])),
							createBy: String($.trim(data[i+3])),
							eyesOpen: String($.trim(data[i+4])),
							verbalResponse : String($.trim(data[i+5])),
							motorResponse : String($.trim(data[i+6])),
							diastolic : String($.trim(data[i+7])),
							systolic : String($.trim(data[i+8])),
							leftPupil : String($.trim(data[i+9])),
							rightPupil : String($.trim(data[i+10]))
						});
				 }
				 
				 
		

				if ($.fn.dataTable.isDataTable('#eyesOpenTable')) {	
					// alert('object already exists');
					table = $('#eyesOpenTable').DataTable();
					table.clear().destroy();
				}

				// alert('object not exists');
				
				var i =0;

			var	table = $('#eyesOpenTable')
						.DataTable(
								{
									"paging" : false,
									"ordering" : false,
									"info" : false,
									"searching" : false,
									"autoWidth" : false,
									"scrollY" : '20vh',
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
									data : vitalChartList,
									columns : [
											{
												'render' : function(object){
													return ++i;
												}
											},
											{
												'data' : 'date'
											},
											{
												'data' : 'time'
											},
											{
												'data' : 'eyesOpen'
											},
											{
												'data' : 'createBy'
											}
									],
									"columnDefs" : [ {
										"width" : "1%",
										"targets" : 0,
										'className': 'text-center'

									}, {
										"width" : "1%",
										"targets" : 1,
										'className': 'text-center'
									}, {
										"width" : "1%",
										"targets" : 2,
										'className': 'text-center'
									}, {
										"width" : "1%",
										"targets" : 3,
										'className': 'text-center'
									}, {
										"width" : "5%",
										"targets" : 4,
										'className': 'text-center'
									}],
									responsive : {
										details : {
											display : $.fn.dataTable.Responsive.display
													.modal({
														header : function(
																row) {
															var data = row
																	.data();
															return 'Details for '
																	+ data[0]
																	+ ' '
																	+ data[1];
														}
													}),
											renderer : $.fn.dataTable.Responsive.renderer
													.tableAll({
														tableClass : 'table'
													})
										}
									}
						
								});
				
				
				if ($.fn.dataTable.isDataTable('#verbalResTable')) {
					// alert('object already exists');
					table = $('#verbalResTable').DataTable();
					table.clear().destroy();
				}

				// alert('object not exists');
				
				i =0;
				
				var table = $('#verbalResTable')
						.DataTable(
								{
									"paging" : false,
									"ordering" : false,
									"info" : false,
									"searching" : false,
									"autoWidth" : true,
									"scrollY" : '25vh',
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
									data : vitalChartList,
									columns : [
											{
												'render' : function(object){
													return ++i;
												}
											},
											{
												'data' : 'date'
											},
											{
												'data' : 'time'
											},
											{
												'data' : 'verbalResponse'
											},
											{
												'data' : 'createBy'
											}

									],
									"columnDefs" : [ {
										"width" : "1%",
										"targets" : 0,
										'className': 'text-center'

									}, {
										"width" : "1%",
										"targets" : 1,
										'className': 'text-center'
									}, {
										"width" : "1%",
										"targets" : 2,
										'className': 'text-center'
									}, {
										"width" : "4%",
										"targets" : 3,
										'className': 'text-center'
									}, {
										"width" : "4%",
										"targets" : 4,
										'className': 'text-center'
									}],
									responsive : {
										details : {
											display : $.fn.dataTable.Responsive.display
													.modal({
														header : function(
																row) {
															var data = row
																	.data();
															return 'Details for '
																	+ data[0]
																	+ ' '
																	+ data[1];
														}
													}),
											renderer : $.fn.dataTable.Responsive.renderer
													.tableAll({
														tableClass : 'table'
													})
										}
									}
						
								});
				
				if ($.fn.dataTable.isDataTable('#motorResTable')) {
					// alert('object already exists');
					table = $('#motorResTable').DataTable();
					table.clear().destroy();
				}

				// alert('object not exists');
				
				i = 0;
				
				var table = $('#motorResTable')
						.DataTable(
								{
									"paging" : false,
									"ordering" : false,
									"info" : false,
									"searching" : false,
									"autoWidth" : false,
									"scrollY" : '25vh',
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
									data : vitalChartList,
									columns : [
											{
												'render' : function(object){
													return ++i;
												}
											},
											{
												'data' : 'date'
											},
											{
												'data' : 'time'
											},
											{
												'data' : 'motorResponse'
											},
											{
												'data' : 'createBy'
											}
									],
									"columnDefs" : [ {
										"width" : "1%",
										"targets" : 0,
										'className': 'text-center'

									}, {
										"width" : "1%",
										"targets" : 1,
										'className': 'text-center'
									}, {
										"width" : "1%",
										"targets" : 2,
										'className': 'text-center'
									}, {
										"width" : "1%",
										"targets" : 3,
										'className': 'text-center'
									}, {
										"width" : "5%",
										"targets" : 4,
										'className': 'text-center'
									}],
									responsive : {
										details : {
											display : $.fn.dataTable.Responsive.display
													.modal({
														header : function(
																row) {
															var data = row
																	.data();
															return 'Details for '
																	+ data[0]
																	+ ' '
																	+ data[1];
														}
													}),
											renderer : $.fn.dataTable.Responsive.renderer
													.tableAll({
														tableClass : 'table'
													})
										}
									}
						
								});
				
				
				if ($.fn.dataTable.isDataTable('#bpTable')) {
					// alert('object already exists');
					table = $('#bpTable').DataTable();
					table.clear().destroy();
				}

				// alert('object not exists');
				
				i = 0;
				
				var table = $('#bpTable')
						.DataTable(
								{
									"paging" : false,
									"ordering" : false,
									"info" : false,
									"searching" : false,
									"autoWidth" : false,
									"scrollY" : '20vh',
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
									data : vitalChartList,
									columns : [
										{
											'render' : function(object){
												return ++i;
											}
										},
										{
											'data' : 'date'
										},
										{
											'data' : 'time'
										},
										{
											'data' : 'diastolic'
										},
										{
											'data' : 'systolic'
										},
										{
											'data' : 'createBy'
										}
									],
									"columnDefs" : [ {
										"width" : "1%",
										"targets" : 0,
										'className': 'text-center'

									}, {
										"width" : "1%",
										"targets" : 1,
										'className': 'text-center'
									}, {
										"width" : "1%",
										"targets" : 2,
										'className': 'text-center'
									}, {
										"width" : "1%",
										"targets" : 3,
										'className': 'text-center'
									}, {
										"width" : "1%",
										"targets" : 4,
										'className': 'text-center'
									}, {
										"width" : "5%",
										"targets" : 5,
										'className': 'text-center'
									}],
									responsive : {
										details : {
											display : $.fn.dataTable.Responsive.display
													.modal({
														header : function(
																row) {
															var data = row
																	.data();
															return 'Details for '
																	+ data[0]
																	+ ' '
																	+ data[1];
														}
													}),
											renderer : $.fn.dataTable.Responsive.renderer
													.tableAll({
														tableClass : 'table'
													})
										}
									}
						
								});
				
				if ($.fn.dataTable.isDataTable('#pupilTable')) {
					// alert('object already exists');
					table = $('#pupilTable').DataTable();
					table.clear().destroy();
				}

				// alert('object not exists');
				
				i = 0;

				var table = $('#pupilTable')
						.DataTable(
								{
									"paging" : false,
									"ordering" : false,
									"info" : false,
									"searching" : false,
									"autoWidth" : false,
									"scrollY" : '20vh',
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
									data : vitalChartList,
									columns : [
										{
											'render' : function(object){
												return ++i;
											}
										},
										{
											'data' : 'date'
										},
										{
											'data' : 'time'
										},
										{
											'data' : 'leftPupil'
										},
										{
											'data' : 'rightPupil'
										},
										{
											'data' : 'createBy'
										}
									],
									"columnDefs" : [ {
										"width" : "1%",
										"targets" : 0,
										'className': 'text-center'

									}, {
										"width" : "1%",
										"targets" : 1,
										'className': 'text-center'
									}, {
										"width" : "1%",
										"targets" : 2,
										'className': 'text-center'
									}, {
										"width" : "1%",
										"targets" : 3,
										'className': 'text-center'
									}, {
										"width" : "1%",
										"targets" : 4,
										'className': 'text-center'
									}, {
										"width" : "5%",
										"targets" : 5,
										'className': 'text-center'
									}],
									responsive : {
										details : {
											display : $.fn.dataTable.Responsive.display
													.modal({
														header : function(
																row) {
															var data = row
																	.data();
															return 'Details for '
																	+ data[0]
																	+ ' '
																	+ data[1];
														}
													}),
											renderer : $.fn.dataTable.Responsive.renderer
													.tableAll({
														tableClass : 'table'
													})
										}
									}
						
								});
				
				
				

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
		
		};
		
		
		/*
		 * Fetching Doctor List with doctor id
		 */
		
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
			
		
	

	$(document).on('click', '#btn-submit', function(e){

		var frm = $('#vital-chart-frm');
		var nurseNoteId = $('#vital-no').val();
		
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
					        url: "vital.chart",
					        data:  frm.serialize() + "&ACTION=INSERT_UPDATE_VITAL_CHART",
					        cache: false,
					        success: function(response) {
					        	var nurseNoteNo = response;
					        	if (nurseNoteNo === 0){
					        		swal('Ohh no!!','Data have not been saved.<br> ERROR CODE :' + nurseNoteNo,'error');
					        	} else {
					        		swal('Well done!','Data have been saved.<br> VITAL CHART ID :' + nurseNoteNo,'success');
					        		reset();
					        		//location.reload();
					        		ajaxReq();
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
					        data:  frm.serialize() + "&noteNumber="+nurseNoteId+"&ACTION=INSERT_UPDATE_VITAL_CHART",
					        cache: false,
					        success: function(response) {
					        	nurseNoteNo = response;
					        	if (nurseNoteNo === 0){
					        		swal('Ohh no!!','Data have not been saved.<br> ERROR CODE :' + nurseNoteNo,'error');
					        	} else {
					        		swal('Well done!','Data have been updated.<br> VITAL CHART ID :' + nurseNoteNo,'success');
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
	
	
	$(document).on('change', '.left-pupil-close-open', function(event){
		var pupilValue = $(this).val();
		// alert(pupilValue);
		if ($.trim(pupilValue) === 'C'){
			$('.left-pupil-size').prop('disabled', true);
		} else {
			$('.left-pupil-size').prop('disabled', false);
		}
	});
	
	$(document).on('change', '.right-pupil-close-open', function(event){
		var pupilValue = $(this).val();
		// alert(pupilValue);
		if ($.trim(pupilValue) === 'C'){
			$('.right-pupil-size').prop('disabled', true);
		} else {
			$('.right-pupil-size').prop('disabled', false);
		}
	});

	$(document).on('change', '.left-pupil-size', function(event){
		var pupilValue = $(this).val();
		// alert(pupilValue);
		if ($.trim(pupilValue) === 'S'){
			$('.left-pupil-close-open').prop('disabled', true);
		} else {
			$('.left-pupil-close-open').prop('disabled', false);
		}
	});
	
	$(document).on('change', '.right-pupil-size', function(event){
		var pupilValue = $(this).val();
		// alert(pupilValue);
		if ($.trim(pupilValue) === 'S'){
			$('.right-pupil-close-open').prop('disabled', true);
		} else {
			$('.right-pupil-close-open').prop('disabled', false);
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
	
	ajaxReq();

});






