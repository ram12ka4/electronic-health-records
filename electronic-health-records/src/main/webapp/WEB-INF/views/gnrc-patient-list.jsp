<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<jsp:useBean id="patientDao"
	class="com.gnrchospitals.daoimpl.PatientDaoImpl" scope="request"></jsp:useBean>
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
<link rel="stylesheet" type="text/css" href="css/patient-list.css" />
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

tr {
	height: 5px;
}

table {
	outline: none;
}

th {
	border-radius: 2px;
	background: #A90D82;
	color: #fff;
	text-align: center;
}

thead tr {
	font-size: 14px;
}

tbody tr {
	font-size: 11px;
}
</style>

</head>

<%
	String empCode = (String) session.getAttribute("user");
	String userRole = (String) session.getAttribute("userRole");

	/* ArrayList<ArrayList<String>> list = patientDao.getPatientList(empCode, "0");
	ArrayList<String> col = list.get(0);
	ArrayList<String> row = list.get(1); */

	List<String> linkList = patientDao.getParentLink(userRole);

	System.out.println(linkList);
%>



<body>

	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Expires", "0"); // proxies

		// allow access only if session exists
		String user = null;
		if (session.getAttribute("user") == null) {
			response.sendRedirect("/login.do");
		} else
			user = (String) session.getAttribute("user");
		String userName = null;
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user"))
					userName = cookie.getValue();
				if (cookie.getName().equals("JSESSIONID"))
					sessionID = cookie.getValue();
			}
		} else {
			sessionID = session.getId();
		}
	%>

	<nav class="navbar navbar-inverse">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><img alt=""
					src="image/logo.jpg"></a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active"><a href="patient.portal">Home <span
							class="sr-only">(current)</span></a></li>
					<li><a href="#">User Id : ${sessionScope.user}</a></li>
					<li><a href="#">Name : ${sessionScope.userName}</a></li>
					<li><a href="#">Login Time : ${sessionScope.loginTime}</a></li>
					<li><a href="#">Login From : ${sessionScope.loginFrom}</a></li>
					<li><a href="#">Location : ${sessionScope.location}</a></li>
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Sign out <i class="fas fa-user"></i></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" data-toggle="modal"
								data-target="#logoutModal">Logout</a></li>
							<li><a href="#">Change Password</a></li>
						</ul></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!--  /.container-fluid -->
	</nav>

	<div id="content">
		<div id="menu" class="my-nav">
			<a href="#" class="close" onclick="closeSlideMenu()"> <i
				class="fas fa-times"></i>
			</a>
			<ul>
				<li><a href="#" class="active">Home</a></li>

				<%
					int i = 0;
					while (i < linkList.size()) {

						String categoryCode = linkList.get(i);
						String categoryName = linkList.get(i + 1);

						List<String> subLinkList = patientDao.getChildLink(userRole, categoryCode);

						int j = 0;
						int count = 0;
						while (j < subLinkList.size()) {

							String subLinkName = subLinkList.get(j);
							String subLinkPath = subLinkList.get(j + 1);

							if (categoryName.equals(subLinkName)) {
				%>
				<li><a href="<%=subLinkPath%>"><%=subLinkName%></a></li>
				<%
					} else {

								if (count == 0) {
				%>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><%=categoryName%><span class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">

						<%
							}
						%>

						<li><a href="<%=subLinkPath%>"><%=subLinkName%></a></li>




						<%
							}

									j += 2;
									count += 2;
									
								}
						%>

						<%
							if (count == subLinkList.size()) {
						%>
					</ul></li>
				<%
					}
				%>

				<%
					System.out.println("Child link list : " + subLinkList);

						i += 3;
					}
				%>
				<li><a href="#" class="active">Home</a></li>
			</ul>
		</div>
	</div>



	<div class="container">



		<header class="clearfix">
			<span class="slide pull-left"> <a href="#"
				onclick="openSlideMenu()"><i class="fas fa-bars"></i></a>
			</span>


			<h5 class="pull-right">
				<select class="form-control input-sm sel-ward"
					style="display: none;">

				</select>
			</h5>
		</header>

		<table class="table table-striped table-bordered table-hover"
			id="myTable" style="width: 100%">



			<thead>
				<tr>
					<th>IP</th>
					<th>NAME</th>
					<th>WARD</th>
					<th>BED</th>
					<th>ADMN DATE</th>
					<th>ADMN DOCTOR</th>
					<th>SPECIALITY</th>
					<th>CATEGORY</th>
					<th>STATUS</th>
					<th>ACTION</th>
				</tr>
			</thead>


		</table>

	</div>

	<input type="hidden" name="user_id" value="${sessionScope.user}">
	<%@include file="logout-modal.html"%>
	<%@include file="gnrc-common-include-js.html"%>
	<script type="text/javascript" src="js/patient-list.js"></script>
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