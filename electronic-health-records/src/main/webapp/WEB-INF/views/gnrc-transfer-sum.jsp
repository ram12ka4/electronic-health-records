<%@page import="com.gnrchospitals.dto.Patient"%>
<%@page import="com.gnrchospitals.dao.PatientDao"%>
<%@page import="com.gnrchospitals.daoimpl.PatientDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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
	<form id="transfer-frm">

		<!-- DASHBOARD -->
		<div id="dashboard-con">
			<div class="row">
				<div class="col-md-12">
					<div class="admin-content-con">
						<header class="clearfix">
							<h5 class="pull-left">TRANSFER SUMMARY</h5>
							<h5 class="pull-right">QR/28-1</h5>
						</header>


						<div class="form-horizontal">


							<div class="form-group">
								<label class="control-label col-xs-1" for="name"><span
									class="required-label" id="name"> Name</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" id="name"
										value="<%=patient.getPatientName()%>" name="name"
										placeholder="Name" readonly>
								</div>
								<label class="control-label col-xs-1" for="mrd"><span
									class="required-label" id="mrd"> MRD No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="mrd"
										value="<%=patient.getMrdNumber()%>" name="mrd_no"
										placeholder="MRD" readonly>
								</div>
								<label class="control-label col-xs-1" for="ip-no"><span
									class="required-label" id="ip-no"> IP No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="ip-no"
										value="<%=patient.getIpNumber()%>" name="ip_no"
										placeholder="IP Number" readonly>
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
							<h5 class="pull-left">TRANSFER SUMMARY</h5>
							<div class="form-group pull-right">
								<select class="form-control input-sm sel-prev-record"
									style="display: none;"></select>
							</div>

						</header>


						<div class="form-horizontal">


							<div class="form-group">
								<label class="control-label col-xs-3" for="prov-final-diag"><span
									id="gender"> Provisional / Final Diagnosis</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										id="prov-final-diag" name="TS001"
										placeholder="Provisional/Final Diagnosis">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="transfer-from"><span
									id="transfer-from">Transferred From</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										id="transfer-from" name="TS002" placeholder="Transferred From">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="transfer-date-time"><span
									id="transfer-date-time"> Transfer Date/Time</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="TS003" placeholder="Transfer Date/Time">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="transfer-cause"><span
									id="transfer-cause"> Cause of Transfer</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" name="TS004"
										placeholder="Cause of Transfer">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="transfer-doctor"><span
									id="transfer-doctor"> Name of Transfer Doctor</span>:</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										id="transfer-doctor" name="TS005"
										placeholder="Name of Transfer Doctor">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="col-md-6 dashboard-left-cell">
					<div class="admin-content-con">
						<header class="clearfix">
							<h5 class="pull-left">Vitals</h5>
						</header>
						<div class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-xs-5" for="pulse">Pulse:</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" id="pulse"
										name="TS006" placeholder="Pulse">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="temp">Temp :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" name="TS007"
										id="temp" placeholder="Temperature">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="itb-nitb">ITB/NITB
									: </label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" name="TS008"
										id="itb-nitb" placeholder="ITB/NITB">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="chest">Chest
									: </label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" name="TS009"
										id="chest" placeholder="Chest">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="u-cath">U.
									Cath. :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" id="u-cath"
										name="TS010" placeholder="Urinary Catheter">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6 dashboard-right-cell">
					<div class="admin-content-con">
						<header class="clearfix">
							<h5 class="pull-left">Vitals</h5>
						</header>
						<div class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-xs-5" for="blood-pressure">Blood
									Pressure :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm"
										id="blood-pressure" name="TS011" placeholder="Blood Pressure">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="r-rate">R.Rate
									:</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" name="TS012"
										id="r-rate" placeholder="Respiratory Rate">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="v-nv">V/NV :
								</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" name="TS013"
										id="v-nv" placeholder="V/NV">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="cvs">CVS : </label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" name="TS014"
										id="cvs" placeholder="CVS">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="gcs">GCS :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" id="gcs"
										name="TS015" placeholder="GCS">
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
							<h5 class="pull-left">Vitals</h5>
						</header>

						<div class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-xs-3" for="previous-medication">Important
									Previous Medications :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm"
										id="previous-medication" name="TS016"
										placeholder="Important	Previous Medications">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="medical-problem">Other
									associated medical problems:</label>
								<div class="col-xs-3">
									<button class="btn btn-primary btn-sm add-field-button">Add</button>
								</div>
							</div>
							<div class="form-horizontal input-field-wrap">
								 <div class="form-group">
									<div class="col-xs-3"></div>
									<div class="col-xs-3">
										<input type="text"
											class="form-control input-sm medical-problem"
											id="medical-problem" name="TS017"
											placeholder="Medical Problems">
									</div>
								</div>  
							</div>



						</div>

					</div>
				</div>
			</div>

			<div id="hidden-container"></div>


			<div class="row">
				<div class="col-md-12">
					<div class="admin-content-con">
						<div class="form-horizontal">
							<div class="form-group">
								<div style="padding-right: 16px;" class="pull-right add-btn">
									<button type="button" class="btn btn-warning reset-btn">Reset</button>
									<%-- <a href="#myModal" class="btn btn-success" data-id='<%=ipNumber%>' data-toggle="modal">Previous Notes</a> --%>
									<button type="button" class="btn btn-success previous-btn"
										data-id='<%=ipNumber%>'>Previous Summary</button>
									<button type="button" class="btn btn-primary submit-btn">Submit</button>
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
	<script type="text/javascript" src="js/transfer-sum.js"></script>
	<!-- End of JS -->

	<%@include file="gnrc-modal.jsp"%>
	<%@include file="success-error-msg.jsp"%>


</body>
</html>