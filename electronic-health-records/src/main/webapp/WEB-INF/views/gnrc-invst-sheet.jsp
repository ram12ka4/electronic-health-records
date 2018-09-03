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
<link rel="stylesheet" href="css/gnrc-forms.css">
<link rel="stylesheet" href="css/circle.css">
<link rel="stylesheet" href="css/invest-sheet.css">
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
	<form id="invest-frm">

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
										id="name" value="<%=patient.getPatientName()%>"
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
										name="sex" value="<%=patient.getSex()%>" placeholder="Sex"
										readonly>
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
										name="ip_no" value="<%=patient.getIpNumber()%>"
										placeholder="IP Number" readonly>
								</div>
								<label class="control-label col-xs-2" for="bed-no"><span
									class="required-label" id="bed-no"> Bed No</span> :</label>
								<div class="col-xs-1">
									<input type="text" id="bed-no" class="form-control input-sm"
										value="<%=patient.getBedNo()%>" name="bed_no"
										placeholder="Bed No." readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="from-Date"><span
									class="required-label" id="from-Date"> Date</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm text-readonly"
										id="fromDate" name="from_date" placeholder="Date" readonly>
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
							<h5 class="pull-right"><select class="form-control input-sm sel-prev-record"
									style="display: none;"></select></h5>
						</header>
						<div class="panel with-nav-tabs panel-default">
							<div class="panel-heading">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#tab0default"
										data-toggle="tab">BLOOD GROUP</a></li>
									<li><a href="#tab1default" data-toggle="tab">HAEMATOLOGY</a></li>
									<li><a href="#tab2default" data-toggle="tab">BIOCHEMISTRY</a></li>
									<li><a href="#tab3default" data-toggle="tab">CLINICAL
											PATHOLOGY</a></li>
									<li><a href="#tab4default" data-toggle="tab">SEROLOGY</a></li>
									<li><a href="#tab5default" data-toggle="tab">RADIOLOGY</a></li>
								</ul>
							</div>
							<div class="panel-body">
								<div class="tab-content">
									<div class="tab-pane fade in active" id="tab0default">

										<!--  <div class="row"> -->
										<div class="col-md-12">
											<div class="admin-content-con">
												<header class="clearfix">
													<h5 class="pull-left">BLOOD GROUP</h5>
												</header>

												<div class="form-horizontal">
													<div class="form-group">
														<label class="control-label col-xs-2" for="blood-group">Blood
															Group:</label>
														<div class="col-xs-2">
															<select class="form-control input-sm myValue"
																id="blood-group" name="IS016">
															</select>
														</div>
													</div>
												</div>
											</div>
										</div>




									</div>
									<div class="tab-pane fade" id="tab1default">

										<!--  <div class="row"> -->
										<div class="col-md-6 dashboard-left-cell">
											<div class="admin-content-con">
												<header class="clearfix">
													<h5 class="pull-left">HAEMATOLOGY</h5>
												</header>

												<div class="form-horizontal">
													<div class="form-group">
														<label class="control-label col-xs-5" for="total-count">
															Total Count:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="total-count" name="IS001" placeholder="Total Count">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5"
															for="leucocyte-count"> Differential Leucocyte
															Count:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="leucocyte-count" name="IS002"
																placeholder="Leucocyte Count">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="neutrophils">Neutrophils:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="neutrophils" name="IS003" placeholder="Neutrophils">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="lymphocytes">Lymphocytes:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="Lymphocytes" name="IS004" placeholder="Lymphocytes">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="eosinophils">Eosinophils:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="eosinophils" name="IS005" placeholder="Eosinophils">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="monocytes">Monocytes:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="monocytes" name="IS006" placeholder="Monocytes">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="hb">Hb:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="hb" name="IS007" placeholder="Hb">
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
														<label class="control-label col-xs-5" for="pcv">PCV
															:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="pcv" name="IS008" placeholder="PCV">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="esr">ESR:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																name="IS009" id="esr" placeholder="ESR">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="inr">INR:
														</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																name="IS010" id="inr" placeholder="INR">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="prothrombin">Prothrombin
															Time: </label>
														<div class="col-xs-3">
															<input type="text" class="form-control input-sm myValue"
																name="IS011" id="prothrombin-test" placeholder="Test">
														</div>
														<div class="col-xs-3">
															<input type="text" class="form-control input-sm myValue"
																name="IS065" id="prothrombin-control"
																placeholder="Control">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="pleteles-count">Total
															Pleteles Count:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="pleteles-count" name="IS012"
																placeholder="Total Pleteles Count">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="mp">MP:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="mp" name="IS013" placeholder="MP">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="parasite-load">Parasite
															Load:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="parasite-load" name="IS014"
																placeholder="Parasite Load">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5"
															for="reticulocyte-count">Reticulocyte Count:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="reticulocyte-count" name="IS015"
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
														<label class="control-label col-xs-5" for="protein">
															Protein:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="Protein" name="IS017" placeholder="Protein">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="albumin">
															Albumin:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="albumin" name="IS018" placeholder="Albumin">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="sgot">SGOT:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="sgot" name="IS019" placeholder="SGOT">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="sgpt">SGPT:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="sgpt" name="IS020" placeholder="SGPT">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="alkaline">Alkaline
															Phosphatase:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="alkaline" name="IS021"
																placeholder="Alkaline Phosphatase">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5"
															for="billirubin-direct">Billirubin:</label>
														<div class="col-xs-2">
															<input type="text" class="form-control input-sm myValue"
																id="billirubin" name="IS022" placeholder="Direct">
														</div>
														<div class="col-xs-2">
															<input type="text" class="form-control input-sm myValue"
																id="billirubin-indirect" name="IS066"
																placeholder="Indirect">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="urea">Urea:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="urea" name="IS023" placeholder="Urea">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="creatinine">Creatinine:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="creatinine" name="IS024" placeholder="Creatinine">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="sugar">Sugar:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="sugar" name="IS025" placeholder="Sugar">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="na">Na+:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="na" name="IS026" placeholder="Na+">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="k">K+:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="k" name="IS027" placeholder="K+">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="ca">Ca++:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="ca" name="IS028" placeholder="Ca++">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="mg">Mg++:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="mg" name="IS029" placeholder="Mg++">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="nh">NH++:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="nh" name="IS030" placeholder="NH++">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="lipid-profile">
															Lipid Profile:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="lipid-profile" name="IS031"
																placeholder="Lipid Profile">
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
														<label class="control-label col-xs-5"
															for="total-cholesterol"> Total Cholesterol:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="total-cholesterol" name="IS032"
																placeholder="Total Cholesterol">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="triglyceride">Triglyceride:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="triglyceride" name="IS033"
																placeholder="Triglyceride">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="ldh">LDH:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="ldh" name="IS034" placeholder="LDH">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="ldl">LDL:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="ldl" name="IS035" placeholder="LDL">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="hdl">HDL:</label>
														<div class="col-xs-2">
															<input type="text" class="form-control input-sm myValue"
																id="hdl" name="IS036" placeholder="HDL">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="vldl">VLDL:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="vldl" name="IS037" placeholder="VLDL">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="amylase">Amylase:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="amylase" name="IS038" placeholder="Amylase">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="lipase">Lipase:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="lipase" name="IS039" placeholder="Lipase">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="cpk">CPK:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="cpk" name="IS040" placeholder="CPK">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="crp">CRP:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="crp" name="IS041" placeholder="CRP">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="ckmb">CKMB:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="ckmb" name="IS042" placeholder="CKMB">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="trcp-t">Trcp
															T:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="trcp-t" name="IS043" placeholder="Trcp-T">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="procalcitonin">Procalcitonin:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="procalcitonin" name="IS044"
																placeholder="Procalcitonin">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="t3">T3:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="t3" name="IS045" placeholder="T3">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="t4">T4:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="t4" name="IS046" placeholder="T4">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="tsh">TSH:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="tsh" name="IS047" placeholder="TSH">
														</div>
													</div>
												</div>
											</div>

										</div>


									</div>
									<div class="tab-pane fade" id="tab3default">

										<!--  <div class="row"> -->
										<div class="col-md-6 dashboard-left-cell">
											<div class="admin-content-con">
												<header class="clearfix">
													<h5 class="pull-left">CLINICAL PATHOLOGY</h5>
												</header>
												<div class="form-horizontal">
													<div class="form-group">
														<label class="control-label col-xs-5" for="urine">
															Urine:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="urine" name="IS048" placeholder="Urine">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="Stool">
															Stool:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="Stool" name="IS049" placeholder="Stool">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="sputum">Sputum:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="sputum" name="IS050" placeholder="Sputum">
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
														<label class="control-label col-xs-5" for="hbsag">
															HbsAg:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="hbsag" name="IS051" placeholder="HbsAg">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="hiv">
															HIV:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="hiv" name="IS052" placeholder="HIV">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="hcv">HCV:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm myValue"
																id="hcv" name="IS053" placeholder="HCV">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="hav">HAV:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm" id="hav"
																name="IS054" placeholder="HAV">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="hev">HEV:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm" id="hev"
																name="IS055" placeholder="HEV">
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
														<label class="control-label col-xs-5" for="widal">WIDAL:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm"
																id="widal" name="IS056" placeholder="WIDAL">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="vdrl">VDRL:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm"
																id="vdrl" name="IS057" placeholder="VDRL">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="hsv">HSV
															:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm" id="hsv"
																name="IS058" placeholder="HSV">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="je">JE:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm"
																name="IS059" id="je" placeholder="JE">
														</div>
													</div>
												</div>
											</div>
										</div>

									</div>

									<div class="tab-pane fade" id="tab5default">

										<!--  <div class="row"> -->
										<div class="col-md-6 dashboard-left-cell">
											<div class="admin-content-con">
												<header class="clearfix">
													<h5 class="pull-left">RADIOLOGY</h5>
												</header>

												<div class="form-horizontal">

													<div class="form-group">
														<label class="control-label col-xs-5" for="chest-x-ray">Chest
															X Ray: </label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm"
																name="IS061" id="chest-x-ray" placeholder="Chest X Ray">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="c-t-scan">C.
															T. Scan:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm"
																id="c-t-scan" name="IS062" placeholder="C.T. Scan">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="mri">MRI:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm" id="mri"
																name="IS063" placeholder="MRI">
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="others">Others:</label>
														<div class="col-xs-4">
															<input type="text" class="form-control input-sm"
																id="others" name="IS064" placeholder="Others">
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
			</div>

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
	<script type="text/javascript" src="js/invest-sheet.js"></script>
	<!-- End of JS -->


	<%@include file="gnrc-modal.jsp"%>
	<%@include file="success-error-msg.jsp"%>


</body>
</html>