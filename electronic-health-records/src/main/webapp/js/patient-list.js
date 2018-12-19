$(function() {

	var arr = new Array();
	var patientListArr = [];

	var select = $(".sel-ward");
	var id = $('#user-id').val();
	console.log(' User Id : ' + id);
	select.css("display", "inline");

	$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
		$($.fn.dataTable.tables(true)).css('width', '100%');
		$($.fn.dataTable.tables(true)).DataTable().columns.adjust().draw();
	});

	// alert(id);

	$('.circleModal').modal({
		backdrop : 'static',
		keyboard : false
	});

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
					ACTION : 'GET_WARD_LIST',
					empId : id,
				},
				success : function(data) {

					// data = data.replace(/^\W+|\W+$/g, "");
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

	var patientList = function() {

		var wardNo = $('.sel-ward').val();
		if (wardNo === null) {
			wardNo = '0';
		}
		console.log('Ward Number : ' + wardNo);

		$
				.ajax({
					url : 'patient.list',
					method : 'post',
					dataType : 'json',
					data : {
						ACTION : 'GET_PAT_DET',
						empId : id,
						wardId : wardNo

					},
					success : function(data) {

						console.log(data);

						patientListArr.length = 0;

						for (var i = 0; i < data.length; i += 8) {
							patientListArr.push({
								ip : String($.trim(data[i])),
								name : String($.trim(data[i + 1])),
								ward : String($.trim(data[i + 2])),
								bed : String($.trim(data[i + 3])),
								adminDate : String($.trim(data[i + 4])),
								adminDoc : String($.trim(data[i + 5])),
								speciality : String($.trim(data[i + 6])),
								category : String($.trim(data[i + 7]))
							});
						}

						$('.circleModal').modal('hide');

						if ($.fn.dataTable.isDataTable('#myTable')) {
							table = $('#myTable').DataTable();
							table.clear().destroy();
						}

						// alert('object not exists');

						var i = 0;

						table = $('#myTable')
								.DataTable(
										{
											'info' : false,
											'order' : [],
											'data' : patientListArr,
											'createdRow' : function(row, data,
													dataIndex) {
												console.log('Row : '
														+ JSON.stringify(row)
														+ ' ' + ' Data : '
														+ JSON.stringify(data)
														+ ' ' + ' dataIndex : '
														+ dataIndex);
												$(row).attr('pat-number',
														data['ip']);
												$(row).addClass(
														'context-menu-one');
											},
											deferRender : true,
											columns : [ {
												'data' : 'ip'
											}, {
												'data' : 'name'
											}, {
												'data' : 'ward'
											}, {
												'data' : 'bed'
											}, {
												'data' : 'adminDate'
											}, {
												'data' : 'adminDoc'
											}, {
												'data' : 'speciality'
											}, {
												'data' : 'category'
											}, {
												'render' : function(oObj) {
													return 'Bed Allocated';
												}
											}

											],
											columnDefs : [
													{
														'targets' : [ 0, 1, 2,
																3, 4, 5, 6, 7,
																8 ],
														'orderable' : false,
														'width' : '5%'

													},
													{
														'targets' : 0,
														'createdCell' : function(
																td, cellData,
																rowData, row,
																col) {
															console
																	.log('TD : '
																			+ JSON
																					.stringify(td)
																			+ ' '
																			+ ' Cell Data : '
																			+ cellData
																			+ ' '
																			+ ' rowData : '
																			+ JSON
																					.stringify(rowData)
																			+ ' row :'
																			+ row
																			+ ' '
																			+ ' col : '
																			+ col);
															$(td).attr('id',
																	'otherID');
														}

													}

											],
											responsive : {
												details : {
													display : $.fn.dataTable.Responsive.display
															.modal({
																header : function(
																		row) {
																	var data = row
																			.data();
																	console
																			.log('Data : '
																					+ JSON
																							.stringify(data));

																	return 'Patient Number '
																			+ data['ipNumber'];

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

	}

	patientList();

	$(document).on('change', '.sel-ward', function() {

		var ward = $(this).val();

		patientList();

		// alert(ward);

		/*
		 * var req = $ .ajax({ url : 'patient.list', method : 'post', dataType :
		 * 'json', data : { ACTION : 'GET_PAT_DET', empId : id, wardId : ward
		 *  }, success : function(data) {
		 * 
		 * 
		 * console.log(data);
		 * 
		 * patientListArr.length = 0;
		 * 
		 * for (var i =0; i<data.length; i+=8){ patientListArr.push({ ip:
		 * String($.trim(data[i])), name: String($.trim(data[i+1])), ward:
		 * String($.trim(data[i+2])), bed: String($.trim(data[i+3])), adminDate:
		 * String($.trim(data[i+4])), adminDoc: String($.trim(data[i+5])),
		 * speciality: String($.trim(data[i+6])), category:
		 * String($.trim(data[i+7])) }); }
		 * 
		 * 
		 * 
		 * if ($.fn.dataTable.isDataTable('#myTable')) { table =
		 * $('#myTable').DataTable(); table.clear().destroy(); }
		 * 
		 * table = $('#myTable') .DataTable( { info: false, order: [], data :
		 * patientListArr, 'createdRow': function(row, data, dataIndex){
		 * //console.log('Row : ' + JSON.stringify(row) + ' ' + ' Data : ' +
		 * JSON.stringify(data) + ' ' + ' dataIndex : ' + dataIndex);
		 * $(row).attr('pat-number', data['ip']);
		 * $(row).addClass('context-menu-one'); }, columns : [ { 'data' : 'ip' }, {
		 * 'data' : 'name' }, { 'data' : 'ward' }, { 'data' : 'bed' }, { 'data' :
		 * 'adminDate' }, { 'data' : 'adminDoc' }, { 'data' : 'speciality' }, {
		 * 'data' : 'category' }, { 'render' : function(oObj) { return 'Bed
		 * Allocated'; } }
		 * 
		 * 
		 *  ], responsive : { details : { display :
		 * $.fn.dataTable.Responsive.display .modal({ header : function( row) {
		 * var data = row .data(); console.log('Data: ' + data); return 'Details
		 * for ' + data[0] + ' ' + data[1]; } }), renderer :
		 * $.fn.dataTable.Responsive.renderer .tableAll({ tableClass : 'table' }) } },
		 * columnDefs: [ { 'targets': [0,1,2,3,4,5,6,7,8], 'orderable': false,
		 * 'width': '5%'
		 *  },
		 * 
		 *  ] });
		 *  }, failure : function(data) { console.log(data.responseText); },
		 * error : function(data) { console.log(data.responseText); }
		 * 
		 * });
		 */

	});

	/*
	 * 
	 * https://swisnl.github.io/jQuery-contextMenu/demo.html JQuery Context Menu
	 * 2.x
	 * 
	 * 
	 * 
	 */

	var moduleName = $('#module-name').val();
	var contextMenuList = [];

	$.ajax({
		url : 'patient.list',
		method : 'post',
		dataType : 'json',
		data : {
			ACTION : 'GET_USER_CONTEXT_MENU',
			moduleName : moduleName

		},
		success : function(data) {

			console.log(data);

			contextMenuList.length = 0;

			for (var i = 0; i < data.length; i += 2) {
				contextMenuList.push({
					frmName : String($.trim(data[i])),
					frmPath : String($.trim(data[i + 1])),
				});
			}
		},
		error : function(data) {
			console.log(data.responseText);
		},
		failure : function(data) {
			console.log(data.responseText);
		}
	});

	$.contextMenu({

		selector : '.context-menu-one',
		trigger : 'left',
		callback : function(key, options) {
			var m = "clicked: " + key;
			
			window.console && console.log(moduleName) || alert(moduleName);
			window.console && console.log(userCode) || alert(userCode);

		},
		items : {
			"Vital Chart" : {
				name : "Vital Chart",
				icon : "copy",
				disabled : function(itemKey, opt) {
					window.console && console.log('Item Key : ' + itemKey);
					window.console && console.log('Option : ' + opt);
					console.log(' Context Menu List : ' + JSON.stringify(contextMenuList));
					for (var i =0; i<contextMenuList.length; i++){
						if (itemKey === contextMenuList[i]['frmName']) {
							//console.log('Vital Chart found');
							return false;
						}
					}
					return true;
				},
				callback : function(itemKey, opt, e) {
					window.console && console.log('Item Key : ' + itemKey);
					window.console && console.log('Option : ' + opt);
					window.console && console.log('Event : ' + e.which);
					var m = $(this).attr('pat-number');
					window.console && console.log('Pat Number : ' + m);
					m = 'vital.chart?moduleName=vital chart&ip_no=' + m;
					window.location.href = m;
				}
			},
			"Doctor's Note" : {
				name : "Doctor's Note",
				icon : "cut",
				disabled : function(itemKey, opt) {
					for (var i =0; i<contextMenuList.length; i++){
						if (itemKey === contextMenuList[i]['frmName']) {
							return false;
						}
					}
					return true;
				},
				callback : function(itemKey, opt, e) {
					var m = $(this).attr('pat-number');
					window.console && console.log('Pat Number : ' + m);
					m = 'doctor.note?moduleName=doctor\'s note/order&ip_no=' + m;
					window.location.href = m;
				}
			},
			"Nurse's Note" : {
				name : "Nurse's Note",
				icon : "cut",
				disabled : function(itemKey, opt) {
					for (var i =0; i<contextMenuList.length; i++){
						if (itemKey === contextMenuList[i]['frmName']) {
							return false;
						}
					}
					return true;
				},
				callback : function(itemKey, opt, e) {
					var m = $(this).attr('pat-number');
					window.console && console.log('Pat Number : ' + m);
					m = 'nurse.note?moduleName=nurse\'s note&ip_no=' + m;
					window.location.href = m;
				}
			},
			"Service Ordering" : {
				name : "Service Ordering",
				icon : "cut",
				disabled : function(itemKey, opt) {
					for (var i =0; i<contextMenuList.length; i++){
						if (itemKey === contextMenuList[i]['frmName']) {
							return false;
						}
					}
					return true;
				},
				callback : function(itemKey, opt, e) {
					var m = $(this).attr('pat-number');
					window.console && console.log('Pat Number : ' + m);
					m = 'service.order?moduleName=service ordering&ip_no=' + m;
					window.location.href = m;
				}
			},
			"Pharmacy Ordering" : {
				name : "Pharmacy Ordering",
				icon : "cut",
				disabled : function(itemKey, opt) {
					for (var i =0; i<contextMenuList.length; i++){
						if (itemKey === contextMenuList[i]['frmName']) {
							return false;
						}
					}
					return true;
				},
				callback : function(itemKey, opt, e) {
					var m = $(this).attr('pat-number');
					window.console && console.log('Pat Number : ' + m);
					m = 'pharma.order?moduleName=pharmacy ordering&ip_no=' + m;
					window.location.href = m;
				}
			},
			"Patient Transfer" : {
				name : "Patient Transfer",
				icon : "cut",
				disabled : function(itemKey, opt) {
					for (var i =0; i<contextMenuList.length; i++){
						if (itemKey === contextMenuList[i]['frmName']) {
							return false;
						}
					}
					return true;
				},
				callback : function(itemKey, opt, e) {
					var m = $(this).attr('pat-number');
					window.console && console.log('Pat Number : ' + m);
					m = 'patient.transfer?moduleName=Patient Transfer&ip_no='
							+ m;
					window.location.href = m;
				}
			}
		}
	});

});
