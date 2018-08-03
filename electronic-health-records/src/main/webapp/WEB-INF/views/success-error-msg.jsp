
<%
	String token = (String) request.getAttribute("token") == null ? "" : (String) request.getAttribute("token");
	String msg = (String) request.getAttribute("msg") == null ? "" : (String) request.getAttribute("msg");

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