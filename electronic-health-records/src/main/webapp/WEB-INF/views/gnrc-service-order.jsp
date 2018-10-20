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

.form-group-sm select.form-control.select-box {
	height: 25px;
	line-height: 25px;
	padding: 2px 10px;
}

.col-xs-offset-10.button-right-offset {
	margin-left: 88.33333333%;
}

.center-block {
	display: block;
	margin-left: auto;
	margin-right: auto;
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

		String ipNumber = (String)request.getAttribute("ipNumber") == null ? "" : (String)request.getAttribute("ipNumber");

		System.out.println("Patient Number  : " + ipNumber);

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

						<div class="row">

							<div class="form-group-sm">
								<div class="col-md-2">
									<label class="control-label" for="order-id">Order No</label> <input
										type="text"
										class="form-control dis-auto-width dis-bottom-margin"
										id="order-id" value="" name="order" placeholder="Order No"
										readonly="readonly">
								</div>

								<!-- <div class="col-md-1">
									<label class="control-label" for="patient-type">Pat Type</label> <input
										type="text"
										class="form-control dis-auto-width dis-bottom-margin" id="patient-type"
										value="" name="patientType" placeholder="Patient Type" readonly="readonly">
								</div> -->
								<div class="col-md-3">
									<label class="control-label" for="refer-doctor">Referred
										Doctor</label> <input type="text"
										class="form-control dis-auto-width dis-bottom-margin"
										id="refer-doctor" value="" name="referDoctor"
										placeholder="Refer Doctor" readonly="readonly">
								</div>
								<div class="col-md-2">
									<label class="control-label" for="patient-name">Patient
										Name</label> <input type="text"
										class="form-control dis-auto-width dis-bottom-margin input-sm"
										id="patient-name" value="<%=patient.getPatientName()%>"
										name="patientName" placeholder="Name" readonly="readonly">
								</div>

								<div class="col-md-2">
									<label class="control-label" for="admin-dept">Speciality</label>
									<input type="text"
										class="form-control dis-auto-width dis-bottom-margin"
										id="admin-dept" value="<%=patient.getSpeciality()%>"
										name="adminDept" placeholder="Admin Dept" readonly="readonly">
								</div>


								<div class="col-md-3">
									<label class="control-label" for="admin-doctor">Consultant</label>
									<input type="text"
										class="form-control dis-auto-width dis-bottom-margin"
										id="admin-doctor" value="<%=patient.getDoctorIncharge()%>"
										name="adminDoctor" placeholder="Adm Doctor"
										readonly="readonly">
								</div>
							</div>

						</div>

						<div class="row">
							<div class="form-group-sm">
								<div class="col-md-2">
									<label class="control-label" for="order-date">Order
										Date</label> <input type="text"
										class="form-control dis-auto-width dis-bottom-margin"
										id="fromDate" value="" name="orderDate"
										placeholder="Ordering Date" readonly="readonly">
								</div>

								<div class="col-md-2">
									<label class="control-label" for="ward">Ward</label> <input
										type="text"
										class="form-control dis-auto-width dis-bottom-margin"
										id="ward" value="<%=patient.getWardNo()%>" name="ward"
										placeholder="Ward" readonly="readonly">
								</div>

								<div class="col-md-1">
									<label class="control-label" for="bed">Bed</label> <input
										type="text"
										class="form-control dis-auto-width dis-bottom-margin" id="bed"
										value="<%=patient.getBedNo()%>" name="bed" placeholder="Bed"
										readonly="readonly">
								</div>
								<div class="col-md-2">
									<label class="control-label" for="mrd">MR No.</label> <input
										type="text"
										class="form-control dis-auto-width dis-bottom-margin" id="mrd"
										value="<%=patient.getMrdNumber()%>" name="mrd"
										placeholder="Mrd" readonly="readonly">
								</div>

								<div class="col-md-2">
									<label class="control-label" for="patient-no">Pat. No.</label>
									<input type="text"
										class="form-control dis-auto-width dis-bottom-margin"
										id="patient-no" value="<%=patient.getIpNumber()%>"
										name="patientNo" placeholder="Patient No" readonly="readonly">
								</div>
								<div class="col-md-1">
									<label class="control-label" for="visit-no">Visit No.</label> <input
										type="text"
										class="form-control dis-auto-width dis-bottom-margin"
										id="visit-no" value="" name="visitNo" placeholder="Visit No"
										readonly="readonly">
								</div>
								<div class="col-md-1">
									<label class="control-label" for="sex">Sex</label> <input
										type="text"
										class="form-control dis-auto-width dis-bottom-margin" id="sex"
										value="<%=patient.getSex()%>" name="sex" placeholder="Sex"
										readonly="readonly">
								</div>
								<div class="col-md-1">
									<label class="control-label" for="age">Age</label> <input
										type="text"
										class="form-control dis-auto-width dis-bottom-margin" id="age"
										value="<%=patient.getAge()%>" name="age" placeholder="Age"
										readonly="readonly">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="col-md-12">
					<div class="admin-content-con">


						<div class="row">


							<div class="form-horizontal">
								<div class="form-group-sm">
									<label class="control-label col-md-1" for="pat-category">Patient
										Cat</label>
									<div class="col-md-2">
										<input type="hidden" name="patCategoryCode" value="<%=patient.getPatientCategoryCode()%>">
										<input type="text" value="<%=patient.getPatientCategory()%>"
											class="form-control dis-auto-width dis-bottom-margin" readonly="readonly">
									</div>
									<label class="control-label col-md-1" for="pat-category">Pat
										Sub Cat</label>
									<div class="col-md-2">
										<input type="hidden" name="patSubCategoryCode" value="<%=patient.getPatientSubCategoryCode()%>">
										<input type="text" value="<%=patient.getPatientSubCategory()%>"
											class="form-control dis-auto-width dis-bottom-margin input-sm" readonly="readonly">
									</div>
									<div class="col-md-3">
										<select class="form-control select-box">
											<option value="0">Select Request</option>
										</select>
									</div>
									<div class="col-md-3">
										<button type="button" class="btn btn-primary btn-sm">Add Service</button>
										<button type="button" class="btn btn-primary btn-sm row-add">Add Row</button>
										<button type="button" class="btn btn-primary btn-sm previousBtn"
										data-id='<%=ipNumber%>'>Prev Order</button>
									</div>
									
								</div>
							</div>



						</div>



						<table class="table" id="myTable" style="width: 100%">
							<thead>
								<tr>
									<th>S/N</th>
									<th>Service Description</th>
									<th>Qty.</th>
									<th>Rate</th>
									<th>Dis</th>
									<th>Dis Amnt</th>
									<th>Net Amnt</th>
									<th>Spec Desc</th>
									<th>Action</th>
								</tr>
							</thead>
						</table>

						<div class="row">
							<div class="form-horizontal">
								<div class="form-group-sm">
									<label class="control-label col-md-2" for="pat-category">Gross
										Amount</label>
									<div class="col-md-2">
										<input type="text"
											class="form-control dis-auto-width dis-bottom-margin">
									</div>
									<label class="control-label col-md-1" for="pat-category">Discount</label>
									<div class="col-md-1">
										<input type="text"
											class="form-control dis-auto-width dis-bottom-margin input-sm">
									</div>
									<label class="control-label col-md-2" for="pat-category">Net
										Amount</label>
									<div class="col-md-2">
										<input type="text"
											class="form-control dis-auto-width dis-bottom-margin input-sm">
									</div>

								</div>
							</div>
						</div>
						<div class="row">

							<div class="col-xs-offset-10 button-right-offset">
								<button type="reset" class="btn btn-primary btn-sm">Reset</button>
								<button type="submit" class="btn btn-success btn-sm">Submit</button>
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
	<script type="text/javascript" src="js/service-order.js"></script>
	<script type="text/javascript" src="js/moment.js"></script>
	<!-- End of JS -->


	<%@include file="success-error-msg.jsp"%>


</body>
</html>