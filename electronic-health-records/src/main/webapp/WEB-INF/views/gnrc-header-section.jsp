<style>
	h3 {
		text-transform: uppercase;
		text-align: center;
	}
</style>


<%
	String moduleName = (String)session.getAttribute("moduleName");
	String loginUser = (String)session.getAttribute("userName");
	String loginIp = (String)session.getAttribute("loginFrom");

%>


		<div class="row">

					<header id="nav-header" class="clearfix">
						<div class="col-md-5">
						
						<div class="form-header-name">
							
							<h3><%=moduleName%></h3>
													
						</div>
							<!-- NAV BAR -->
							<nav class="navbar-default pull-left">
								<button type="button" class="navbar-toggle collapsed"
									data-toggle="offcanvas" data-target="#side-menu"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <span
										class="icon-bar"></span> <span class="icon-bar"></span> <span
										class="icon-bar"></span>
								</button>

							</nav> 

		
							<!-- <input type="text" class="hidden-sm hidden-xs"
								id="header-search-field" placeholder="Search for something...."> -->
						</div>
						<div class="col-md-7">
							<ul>
								<li id="welcome" class="hidden-xs">Welcome to GNRC&nbsp;!!!&nbsp;<%=loginUser%></li>
								<li id="welcome" class="hidden-xs">Login IP : <%=loginIp%></li>
								<li class="fixed-width"><a href="#"> <span
										class="glyphicon glyphicon-bell" aria-hidden="true"></span> <span
										class="label label-warning">3</span>
								</a></li>
								<li class="fixed-width"><a href="#"> <span
										class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
										<span class="label label-message">3</span>
								</a></li>
								<li><a href="logout.do" class="logout"> <span
										class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
										log out
								</a></li>
							</ul>
						</div>
					</header>

				</div>