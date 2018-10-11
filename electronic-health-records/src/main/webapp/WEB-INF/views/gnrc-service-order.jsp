<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.gnrchospitals.dto.Patient"%>
<%@page import="com.gnrchospitals.dao.PatientDao"%>
<%@page import="com.gnrchospitals.daoimpl.PatientDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>GNRC</title>
<!-- CSS  -->
<%@include file="gnrc-common-include-css.html"%>
<link rel="stylesheet" href="css/dashboard.css">
<link rel="stylesheet" href="css/create-user.css">
<link rel="stylesheet" href="css/chosen.min.css">
<link rel="stylesheet" href="css/gnrc-forms.css">
<link rel="stylesheet" href="css/circle.css">
<link rel="stylesheet" href="css/service-order.css">
<link rel="icon" href="images/favicon.jpg" type="image/jpeg"
	sizes="16x16" />
<!-- End of CSS -->


<style type="text/css">
.margin-bottom {
	margin-bottom: 15px;
}

.form-inline .form-control.dis-auto-width {
	width: 100%;
	height: 25px;
	padding: 3px 3px;
	
}

input[type="text"].dis-bottom-margin {
	margin-bottom: 0;
	width: 100%;
	height: 25px;
	padding: 3px 3px;
}
</style>


</head>
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

		String ipNumber = (String) session.getAttribute("ipNo") == null ? ""
				: (String) session.getAttribute("ipNo");

		System.out.println("Ip Name : " + ipNumber);

		Enumeration<String> noteDate = request.getParameterNames();

		PatientDao patientDao = new PatientDaoImpl();
		Patient patient = patientDao.findByIpNumber(ipNumber);

		System.out.println("Patient Object " + patient);
	%>


	<!-- Upper Layout -->
	<%@include file="gnrc-page-upper-layout.jsp"%>
	<!-- End of Upper Layout -->

	<!-- User Registration Form -->
	<form id="doctor-note-frm">

		<!-- DASHBOARD -->
		<div id="dashboard-con">


			<div class="row">


				<div class="col-md-6">

					<div class="admin-content-con">

						<div class="row">

							<div class="form-group-sm">
								<div class="col-md-4">
									<label class="control-label" for="sex">Order No</label> <input
										type="text" class="form-control dis-auto-width dis-bottom-margin" id="sex" value="" name="sex"
										placeholder="Order No">
								</div>

								<div class="col-md-4">
									<label class="control-label" for="sex">Patient Type</label> <input
										type="text" class="form-control dis-auto-width dis-bottom-margin" id="sex" value="" name="sex"
										placeholder="Order No">
								</div>
								<div class="col-md-4">
									<label class="control-label" for="pat-type">Referred
										Doctor</label> <input type="text" class="form-control dis-auto-width dis-bottom-margin" id="pat-type"
										value="" name="bed" placeholder="Patient Type">
								</div>
							</div>

						</div>

						<div class="row">
							<div class="form-group-sm">
								<div class="col-md-3">
									<label class="control-label" for="pat-type">Odering
										Date</label> <input type="text" class="form-control dis-auto-width dis-bottom-margin" id="pat-type"
										value="" name="bed" placeholder="Patient Type">
								</div>

								<div class="col-md-5">
									<label class="control-label" for="pat-name">Ward</label> <input
										type="text" class="form-control dis-auto-width dis-bottom-margin" id="pat-name" value=""
										name="sex" placeholder="Name">
								</div>

								<div class="col-md-4">
									<label class="control-label" for="pat-type">Bed</label> <input
										type="text" class="form-control dis-auto-width dis-bottom-margin" id="pat-type" value=""
										name="bed" placeholder="Patient Type">
								</div>
							</div>
						</div>
					</div>
				</div>


				<div class="col-md-6">
					<div class="admin-content-con">
						<div class="row">
							<div class="form-group-sm">
								<div class="col-md-4">
									<label class="control-label">Patient Name</label> <input
										type="text" class="form-control dis-auto-width dis-bottom-margin input-sm" id="pat-name"
										value="" name="sex" placeholder="Name">
								</div>

								<div class="col-md-4">
									<label class="control-label" for="pat-type">Adm Dept.</label> <input
										type="text" class="form-control dis-auto-width dis-bottom-margin" id="pat-type" value=""
										name="bed" placeholder="Patient Type">
								</div>


								<div class="col-md-4">
									<label class="control-label" for="sex">Adm Doctor</label> <input
										type="text" class="form-control dis-auto-width dis-bottom-margin" id="sex" value="" name="sex"
										placeholder="Order No">
								</div>
							</div>
						</div>

						<div class="row">
							<div class="form-group-sm">
								<div class="col-md-3">
									<label class="control-label" for="pat-type">MR No.</label> <input
										type="text" class="form-control dis-auto-width dis-bottom-margin" id="pat-type" value=""
										name="bed" placeholder="Patient Type">
								</div>

								<div class="col-md-3">
									<label class="control-label" for="pat-type">Pat. No.</label> <input
										type="text" class="form-control dis-auto-width dis-bottom-margin" id="pat-type" value=""
										name="bed" placeholder="Patient Type">
								</div>
								<div class="col-md-2">
									<label class="control-label" for="pat-type">Visit No.</label> <input
										type="text" class="form-control dis-auto-width dis-bottom-margin" id="pat-type" value=""
										name="bed" placeholder="Patient Type">
								</div>
								<div class="col-md-2">
									<label class="control-label" for="pat-type">Sex</label> <input
										type="text" class="form-control dis-auto-width dis-bottom-margin" id="pat-type" value=""
										name="bed" placeholder="Patient Type">
								</div>
								<div class="col-md-2">
									<label class="control-label" for="pat-type">Age</label> <input
										type="text" class="form-control dis-auto-width dis-bottom-margin" id="pat-type" value=""
										name="bed" placeholder="Patient Type">
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="col-md-12">
					<div class="admin-content-con">


					<!-- 	<div class="row">


							<div class="form-horizontal">
								<div class="form-group-sm">
									<label class="control-label col-md-1" for="pat-category">Patient
										Cat</label>
									<div class="col-md-3">
										<input type="text" class="form-control input-sm">
									</div>
									<label class="control-label col-md-1" for="pat-category">Pat
										Sub Cat</label>
									<div class="col-md-3">
										<input type="text" class="form-control input-sm">
									</div>
									<div class="col-md-4">
										<select class="form-control input-sm">
											<option value="0">Select Request</option>
										</select>
									</div>
								</div>
							</div>



						</div>
 -->
					<!-- 	<div class="row">

							<div class="col-md-12">
 -->
							

									<table class="table" id="myTable" style="width: 100%">
										<thead>
											<tr>
												<th>Sl. No.</th>
												<th>Service Description</th>
												<th>Qty.</th>
												<th>Rate</th>
												<th>Dis</th>
												<th>Dis Amnt</th>
												<th>Net Amnt</th>
												<th>Spec Desc</th>
											</tr>
										</thead>
										<tbody>
											<tr class="form-group-sm">
												<td>1</td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
											</tr>
											<tr class="form-group-sm">
												<td>2</td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
											</tr>
											<tr class="form-group-sm">
												<td>3</td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
											</tr>
											<tr class="form-group-sm">
												<td>4</td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
											</tr>
											<tr class="form-group-sm">
												<td>5</td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
												<td><input type="text"
													class="form-control dis-auto-width dis-bottom-margin"></td>
											</tr>



										</tbody>



									</table>

								

						<!-- 	</div>
						</div> -->



					</div>
				</div>
			</div>



		</div>



		<!-- End of Dashboard -->
	</form>

	<!-- End of Registration Form -->



	<!-- Form Submit Alert Message -->
	<%@include file="progress-circle-modal.jsp"%>
	<%@include file="confirm-box.html"%>
	<%@include file="alert-box.html"%>
	<%@include file="doctor-note-modal.jsp"%>

	<!-- End of Form Submit Alert Message -->






	<!-- Lower Layout -->
	<%@include file="gnrc-page-lower-layout.jsp"%>
	<!-- End of Lower Layout -->

	<!-- JS -->
	<%@include file="gnrc-common-include-js.html"%>
	<script type="text/javascript" src="js/chosen.jquery.min.js"></script>
	<script type="text/javascript" src="js/dashboard.js"></script>
	<script type="text/javascript" src="js/service-order.js"></script>
	<script type="text/javascript" src="js/moment.js"></script>
	<!-- End of JS -->


	<%@include file="success-error-msg.jsp"%>


</body>
</html>