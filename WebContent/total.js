do_login = function() {
//	var theform = document.forms["login"];//["fname"].value
	//alert("whee" + JSON.stringify(theform));
	var req = $.getJSON("Login",
			{email:$("#login_email").val(), password:$("#login_password").val()})
	.done(function(data) {
		if(data.status==1) {
			window.location="mypage.jsp";
		}
		else {
			alert("Invalid user/password");
		}
	});
	return false;
};



////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////


do_resetpassword = function() {
	var req = $.getJSON("EmailPassword",
			{email:$("#email").val()})
	.done(function(data) {
		if(data.status==1){
			alert("Password sent");
			window.location="mypage.jsp";
		}
		else {
			alert("Invalid user");
		}
		return false; //needed?
	});
	return false;
};



////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////


do_passwordresetconfirm = function() {
	if($("#reset_password").val()!=$("#reset_password_confirmation").val()){
		alert("Passwords are not matching");
	}
	else{
		var req = $.getJSON("EmailPasswordConfirm",
				{uid:$("#reset_uid").val(), code:$("#reset_code").val(), password:$("#reset_password").val()})
		.done(function(data) {
			if(data.status==1){
				alert("Password reset");
				window.location="mypage.jsp";
			}
			else {
				alert("Failed to reset password");
			}
			return false; //needed?
		});
	}
	return false;
};





////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

do_logout = function() {
	var req = $.getJSON("Logout")
	.done(function(data) {
		window.location="index.jsp";
		return false;
	});
	return false;
};




////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////


do_register = function() {
	
	if($("#register_password").val()!=$("#register_password_confirmation").val()){
		alert("Passwords are not matching");
	}
	else{
		var req = $.getJSON("CreateUser",{
			edit:"0", 
			name:$("#register_name").val(), 
			surname:$("#register_surname").val(), 
			email:$("#register_email").val(), 
			password:$("#register_password").val()
		})
		.done(function(data) {
			if(data.status==1){
				window.location="mypage.jsp";
			}
			else{
				alert("Could not create user");
			}
		});
	}
	return false;
};



////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////


do_edituser = function() {
	if($("#register_password").val()!=$("#register_password_confirmation").val())
		{
		alert("Passwords are not matching");
		}
	else{
		var req = $.getJSON("CreateUser",{
			edit:"1", 
			id:$("#register_id").val(), 
			name:$("#register_name").val(), 
			surname:$("#register_surname").val(), 
			email:$("#register_email").val(), 
			password:$("#register_password").val()
		})
		.done(function(data) {
			if(data.status==1){
				window.location="mypage.jsp";
			}
			else{
				alert("Could not update user");
			}
		});
}
return false;
};



////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////



do_upload_zip = function() {
	var data = new FormData();
	jQuery.each(jQuery('#uploadzip_file')[0].files, function(i, file) {
	    data.append('file', file);
	});
	
	$("#uploadstatus").text("Note: Please only click once and be patient. Especially for large files");
	
	jQuery.ajax({
	    url: 'UploadZip',
	    data: data,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: 'POST',
	    success: function(data){
			window.location="mypage.jsp";
			location.reload();
	    }
	});
	
	return false;
};



////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

do_deletedocument = function() {
	if (confirm('Are you sure you want to delete this project?')) {
		var req = $.getJSON("DeleteDocument",{id:$("#deletedocument_id").val()})
		.done(function(data) {
			window.location="mypage.jsp";
		});
	} else {
	}
	
	return false;
};


////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

do_editproject = function() {
	var req = $.getJSON("EditDocument",{
		id:$("#projprop_id").val(),
		name:$("#projprop_name").val(),
		image:$("#projprop_image").val(),
		description:$("#projprop_description").val(),
		tags:$("#projprop_tags").val(),
		ispublic:$("#projprop_ispublic").is(":checked")
	})
	.done(function(data) {
		window.location="mypage.jsp";
	});
	return false;
};




////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////


do_uploadprojimage = function() {
	var data = new FormData();
	jQuery.each(jQuery('#uploadprojimage_file')[0].files, function(i, file) {
	    data.append('file', file);
	});
	data.append('id', $("#projprop_id").val());
	
	jQuery.ajax({
	    url: 'PutDocumentFile',
	    data: data,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: 'POST',
	    success: function(data){
	    	data = JSON.parse(data);
	    	if(data.status=="1"){
		    	//Once the image is uploaded, update the image to be used
		    	$("#projprop_image").val(data.filename);
		    	do_editproject();
		    	///// actually, should call 
				//window.location="projectproperties.jsp?id="+$("#projprop_id").val();
	    		
	    	} else {
	    		alert("Failed to upload image "+JSON.stringify(data.status));
	    	}
	    	
	    }
	});
	return false;
};









/*

//Function to submit to. 

function createuser(form) 
	{
	//check that passwords match and are not null
	
	//check name is not null
	
	//check email is valid
	
	
	//now submit to the server
	
        //form.action = form_action;
    }
*/

function validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}






