$(function() {

	var arr = new Array();

	var req = $
			.ajax({
				url : 'transfer.do',
				type : 'post',
				datatype : 'text',
				data : {
					ACTION : 'GET_PREV_ED_NO',
					paramType : "TSNS"
				},
				success : function(data) {
					var select = $(".sel-prev-record");
					select.css("display", "inline");

					select
							.empty()
							.append(
									'<option selected="selected" value="0" disabled = "disabled">Loading.....</option>');
					arr = data.replace("[", "").replace("]", "").split(",");
					select
							.empty()
							.append(
									'<option selected="selected" value="0">Select Previous Record</option>');
					var i = 0;

					while (i < arr.length) {

						select.append($("<option></option>").val(arr[i]).html(
								arr[i] + " <------> " + arr[i + 1]));
						i += 3;
					}

				},
				failure : function(data) {
					alert("failure");
					alert(data.responseText);
				},
				error : function(data) {
					alert("error");
					alert(data.responseText);
				}

			});

	$(document).on('click', '.submit-btn', function(e) {

		alert('Submit Button');

		e.preventDefault();

		$('.confirmModal').modal();

	});

	$(document).on('click', '.confirm-btn', function() {
		alert("Final confirm");
		$('.circleModal').modal({
			backdrop : 'static',
			keyboard : false
		});
		$('.confirmModal').modal('hide');
		document.getElementById("transfer-frm").method = "post";
		document.getElementById("transfer-frm").action = "transfer.do";
		document.getElementById("transfer-frm").submit();
	});

	$(document).on('click', '.previous-btn', function() {

		document.getElementById("transfer-frm").method = "get";
		document.getElementById("transfer-frm").action = "transfer.report";
		document.getElementById("transfer-frm").target = "_blank";
		document.getElementById("transfer-frm").submit();

	});

	$(document).on('change', '.sel-prev-record', function() {

		var edId = $.trim($(this).val());
		var arr = new Array();
		var index = 0;

		var req = $.ajax({

			url : 'transfer.do',
			type : 'post',
			datatype : 'json',
			data : {
				ACTION : 'GET_PREV_TRANSFER_RECORD',
				paramType : 'TSNS',
				edNo : edId
			},
			success : function(data) {
				
				alert(data);
				arr = data.replace("{","").replace("}", "").split(",");
				//alert("Length : " + arr.length);
				
				$('.form-control')
				.each(
						function() {

							var string = $(this)
									.attr(
											'name');

							//alert(string);

							if (string !== undefined
									&& string !== null
									&& string.length) {
								
								var i;
								
								for (i=0; i<arr.length; i++){
									
									var keyValue  = arr[i].split("="); 

									var key = keyValue[0].trim();
									var value = keyValue[1].trim();
									
									
									
									var expr = string.trim();
									
									//alert("Array Value length : " + value.length);
									//alert("Expr Value  : " + expr + " Key Value : " + key + " Result : " + key.localeCompare(expr));
									
									if (key.localeCompare(expr) == 0){
										//alert("Compared Key : " + key);
										
										var otherValue = new Array();
										
										if (key.localeCompare("TS017") == 0){
											//alert("In If clause");
											otherValue = value.replace("[", "").replace("]", "").split("-");
											var j;
											
											//$('.input-field-wrap .medical-problem').css('display', 'none');
											//$('.remove_field').remove();
											
											$('.remove_field').trigger('click');
											
											
											for (j=0; j<otherValue.length; j++){
												alert("Counter : " + j);
												/*$(".input-field-wrap")
												.append(
														
												'<div class="form-group"><div class="col-xs-3"></div><div class="col-xs-3"><input type="text" class="form-control input-sm medical-problem" value=\"'+ otherValue[j] +'\" id="speciality" name="TS017" placeholder="Medical Problems"></div><a href="#" class="remove_field">Remove</a></div>'); //add input box */
											$('.add-field-button').trigger('click', [otherValue[j]]);
												
											}
										} else {
											//alert("In else clause");
											$(this).val(value);
										}
										//$(this).val(value);
										
									}
								}
							}
						});
			},
			failure : function(data) {
				alert("failure");
				alert(data.responseText);
			},
			error : function(data) {
				alert("error");
				alert(data.responseText);
			}

		});

	});
	
	var y =1;
	
	$('.add-field-button').on('click', function(e, value) { //on add input button click
			alert("Value is : " + value);	
			e.preventDefault();
				if (y < 10) { //max input box allowed
					y++; //text box increment
					$('.input-field-wrap')
							.append(

									'<div class="form-group "><div class="col-xs-3"></div><div class="col-xs-3"><input type="text" class="form-control input-sm" value=\"'+ value +'\" id="speciality" name="TS017" placeholder="Medical Problems"></div><a href="#" class="remove_field">Remove</a></div>'); //add input box
				}
			});

	
	$('.input-field-wrap').on("click", function(e) { //user click on remove text
		alert("remove done");
				e.preventDefault();
				$(this).parent('div').remove();
				y--;
			});

});