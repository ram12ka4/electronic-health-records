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

<style>
.modal {
	text-align: center;
	padding: 0 !important;
}

.modal:before {
	content: '';
	display: inline-block;
	height: 100%;
	vertical-align: middle;
	margin-right: -4px;
}

.modal-dialog {
	display: inline-block;
	text-align: left;
	vertical-align: middle;
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

		String ipNumber = (String) request.getAttribute("ipNumber") == null ? ""
				: (String) request.getAttribute("ipNumber");

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
	<form id="service-order-frm">

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
										id="order-id" value="" name="orderNo" placeholder="Order No"
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
										placeholder="Refer Doctor">
								</div>
								<div class="col-md-2">
									<label class="control-label" for="patient-name">Patient
										Name</label> <input type="text"
										class="form-control dis-auto-width dis-bottom-margin input-sm"
										id="patient-name" value="<%=patient.getPatientName()%>"
										name="patientName" placeholder="Name" readonly="readonly">
								</div>

								<div class="col-md-2">
									<label class="control-label" for="speciality">Speciality</label>
									<input type="text"
										class="form-control dis-auto-width dis-bottom-margin"
										id="admin-dept" value="<%=patient.getSpeciality()%>"
										name="speciality" placeholder="Speciality" readonly="readonly">
								</div>


								<div class="col-md-3">
									<label class="control-label" for="consultant">Consultant</label>
									<input type="text"
										class="form-control dis-auto-width dis-bottom-margin"
										id="admin-doctor" value="<%=patient.getDoctorIncharge()%>"
										name="consultant" placeholder="Consultant" readonly="readonly">
									<input type="hidden" value="<%=patient.getDoctorId()%>"
										name="doctorId">
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
										id="ward" value="<%=patient.getWardDesc()%>" name="ward"
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
										<input type="hidden" name="patCategoryCode"
											value="<%=patient.getPatientCategoryCode()%>"> <input
											type="text" value="<%=patient.getPatientCategory()%>"
											class="form-control dis-auto-width dis-bottom-margin"
											readonly="readonly">
									</div>
									<label class="control-label col-md-1" for="pat-category">Pat
										Sub Cat</label>
									<div class="col-md-2">
										<input type="hidden" name="patSubCategoryCode"
											value="<%=patient.getPatientSubCategoryCode()%>"> <input
											type="text" value="<%=patient.getPatientSubCategory()%>"
											class="form-control dis-auto-width dis-bottom-margin input-sm"
											readonly="readonly">
									</div>
									<div class="col-md-3">
										<select class="form-control select-box" name="serviceCategory">
											<option value="0">Select Request</option>
										</select>
									</div>
									<div class="col-md-3">
										<button type="button"
											class="btn btn-primary btn-sm btn-pat-history">Pat.
											History</button>
										<button type="button"
											class="btn btn-primary btn-sm btn-row-add">Add Row</button>
										<button type="button"
											class="btn btn-primary btn-sm btn-previous"
											data-id='<%=ipNumber%>'>Prev Order</button>
									</div>

								</div>
							</div>



						</div>

						<hr>

						<table class="table" id="myTable" style="width: 100%">
							<thead>
								<tr>
									<th>S/N</th>
									<th>Service Description</th>
									<th>Qty.</th>
									<th>Rate</th>
									<th>Dis(%)</th>
									<th>Dis Amnt</th>
									<th>Net Amnt</th>
									<th>Specimen/Treated by</th>
									<th>Action</th>
								</tr>
							</thead>
						</table>

						<hr>

						<!-- <div class="row">
							<div class="form-horizontal">
								<div class="form-group-sm">
									<label class="control-label col-md-1" for="gross-amount">Gross</label>
									<div class="col-md-2">
										<input type="text" name="grossAmount"
											class="form-control dis-auto-width dis-bottom-margin gross-text-right"
											id="sum-gross-amount">
									</div>
									<label class="control-label col-md-1" for="discount">Discount</label>
									<div class="col-md-2">
										<input type="text" name="totalDiscount"
											class="form-control dis-auto-width dis-bottom-margin gross-text-right"
											id="sum-discount-amount">
									</div>
									<label class="control-label col-md-1" for="net-amount">Net</label>
									<div class="col-md-2">
										<input type="text" name="totalNetAmount"
											class="form-control dis-auto-width dis-bottom-margin gross-text-right"
											id="sum-net-amount">
									</div>
									<div class="col-xs-offset-10 button-right-offset">
										<button type="button" id="btn-reset" class="btn btn-primary btn-sm">Reset</button>
										<button type="button" id="btn-submit" class="btn btn-success btn-sm">Submit</button>
									</div>

								</div>
							</div>
						</div> -->
						<div class="row">

							<label id="total-label" for="pat-category">Total : </label> <input
								type="text" name="grossAmount"
								class="form-control dis-auto-width dis-bottom-margin gross-text-right"
								id="sum-gross-amount" readonly="readonly"> <input
								type="text" name="totalDiscount"
								class="form-control dis-auto-width dis-bottom-margin gross-text-right"
								id="sum-discount-amount" readonly="readonly"> <input
								type="text" name="totalNetAmount"
								class="form-control dis-auto-width dis-bottom-margin gross-text-right"
								id="sum-net-amount" readonly="readonly">

							<button type="button" id="btn-reset"
								class="btn btn-primary btn-reset">Reset</button>
							<button type="button" id="btn-submit"
								class="btn btn-success btn-submit">Submit</button>
						</div>








					</div>
				</div>
			</div>


			<div
				class="customizer border-left-blue-grey border-left-lighten-4 d-none d-xl-block open">
				<a class="customizer-close" href="#"> <i
					class="fas fa-times font-medium-3"></i>
				</a> <a class="customizer-toggle bg-danger box-shadow-3" href="#"> <i
					class="fas fa-cog font-medium-3 spinner white"></i>
				</a>
				<div
					class="customizer-content p-2 ps-container ps-theme-dark ps-active-y">
					<h4 class="text-uppercase text-center">Patient History</h4>
					<h5 class="text-uppercase text-center doc-note"></h5>
					<h5 class="text-uppercase text-center note-date"></h5>
					<h5 class="text-uppercase text-center refer-doctor"></h5>

					<hr>
					<div class="auto-scrollbar-container">
						<div class="content">
							<div class="form-group custom-form-group">
								<label for="treatment">Advice</label>
								<textarea name="treatment" class="form-control" id="treatment"></textarea>
							</div>
							<div class="form-group custom-form-group">
								<label for="medication">Medication</label>
								<textarea name="medication" class="form-control" id="medication"></textarea>
							</div>
							<div class="form-group custom-form-group">
								<label for="laboratory">Laboratory</label>
								<textarea name="laboratory" class="form-control" id="laboratory"></textarea>
							</div>
							<div class="form-group custom-form-group">
								<label for="diet">Diet</label>
								<textarea name="diet" class="form-control" id="diet"></textarea>
							</div>
							<div class="form-group custom-form-group">
								<label for="progress">Progress</label>
								<textarea name="progress" class="form-control" id="progress"></textarea>
							</div>
						</div>
					</div>
				</div>
			</div>



		</div>

		<input type="hidden" name="voucherNumber" id="voucher-id" />
		<input type="hidden" name="chkBoxFlag" id="chk-box-flag" />

		<!-- End of Dashboard -->
	</form>

	<!-- End of Registration Form -->

	<%@include file="gnrc-modal.jsp"%>
	<%-- <%@include file="success-error-msg.jsp"%> --%>


	<!-- Form Submit Alert Message -->
	
	<%-- <%@include file="confirm-box.html"%> --%>
	<%-- <%@include file="alert-box.html"%> --%>
	

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

	


</body>
</html>