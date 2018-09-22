<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<jsp:useBean id="indoorPat"
	class="com.gnrchospitals.servlet.IndoorPatient" scope="request"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>GNRC</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="images/favicon.jpg" type="image/jpeg"
	sizes="16x16" />
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<%@include file="gnrc-common-include-css.html"%>
<link
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/patient-portal.css">

</head>

<body>
	<nav class="navbar navbar-inverse">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><img alt=""
					src="image/logo.jpg"></a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home <span class="sr-only">(current)</span></a></li>
					<li><a href="#">User Id : ${sessionScope.user}</a></li>
					<li><a href="#">Name : ${sessionScope.userName}</a></li>
					<li><a href="#">Login Time : ${sessionScope.loginTime}</a></li>
					<li><a href="#">Login From : ${sessionScope.loginFrom}</a></li>
					<li><a href="#">Location : ${sessionScope.location}</a></li>
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
					<a href="#" class="dropdown-toggle"	data-toggle="dropdown">Sign out <i class="fas fa-user"></i></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" data-toggle="modal" data-target="#logoutModal">Logout</a></li>
							<li><a href="#">Change Password</a></li>
						</ul>
					</li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!--  /.container-fluid -->
	</nav>


	<div class="wrapper">
		<img src="/images/favicon.jpg" alt="" />
		<div class="title">
			Patient Care Portal
			<hr>
		</div>
		<div class="sub-title">Provides clinical services to patients</div>

		<div class="child-wrapper">
			<div class="item">
				<a href="#"> <img alt=""
					src="https://images.unsplash.com/photo-1537083702767-42f85f12834f?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=4d6a5ae6edc99fca49f32ac49bd9651a&auto=format&fit=crop&w=500&q=60" />
				</a>
				<p>Doctor's Workbench</p>
			</div>

			<div class="item">
				<a href="#"> <img alt=""
					src="https://images.unsplash.com/photo-1537301696988-4a82a4959466?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=3661e70b004783da572c645b572d9bb4&auto=format&fit=crop&w=500&q=60" />
				</a>
				<p>CPOE</p>
			</div>

			<div class="item">
				<a href="#"> <img alt=""
					src="https://images.unsplash.com/photo-1537247008051-36551b94590b?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=68be66396106df92785383421081aa2f&auto=format&fit=crop&w=500&q=60" />
				</a>
				<p>EMR</p>
			</div>

			<div class="item">
				<a href="#"> <img alt=""
					src="https://images.unsplash.com/photo-1537283211-be6e1d3a549b?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=3a98cccc95076a3f4db6a786d897d746&auto=format&fit=crop&w=500&q=60" />
				</a>
				<p>MRD</p>
			</div>

			<div class="item">
				<a href="#"> <img alt=""
					src="https://images.unsplash.com/photo-1537278471568-5eedf39a5a0c?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=e134cd610674d094432ff5490fdf9504&auto=format&fit=crop&w=500&q=60" />
				</a>
				<p>Birth's Notification</p>
			</div>

			<div class="item">
				<a href="patient.list"> <img alt="nurshing_workbench"
					src="https://images.unsplash.com/photo-1537278917244-20d2027b22a4?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=5cc337d3abb07920a0cc130e53550043&auto=format&fit=crop&w=500&q=60" />
				</a>
				<p>Nursing Workbench</p>
			</div>

			<div class="item">
				<a href="#"> <img alt=""
					src="https://images.unsplash.com/photo-1537255234092-998a91a02a5b?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=239729f43e3375d39fc7177a97933bd7&auto=format&fit=crop&w=500&q=60" />
				</a>
				<p>EMR</p>
			</div>

			<div class="item">
				<a href="#"> <img alt=""
					src="https://images.unsplash.com/photo-1537217988244-5b7cf85aa5f8?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=7723d54357abc6adb93c90ce2cf4dc38&auto=format&fit=crop&w=500&q=60" />
				</a>
				<p>MRD</p>
			</div>



		</div>


	</div>


	<%@include file="logout-modal.html"%>
	<%@include file="gnrc-common-include-js.html"%>

</body>
</html>