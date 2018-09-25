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
							'data': "ipNumber",
							"render" : function(jsonIpNumber){ 
								return '<a href="/pat_panel.do?ip_no='+ jsonIpNumber +'"class="btn btn-info btn-xs">Click</a>';
						}
						}

						]
					}).cloumns.adjust().draw();

				

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

});