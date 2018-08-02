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