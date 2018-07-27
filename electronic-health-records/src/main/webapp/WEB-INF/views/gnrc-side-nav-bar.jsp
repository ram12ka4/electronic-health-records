

<%
	String ipNo = (String) session.getAttribute("ipNo");
	
	System.out.println("IP No set in session " + ipNo);
%>



<div class="col-md-2 col-sm-1 hidden-xs display-table-cell valign-top side-active"
	id="side-menu">
	<h1 class="hidden-xs hidden-sm"><a id="menu-toggle" href="#"><span id="main_icon" class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>GNRC</a></h1>
	<ul>
		<li class="link active"><a href="/dashboard.do"> <span
				class="glyphicon glyphicon-th" aria-hidden="true"></span> <span
				class="hidden-xs hidden-sm">Dashboard</span>
		</a></li>


		<li class="link"><a href="#collapse-registration"
			data-toggle="collapse" aria-controls="collapse-registration"> <i
				class="fas fa-plus"></i> <span class="hidden-xs hidden-sm">Electronic
					Health Record</span>

		</a>
			<ul class="collapse collapseable" id="collapse-registration">
				<li><a href="/patient.do?ip_no=<%=ipNo%>">Patient Identification Card </a></li>
				<li><a href="/invest.do?ip_no=<%=ipNo%>">Investigation Sheet </a></li>
				<li><a href="/docnote.do?ip_no=<%=ipNo%>">Doctor's Notes </a></li>
				<li><a href="/docorder.do?ip_no=<%=ipNo%>">Doctor's Orders </a></li>
				<li><a href="/consult.do?ip_no=<%=ipNo%>">Consultation Record</a></li>
				<li><a href="/lab.do?ip_no=<%=ipNo%>">Laboratory Report</a></li>
				<li><a href="/transfer.do?ip_no=<%=ipNo%>">Transfer Summary </a></li>
			</ul></li>




		<li class="link"><a href="#"> <span
				class="glyphicon glyphicon-user" aria-hidden="true"></span> <span
				class="hidden-xs hidden-sm">Commenters</span>
		</a></li>

		<li class="link"><a href="#"> <span
				class="glyphicon glyphicon-tags" aria-hidden="true"></span> <span
				class="hidden-xs hidden-sm">Tags</span>
		</a></li>

		<li class="link setting-btn"><a href="#"> <span
				class="glyphicon glyphicon-cog" aria-hidden="true"></span> <span
				class="hidden-xs hidden-sm">Settings</span>
		</a></li>
	</ul>
</div>