
<%@page import="java.util.*"%>

<!-- Modal -->
<div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Doctor's Previous
					Notes</h4>
			</div>
			<div class="modal-body">

				<%
					List<List<String>> list = (ArrayList) request.getAttribute("doctorNotes");
					List<String> col = list.get(0);
					List<String> row = list.get(1);
					System.out.println("Doctor list in modal " + list);
				%>

				<%
					int rowCount = row.size() / col.size();
					int i = 0;

					while (i < row.size()) {
				%>



				<div class="comment-head-dash clearfix">
					<div class="commenter-name-dash pull-left"><%=row.get(i)%></div>
					<div class="days-dash pull-right" id="now"><%=row.get(i + 3)%></div>
				</div>
				<hr>
				<%=row.get(i + 1)%>

				<div class="button-right clearfix pull-right">
					<a href="#edit-doctor-note" data-id="<%=row.get(i + 2)%>"
						data-toggle="modal"
						class="btn btn-warning btn-sm doctor-edit-button">edit</a> <a
						href="#delete-doctor-note" data-id="<%=row.get(i + 2)%>"
						data-toggle="modal"
						class="btn btn-danger btn-sm doctor-del-button">del</a>
				</div>

				<%
					i += col.size();
					}
				%>

			</div>




			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save changes</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" tabindex="-1" role="dialog"
	id="edit-doctor-note">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Modal title</h4>
			</div>
			<div class="modal-body">
				<p>One fine body&hellip;</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save changes</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" tabindex="-1" role="dialog"
	id="delete-doctor-note">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Doctor Note</h4>
			</div>
			<form id="delete-doctor-form">
				<div class="modal-body">
					<p>Do you want to delete&hellip;</p>
				</div>
				<input type="hidden" value="" name="ed_name" id="ed_name" />
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" >Delete</button>
				</div>
			</form>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

