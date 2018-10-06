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

		String ipNumber = (String) session.getAttribute("ipNo") == null ? ""
				: (String) session.getAttribute("ipNo");

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




				<div class="col-md-9">
					<div class="admin-content-con">
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
										<div class="col-md-12">
											<div class="admin-content-con">
												<header class="clearfix">
													<h5 class="pull-left">Eyes Open</h5>
												</header>

												<div class="form-horizontal">

													<div class="radio">
														<label> <input type="radio" name="optionsRadios"
															id="optionsRadios1" value="option1" checked>
															Spontaneously
														</label>
													</div>


													<div class="radio">
														<label> <input type="radio" name="optionsRadios"
															id="optionsRadios2" value="option2"> To Speech
														</label>
													</div>


													<div class="radio">
														<label> <input type="radio" name="optionsRadios"
															id="optionsRadios1" value="option1" checked> To
															Pain
														</label>
													</div>
													<div class="radio">
														<label> <input type="radio" name="optionsRadios"
															id="optionsRadios2" value="option2"> None
														</label>

													</div>

													<div class="radio">
														<label> <input type="radio" name="optionsRadios"
															id="optionsRadios2" value="option2"> Eyes closed
															by swelling = c
														</label>
													</div>

												</div>
											</div>
										</div>
									</div>

									<div class="tab-pane fade" id="tab2default">

										<div class="col-md-6 dashboard-left-cell">
											<div class="admin-content-con">
												<header class="clearfix">
													<h5 class="pull-left">Best Verbal Response</h5>
												</header>

												<div class="radio">
													<label> <input type="radio" name="optionsRadios"
														id="optionsRadios1" value="option1" checked>
														Oriented
													</label>
												</div>


												<div class="radio">
													<label> <input type="radio" name="optionsRadios"
														id="optionsRadios2" value="option2"> Confused
													</label>
												</div>


												<div class="radio">
													<label> <input type="radio" name="optionsRadios"
														id="optionsRadios1" value="option1" checked>
														Inappropriate Words
													</label>
												</div>


												<div class="radio">
													<label> <input type="radio" name="optionsRadios"
														id="optionsRadios2" value="option2">
														Incompressible Sounds
													</label>

												</div>

												<div class="radio">
													<label> <input type="radio" name="optionsRadios"
														id="optionsRadios2" value="option2"> None
													</label>
												</div>

												<div class="radio">
													<label> <input type="radio" name="optionsRadios"
														id="optionsRadios2" value="option2"> Endotracheal
														tube or tracheostomy = T
													</label>
												</div>
											</div>
										</div>



									</div>
									<div class="tab-pane fade" id="tab3default">
										<!--  <div class="row"> -->
										<div class="col-md-6 dashboard-left-cell">
											<div class="admin-content-con">
												<header class="clearfix">
													<h5 class="pull-left">Best Motor Response</h5>
												</header>

												<div class="radio">
													<label> <input type="radio" name="optionsRadios"
														id="optionsRadios1" value="option1" checked> Obey
														Commands
													</label>
												</div>


												<div class="radio">
													<label> <input type="radio" name="optionsRadios"
														id="optionsRadios2" value="option2"> Localize Pain
													</label>
												</div>


												<div class="radio">
													<label> <input type="radio" name="optionsRadios"
														id="optionsRadios1" value="option1" checked>
														Felxion to Pain
													</label>
												</div>


												<div class="radio">
													<label> <input type="radio" name="optionsRadios"
														id="optionsRadios2" value="option2"> Abnormal
														Flexion
													</label>

												</div>

												<div class="radio">
													<label> <input type="radio" name="optionsRadios"
														id="optionsRadios2" value="option2"> Extension to
														Pain
													</label>

												</div>

												<div class="radio">
													<label> <input type="radio" name="optionsRadios"
														id="optionsRadios2" value="option2"> None
													</label>
												</div>
											</div>
										</div>




									</div>
									<div class="tab-pane fade" id="tab4default">
										<!--  <div class="row"> -->
										<div class="col-md-6 dashboard-left-cell">
											<div class="admin-content-con">
												<header class="clearfix">
													<h5 class="pull-left">Blood Pressure</h5>
												</header>

												<div class="form-horizontal">
													<div class="form-group">
														<label class="control-label col-xs-5" for="name"><span
															class="required-label" id="name">Systolic</span> :</label>
														<div class="col-xs-5">
															<select class="form-control input-sm">
																<option value="10">10</option>
																<option value="10">20</option>
																<option value="10">30</option>
																<option value="10">40</option>
																<option value="10">50</option>
																<option value="10">60</option>
																<option value="10">70</option>
																<option value="10">80</option>
																<option value="10">90</option>
																<option value="10">100</option>
																<option value="10">110</option>
																<option value="10">120</option>
																<option value="10">130</option>
																<option value="10">140</option>
																<option value="10">150</option>
																<option value="10">160</option>
																<option value="10">170</option>
																<option value="10">180</option>
																<option value="10">190</option>
																<option value="10">200</option>
																<option value="10">210</option>
																<option value="10">220</option>
																<option value="10">230</option>
																<option value="10">240</option>
															</select>
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-5" for="name"><span
															class="required-label" id="name">Diastolic</span> :</label>
														<div class="col-xs-5">
															<select class="form-control input-sm">
																<option value="10">10</option>
																<option value="10">20</option>
																<option value="10">30</option>
																<option value="10">40</option>
																<option value="10">50</option>
																<option value="10">60</option>
																<option value="10">70</option>
																<option value="10">80</option>
																<option value="10">90</option>
																<option value="10">100</option>
																<option value="10">110</option>
																<option value="10">120</option>
																<option value="10">130</option>
																<option value="10">140</option>
																<option value="10">150</option>
																<option value="10">160</option>
																<option value="10">170</option>
																<option value="10">180</option>
																<option value="10">190</option>
																<option value="10">200</option>
																<option value="10">210</option>
																<option value="10">220</option>
																<option value="10">230</option>
																<option value="10">240</option>
															</select>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="tab-pane fade" id="tab5default">

										<!--  <div class="row"> -->
										<div class="col-md-12">
											<div class="admin-content-con">
												<header class="clearfix">
													<h5 class="pull-left">Pupils</h5>
												</header>

												<div class="form-horizontal">
													<div class="form-group">
														<label class="control-label col-xs-5" for="name"><span
															class="required-label" id="name">Right Pupil</span> </label> <label
															class="control-label col-xs-5" for="name"><span
															class="required-label" id="name">Left Pupil</span></label>
													</div>
													<div class="form-group">
														<label class="control-label col-xs-2" for="name"><span
															class="required-label" id="name"></span> </label>
														<div class="col-xs-4">
															<select class="form-control selectpicker">
																<option value="+">&plus;</option>
																<option value="-">&minus;</option>
																<option value="s">S</option>
															</select>
														</div>
														<div class="col-xs-4">
															<select class="form-control selectpicker">
																<option value="+">&plus;</option>
																<option value="-">&minus;</option>
																<option value="s">S</option>
															</select>
														</div>
													</div>
													<div class="form-group">
														<div class="col-xs-6">
															<select class="form-control selectpicker">
																<option value="c">C</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 5px;'></i>&nbsp;1"
																	value="1">1</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 10px;'></i>&nbsp;2"
																	value="2">2</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 15px;'></i>&nbsp;3"
																	value="1">3</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 20px;'></i>&nbsp;4"
																	value="2">4</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 25px;'></i>&nbsp;5"
																	value="1">5</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 30px;'></i>&nbsp;6"
																	value="2">6</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 35px;'></i>&nbsp;7"
																	value="1">7</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 40px;'></i>&nbsp;8"
																	value="2">8</option>
															</select>
														</div>
														<div class="col-xs-6">
															<select class="form-control selectpicker input-sm">
																<option value="c">C</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 5px;'></i>&nbsp;1"
																	value="1">1</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 10px;'></i>&nbsp;2"
																	value="2">2</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 15px;'></i>&nbsp;3"
																	value="1">3</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 20px;'></i>&nbsp;4"
																	value="2">4</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 25px;'></i>&nbsp;5"
																	value="1">5</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 30px;'></i>&nbsp;6"
																	value="2">6</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 35px;'></i>&nbsp;7"
																	value="1">7</option>
																<option
																	data-content="<i class='fas fa-circle' aria-hidden='true' style='font-size: 40px;'></i>&nbsp;8"
																	value="2">8</option>
															</select>
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


				<div class="col-md-3">
					<div class="admin-content-con">
						<header class="clearfix">
							<h5 class="pull-left">NEURO VITAL CHART</h5>
							<h5 class="pull-right">QR/14-12</h5>
						</header>
						<div class="form-group">
							<label class="control-label" for="name">Name :</label> <input
								type="text" class="form-control input-sm" id="name"
								value="<%=patient.getPatientName()%>" name="name"
								placeholder="Name">
						</div>


						<div class="form-horizontal">

							<div class="form-group">
								<label class="control-label col-xs-3" for="sex">Sex</label>
								<div class="col-xs-9">
									<input type="text" class="form-control input-sm" id="sex"
										value="<%=patient.getSex()%>" name="sex" placeholder="Sex">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="bed">Bed</label>
								<div class="col-xs-9">
									<input type="text" class="form-control input-sm" id="bed"
										value="<%=patient.getBedNo()%>" name="bed" placeholder="Bed No">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="mrd"><span
									class="" id="mrd">MRD</span></label>
								<div class="col-xs-9">
									<input type="text" class="form-control input-sm" id="mrd"
										id="mrd" value="<%=patient.getMrdNumber()%>" name="mrd_no"
										placeholder="MRD">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="ip-no"><span
									class="" id="ip-no"> IP</span></label>
								<div class="col-xs-9">
									<input type="text" class="form-control input-sm" id="ip-no"
										value="<%=patient.getIpNumber()%>" name="ip_no"
										placeholder="IP Number">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="note-date"><span
									class="" id="note-date"> Date</span></label>
								<div class="col-xs-9">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="DN003" placeholder="Date of Birth" >
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-xs-3" for="note-date"><span
									class="" id="note-date"> Time</span></label>
								<div class="col-xs-9">
									<input type="text" class="form-control input-sm"
										id="timepicker" name="DN003" placeholder="Time">
								</div>
							</div>

							<div class="form-group">
								<div class="col-xs-9  col-xs-offset-5">
									<button type="reset" class="btn btn-primary btn-sm">Reset</button>
									<button type="submit" class="btn btn-success btn-sm">Submit</button>
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