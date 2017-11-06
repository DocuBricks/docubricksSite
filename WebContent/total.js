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
			alert("A link to reset your password has been sent to your email");
			window.location=".";
		}
		else {
			alert("Failed to reset password - Wrong email?");
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
//		    	retob.put("status","alreadyexists");

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
		var req = $.getJSON("EditUser",{
			uid:$("#register_id").val(), 
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
				alert("Could not update user information");
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
			//alert("Project deleted");
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
		shortlink:$("#projprop_shortlink").val(),
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

list_projects = function() {
	var query_url, req;
	query_url = "GetDocumentList";
	if(isadmin){
		req = $.getJSON(query_url,{});
	} else {
		req = $.getJSON(query_url,{ownerid:current_uid});
	}
	req.success(function(data) {
		return list_projects_disp(data);
	});
	return req.fail(function(data) {
		return alert("failed to query data, " + query_url);
	});
};


list_projects_disp = function(data) {
	var link, rec, table, _i, _len;
	table = $("#table_projects");
	table.empty();
	for (_i = 0, _len = data.length; _i < _len; _i++) {
		rec = data[_i];
		var row = $("<tr>");
		table.append(row);
		
		/////////// view
		var td = $("<td>");
		row.append(td);
		var link = $("<a>");
		td.append(link);
		link.append(rec.name);
		link.attr({href:"viewer.jsp?id="+rec.id});
		table.append(row);

		/////////// edit prop
		td = $("<td>");
		link = $("<form action=\"projectproperties.jsp\">" +
				"<input type=\"hidden\" name=\"id\" value=\"" + rec.id +"\"/>"+
				"<button type=\"submit\">Edit</button>" +
				"</form>");
		row.append(td);
		td.append(link);

		////////////// edit content
		/*
		td = $("<td>");
		link = $("<form action=\"edit.jsp\">" +
				"<input type=\"hidden\" name=\"id\" value=\"" + rec.id +"\"/>"+
				"<button type=\"submit\">Edit</button>" +
				"</form>");
		row.append(td);
		td.append(link);
		*/

		
		
		//////////////// desc
		td = $("<td>");
		row.append(td);
		td.append(rec.description)
		
		
	}
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







//if($("#reset_password").val()!=$("#reset_password_confirmation").val()){

function validatePassword(email,pass, pass2){
	
	if(pass!=pass2){
		return "Passwords do not match";
	}
	
	
	
	/*
	 * 
	 * if(form.pwd1.value == form.username.value) {
        alert("Error: Password must be different from Username!");
        form.pwd1.focus();
        return false;
      }
      
	   re = /[0-9]/;
	      if(!re.test(form.pwd1.value)) {
	        alert("Error: password must contain at least one number (0-9)!");
	        form.pwd1.focus();
	        return false;
	      }
	      re = /[a-z]/;
	      if(!re.test(form.pwd1.value)) {
	        alert("Error: password must contain at least one lowercase letter (a-z)!");
	        form.pwd1.focus();
	        return false;
	      }
	      re = /[A-Z]/;
	      if(!re.test(form.pwd1.value)) {
	        alert("Error: password must contain at least one uppercase letter (A-Z)!");
	        form.pwd1.focus();
	        return false;
	      }
	      */
	
	return "";
}


function validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}







