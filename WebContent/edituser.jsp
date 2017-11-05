<%@page import="site.record.RecordUser"%>
<%@page import="site.DocubricksSite"%>
<jsp:include page="header.html" />
<jsp:include page="top.jsp" />

<%
DocubricksSite ws2=new DocubricksSite();
ws2.fromSession(request.getSession());

RecordUser user=ws2.session.getUserInfo(ws2);
if(user!=null)
	{
	
	%>
	
	<div id="main">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-8 col-md-offset-2">
					<div class="panel panel-default">
						<div class="panel-heading">Edit user</div>
						<div class="panel-body">
	
							<form class="form-horizontal" role="form" method="POST" onsubmit="return do_edituser()">

								<input type="hidden" value="<% out.println(user.id); %>" id="register_id">
	
								<div class="form-group">
									<label class="col-md-4 control-label">First Name</label>
									<div class="col-md-6">
										<input type="text" class="form-control" value="<% out.println(user.firstName); %>" id="register_name">
									</div>
								</div>
	
								<div class="form-group">
									<label class="col-md-4 control-label">Last Name</label>
									<div class="col-md-6">
										<input type="text" class="form-control" value="<% out.println(user.lastName); %>" id="register_surname">
									</div>
								</div>
	
								<div class="form-group">
									<label class="col-md-4 control-label">E-Mail Address</label>
									<div class="col-md-6">
										<input type="email" class="form-control" value="<% out.println(user.emailPrimary); %>" id="register_email">
									</div>
								</div>
	
	
								<br/>
								<div class="form-group">
									<label class="col-md-4 control-label">New Password (if changing)</label>
									<div class="col-md-6">
										<input type="password" class="form-control" name="password" id="register_password">
									</div>
								</div>
	
								<div class="form-group">
									<label class="col-md-4 control-label">Confirm Password</label>
									<div class="col-md-6">
										<input type="password" class="form-control" name="password_confirmation" id="register_password_confirmation">
									</div>
								</div>
	
								<div class="form-group">
									<div class="col-md-6 col-md-offset-4">
										<button type="submit" class="btn btn-primary">Update</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<%
	}
%>

<jsp:include page="bottom.html" />
