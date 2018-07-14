<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<jsp:useBean id="indoorPat" class="com.gnrchospitals.IndoorPatient"
	scope="request"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>GNRC</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="images/favicon.jpg" type="image/jpeg"
	sizes="16x16" />
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<%@include file="gnrc-common-include-css.html"%>
<style>

/* Ensure that the demo table scrolls */
th, td {
	white-space: nowrap;
}

/* div.dataTables_wrapper {
	width: 100%;
	margin: 0 auto;
}
 */
div.dataTables_wrapper {
	margin: 0 auto;
}

tr, tbody tr {
	height: 10px;
}
</style>

</head>

<%
	ArrayList<ArrayList<String>> list = indoorPat.getPatientList();
	ArrayList<String> col = list.get(0);
	ArrayList<String> row = list.get(1);
%>



<body>
	<p>
		<br />
	</p>
	<div class="container">
		<h1 style="text-align: center;">Electronic Health Record</h1>

		<table class="table table-striped table-bordered table-hover"
			id="myTable" style="width: 100%">


			<thead>
				<tr style="font-size: 14px;">
					<%
						for (int i = 0; i < col.size(); i++) {
					%>
					<th
						style="background-color: #A90D82; color: #fff; text-align: center;"><%=col.get(i)%></th>

					<%
						}
					%>
					<th
						style="background-color: #A90D82; color: #fff; text-align: center;">Action</th>
				</tr>
			</thead>
			<tfoot>
				<tr style="font-size: 14px;">
					<%
						for (int i = 0; i < col.size(); i++) {
					%>
					<th
						style="background-color: #A90D82; color: #fff; text-align: center;"><%=col.get(i)%></th>

					<%
						}
					%>
					<th></th>
				</tr>
			</tfoot>
			<tbody>

				<%
					int rowSize = row.size() / col.size();
					int i = 0;
					int index = 0;
					int ipIndex = 0;
					String ipNo = "";
					while (i < rowSize) {
						ipNo = (String) row.get(ipIndex);
				%>

				<tr style="font-size: 11px;">
					<%
						for (int j = 0; j < col.size(); j++) {
					%>

					<td><%=row.get(index)%></td>

					<%
						index++;
							}
					%>


					<td><a href="/dashboard.do?ip_no=<%=ipNo%>"
						class="btn btn-info btn-xs">EMR</a></td>
				</tr>
				<%
					ipIndex += col.size();
						i++;
					}
				%>

			</tbody>
		</table>


	</div>
	<%@include file="gnrc-common-include-js.html"%>
	<script>
		$(document).ready(function() {

			/* $('#myTable').DataTable({

				scrollY : "350px",
				scrollX : true,
				scrollCollapse : true

			}); */

			var table = $('#myTable').DataTable();

			//table.columns.adjust().draw();

			/*  $('#myTable').DataTable( {
				scrollY : "350px",
				scrollX : true,
				scrollCollapse : true,
				stateSave: true,
				autoWidth: true,
				 columnDefs : [
			            { width: '20%', "targets": 0},
			            { width: '20%', "targets": 0},
			            { width: '20%', "targets": 0},
			            { width: '10%', "targets": 0}
			        ], 
			    initComplete: function () {
			        this.api().columns([2, 4, 5]).every( function () {
			            var column = this;
			            var select = $('<select><option value=""></option></select>')
			                .appendTo( $(column.footer()).empty() )
			                .on( 'change', function () {
			                    var val = $.fn.dataTable.util.escapeRegex(
			                        $(this).val()
			                    );
			
			                    column
			                        .search( val ? '^'+val+'$' : '', true, false )
			                        .draw();
			                   
			                    
			                } );
			
			            column.data().unique().sort().each( function ( d, j ) {

			        		var val = $.fn.dataTable.util.escapeRegex(d);
				            
			            	 if(column.search() === '^'+val+'$'){
			            		 select.append('<option value="'+d+'" selected="selected">'+d+'</option>') 
			            		 
			            	 } else {
			            		
			            		  select.append( '<option value="'+d+'">'+d+'</option>' ) 
				            	 }
			               
			            } );
			        } );
			    }
			} );  */

		});
	</script>
</body>
</html>