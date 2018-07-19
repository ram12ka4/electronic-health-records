<%@page import="com.gnrchospitals.dto.Patient"%>
<%@page import="com.gnrchospitals.dao.PatientDao"%>
<%@page import="com.gnrchospitals.daoimpl.PatientDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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
<link rel="stylesheet" href="css/gnrc-user-reg.css">
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
							<h5 class="pull-left">CONSULTATION RECORD</h5>

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
									<input type="text" class="form-control input-sm" id="age"
										value="<%=patient.getAge()%>" name="age" placeholder="Age"
										readonly>
								</div>
								<label class="control-label col-xs-2" for="sex"><span
									class="required-label" id="sex"> Sex</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="sex"
										value="<%=patient.getSex()%>" name="sex" placeholder="Sex"
										readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="mar-status"><span
									class="required-label" id="mar-status"> Marital Satus</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										id="mar-status" value="<%=patient.getMaritalStatus()%>"
										name="mar_status" placeholder="Marital Status" readonly>
								</div>
								<label class="control-label col-xs-1" for="service"><span
									class="required-label" id="service"> Service</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="service"
										name="service" placeholder="Service" readonly>
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
								<label class="control-label col-xs-2" for="bed"><span
									class="required-label" id="bed"> BED No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="bed"
										value="<%=patient.getBedNo()%>" name="bed_no"
										placeholder="Bed No" readonly>
								</div>
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd"> MRD No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="mrd"
										value="<%=patient.getMrdNumber()%>" name="mrd_no"
										placeholder="Date of Birth" readonly>
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
								<label class="control-label col-xs-2" for="status"><span
									class="required-label"> Religion Status</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="dt_of_birth" placeholder="Date of Birth" required>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="col-md-12">
					<div class="admin-content-con">
						<header class="clearfix">
							<h5 class="pull-left">CONSULTATION RECORD</h5>

						</header>


						<div class="form-horizontal">

							<div class="form-group">
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">Referred by Dr. </span> :</label>
								<div class="col-xs-3">
									<select data-placeholder="" name="title" id="title"
										class="form-control input-sm">
										<option value="MR">MR</option>
										<option value="MR">MRS</option>
										<option value="MR">DR</option>
									</select>
								</div>
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">Consultant & Speciality</span>
									:</label>
								<div class="col-xs-3">
									<select data-placeholder="" name="title" id="title"
										class="form-control input-sm">
										<option value="MR">MR</option>
										<option value="MR">MRS</option>
										<option value="MR">DR</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">Referred to Dr. </span> :</label>
								<div class="col-xs-3">
									<select data-placeholder="" name="title" id="title"
										class="form-control input-sm">
										<option value="MR">MR</option>
										<option value="MR">MRS</option>
										<option value="MR">DR</option>
									</select>
								</div>
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">Consultant & Speciality</span>
									:</label>
								<div class="col-xs-3">
									<select data-placeholder="" name="title" id="title"
										class="form-control input-sm">
										<option value="MR">MR</option>
										<option value="MR">MRS</option>
										<option value="MR">DR</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">Date</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="dt_of_birth" placeholder="Date of Birth" required>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">Brief clinical notes<br />and
										reasons for referral
								</span> :</label>
								<div class="col-xs-5">
									<textarea rows="5" class="form-control input-sm"></textarea>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">Consultant's opinion<br />and
										recommendations
								</span> :</label>
								<div class="col-xs-5">
									<textarea rows="5" class="form-control input-sm"></textarea>
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