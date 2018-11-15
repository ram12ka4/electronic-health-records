$(document).ready(
		function() {

			$('button[name=index_submit]').click(
					function(e) {
						e.preventDefault();

						var userName = $("input[name=user]").val();
						var password = $("input[name=password]").val();
						var location = $('#location').val();
						
						//alert(userName + " " + password + " " + location);

						if ($("input[name=user]").val() === "") {
							$("#output").addClass(
									"alert alert-danger animated fadeInUp")
									.html("sorry enter a username");
							return false;
						} else if ($("input[name=password]").val() === "") {
							//alert(password);
							$("#output").addClass(
									"alert alert-danger animated fadeInUp")
									.html("sorry enter a password");
							return false;
						} else if ($("select[name=location]").val() === "0") {
							$("#output").addClass(
									"alert alert-danger animated fadeInUp")
									.html("sorry select a location");
							return false;
						}
						
						document.getElementById("index").method = "post";
						document.getElementById("index").action = "login.do";
						document.getElementById("index").submit(); 

					});

		});
