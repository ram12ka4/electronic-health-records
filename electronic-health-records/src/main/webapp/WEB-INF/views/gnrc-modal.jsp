<!-- Alert pop up Modal -->
<div class="modal fade alertModal" tabindex="-1"
	aria-labelledby="source-model-lebel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div id="myAlert" role="alert">
				<button type="button" data-dismiss="alert" class="close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</div>
	</div>
</div>


<!-- Form submitting confirmation Modal -->
<div class="modal fade confirmModal" role="dialog" tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span>
				</button>
				<h2 class="modal-title">Please Confirm</h2>
			</div>
			<div class="modal-body">
				<p>Do you want to continue&hellip;
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-primary confirm-btn">Confirm</button>
			</div>
		</div>
	</div>
</div>

<!-- Progression Circle -->
<div class="modal fade circleModal" tabindex="-1"
	aria-labelledby="source-model-lebel">
	<div class="modal-dialog">
		<div class="ring">
			Please wait <span> </span>
		</div>
	</div>
</div>


<!-- Previous Entry Modal -->
<div class="modal fade bs-example-modal-lg myModal" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Doctor's Previous
					Orders</h4>
			</div>
			<div class="modal-body"></div>
		</div>
	</div>
</div>

<!-- Edit previous entry modal -->
<div class="modal fade editModal" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Edit Doctor Note</h4>
			</div>
			<div class="modal-body">
				<p>No data found&hellip;</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default close-btn">Close</button>
				<button type="button" class="btn btn-primary update-btn">Update</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Delete previous modal -->
<div class="modal fade deleteModal" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Delete Doctor Order</h4>
			</div>
			<div class="modal-body">
				<p>Do you want to delete&hellip;</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default close-btn">Close</button>
				<button type="button" class="btn btn-primary delete-btn">Delete</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

