

$(function(){
	
	
	var table = $('#myTable').DataTable({
		
		"paging":   false,
        "ordering": false,
        "info":     false,
        "searching": false,
        "autoWidth": false,
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
        },
       
        "columnDefs": [
        	{"width": "5%", "targets": 0},
        	{"width": "27%", "targets": 1},
        	{"width": "7%", "targets": 2},
        	{"width": "10%", "targets": 3},
        	{"width": "5%", "targets": 4},
        	{"width": "10%", "targets": 5},
        	{"width": "10%", "targets": 6},
        	{"width": "10%", "targets": 7},
        ]
		
		
	});
	
	
	
	
	
	
	
	
});

