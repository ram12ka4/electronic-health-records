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
<link rel="icon" href="images/favicon.jpg" type="image/jpeg"
	sizes="16x16" />
<!-- End of CSS -->
</head>
<body>
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
							<h5 class="pull-left">TRANSFER SUMMARY (Neuro Intensive Care
								Unit)</h5>
							<h5 class="pull-right">QR/28-1</h5>
						</header>


						<div class="form-horizontal">


							<div class="form-group">
								<label class="control-label col-xs-1" for="status"><span
									class="required-label" id="gender"> Name</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Name" required>
								</div>
								<label class="control-label col-xs-1" for="status"><span
									class="required-label"> MRD No.</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="dt_of_birth" placeholder="Date of Birth" required>
								</div>
								<label class="control-label col-xs-2" for="status"><span
									class="required-label"> Hospital No.</span> :</label>
								<div class="col-xs-1">
									<input type="text" class="form-control input-sm" id="age-yy"
										name="age_yy" placeholder="Y" required>
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

						</header>


						<div class="form-horizontal">


							<div class="form-group">
								<label class="control-label col-xs-3" for="status"><span
									class="required-label" id="gender"> Provisional / Final
										Diagnosis</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="status"><span
									class="required-label" id="gender"> Transferred From</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="status"><span
									class="required-label" id="gender"> Transfer Date / Time</span>
									:</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="status"><span
									class="required-label" id="gender"> Cause of Transfer</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="status"><span
									class="required-label" id="gender"> Name of Transfer
										Doctor</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
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
								<label class="control-label col-xs-5" for="visit_no">Pulse
									:</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" id="visit_no"
										name="dt_of_birth" placeholder="PCV">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="visit_date">Temp
									:</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm"
										name="visit_date" id="visit_date" placeholder="ESR">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="appt_no">ITB/NITB
									: </label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm"
										name="appt_number" id="appt_number" placeholder="INR">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="doctor">Chest
									: </label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" name="doctor"
										id="doctor" placeholder="Test">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="remark">U.
									Cath. :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" id="treatment"
										name="treatment" placeholder="Total Pleteles Count">
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
								<label class="control-label col-xs-5" for="visit_no">B.
									P. :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" id="visit_no"
										name="dt_of_birth" placeholder="PCV">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="visit_date">R.
									R. :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm"
										name="visit_date" id="visit_date" placeholder="ESR">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="appt_no">V/NV
									: </label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm"
										name="appt_number" id="appt_number" placeholder="INR">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="doctor">CVS :
								</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" name="doctor"
										id="doctor" placeholder="Test">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-5" for="remark">GCS :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" id="treatment"
										name="treatment" placeholder="Total Pleteles Count">
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
								<label class="control-label col-xs-3" for="visit_no">Important
									previous medications :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" id="visit_no"
										name="dt_of_birth" placeholder="PCV">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-3" for="visit_date">Other
									associated medical problems:</label>
								<div class="col-xs-3">
									<button class="btn btn-primary btn-sm add-field-button">Add</button>
								</div>
							</div>
							<div class="form-horizontal input-field-wrap">
								<div class="form-group">
									<div class="col-xs-3"></div>
									<div class="col-xs-3">
										<input type="text" class="form-control input-sm"
											id="speciality" name="lab[]" placeholder="Laboratory">
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

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							var max_fields = 10; //maximum input boxes allowed

							var wrapper = $(".input-field-wrap"); //Fields wrapper
							var add_button = $(".add-field-button"); //Add button ID

							

							var x = 1; //initlal text box count
							$(add_button)
									.click(
											function(e) { //on add input button click
												e.preventDefault();
												if (x < max_fields) { //max input box allowed
													x++; //text box increment
													$(wrapper)
															.append(

																	'<div class="form-group "><div class="col-xs-3"></div><div class="col-xs-3"><input type="text" class="form-control input-sm" id="speciality" name="medicine[]" placeholder="Medicine"></div><a href="#" class="remove_field">Remove</a></div>'); //add input box
												}
											});

			

							$(wrapper).on("click", ".remove_field",
									function(e) { //user click on remove text
										e.preventDefault();
										$(this).parent('div').remove();
										x--;
									});

						
						});
	</script>
</body>
</html>