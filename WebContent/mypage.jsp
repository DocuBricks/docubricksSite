<%@page import="site.DocubricksSite"%>
<%@page%>


<%
	DocubricksSite ws=new DocubricksSite();
	ws.fromSession(request.getSession());

%>

<jsp:include page="header.html" />
<script src="mypage.js"></script>
<jsp:include page="top.jsp" />




<hidden>
	<tr id="table_projects_row">
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>

</hidden>



<div id="main">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-default">
					<div class="panel-heading">My projects</div>
					<div class="panel-body">
					
						<table>
							<tr>
								<th>Name&nbsp;&nbsp;</th>
								<th>Properties&nbsp;&nbsp;</th>
								<th>Content&nbsp;&nbsp;</th>
								<th>Description&nbsp;&nbsp;</th>
							</tr>
							<tbody id="table_projects">
							</tbody>
						
						
						</table>
						
						
						
						<!-- 
						<form action="projectproperties.jsp" method="get">
							<button type="submit" id="b_newproject">Create new project</button><br/>
							<input type="hidden" id=""/>
						</form>
						or
						 -->
						<br/>
						<br/>
						<br/>
						Upload a project ZIP-file: (Use our off-line editor to create a project, then ZIP it)
						
						<form onsubmit="return do_upload_zip()" method="post" enctype="multipart/form-data">
						    <input type="file" name="file" id="uploadzip_file"/>
							<button type="submit" class="btn btn-primary">Upload project</button> (Note: Please only click once and be patient. Especially for large files)
							<p><b id="uploadstatus">
							</b>
							</p>
						</form>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>







<jsp:include page="bottom.html" />
