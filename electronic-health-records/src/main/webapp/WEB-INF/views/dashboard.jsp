<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
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

</head>
<body>
	<p>
		<br/>
	</p>
	<div class="container">
		<h1>Data Table</h1>
		<table class="table table-striped table-bordered table-hover"
			id="myTable">
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Email</th>
					<th>Phone</th>
					<th>Address</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Email</th>
					<th>Phone</th>
					<th>Address</th>
				</tr>
			</tfoot>
			<tbody>
				<tr>
					<td>8169766072</td>
					<td>Ram</td>
					<td>ram.kumar.basak@gmail.com</td>
					<td>8169766072</td>
					<td>Maligaon</td>
				</tr>
				<tr>
					<td>8169766072</td>
					<td>Ram</td>
					<td>ram.kumar.basak@gmail.com</td>
					<td>8169766072</td>
					<td>Maligaon</td>
				</tr>
				<tr>
					<td>8169766072</td>
					<td>Ram</td>
					<td>ram.kumar.basak@gmail.com</td>
					<td>8169766072</td>
					<td>Maligaon</td>
				</tr>
				<tr>
					<td>8169766072</td>
					<td>Ram</td>
					<td>ram.kumar.basak@gmail.com</td>
					<td>8169766072</td>
					<td>Chariali</td>
				</tr>
			</tbody>
		</table>
	</div>
	<%@include file="gnrc-common-include-js.html"%>
	<script>
		$("#myTable").DataTable();
	</script>
</body>
</html>