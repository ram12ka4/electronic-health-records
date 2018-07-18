$(document).ready(function() {

	/*
	 * Toggle side navigation bar
	 */

	$('[data-toggle="offcanvas"]').click(function() {
		$('#side-menu').toggleClass('hidden-xs');
	});

	/*
	 * validate select element compulsory value
	 */

	$('select').on('change', function() {
		var id = $(this).attr("id");
		if ($('option:selected', this).val() === '0') {
			// alert("red");
			$(this).css('border', '1px solid red');
			$('#' + id).css('color', 'red');
		} else if ($(this).val() !== 0) {
			$(this).css('border', '1px solid #00C492');
			$('#' + id).css('color', '#00C492');
		}
	});

	/*
	 * Validate input[type="text"] element compulsory value
	 */

	$('input[type="text"]').keyup(function() {
		var id = $(this).attr("id");
		if ($(this).val().length === 0) {
			$("#" + id).css('color', 'red');
		} else if ($(this).val().length !== 0) {
			$('#' + id).css('color', '#00C492');
		}
	});

});

/*
 * Photo upload bootstrap JQuery Code
 */
function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			$('#blah').attr('src', e.target.result);
		};

		reader.readAsDataURL(input.files[0]);
	}
}