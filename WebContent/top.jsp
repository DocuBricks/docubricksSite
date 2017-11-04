<%@page import="site.record.RecordUser"%>
<%@page import="site.DocubricksSite"%>
	 
	 
</head>
<body>

<jsp:include page="ganalytics.html" />

	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle Navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="pull-left logo-link" href="http://www.docubricks.com/">
					<img width="180" src="images/db-logo-medium.png" />
				</a>
			</div>

			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">

				<ul class="nav navbar-nav navbar-right">
					<li>
					<li><a href="search.jsp">Projects</a>
<!-- 						<form method="GET" action="search.jsp" -->
<!-- 							accept-charset="UTF-8" class="navbar-form"> -->
<!-- 							<div class="input-group"> -->
<!-- 								Discover Projects: -->
<!-- 								<input type="text" class="form-control" -->
<!-- 									placeholder="Discover Projects" name="q"/> -->
<!-- 								<div class="input-group-btn"> -->
<!-- 									<button class="btn btn-default" type="submit"> -->
<!-- 										<i class="glyphicon glyphicon-search"></i> -->
<!-- 									</button> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</form> -->
					</li>
					<li><a href="software.jsp">Software</a></li>
					<li><a href="resources.jsp">Resources</a></li>
					<li><a href="faq.jsp">FAQ</a></li>
					<li><a href="about.jsp">About</a></li>

					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false"><span
							class="glyphicon glyphicon-user" aria-hidden="true"></span><span
							class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
						
						<%
						DocubricksSite ws2=new DocubricksSite();
						ws2.fromSession(request.getSession());

						if(ws2.loggedIn())
							{
							RecordUser recuser=ws2.getUserInfo();
							
							%>
							<li><% out.println(recuser.emailPrimary==null ? "" : recuser.emailPrimary); %></li>
							<li><a href="mypage.jsp">My projects</a></li>
							<li><a href="edituser.jsp">Account settings</a></li>
							<li><a onclick="return do_logout()">Logout</a></li>
							<%
							}
						else
							{
							%>
							<li><a href="login.jsp">Login</a></li>
							<li><a href="register.jsp">Register</a></li>
							<%
							}
						
						ws2.close();

						%>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>


