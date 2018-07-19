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
							<h5 class="pull-left">INVESTIGATION SHEET</h5>
							<h5 class="pull-right">QR/9-28</h5>
						</header>


						<div class="form-horizontal">


							<div class="form-group">
								<label class="control-label col-xs-2" for="name"><span
									class="required-label" id="name"> Name</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" name="name"
										id="name" value="<%=patient.getPatientName()%>" placeholder="Name" readonly>
								</div>
								<label class="control-label col-xs-1" for="age"><span
									class="required-label" id="age"> Age</span> :</label>
								<div class="col-xs-1">
									<input type="text" class="form-control input-sm" id="age"
										value="<%=patient.getAge()%>" name="age"  placeholder="Age" readonly>
								</div>
								<label class="control-label col-xs-2" for="sex"><span
									class="required-label" id="sex"> Sex</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="sex"
										name="sex" value="<%=patient.getSex()%>" placeholder="Sex" readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="service"><span
									class="required-label" id="service"> Service </span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" id="service"
										name="service" placeholder="Service & Unit" readonly>
								</div>
								<label class="control-label col-xs-1" for="ip-no"><span
									class="required-label" id="ip-no"> IP No</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="ip-no"
										name="ip_no" value="<%=patient.getIpNumber()%>" placeholder="IP Number" readonly>
								</div>
								<label class="control-label col-xs-2" for="bed-no"><span
									class="required-label" id="bed-no"> Bed No</span> :</label>
								<div class="col-xs-1">
									<input type="text" id="bed-no" class="form-control input-sm"
										value="<%=patient.getBedNo()%>" name="bed_no" placeholder="Bed No." readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="from-Date"><span
									class="required-label" id="from-Date"> Date</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm text-readonly" id="fromDate"
										name="from_date" placeholder="Date" readonly>
								</div>
								<label class="control-label col-xs-2" for="blood-group"><span
									class="required-label" id="blood-group"> Blood Group</span> :</label>
								<div class="col-xs-2">
									<select class="form-control input-sm" id="blood-group"
										name="blood_group">
										<option value="A+">A+</option>
										<option value="A-">A+</option>
										<option value="B+">A+</option>
									</select>
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
								<li class="active"><a href="#tab1default" data-toggle="tab">HAEMATOLOGY</a></li>
								<li><a href="#tab2default" data-toggle="tab">BIOCHEMISTRY</a></li>
								<li><a href="#tab3default" data-toggle="tab">CLINICAL
										PATHOLOGY</a></li>
								<li><a href="#tab4default" data-toggle="tab">SEROLOGY</a></li>
							</ul>
						</div>
						<div class="panel-body">
							<div class="tab-content">
								<div class="tab-pane fade in active" id="tab1default">

									<!--  <div class="row"> -->
									<div class="col-md-6 dashboard-left-cell">
										<div class="admin-content-con">
											<header class="clearfix">
												<h5 class="pull-left">HAEMATOLOGY</h5>
											</header>

											<div class="form-horizontal">
												<div class="form-group">
													<label class="control-label col-xs-5" for="speciality">
														Total Count:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="speciality" name="speciality"
															placeholder="Total Count">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="treatment">
														Differential Leucocyte Count:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment"
															placeholder="Leucocyte Count">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Neutrophils:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Neutrophils">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Lymphocytes:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Lymphocytes">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Eosinophils:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Eosinophils">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Monocytes:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Monocytes">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Hb:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>

											</div>
										</div>
									</div>

									<div class="col-md-6 dashboard-right-cell">
										<div class="admin-content-con">
											<header class="clearfix">
												<h5 class="pull-left">HAEMATOLOGY</h5>
											</header>


											<div class="form-horizontal">
												<div class="form-group">
													<label class="control-label col-xs-5" for="visit_no">PCV
														:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="visit_no" name="dt_of_birth" placeholder="PCV">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="visit_date">ESR:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															name="visit_date" id="visit_date" placeholder="ESR">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="appt_no">INR:
													</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															name="appt_number" id="appt_number" placeholder="INR">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="doctor">Prothrombin
														Time: </label>
													<div class="col-xs-3">
														<input type="text" class="form-control input-sm"
															name="doctor" id="doctor" placeholder="Test">
													</div>
													<div class="col-xs-3">
														<input type="text" class="form-control input-sm"
															name="doctor" id="doctor" placeholder="Control">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Total
														Pleteles Count:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment"
															placeholder="Total Pleteles Count">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">MP:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="MP">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Parasite
														Load:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment"
															placeholder="Parasite Load">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Reticulocyte
														Count:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment"
															placeholder="Reticulocyte Count">
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="tab-pane fade" id="tab2default">

									<div class="col-md-6 dashboard-left-cell">
										<div class="admin-content-con">
											<header class="clearfix">
												<h5 class="pull-left">BIOCHEMISTRY</h5>
											</header>

											<div class="form-horizontal">
												<div class="form-group">
													<label class="control-label col-xs-5" for="speciality">
														Protein:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="speciality" name="speciality"
															placeholder="Total Count">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="treatment">
														Albumin:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment"
															placeholder="Leucocyte Count">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">SGOT:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Neutrophils">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">SGPT:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Lymphocytes">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Alkaline
														Phosphatase:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Eosinophils">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Billirubin:</label>
													<div class="col-xs-2">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Monocytes">
													</div>
													<div class="col-xs-2">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Monocytes">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Urea:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Creatinine:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Sugar:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Na+:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">K+:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Ca++:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Mg++:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">NH++:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="speciality">
														Lipid Profile:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="speciality" name="speciality"
															placeholder="Total Count">
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="col-md-6 dashboard-right-cell">
										<div class="admin-content-con">
											<header class="clearfix">
												<h5 class="pull-left">BIOCHEMISTRY</h5>
											</header>


											<div class="form-horizontal">

												<div class="form-group">
													<label class="control-label col-xs-5" for="treatment">
														Total Cholesterol:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment"
															placeholder="Leucocyte Count">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Triglyceride:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Neutrophils">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">LDH:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Lymphocytes">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">LDL:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Eosinophils">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">HDL:</label>
													<div class="col-xs-2">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Monocytes">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">VLDL:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Amylase:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Lipase:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">CPK:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">CRP:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">CKMB:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Trcp
														T:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Procalcitonin:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">T3:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">T4:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">TSH:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
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
												<h5 class="pull-left">CLINICAL PATHOLOGY</h5>
											</header>

											<div class="form-horizontal">
												<div class="form-group">
													<label class="control-label col-xs-5" for="speciality">
														Urine:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="speciality" name="speciality"
															placeholder="Total Count">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="treatment">
														Stool:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment"
															placeholder="Leucocyte Count">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Sputum:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Neutrophils">
													</div>
												</div>
											</div>
										</div>
									</div>




								</div>
								<div class="tab-pane fade" id="tab4default">

									<!--  <div class="row"> -->
									<div class="col-md-6 dashboard-left-cell">
										<div class="admin-content-con">
											<header class="clearfix">
												<h5 class="pull-left">SEROLOGY</h5>
											</header>

											<div class="form-horizontal">
												<div class="form-group">
													<label class="control-label col-xs-5" for="speciality">
														HbsAg:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="speciality" name="speciality"
															placeholder="Total Count">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="treatment">
														HIV:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment"
															placeholder="Leucocyte Count">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">HCV:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Neutrophils">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">HAV:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Lymphocytes">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">HEV:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Eosinophils">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">WIDAL:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Monocytes">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">VDRL:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="Hb">
													</div>
												</div>

											</div>
										</div>
									</div>

									<div class="col-md-6 dashboard-right-cell">
										<div class="admin-content-con">
											<header class="clearfix">
												<h5 class="pull-left">SEROLOGY</h5>
											</header>


											<div class="form-horizontal">
												<div class="form-group">
													<label class="control-label col-xs-5" for="visit_no">HSV
														:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="visit_no" name="dt_of_birth" placeholder="PCV">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="visit_date">JE:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															name="visit_date" id="visit_date" placeholder="ESR">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="appt_no">RADIOLOGY:
													</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															name="appt_number" id="appt_number" placeholder="INR">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="doctor">Chest
														X Ray: </label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															name="doctor" id="doctor" placeholder="Test">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">C.
														T. Scan:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment"
															placeholder="Total Pleteles Count">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">MRI:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment" placeholder="MP">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-xs-5" for="remark">Others:</label>
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="treatment" name="treatment"
															placeholder="Parasite Load">
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