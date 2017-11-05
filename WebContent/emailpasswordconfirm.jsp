<jsp:include page="header.html" />
<jsp:include page="top.jsp" />


<div id="main">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-default">
					<div class="panel-heading">Confirm Password Reset</div>
					<div class="panel-body">

						<form class="form-horizontal" role="form" onsubmit="return do_passwordresetconfirm()" name="login" accept-charset="UTF-8">

							<input type="hidden" id="reset_uid" value="<% out.println(request.getParameter("uid")); %>" id="projprop_id">

							<input type="hidden" id="reset_code" value="<% out.println(request.getParameter("code")); %>" id="projprop_id">

							<div class="form-group">
								<label class="col-md-4 control-label">New Password</label>
								<div class="col-md-6">
									<input type="password" class="form-control" id="reset_password">
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Confirm Password</label>
								<div class="col-md-6">
									<input type="password" class="form-control" id="reset_password_confirmation">
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

<jsp:include page="bottom.html" />
