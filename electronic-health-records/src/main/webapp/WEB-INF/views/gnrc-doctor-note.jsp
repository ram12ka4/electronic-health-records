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
<link rel="stylesheet" href="css/gnrc-user-reg.css">
<link rel="stylesheet" href="css/circle.css">
<link rel="stylesheet" href="css/doctor-note.css">
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
				<div class="col-md-12">
					<div class="admin-content-con">
						<header class="clearfix">
							<h5 class="pull-left">DOCTOR'S NOTES</h5>
							<h5 class="pull-right">QR/9-28</h5>
						</header>


						<div class="form-horizontal">


							<div class="form-group">
								<label class="control-label col-xs-2" for="name"><span
									class="required-label" id="name"> Name</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" id="name"
										value="<%=patient.getPatientName()%>" name="name"
										placeholder="Name" readonly>
								</div>
								<label class="control-label col-xs-1" for="age"><span
									class="required-label" id="age"> Age</span> :</label>
								<div class="col-xs-1">
									<input type="text" class="form-control input-sm"
										value="<%=patient.getAge()%>" id="age" name="age"
										placeholder="Age" readonly>
								</div>
								<label class="control-label col-xs-2" for="sex"><span
									class="required-label" id="sex"> Sex</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="sex"
										value="<%=patient.getSex()%>" name="sex" placeholder="Y"
										readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="service"><span
									class="required-label" id="service"> Service Unit</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" name="service"
										placeholder="Service Unit" readonly>
								</div>
								<label class="control-label col-xs-1" for="bed"><span
									class="required-label" id="bed"> Bed</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="bed"
										value="<%=patient.getBedNo()%>" name="bed"
										placeholder="Date of Birth" readonly>
								</div>
								<label class="control-label col-xs-1" for="ward"><span
									class="required-label" id="ward"> Ward</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" id="ward"
										value="<%=patient.getWardNo()%>" name="ward"
										placeholder="Ward" readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="ip-no"><span
									class="required-label" id="ip-no"> IP No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="ip-no"
										value="<%=patient.getIpNumber()%>" name="ip_no"
										placeholder="IP Number" readonly>
								</div>
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd"> MRD No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="mrd"
										id="mrd" value="<%=patient.getMrdNumber()%>" name="mrd_no"
										placeholder="Date of Birth" readonly>
								</div>
								<label class="control-label col-xs-1" for="note-date"><span
									class="required-label" id="note-date"> Date</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="DN003" placeholder="Date of Birth">
								</div>

							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="doctor-name"><span
									class="required-label" id="doctor-name"> Doctor Name</span> :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm"
										value="${sessionScope.username.username}" name="DN001"
										placeholder="Doctor Name">
									<div class="checkbox">
										<label> <input type="checkbox" name="DN002">
											Visiting Doctor
										</label>
									</div>
								</div>

							</div>



							<div class="form-group">
								<label class="control-label col-xs-2" for="note"><span
									class="required-label" id="note"> Note</span> :</label>
								<div class="col-xs-8">
									<textarea rows="5" cols="" class="form-control input-sm"
										name="DN004" id="note"></textarea>
								</div>

							</div>

							<div class="form-group">
								<div style="padding-right: 16px;" class="pull-right">
									<button type="button" class="btn btn-warning" name="reset">Reset</button>
									<%-- <a href="#myModal" class="btn btn-success" data-id='<%=ipNumber%>' data-toggle="modal">Previous Notes</a> --%>
									<button type="button" class="btn btn-success previousBtn"
										data-id='<%=ipNumber%>'>Previous Notes</button>
									<button type="button" class="btn btn-primary"
										name="doctor_note_submit" id="submit-btn">Submit</button>
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
	<script type="text/javascript" src="js/doctor-note.js"></script>
	<script type="text/javascript" src="js/moment.js"></script>
	<!-- End of JS -->


	<%@include file="success-error-msg.jsp"%>


</body>
</html>