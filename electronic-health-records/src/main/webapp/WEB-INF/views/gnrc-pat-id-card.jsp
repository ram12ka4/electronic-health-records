<%@page import="com.gnrchospitals.dto.Patient"%>
<%@page import="com.gnrchospitals.dao.PatientDao"%>
<%@page import="com.gnrchospitals.daoimpl.PatientDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<link rel="icon" href="images/favicon.jpg" type="image/jpeg"
	sizes="16x16" />
<!-- End of CSS -->
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

		String ipNumber = (String) request.getAttribute("ipName") == null ? ""
				: (String) request.getAttribute("ipName");
		System.out.println("Ip Name : " + ipNumber);

		PatientDao patientDao = new PatientDaoImpl();
		Patient patient = patientDao.findByIpNumber(ipNumber);

		System.out.println("Patient Object " + patient);
	%>

	<!-- Upper Layout -->
	<%@include file="gnrc-page-upper-layout.jsp"%>
	<!-- End of Upper Layout -->


	<!-- User Registration Form -->
	<form action="" method="post">

		<!-- DASHBOARD -->
		<div id="dashboard-con">
			<div class="row">
				<div class="col-md-12">
					<div class="admin-content-con">
						<header class="clearfix">
							<h5 class="pull-left">PATIENT IDENTIFICATION CARD</h5>
							<h5 class="pull-right">QR/14-16</h5>
						</header>


						<div class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">MRD Number</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" id="mrd"
										value="<%=patient.getMrdNumber()%>" name="mrd_number"
										placeholder="MRD Number" readonly>
								</div>
								<label class="control-label col-xs-2" for="ip-num"><span
									class="required-label" id="ip">IP Number</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" id="ip"
										name="ip_number" value="<%=patient.getIpNumber()%>"
										placeholder="IP Number" readonly>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-xs-2" for="title"><span
									class="required-label" id="pat-name"> Patient Name</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" id="pat-name"
										value="<%=patient.getPatientName()%>" name="pat_name"
										placeholder="First Name" readonly>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-xs-2" for="age"><span
									class="required-label" id="age"> Age</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" name="age"
										value="<%=patient.getAge()%>" placeholder="Age" readonly>
								</div>
								<label class="control-label col-xs-2" for="sex"><span
									class="required-label"> Sex</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="sex"
										name="sex" value="<%=patient.getSex()%>" placeholder="Sex"
										readonly>
								</div>
								<label class="control-label col-xs-2" for="weight"><span
									class="required-label"> Weight</span> :</label>
								<div class="col-xs-1">
									<input type="text" class="form-control input-sm" id="weight"
										value="" name="weight" placeholder="Weight">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="dt-of-admit"><span
									class="required-label" id="dt-of-admit"> Date of
										Admission</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm"
										name="dt_of_admit" value="<%=patient.getAdmissionDate()%>"
										placeholder="Date of Admission" readonly>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-xs-2" for="doctor"><span
									class="required-label" id="doctor"> Consultant</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" name="doctor"
										value="<%=patient.getDoctorIncharge()%>" placeholder="Doctor"
										readonly>
								</div>

							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="status"><span
									class="required-label" id="gender"> Clinical Diagnosis</span> :</label>
								<div class="col-xs-5">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
								</div>
							</div>

						</div>
					</div>
				</div>


			</div>





			<div class="row">
				<div class="col-md-12">
					<div class="admin-content-con">
						<div class="form-horizontal">
							<div class="form-group">
								<div style="padding-right: 16px;" class="pull-right">
									<input type="reset" class="btn btn-default" value="Reset">
									<input type="submit" class="btn btn-primary" value="Submit">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- End of Dashboard -->
	</form>

	<!-- End of Registration Form -->


	<!-- Lower Layout -->
	<%@include file="gnrc-page-lower-layout.jsp"%>
	<!-- End of Lower Layout -->



	<!-- JS -->
	<%@include file="gnrc-common-include-js.html"%>
	<script type="text/javascript" src="js/chosen.jquery.min.js"></script>
	<script type="text/javascript" src="js/dashboard.js"></script>
	<!-- End of JS -->

</body>
</html>