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
							<h5 class="pull-left">DOCTOR'S ORDERS</h5>
							<h5 class="pull-right">QR/9-28</h5>
						</header>


						<div class="form-horizontal">


							<div class="form-group">
								<label class="control-label col-xs-2" for="status"><span
									class="required-label" id="gender"> Name</span> :</label>
								<div class="col-xs-3">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
								</div>
								<label class="control-label col-xs-1" for="status"><span
									class="required-label"> Age</span> :</label>
								<div class="col-xs-1">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="dt_of_birth" placeholder="Date of Birth" required>
								</div>
								<label class="control-label col-xs-2" for="status"><span
									class="required-label"> Sex</span> :</label>
								<div class="col-xs-1">
									<input type="text" class="form-control input-sm" id="age-yy"
										name="age_yy" placeholder="Y" required>
								</div>

							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="status"><span
									class="required-label"> HOSP. No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="age-yy"
										name="age_yy" placeholder="Y" required>
								</div>
								<label class="control-label col-xs-2" for="status"><span
									class="required-label" id="gender"> Service Unit</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
								</div>
								<label class="control-label col-xs-1" for="status"><span
									class="required-label"> Bed</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="dt_of_birth" placeholder="Date of Birth" required>
								</div>

							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="status"><span
									class="required-label"> Ward</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="age-yy"
										name="age_yy" placeholder="Y" required>
								</div>
								<label class="control-label col-xs-2" for="status"><span
									class="required-label" id="gender"> MRD No.</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm"
										name="last_name" placeholder="Last Name" required>
								</div>
								<label class="control-label col-xs-1" for="status"><span
									class="required-label"> OCCI</span> :</label>
								<div class="col-xs-2">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="dt_of_birth" placeholder="Date of Birth" required>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-xs-2" for="status"><span
									class="required-label"> Doctor Name</span> :</label>
								<div class="col-xs-4">
									<input type="text" class="form-control input-sm" id="fromDate"
										name="dt_of_birth" placeholder="Date of Birth" required>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" value="">
										Visiting Doctor
									</label>
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
														<button class="btn btn-primary btn-sm add-field-medic-button">Add
															Medicine</button>
													</div>
												</div>
												<div class="form-group ">
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="speciality" name="medicine[]" placeholder="Medicine">
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
														<textarea rows="5" class="form-control input-sm"></textarea>
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
														<textarea rows="5" class="form-control input-sm"></textarea>
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
														<button class="btn btn-primary btn-sm add-field-lab-button">Add
															Laboratory</button>
													</div>
												</div>
												<div class="form-group">
													<div class="col-xs-4">
														<input type="text" class="form-control input-sm"
															id="speciality" name="lab[]" placeholder="Laboratory">
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
						<header class="clearfix">
							<h5 class="pull-left">DOCTOR'S PREVIOUS ORDERS</h5>
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

																	'<div class="form-group "><div class="col-xs-4"><input type="text" class="form-control input-sm" id="speciality" name="medicine[]" placeholder="Medicine"></div><a href="#" class="remove_field">Remove</a></div>'); //add input box
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

															'<div class="form-group "><div class="col-xs-4"><input type="text" class="form-control input-sm" id="speciality" name="lab[]" placeholder="Laboratory"></div><a href="#" class="remove_field2">Remove</a></div>'); //add input box
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