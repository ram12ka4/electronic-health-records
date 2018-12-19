<%@page import="java.util.*"%>
<jsp:useBean id="loginDao"
	class="com.gnrchospitals.daoimpl.LoginDaoImpl"
	type="com.gnrchospitals.dao.LoginDao" scope="request"></jsp:useBean>

<%
	String ipNo = (String) session.getAttribute("ipNo");
	System.out.println("IP No set in session " + ipNo);

	String userCode = (String) session.getAttribute("user");
	String categoryCode = (String) session.getAttribute("categoryCode");

	List<String> list = loginDao.userMenu(userCode, categoryCode);
	System.out.println("User Access Menu : " + list);
%>



<div
	class="col-md-2 col-sm-1 hidden-xs display-table-cell valign-top side-active"
	id="side-menu">
	<h1 class="hidden-xs hidden-sm">
		<a id="menu-toggle" href="patient.portal"><span id="main_icon"
			class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>GNRC</a>
	</h1>
	<ul>
		<li class="link active"><a
			href="patient.list?catCode=${sessionScope.categoryCode}"> <span
				class="glyphicon glyphicon-th" aria-hidden="true"></span> <span
				class="hidden-xs hidden-sm">Dashboard</span>
		</a></li>


		<li class="link"><a href="#collapse-registration"
			data-toggle="collapse" aria-controls="collapse-registration"> <i
				class="fas fa-plus"></i> <span class="hidden-xs hidden-sm">Electronic
					Health Record</span>

		</a>
			<ul class="collapse collapseable" id="collapse-registration">
				<%
					for (int i = 0; i < list.size(); i += 2) {
						String formName = list.get(i);
						String url = list.get(i + 1);
				%>
				<li><a href="<%=url%>?ip_no=${sessionScope.ipNo}&moduleName=<%=formName%>"><%=formName%></a></li>
				<%
					}
				%>
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