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
<link rel="stylesheet" href="css/doctor-note-order.css">
<link rel="stylesheet" href="css/vital-chart.css">
<link rel="icon" href="images/favicon.jpg" type="image/jpeg"
	sizes="16x16" />
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	response.setHeader("Expires", "0"); // proxies
	int timeout = session.getMaxInactiveInterval();
	System.out.println("Vital Chart session time : " + timeout);
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
	<form id="vital-chart-frm">

		<!-- DASHBOARD -->
		<div id="dashboard-con">


			<div class="row">


				<div class="col-md-12">

					<div class="admin-content-con">

						<div class="row">

							<div class="form-group-sm">
								<div class="col-md-2">
									<label class="control-label" for="order-id">Vital Chart
										No</label> <input type="text"
										class="form-control dis-auto-width dis-bottom-margin"
										id="vital-no" value="" name="vitalNo" placeholder="Vital No"
										readonly="readonly">
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
								</div>
							</div>
						</div>



						<div class="row">
							<div class="col-md-12 panel-top-margin">

								<div class="panel with-nav-tabs panel-default">
									<div class="panel-heading">
										<ul class="nav nav-tabs">
											<li class="active"><a href="#tab1default"
												data-toggle="tab">EYES OPEN</a></li>
											<li><a href="#tab2default" data-toggle="tab">VERBAL
													RESPONSE</a></li>
											<li><a href="#tab3default" data-toggle="tab">MOTOR
													RESPONSE</a></li>
											<li><a href="#tab4default" data-toggle="tab">BLOOD
													PRESSURE</a></li>
											<li><a href="#tab5default" data-toggle="tab">PUPILS</a></li>
										</ul>
									</div>
									<div class="panel-body">
										<div class="tab-content">
											<div class="tab-pane fade in active" id="tab1default">

												<!--  <div class="row"> -->
												<div class="col-md-3 dashboard-left-cell">
													<!-- <div class="admin-content-con"> -->
													<header class="clearfix">
														<h5 class="pull-left">Eyes Open</h5>
													</header>

													<div class="form-horizontal">
														<input type="hidden" name="EYOP" value="EYOP">
														<div class="radio">
															<label> <input type="radio" name="eyesOpen"
																class="eyesOpen" value="Spontaneously" checked>
																Spontaneously
															</label>
														</div>


														<div class="radio">
															<label> <input type="radio" name="eyesOpen"
																class="eyesOpen" value="To Speech"> To Speech
															</label>
														</div>


														<div class="radio">
															<label> <input type="radio" name="eyesOpen"
																class="eyesOpen" value="To Pain" checked> To
																Pain
															</label>
														</div>
														<div class="radio">
															<label> <input type="radio" name="eyesOpen"
																class="eyesOpen" value="None"> None
															</label>

														</div>

														<!-- 	<div class="radio">
															<label> <input type="radio" name="optionsRadios"
																id="optionsRadios2" value="Eyes closed by swelling = c"> Eyes closed
																by swelling = c
															</label>
														</div> -->

													</div>
													<!-- 	</div> -->
												</div>
												<div class="col-md-9 dashboard-right-cell">
													<table
														class="table table-striped table-bordered table-hover"
														id="eyesOpenTable" style="width: 100%">
														<thead>
															<tr>
																<th>S/N</th>
																<th>Date</th>
																<th>Time</th>
																<th>Eyes Open</th>
																<th>Created By</th>
															</tr>
														</thead>

													</table>
												</div>
											</div>

											<div class="tab-pane fade" id="tab2default">

												<div class="col-md-3 dashboard-left-cell">
													<!-- <div class="admin-content-con"> -->
													<header class="clearfix">
														<h5 class="pull-left">Best Verbal Response</h5>
													</header>
													<input type="hidden" name="VERE" value="VERE">
													<div class="radio">
														<label> <input type="radio" name="verbalResponse"
															class="verbalResponse" value="Oriented" checked>
															Oriented
														</label>
													</div>


													<div class="radio">
														<label> <input type="radio" name="verbalResponse"
															class="verbalResponse" value="Confused"> Confused
														</label>
													</div>


													<div class="radio">
														<label> <input type="radio" name="verbalResponse"
															class="verbalResponse" value="Inappropriate Words"
															checked> Inappropriate Words
														</label>
													</div>


													<div class="radio">
														<label> <input type="radio" name="verbalResponse"
															class="verbalResponse" value="Incomprehensible Sounds">
															Incomprehensible Sounds
														</label>

													</div>

													<div class="radio">
														<label> <input type="radio" name="verbalResponse"
															id="verbalResponse" value="None"> None
														</label>
													</div>

													<div class="radio">
														<label> <input type="radio" name="verbalResponse"
															class="verbalResponse"
															value="Endotracheal tube or tracheostomy = T">
															Endotracheal tube or tracheostomy = T
														</label>
													</div>
													<!-- 	</div> -->
												</div>
												<div class="col-md-9 dashboard-right-cell">
													<table
														class="table table-striped table-bordered table-hover"
														id="verbalResTable" style="width: 100%">
														<thead>
															<tr>
																<th>S/N</th>
																<th>Date</th>
																<th>Time</th>
																<th>Verbal Response</th>
																<th>Created By</th>
															</tr>
														</thead>
													</table>
												</div>

											</div>
											<div class="tab-pane fade" id="tab3default">
												<div class="col-md-3 dashboard-left-cell">
													<header class="clearfix">
														<h5 class="pull-left">Best Motor Response</h5>
													</header>
													<input type="hidden" name="MORE" value="MORE">
													<div class="radio">
														<label> <input type="radio" name="motorResponse"
															class="motorResponse" value="Obey Commands" checked>
															Obey Commands
														</label>
													</div>


													<div class="radio">
														<label> <input type="radio" name="motorResponse"
															class="motorResponse" value="Localize Pain">
															Localize Pain
														</label>
													</div>


													<div class="radio">
														<label> <input type="radio" name="motorResponse"
															class="motorResponse" value="Felxion to Pain" checked>
															Felxion to Pain
														</label>
													</div>


													<div class="radio">
														<label> <input type="radio" name="motorResponse"
															class="motorResponse" value="Abnormal Flexion">
															Abnormal Flexion
														</label>

													</div>

													<div class="radio">
														<label> <input type="radio" name="motorResponse"
															class="motorResponse" value="Extension to Pain">
															Extension to Pain
														</label>

													</div>

													<div class="radio">
														<label> <input type="radio" name="motorResponse"
															class="motorResponse" value="None"> None
														</label>
													</div>
												</div>

												<div class="col-md-9 dashboard-right-cell">
													<table
														class="table table-striped table-bordered table-hover"
														id="motorResTable" style="width: 100%">
														<thead>
															<tr>
																<th>S/N</th>
																<th>Date</th>
																<th>Time</th>
																<th>Motor Response</th>
																<th>Created By</th>
															</tr>
														</thead>
													</table>
												</div>

											</div>
											<div class="tab-pane fade" id="tab4default">
												<!--  <div class="row"> -->
												<div class="col-md-3 dashboard-left-cell">
													<!-- <div class="admin-content-con"> -->
													<header class="clearfix">
														<h5 class="pull-left">Blood Pressure</h5>
													</header>

													<div class="form-horizontal">
														<div class="form-group">
															<label class="control-label col-xs-5" for="name"><span
																class="required-label" id="name">Systolic</span> :</label>
															<div class="col-xs-5">
																<input type="hidden" name="BLPS" value="BLPS"> <select
																	class="form-control select-form-control input-sm systolic"
																	name="systolic">
																	<option value="10">10</option>
																	<option value="20">20</option>
																	<option value="30">30</option>
																	<option value="40">40</option>
																	<option value="50">50</option>
																	<option value="60">60</option>
																	<option value="70">70</option>
																	<option value="80">80</option>
																	<option value="90">90</option>
																	<option value="100">100</option>
																	<option value="110">110</option>
																	<option value="120">120</option>
																	<option value="130">130</option>
																	<option value="140">140</option>
																	<option value="150">150</option>
																	<option value="160">160</option>
																	<option value="170">170</option>
																	<option value="180">180</option>
																	<option value="190">190</option>
																	<option value="200">200</option>
																	<option value="210">210</option>
																	<option value="220">220</option>
																	<option value="230">230</option>
																	<option value="240">240</option>
																</select>
															</div>
														</div>
														<div class="form-group">
															<label class="control-label col-xs-5" for="name"><span
																class="required-label" id="name">Diastolic</span> :</label>

															<div class="col-xs-5">
																<input type="hidden" name="BLPD" value="BLPD"> <select
																	class="form-control select-form-control input-sm diastolic"
																	name="diastolic">
																	<option value="10">10</option>
																	<option value="20">20</option>
																	<option value="30">30</option>
																	<option value="40">40</option>
																	<option value="50">50</option>
																	<option value="60">60</option>
																	<option value="70">70</option>
																	<option value="80">80</option>
																	<option value="90">90</option>
																	<option value="100">100</option>
																	<option value="110">110</option>
																	<option value="120">120</option>
																	<option value="130">130</option>
																	<option value="140">140</option>
																	<option value="150">150</option>
																	<option value="160">160</option>
																	<option value="170">170</option>
																	<option value="180">180</option>
																	<option value="190">190</option>
																	<option value="200">200</option>
																	<option value="210">210</option>
																	<option value="220">220</option>
																	<option value="230">230</option>
																	<option value="240">240</option>
																</select>
															</div>
														</div>
													</div>
													<!-- </div> -->
												</div>
												<div class="col-md-9 dashboard-right-cell">
													<table
														class="table table-striped table-bordered table-hover"
														id="bpTable" style="width: 100%">
														<thead>
															<tr>
																<th>S/N</th>
																<th>Date</th>
																<th>Time</th>
																<th>Diastolic</th>
																<th>Systolic</th>
																<th>Created By</th>
															</tr>
														</thead>
													</table>
												</div>
											</div>

											<div class="tab-pane fade" id="tab5default">

												<!--  <div class="row"> -->
												<div class="col-md-3 dashboard-left-cell">
													<!-- <div class="admin-content-con"> -->
													<header class="clearfix">
														<h5 class="pull-left">Pupils</h5>
													</header>

													<div class="form-horizontal">
														<div class="form-group-sm">
															<label class="control-label col-xs-5" for="name"><span
																class="required-label" id="name">Left Pupil</span> </label> <label
																class="control-label col-xs-5" for="name"><span
																class="required-label" id="name">Right Pupil</span></label>
														</div>
														<div class="form-group">
															<label class="control-label col-xs-2" for="name"><span
																class="required-label" id="name"></span> </label>
															<div class="col-xs-6">
																<input type="hidden" name="PUPL" value="PUPL"> <select
																	class="form-control left-pupil-close-open"
																	name="leftPupilCloseOPen">
																	<option value="+">&plus;</option>
																	<option value="-">&minus;</option>
																	<option value="C">C</option>
																</select>
															</div>
															<div class="col-xs-6">
																<input type="hidden" name="PUPR" value="PUPR"> <select
																	class="form-control right-pupil-close-open"
																	name="rightPupilCloseOPen">
																	<option value="+">&plus;</option>
																	<option value="-">&minus;</option>
																	<option value="C">C</option>
																</select>
															</div>
														</div>
														<div class="form-group">
															<div class="col-xs-6">
																<select class="form-control left-pupil-size"
																	name="leftPupilSize">
																	<option value="S">S</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 5px;'></i>&nbsp;1"
																		value="1" selected>1</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 10px;'></i>&nbsp;2"
																		value="2">2</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 15px;'></i>&nbsp;3"
																		value="3">3</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 20px;'></i>&nbsp;4"
																		value="4">4</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 25px;'></i>&nbsp;5"
																		value="5">5</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 30px;'></i>&nbsp;6"
																		value="6">6</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 35px;'></i>&nbsp;7"
																		value="7">7</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 40px;'></i>&nbsp;8"
																		value="8">8</option>
																</select>
															</div>
															<div class="col-xs-6">
																<select class="form-control right-pupil-size"
																	name="rightPupilSize">
																	<option value="S">S</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 5px;'></i>&nbsp;1"
																		value="1" selected>1</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 10px;'></i>&nbsp;2"
																		value="2">2</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 15px;'></i>&nbsp;3"
																		value="3">3</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 20px;'></i>&nbsp;4"
																		value="4">4</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 25px;'></i>&nbsp;5"
																		value="5">5</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 30px;'></i>&nbsp;6"
																		value="6">6</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 35px;'></i>&nbsp;7"
																		value="7">7</option>
																	<option
																		data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 40px;'></i>&nbsp;8"
																		value="8">8</option>
																</select>
															</div>
														</div>
													</div>
												</div>
												<!-- 	</div> -->

												<div class="col-md-9 dashboard-right-cell">
													<table
														class="table table-striped table-bordered table-hover"
														id="pupilTable" style="width: 100%">
														<thead>
															<tr>
																<th>S/N</th>
																<th>Date</th>
																<th>Time</th>
																<th>Left Pupil</th>
																<th>Right Pupil</th>
																<th>Created By</th>
															</tr>
														</thead>
													</table>
												</div>

											</div>
										</div>
									</div>
								</div>

							</div>



						</div>




						<div class="row">
							<div class="form-horizontal">
								<div class="form-group-sm">
									<div class="col-xs-offset-9 button-right-offset">
										<button type="button" id="btn-reset"
											class="btn btn-primary btn-sm">Reset</button>
										<button type="button" id="btn-submit"
											class="btn btn-success btn-sm">Submit</button>
									</div>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>



		</div>

	<input type="hidden" name="formName" id="frm-name" value="${sessionScope.moduleName}">

		<!-- End of Dashboard -->
	</form>

	<!-- End of Registration Form -->



	<!-- Form Submit Alert Message -->
	<%@include file="progress-circle-modal.jsp"%>
	<%@include file="confirm-box.html"%>
	<%@include file="alert-box.html"%>
	<%-- <%@include file="doctor-note-modal.jsp"%> --%>

	<!-- End of Form Submit Alert Message -->






	<!-- Lower Layout -->
	<%@include file="gnrc-page-lower-layout.jsp"%>
	<!-- End of Lower Layout -->

	<!-- JS -->
	<%@include file="gnrc-common-include-js.html"%>
	<script type="text/javascript" src="js/chosen.jquery.min.js"></script>
	<script type="text/javascript" src="js/dashboard.js"></script>
	<script type="text/javascript" src="js/vital-chart.js"></script>
	<script type="text/javascript" src="js/moment.js"></script>
	<!-- End of JS -->

	<%@include file="gnrc-modal.jsp"%>
	<%@include file="success-error-msg.jsp"%>



</body>
</html>