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
							<h5 class="pull-left">PATIENT IDENTIFICATION CARD</h5>
							<h5 class="pull-right">QR/14-16</h5>
						</header>


						<div class="form-horizontal">

							<div class="form-group">
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">MRD Number</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" id="mrd"
										name="mrd_number" placeholder="MRD Number" required>
								</div>
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">IP Number</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm" id="mrd"
										name="mrd_number" placeholder="MRD Number" required>
								</div>
							</div>



							<div class="form-group">
								<label class="control-label col-xs-2" for="title"><span
									class="required-label" id="title"> Patient Name</span> :</label>
							
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										id="first-name" name="first_name" placeholder="First Name"
										required>

								</div>
							
							</div>

							<div class="form-group">
								<label class="control-label col-xs-2" for="status"><span
									class="required-label" id="gender"> Age</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
								</div>
								<label class="control-label col-xs-2" for="status"><span
									class="required-label"> Sex</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="dt_of_birth" placeholder="Date of Birth" required>
								</div>
								<label class="control-label col-xs-2" for="status"><span
									class="required-label"> Weight</span> :</label>
								<div class="col-xs-1">
									<input type="text" class="form-control input-sm" id="age-yy"
										name="age_yy" placeholder="Y" required>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="status"><span
									class="required-label" id="gender"> Date of Admission</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-xs-2" for="status"><span
									class="required-label" id="gender"> Consultant</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
								</div>
								
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="status"><span
									class="required-label" id="gender"> Clinical Diagnosis</span> :</label>
								<div class="col-xs-5">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
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