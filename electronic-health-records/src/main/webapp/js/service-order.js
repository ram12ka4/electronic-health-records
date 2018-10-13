$(function() {

	var table = $('#myTable').DataTable({

		"paging" : false,
		"ordering" : false,
		"info" : false,
		"searching" : false,
		"autoWidth" : false,
		"scrollY" : '50vh',
		"scrollCollapse": true,
		responsive : {
			details : {
				display : $.fn.dataTable.Responsive.display.modal({
					header : function(row) {
						var data = row.data();
						return 'Details for ' + data[0] + ' ' + data[1];
					}
				}),
				renderer : $.fn.dataTable.Responsive.renderer.tableAll({
					tableClass : 'table'
				})
			}
		},

		"columnDefs" : [ {
			"width" : "5%",
			"targets" : 0
		
		}, {
			"width" : "27%",
			"targets" : 1
		}, {
			"width" : "7%",
			"targets" : 2
		}, {
			"width" : "10%",
			"targets" : 3
		}, {
			"width" : "5%",
			"targets" : 4
		}, {
			"width" : "10%",
			"targets" : 5
		}, {
			"width" : "10%",
			"targets" : 6
		}, {
			"width" : "10%",
			"targets" : 7
		}, {
			"width" : "5%",
			"targets" : 8
		},]

	}).draw(false);

	var counter = 7;

	for (var i = 0; i < counter; i++) {
	
		table.row.add([ 
			'<div class="pull-right row-order" id="'+ (i+1) +'">' + (i + 1) + '</div>', 
			'<input type="text"	class="form-control dis-auto-width dis-bottom-margin">', 
			'<input type="text"	class="form-control dis-auto-width dis-bottom-margin">', 
			'<input type="text"	class="form-control dis-auto-width dis-bottom-margin">', 
			'<input type="text"	class="form-control dis-auto-width dis-bottom-margin">', 
			'<input type="text"	class="form-control dis-auto-width dis-bottom-margin">', 
			'<input type="text"	class="form-control dis-auto-width dis-bottom-margin">',
			'<input type="text"	class="form-control dis-auto-width dis-bottom-margin">',
			'<button class="btn btn-warning btn-sm pull-right row-delete">X</button>'
			]).draw();
	}
	
	
	$('#myTable tbody').on('click', '.row-delete', function(){
		
		var row = $(this).closest('tr')
		var id = row.find('.row-order').attr('id');
		alert('ID : ' + id);
			var siblings = row.siblings();
			row.remove();
			siblings.each(function(index){
				 $(this).children().first().addClass('pull-right row-order').attr('id', index+1).text(index + 1);
			});
			
			
		
		//table.row($(this).parents('tr')).remove().draw(false);
	});
	
	$(document).on('click', '.row-add', function(){
		
		var lastRowId = $('.row-order:last').attr('id');
		
		alert(lastRowId);
		
		
		
		table.row.add([
			'<div class="pull-right row-order" id="'+ (parseInt(lastRowId)+1) +'">'+ (parseInt(lastRowId)+1) + '</div>', 
			'<input type="text"	class="form-control dis-auto-width dis-bottom-margin">', 
			'<input type="text"	class="form-control dis-auto-width dis-bottom-margin">', 
			'<input type="text"	class="form-control dis-auto-width dis-bottom-margin">', 
			'<input type="text"	class="form-control dis-auto-width dis-bottom-margin">', 
			'<input type="text"	class="form-control dis-auto-width dis-bottom-margin">', 
			'<input type="text"	class="form-control dis-auto-width dis-bottom-margin">',
			'<input type="text"	class="form-control dis-auto-width dis-bottom-margin">',
			'<button class="btn btn-warning btn-sm pull-right row-delete">X</button>'
		]).draw();
		
		
	});
	

});
