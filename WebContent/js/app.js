$( document ).ready(function() {
	$('#description-input').wysihtml5({
    	toolbar: {
      		"fa": true,
      		"image": false
    	}
  	});

  	$('#delete-button').confirmation({
  		onConfirm: function(){
  			$('#delete-button').parent().submit();
  		}
  	});
});