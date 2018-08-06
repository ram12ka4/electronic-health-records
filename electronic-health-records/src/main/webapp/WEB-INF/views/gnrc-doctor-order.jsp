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
<link rel="stylesheet" href="css/gnrc-user-reg.css">
<link rel="stylesheet" href="css/circle.css">
<link rel="stylesheet" href="css/doctor-order.css">
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

		String ipNumber = (String) request.getAttribute("ipName") == null
				? ""
				: (String) request.getAttribute("ipName");
		System.out.println("Ip Name : " + ipNumber);

		try {

			PatientDao patientDao = new PatientDaoImpl();
			Patient patient = patientDao.findByIpNumber(ipNumber);

			if (patient != null) {

				System.out.println("Patient Object " + patient);
	%>


	<!-- Upper Layout -->
	<%@include file="gnrc-page-upper-layout.jsp"%>
	<!-- End of Upper Layout -->


	<!-- User Registration Form -->
	<form id="doctor-order-frm">

		<!-- DASHBOARD -->
		<div id="dashboard-con">
			<div class="row">
				<div class="col-md-12">
					<div class="admin-content-con">
						<header class="clearfix">
							<h5 class="pull-left">DOCTOR'S ORDERS</h5>
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
									<input type="text" class="form-control input-sm" id="age"
										name="age" value="<%=patient.getAge()%>" placeholder="Age"
										readonly>
								</div>
								<label class="control-label col-xs-2" for="sex"><span
									class="required-label" id="sex"> Sex</span> :</label>
								<div class="col-xs-1">
									<input type="text" class="form-control input-sm" id="sex"
										value="<%=patient.getSex()%>" name="sex" placeholder="Sex"
										readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="ip-no"><span
									class="required-label" id="ip-no"> HOSP. No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="ip-no"
										value="<%=patient.getIpNumber()%>" name="ip_no"
										placeholder="Ip Number" readonly>
								</div>
								<label class="control-label col-xs-2" for="service"><span
									class="required-label" id="service"> Service Unit</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="service"
										name="service" placeholder="Service" readonly>
								</div>
								<label class="control-label col-xs-1" for="bed"><span
									class="required-label" id="bed"> Bed</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="bed"
										value="<%=patient.getBedNo()%>" name="bed"
										placeholder="Date of Birth" readonly>
								</div>

							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="ward"><span
									class="required-label" id="ward"> Ward</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" id="ward"
										value="<%=patient.getWardNo()%>" name="ward" placeholder="Y"
										readonly>
								</div>
								<label class="control-label col-xs-1" for="mrd"><span
									class="required-label" id="mrd"> MRD No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="mrd"
										value="<%=patient.getMrdNumber()%>" name="mrd_no"
										placeholder="Last Name" readonly>
								</div>
								<label class="control-label col-xs-1" for="status"><span
									class="required-label"> OCCU</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm occi"
										name="DO001" placeholder="OCCU">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="doctor-name"><span
									class="required-label"> Doctor Name</span> :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm "
										value="${sessionScope.username.username}" name="DO007"
										placeholder="Doctor Name" required>
									<div id="hidden-container"></div>


									<div class="checkbox">
										<label> <input type="checkbox" name="DO008" value="">
											Visiting Doctor
										</label>
									</div>
								</div>
								<label class="control-label col-xs-3" for="date"><span
									class="required-label"> Date</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm " id="fromDate"
										name="date" placeholder="Date" readonly>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<div class="panel with-nav-tabs panel-default">
						<div class="panel-heading">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tab1default" data-toggle="tab">MEDICINE</a></li>
								<li><a href="#tab2default" data-toggle="tab">TREATMENT</a></li>
								<li><a href="#tab3default" data-toggle="tab">DIET</a></li>
								<li><a href="#tab4default" data-toggle="tab">LABORATORY</a></li>
							</ul>
						</div>
						<div class="panel-body">
							<div class="tab-content">
								<div class="tab-pane fade in active" id="tab1default">

									<!--  <div class="row"> -->
									<div class="col-md-12">
										<div class="admin-content-con">
											<header class="clearfix">
												<h5 class="pull-left">MEDICINE</h5>
											</header>

											<div class="form-horizontal input-field-medic-wrap">
												<div class="form-group">
													<div class="col-xs-4">
														<button
															class="btn btn-primary btn-sm add-field-medic-button">Add
															Medicine</button>
													</div>
												</div>
												<div class="form-group ">
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="speciality" name="DO003" placeholder="Medicine">
													</div>
												</div>
											</div>
										</div>
									</div>


								</div>
								<div class="tab-pane fade" id="tab2default">

									<div class="col-md-12">
										<div class="admin-content-con">
											<header class="clearfix">
												<h5 class="pull-left">TREATMENT</h5>
											</header>

											<div class="form-horizontal">
												<div class="form-group">
													<div class="col-xs-12">
														<textarea rows="5" class="form-control input-sm"
															name="DO002"></textarea>
													</div>
												</div>
											</div>
										</div>
									</div>




								</div>
								<div class="tab-pane fade" id="tab3default">

									<!--  <div class="row"> -->
									<div class="col-md-12">
										<div class="admin-content-con">
											<header class="clearfix">
												<h5 class="pull-left">DIET</h5>
											</header>

											<div class="form-horizontal">
												<div class="form-group">
													<div class="col-xs-12">
														<textarea rows="5" class="form-control input-sm"
															name="DO005"></textarea>
													</div>
												</div>
											</div>
										</div>
									</div>




								</div>
								<div class="tab-pane fade" id="tab4default">

									<!--  <div class="row"> -->
									<div class="col-md-12">
										<div class="admin-content-con">
											<header class="clearfix">
												<h5 class="pull-left">LABORATORY</h5>
											</header>

											<div class="form-horizontal input-field-lab-wrap">
												<div class="form-group">
													<div class="col-xs-4">
														<button
															class="btn btn-primary btn-sm add-field-lab-button">Add
															Laboratory</button>
													</div>
												</div>
												<div class="form-group">
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="speciality" name="DO004" placeholder="Laboratory">
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div style="padding-right: 16px;" class="pull-right">
						<button type="button" class="btn btn-warning" name="reset">Reset</button>
						<button type="button" class="btn btn-success previousBtn">Previous
							Orders</button>
						<button type="button" class="btn btn-primary submit-btn"
							name="doctor_order_submit">Submit</button>
					</div>
				</div>
			</div>
		</div>
		<!-- End of Dashboard -->
	</form>

	<!-- End of Registration Form -->

	<%
		} else {
				response.sendRedirect("/login.do");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendRedirect("/login.do");
			//throw ex;
		}
	%>



	<!-- Lower Layout -->
	<%@include file="gnrc-page-lower-layout.jsp"%>
	<%@include file="confirm-box.html"%>
	<%@include file="alert-box.html"%>
	<%@include file="progress-circle-modal.jsp"%>
	<!-- End of Lower Layout -->

	<!-- JS -->
	<%@include file="gnrc-common-include-js.html"%>
	<script type="text/javascript" src="js/chosen.jquery.min.js"></script>
	<script type="text/javascript" src="js/dashboard.js"></script>
	<script type="text/javascript" src="js/doctor-order.js"></script>
	<!-- End of JS -->

	<%@include file="doctor-order-modal.jsp"%>
	<%@include file="success-error-msg.jsp"%>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							var max_fields = 10; //maximum input boxes allowed

							var wrapper = $(".input-field-medic-wrap"); //Fields wrapper
							var add_button = $(".add-field-medic-button"); //Add button ID

							var wrapper2 = $(".input-field-lab-wrap"); //Fields wrapper
							var add_button2 = $(".add-field-lab-button"); //Add button ID

							var x = 1; //initlal text box count
							$(add_button)
									.click(
											function(e) { //on add input button click
												e.preventDefault();
												if (x < max_fields) { //max input box allowed
													x++; //text box increment
													$(wrapper)
															.append(

																	'<div class="form-group "><div class="col-xs-4"><input type="text" class="form-control input-sm" id="speciality" name="DO003" placeholder="Medicine"></div><a href="#" class="remove_field">Remove</a></div>'); //add input box
												}
											});

							$(add_button2)
									.click(
											function(e) { //on add input button click
												e.preventDefault();
												if (x < max_fields) { //max input box allowed
													x++; //text box increment
													$(wrapper2)
															.append(

																	'<div class="form-group "><div class="col-xs-4"><input type="text" class="form-control input-sm" id="speciality" name="DO004" placeholder="Laboratory"></div><a href="#" class="remove_field2">Remove</a></div>'); //add input box
												}
											});

							$(wrapper).on("click", ".remove_field",
									function(e) { //user click on remove text
										e.preventDefault();
										$(this).parent('div').remove();
										x--;
									});

							$(wrapper2).on("click", ".remove_field2",
									function(e) { //user click on remove text
										e.preventDefault();
										$(this).parent('div').remove();
										x--;
									});
						});
	</script>



</body>
</html>