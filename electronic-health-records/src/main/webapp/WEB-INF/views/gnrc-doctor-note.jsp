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
<link rel="stylesheet" href="css/gnrc-user-reg.css">
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

		String token = (String) request.getAttribute("token") == null ? "" : (String) request.getAttribute("token");
		String msg = (String) request.getAttribute("msg") == null ? "" : (String) request.getAttribute("msg");
		String ipNumber = (String) request.getAttribute("ipName") == null
				? ""
				: (String) request.getAttribute("ipName");

		System.out.println("Ip Name : " + ipNumber);
		System.out.println("MSG : " + msg);
		System.out.println("token : " + token);

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
						<header class="clearfix">
							<h5 class="pull-left">DOCTOR'S NOTES</h5>
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
									<input type="text" class="form-control input-sm"
										value="<%=patient.getAge()%>" id="age" name="age"
										placeholder="Age" readonly>
								</div>
								<label class="control-label col-xs-2" for="sex"><span
									class="required-label" id="sex"> Sex</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="sex"
										value="<%=patient.getSex()%>" name="sex" placeholder="Y"
										readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="service"><span
									class="required-label" id="service"> Service Unit</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" name="service"
										placeholder="Service Unit" readonly>
								</div>
								<label class="control-label col-xs-1" for="bed"><span
									class="required-label" id="bed"> Bed</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="bed"
										value="<%=patient.getBedNo()%>" name="bed"
										placeholder="Date of Birth" readonly>
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
								<label class="control-label col-xs-2" for="ip-no"><span
									class="required-label" id="ip-no"> IP No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="ip-no"
										value="<%=patient.getIpNumber()%>" name="ip_no"
										placeholder="IP Number" readonly>
								</div>
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd"> MRD No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="mrd"
										id="mrd" value="<%=patient.getMrdNumber()%>" name="mrd_no"
										placeholder="Date of Birth" readonly>
								</div>
								<label class="control-label col-xs-1" for="note-date"><span
									class="required-label" id="note-date"> Date</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="DN003" placeholder="Date of Birth">
								</div>

							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="doctor-name"><span
									class="required-label" id="doctor-name"> Doctor Name</span> :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" name="DN001"
										placeholder="Doctor Name">
									<div class="checkbox">
										<label> <input type="checkbox" name="DN002">
											Visiting Doctor
										</label>
									</div>
								</div>

							</div>



							<div class="form-group">
								<label class="control-label col-xs-2" for="note"><span
									class="required-label" id="note"> Note</span> :</label>
								<div class="col-xs-8">
									<textarea rows="5" cols="" class="form-control input-sm"
										name="DN004" id="note"></textarea>
								</div>

							</div>
							
							<div class="form-group">
								<div style="padding-right: 16px;" class="pull-right">
									<button type="button" class="btn btn-default" name="reset">Reset</button>
									<button type="button" class="btn btn-primary"
										name="doctor_note_submit" id="submit-btn">Submit</button>
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
							<h5 class="pull-left">DOCTOR'S PREVIOUS NOTES</h5>
							<h5 class="pull-right">QR/9-28</h5>
						</header>
						<div class="panel-group" id="accordion" role="tablist"
							aria-multiselectable="true">
							<div class="panel panel-default">
								<div class="panel-heading" role="tab" id="headingOne">
									<h4 class="panel-title">
										<a role="button" data-toggle="collapse"
											data-parent="#accordion" href="#collapseOne"
											aria-expanded="true" aria-controls="collapseOne"> Date :
										</a>
									</h4>
								</div>
								<div id="collapseOne" class="panel-collapse collapse in"
									role="tabpanel" aria-labelledby="headingOne">
									<div class="panel-body">Anim pariatur cliche
										reprehenderit, enim eiusmod high life accusamus terry
										richardson ad squid. 3 wolf moon officia aute, non cupidatat
										skateboard dolor brunch. Food truck quinoa nesciunt laborum
										eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on
										it squid single-origin coffee nulla assumenda shoreditch et.
										Nihil anim keffiyeh helvetica, craft beer labore wes anderson
										cred nesciunt sapiente ea proident. Ad vegan excepteur butcher
										vice lomo. Leggings occaecat craft beer farm-to-table, raw
										denim aesthetic synth nesciunt you probably haven't heard of
										them accusamus labore sustainable VHS.</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading" role="tab" id="headingTwo">
									<h4 class="panel-title">
										<a class="collapsed" role="button" data-toggle="collapse"
											data-parent="#accordion" href="#collapseTwo"
											aria-expanded="false" aria-controls="collapseTwo"> Date :
										</a>
									</h4>
								</div>
								<div id="collapseTwo" class="panel-collapse collapse"
									role="tabpanel" aria-labelledby="headingTwo">
									<div class="panel-body">Anim pariatur cliche
										reprehenderit, enim eiusmod high life accusamus terry
										richardson ad squid. 3 wolf moon officia aute, non cupidatat
										skateboard dolor brunch. Food truck quinoa nesciunt laborum
										eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on
										it squid single-origin coffee nulla assumenda shoreditch et.
										Nihil anim keffiyeh helvetica, craft beer labore wes anderson
										cred nesciunt sapiente ea proident. Ad vegan excepteur butcher
										vice lomo. Leggings occaecat craft beer farm-to-table, raw
										denim aesthetic synth nesciunt you probably haven't heard of
										them accusamus labore sustainable VHS.</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading" role="tab" id="headingThree">
									<h4 class="panel-title">
										<a class="collapsed" role="button" data-toggle="collapse"
											data-parent="#accordion" href="#collapseThree"
											aria-expanded="false" aria-controls="collapseThree"> Date
											: </a>
									</h4>
								</div>
								<div id="collapseThree" class="panel-collapse collapse"
									role="tabpanel" aria-labelledby="headingThree">
									<div class="panel-body">Anim pariatur cliche
										reprehenderit, enim eiusmod high life accusamus terry
										richardson ad squid. 3 wolf moon officia aute, non cupidatat
										skateboard dolor brunch. Food truck quinoa nesciunt laborum
										eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on
										it squid single-origin coffee nulla assumenda shoreditch et.
										Nihil anim keffiyeh helvetica, craft beer labore wes anderson
										cred nesciunt sapiente ea proident. Ad vegan excepteur butcher
										vice lomo. Leggings occaecat craft beer farm-to-table, raw
										denim aesthetic synth nesciunt you probably haven't heard of
										them accusamus labore sustainable VHS.</div>
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
	<%@include file="alert-modal.jsp"%>
	<%@include file="confirm-box.html"%>
	<%@include file="alert-box.html"%>

	<!-- End of Form Submit Alert Message -->
	
	
	



	<!-- Lower Layout -->
	<%@include file="gnrc-page-lower-layout.jsp"%>
	<!-- End of Lower Layout -->

	<!-- JS -->
	<%@include file="gnrc-common-include-js.html"%>
	<script type="text/javascript" src="js/chosen.jquery.min.js"></script>
	<script type="text/javascript" src="js/dashboard.js"></script>
	<script type="text/javascript" src="js/doctor-note.js"></script>
	<!-- End of JS -->



	<%
		if (!"".equals(msg) && !"".equals(token)) {

			//System.out.println(msg);

			if ("success".equalsIgnoreCase(token)) {
				//System.out.println(token);
	%>
	<script>
		$(document).ready(function() {
					//alert("if clause");
					
					swal("Well done!", '<%=msg%>', "success");

				});
	</script>

	<%
		} else {
	%>
	<script>
		$(document).ready(function() {
					
					swal("Oh no!", '<%=msg%>', "error");
				});
	</script>

	<%
		}
		}
	%>


</body>
</html>