<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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
							<h5 class="pull-left">CONSULTATION RECORD</h5>

						</header>


						<div class="form-horizontal">


							<div class="form-group">
								<label class="control-label col-xs-2" for="status"><span
									class="required-label" id="gender"> Name</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Name" required>
								</div>
								<label class="control-label col-xs-1" for="status"><span
									class="required-label"> Age</span> :</label>
								<div class="col-xs-1">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="dt_of_birth" placeholder="Date of Birth" required>
								</div>
								<label class="control-label col-xs-2" for="status"><span
									class="required-label"> Sex</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="age-yy"
										name="age_yy" placeholder="Y" required>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="status"><span
									class="required-label" id="gender"> Marital Satus</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
								</div>
								<label class="control-label col-xs-1" for="status"><span
									class="required-label"> Service</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="dt_of_birth" placeholder="Date of Birth" required>
								</div>
								<label class="control-label col-xs-2" for="status"><span
									class="required-label"> Ward</span> :</label>
								<div class="col-xs-1">
									<input type="text" class="form-control input-sm" id="age-yy"
										name="age_yy" placeholder="Y" required>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="status"><span
									class="required-label" id="gender"> BED No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
								</div>
								<label class="control-label col-xs-2" for="status"><span
									class="required-label"> MRD No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="dt_of_birth" placeholder="Date of Birth" required>
								</div>

							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="status"><span
									class="required-label" id="gender"> Hospital No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
								</div>
								<label class="control-label col-xs-2" for="status"><span
									class="required-label"> Religion Status</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="dt_of_birth" placeholder="Date of Birth" required>
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
							<h5 class="pull-left">CONSULTATION RECORD</h5>

						</header>


						<div class="form-horizontal">

							<div class="form-group">
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">Referred by Dr. </span> :</label>
								<div class="col-xs-3">
									<select data-placeholder="" name="title" id="title"
										class="form-control input-sm">
										<option value="MR">MR</option>
										<option value="MR">MRS</option>
										<option value="MR">DR</option>
									</select>
								</div>
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">Consultant & Speciality</span>
									:</label>
								<div class="col-xs-3">
									<select data-placeholder="" name="title" id="title"
										class="form-control input-sm">
										<option value="MR">MR</option>
										<option value="MR">MRS</option>
										<option value="MR">DR</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">Referred to Dr. </span> :</label>
								<div class="col-xs-3">
									<select data-placeholder="" name="title" id="title"
										class="form-control input-sm">
										<option value="MR">MR</option>
										<option value="MR">MRS</option>
										<option value="MR">DR</option>
									</select>
								</div>
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">Consultant & Speciality</span>
									:</label>
								<div class="col-xs-3">
									<select data-placeholder="" name="title" id="title"
										class="form-control input-sm">
										<option value="MR">MR</option>
										<option value="MR">MRS</option>
										<option value="MR">DR</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">Date</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="dt_of_birth" placeholder="Date of Birth" required>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">Brief clinical notes<br />and
										reasons for referral
								</span> :</label>
								<div class="col-xs-5">
									<textarea rows="5" class="form-control input-sm"></textarea>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-xs-2" for="mrd"><span
									class="required-label" id="mrd">Consultant's opinion<br />and
										recommendations
								</span> :</label>
								<div class="col-xs-5">
									<textarea rows="5" class="form-control input-sm"></textarea>
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