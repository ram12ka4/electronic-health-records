$(function(){

	var userRole = $('#modules').val();
	var roleArr = userRole.replace("[", "").replace("]", "").split(",");
	
	for (var i=0; i<roleArr.length; i+=4){
		
		var categoryCode = roleArr[i];
		var categoryDesc = roleArr[i+1];
		var formPath = roleArr[i+2] + '?catCode=' + categoryCode;
		var formCount = roleArr[i+3];
		
		console.log(' Category Code : ' + categoryCode);
		console.log(' Category Desc : ' + categoryDesc);
		console.log(' Form Path : ' + formPath);
		console.log(' Form Count : ' + formCount);
		
		$('a').each(function(){
			
			var fetchCatCode = $(this).attr('id');
			
			if ($.trim(fetchCatCode) === $.trim(categoryCode)){
				$(this).attr('href', $.trim(formPath));
			}
			
		});
		
		
	}
});

if (window.history && window.history.pushState) {
    window.history.pushState('', null, 'patient.portal');
    $(window).on('popstate', function() {
        document.location.href = '#';
    });
} 

