$(function() {

	var arr = new Array();

	var select = $(".sel-ward");
	var id = $('input[name=user_id]').val();
	select.css("display", "inline");

	// alert(id);

	select
			.empty()
			.append(
					'<option selected="selected" value="0" disabled = "disabled">Loading.....</option>');

	var req = $
			.ajax({
				url : 'patient.list',
				type : 'post',
				datatype : 'text',
				data : {
					ACTION : 'GET_WARD',
					empId : id,
				},
				success : function(data) {

					//data = data.replace(/^\W+|\W+$/g, "");
					// alert(data.length);

					if (data.length !== 0) {
						arr = data.replace("[", "").replace("]", "").split(",");
						select
								.empty()
								.append(
										'<option selected="selected" value="0">Select Ward</option>');
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
	
	
	/* $('#myTable').DataTable( {
		 
	        ajax: {
	            url: "patient.list",
	            method:   'post',
	            dataType: 'json',
	            data: {
	            	ACTION : 'GET_PAT_DET',
					empId : id,
					wardId : '0'
	            },
	        
	            success: function(data){
	            	alert(data);
	            }
	        },
	        deferRender: true,
	        
	        columns: [
	            { 'data': "ipNumber" },
	            { 'data': "ipName" },
	            { 'data': "ward"},
	            { 'data': "bedNumber" },
	            { 'data': "admissionDate" },
	            { 'data': "admittingDoctor" },
	            { 'data' : "speciality" }, 
	            { 'data' : "subCategory"},
	            {
					'render' : function(oObj){
						return 'Bed Allocated'; 
					}
				},
				{
					'data': "ipNumber",
					"render" : function(jsonIpNumber){ 
						return '<a href="/pat_panel.do?ip_no='+ jsonIpNumber +'"class="context-menu-one btn btn-info btn-xs">Click</a>';
				}
				}
	        ],
	        rowId: 'extn',
	        select: true,
	        dom: 'Bfrtip',
	        scrollY: 500,
			paging: true,
	        buttons: [
	            {
	                text: 'Reload table',
	                action: function () {
	                    table.ajax.reload();
	                }
	            }
	        ]
	    } );*/
	
	var req = $.ajax({
		url : 'patient.list',
		method : 'post',
		dataType : 'json',
		data : {
			ACTION : 'GET_PAT_DET',
			empId : id,
			wardId : '0'

		},
		success : function(data) {

			//alert(data);

			if ($.fn.dataTable.isDataTable('#myTable')) {
				//alert('object already exists');
				table = $('#myTable').DataTable();
				table.clear().destroy();
			} 
			
				//alert('object not exists');
				
				table = $('#myTable').DataTable({
					
					data : data,
					 //deferRender: true,
					columns : [ {
						'data': "ipNumber"
					}, {
						'data' : 'ipName'
					}, {
						'data' : 'ward'
					}, {
						'data' : 'bedNumber'
					}, {
						'data' : 'admissionDate'
					}, {
						'data' : 'admittingDoctor'
					}, {
						'data' : 'speciality'
					}, {
						'data' : 'subCategory'
					},
					 {
						'render' : function(oObj){
							return 'Bed Allocated'; 
						}
					},
					{
						'data': "ipNumber",
						"render" : function(jsonIpNumber){ 
							return '<a href="/pat_panel.do?ip_no='+ jsonIpNumber +'"class="context-menu-one btn btn-info btn-xs">Click</a>';
					}
					}

					],
					 responsive: {
				            details: {
				                display: $.fn.dataTable.Responsive.display.modal( {
				                    header: function ( row ) {
				                        var data = row.data();
				                        return 'Details for '+data[0]+' '+data[1];
				                    }
				                } ),
				                renderer: $.fn.dataTable.Responsive.renderer.tableAll( {
				                    tableClass: 'table'
				                } )
				            }
				        }
					/*rowId: 'extn',
			        select: true,
			        dom: 'Bfrtip',
			        scrollY: 500,
					paging: false,
					buttons: [
			            {
			                text: 'Reload Table',
			                action: function () {
			                    table.ajax.reload(null, false);
			                }
			            }
			        ]*/
					
					
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
	
	
	

	$(document).on('change', '.sel-ward', function() {

		var ward = $(this).val();

		//alert(ward);

		var req = $.ajax({
			url : 'patient.list',
			method : 'post',
			dataType : 'json',
			data : {
				ACTION : 'GET_PAT_DET_BY_WARD',
				empId : id,
				wardId : ward

			},
			success : function(data) {

				//alert(data);

				if ($.fn.dataTable.isDataTable('#myTable')) {
					//alert('object already exists');
					table = $('#myTable').DataTable();
					table.clear().destroy();
				} 
				
					//alert('object not exists');
					
					table = $('#myTable').DataTable({
						
						data : data,
						columns : [ {
							'data': "ipNumber"
						}, {
							'data' : 'ipName'
						}, {
							'data' : 'ward'
						}, {
							'data' : 'bedNumber'
						}, {
							'data' : 'admissionDate'
						}, {
							'data' : 'admittingDoctor'
						}, {
							'data' : 'speciality'
						}, {
							'data' : 'subCategory'
						},
						 {
							'render' : function(oObj){
								return 'Bed Allocated'; 
							}
						},
						{
							'data': "ipNumber",
							"render" : function(jsonIpNumber){ 
								return '<a href="/pat_panel.do?ip_no='+ jsonIpNumber +'"class="context-menu-one btn btn-info btn-xs">Click</a>';
						}
						}

						],
						  responsive: {
					            details: {
					                display: $.fn.dataTable.Responsive.display.modal( {
					                    header: function ( row ) {
					                        var data = row.data();
					                        return 'Details for '+data[0]+' '+data[1];
					                    }
					                } ),
					                renderer: $.fn.dataTable.Responsive.renderer.tableAll( {
					                    tableClass: 'table'
					                } )
					            }
					        }
						//paging: false
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

	});
	
	  $.contextMenu({
		  
          selector: '.context-menu-one', 
          callback: function(key, options) {
              var m = "clicked: " + key;
              window.console && console.log(m) || alert(m); 
          },
          items: {
              "receivePatient": {
            	  name: "Receive Patient", 
            	  icon: "edit",
            	  // superseeds "global" callback
                  callback: function(itemKey, opt, e) {
                  var m = $(this).attr('href');
                  window.location.href = m;
                  //window.console && console.log(m) || alert(m); 
            		  }
              },
              "vitalEntry": {name: "Vital Entry", icon: "cut"},
              "bedPosition": {name: "Bed Position", icon: "copy"},
              "nurseNotes": {name: "Nurse Notes", icon: "paste"},
              "bedTransfer": {name: "Bed Transfer", icon: "delete"},
              "dischargeRequest": {name: "Discharge Request", icon: "delete"},
              "deathPosting": {name: "Death Posting", icon: "delete"},
              "drugReturn": {name: "Drug Return", icon: "delete"},
              "dischargeSummary": {name: "Discharge Summary", icon: "delete"},
              "sep1": "---------",
              "quit": {name: "Quit", icon: function(){
                  return 'context-menu-icon context-menu-icon-quit';
              }}
          }
      });

   

});

function openSlideMenu(){
	document.getElementById('menu').style.width = "250px";
	document.getElementById('container').style.marginLeft = "250px";
}

function closeSlideMenu(){
	document.getElementById('menu').style.width = "0";
	document.getElementById('container').style.marginLeft = "250px";
}

