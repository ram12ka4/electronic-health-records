<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.gnrchospitals.dto.Patient"%>
<jsp:useBean id="patientDao"
	class="com.gnrchospitals.daoimpl.PatientDaoImpl"
	type="com.gnrchospitals.dao.PatientDao" scope="request"></jsp:useBean>
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
<link rel="stylesheet" href="css/pharmacy-return.css">
<link rel="icon" href="images/favicon.jpg" type="image/jpeg"
	sizes="16x16" />
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	response.setHeader("Expires", "0"); // proxies
	int timeout = session.getMaxInactiveInterval();
	System.out.println("Pharmacy Return session time : " + timeout);
	response.setHeader("Refresh", timeout + "; URL = logout.do");
%>
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

div.table-container {
	width: 100%;
}
</style>



</head>
<body>

	<%
		String ipNumber = (String) request.getAttribute("ipNumber") == null ? ""
				: (String) request.getAttribute("ipNumber");
		System.out.println("Patient Number  : " + ipNumber);
		Patient patient = patientDao.findByIpNumber(ipNumber);
		System.out.println("Patient Object " + patient);
	%>


	<!-- Upper Layout -->
	<%@include file="gnrc-page-upper-layout.jsp"%>
	<!-- End of Upper Layout -->

	<!-- User Registration Form -->
	<form id="pharmacy-order-frm">

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
										id="order-id" value="" name="orderNo" placeholder="Order No">
								</div>
								<div class="col-md-3">
									<label class="control-label" for="refer-doctor">Referred
										Doctor</label> <input type="text"
										class="form-control dis-auto-width dis-bottom-margin"
										id="refer-doctor" value="" name="referDoctor"
										placeholder="Refer Doctor"><input type="hidden"
										name="referDocId" id="refDocId">
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

									<div class="col-md-offset-9 left-margin">
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
						<div class="table-container">
							<table class="table" id="myTable" style="width: 100%">
								<thead>
									<tr>
										<th>S/N</th>
										<th>Item Name</th>
										<th>Batch</th>
										<th>Issued Qty.</th>
										<th>Prev. Ret. Qty.</th>
										<th>Return Qty.</th>
										<th>Voucher No.</th>
										<th>Remark</th>
										<th>Action</th>
									</tr>
								</thead>
							</table>
						</div>


						<hr>

						<div class="row">
							<div class="col-md-offset-9 left-margin-sub-reset">
								<button type="button" id="btn-reset"
									class="btn btn-warning btn-sm btn-reset">Reset</button>
								<button type="button" id="btn-submit"
									class="btn btn-success btn-sm btn-submit">Submit</button>
							</div>
						</div>
					</div>
				</div>
			</div>





		</div>

		<input type="hidden" name="voucherNumber" id="voucher-id" /> <input
			type="hidden" name="chkBoxFlag" id="chk-box-flag" />
			<input type="hidden" name="formName" id="frm-name" value="${sessionScope.moduleName}">
			

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
	<script type="text/javascript" src="js/pharmacy-return.js"></script>
	<script type="text/javascript" src="js/moment.js"></script>
	<!-- End of JS -->




</body>
</html>