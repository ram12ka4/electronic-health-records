<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="images/favicon.jpg" type="image/jpeg"
	sizes="16x16" />
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<%@include file="gnrc-common-include-css.html"%>
<link rel="stylesheet" type="text/css" href="css/login.css">
<title>GNRC</title>
</head>
<body>


	<div class="container">
		<div class="login-container">
			<div id="output"></div>
			<div class="avatar"></div>
			<div class="form-box">
				<form id="index">
					<input name="user" type="text" placeholder="Username"> <input
						type="password" placeholder="Password" name="password"> <select
						data-placeholder="" name="location" id="location"
						class="form-control">
						<option value="0">Select Location</option>
						<option value="DIS">GNRC Dispur</option>
						<option value="SIX">GNRC Six Mile</option>
						<option value="NGM">GNRC North Guwahati</option>
						<option value="BRS">GNRC Barasat</option>
						<option value="TST">GNRC TEST</option>
					</select>
					<button class="btn btn-info btn-block login" name="index_submit">Login</button>
				</form>
			</div>
		</div>

	</div>
	<%@include file="gnrc-common-include-js.html"%>
	<script type="text/javascript" src="js/login.js"></script>
	<%
		String msg = (String) request.getAttribute("msg") == null ? "" : (String) request.getAttribute("msg");

		if (!"".equals(msg)) {
	%>
	<script>
	$(document).ready(function() {
				$("#output").addClass("alert alert-danger animated fadeInUp")
							.html('<%=msg%>');
				});
	</script>
	<%
		}
	%>
</body>
</html>