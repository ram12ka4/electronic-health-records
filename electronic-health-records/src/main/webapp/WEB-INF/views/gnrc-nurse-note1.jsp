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
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="css/circle.css">
<link rel="stylesheet" href="css/nurse-note.css">
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
			
				<div class="col-lg-8">
					<div class="admin-content-con">
						<header class="clearfix">
							<h5 class="pull-left">NURSE'S NOTES</h5>
							<h5 class="pull-right">QR/14-4</h5>
						</header>
						<textarea id="txtEditor"></textarea>
					</div>
				</div>
			
				<div class="col-lg-4">
					<div class="admin-content-con">
						<header class="clearfix">
							<h5 class="pull-left">NURSE'S NOTES</h5>
							<h5 class="pull-right">QR/14-4</h5>
						</header>

						<div class="form-group">
							<label class="control-label" for="name">Name :</label>
							
								<input type="text" class="form-control input-sm" id="name"
									value="<%=patient.getPatientName()%>" name="name"
									placeholder="Name" readonly>
						</div>


						<div class="form-horizontal">

							<div class="form-group">
								<label class="control-label col-xs-2" for="sex">Sex :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" id="sex"
										value="<%=patient.getSex()%>" name="sex" placeholder="Y"
										readonly>
								</div>
								<label class="control-label col-xs-2" for="bed">Bed :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" id="bed"
										value="<%=patient.getBedNo()%>" name="bed"
										placeholder="Date of Birth" readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="mrd"><span
									class="required-label" id="mrd"> MRD No.</span> :</label>
								<div class="col-xs-9">
									<input type="text" class="form-control input-sm" id="mrd"
										id="mrd" value="<%=patient.getMrdNumber()%>" name="mrd_no"
										placeholder="Date of Birth" readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="ip-no"><span
									class="required-label" id="ip-no"> IP No.</span> :</label>
								<div class="col-xs-9">
									<input type="text" class="form-control input-sm" id="ip-no"
										value="<%=patient.getIpNumber()%>" name="ip_no"
										placeholder="IP Number" readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="note-date"><span
									class="required-label" id="note-date"> Date</span> :</label>
								<div class="col-xs-9">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="DN003" placeholder="Date of Birth">
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-xs-3" for="note-date"><span
									class="required-label" id="note-date"> Time</span> :</label>
								<div class="col-xs-9">
									<input type="text" class="form-control input-sm" id="timepicker"
										name="DN003" placeholder="Date of Birth">
								</div>
							</div>
						
							<div class="form-group">
								<div class="col-lg-10 col-lg-offset-7">
										<button type="submit" class="btn btn-primary btn-sm">Reset</button>
									<button type="submit" class="btn btn-success btn-sm">Submit</button>
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
	<script type="text/javascript" src="js/nurse-note.js"></script>
	<script type="text/javascript" src="js/moment.js"></script>
	<!-- End of JS -->


	<%@include file="success-error-msg.jsp"%>


</body>
</html>