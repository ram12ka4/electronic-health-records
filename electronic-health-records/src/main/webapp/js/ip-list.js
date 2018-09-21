$(function() {

	var arr = new Array();

	var select = $(".sel-ward");
	select.css("display", "inline");

	select
			.empty()
			.append(
					'<option selected="selected" value="0" disabled = "disabled">Loading.....</option>');

	var req = $
			.ajax({
				url : 'dashboard.do',
				type : 'post',
				datatype : 'text',
				data : {
					ACTION : 'GET_PREV_ED_NO',
					paramType : "TSNS",

				},
				success : function(data) {

					data = data.replace(/^\W+|\W+$/g, "");
					// alert(data.length);

					if (data.length !== 0) {
						arr = data.replace("[", "").replace("]", "").split(",");
						select
								.empty()
								.append(
										'<option selected="selected" value="0">Select Previous Record</option>');
						var i = 0;

						while (i < arr.length) {

							select.append($("<option></option>").val(arr[i])
									.html(arr[i] + " <------> " + arr[i + 1]));
							i += 3;
						}
					} else {
						select.css("display", "none");
					}

				},
				failure : function(data) {
					// alert("failure");
					alert(data.responseText);
				},
				error : function(data) {
					// alert("error");
					alert(data.responseText);
				}

			});

});