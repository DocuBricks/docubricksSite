<%@page import="org.jdom2.output.XMLOutputter"%>
<%@page import="site.record.RecordDocument"%>
<%@page import="org.jdom2.Document"%>
<%@page import="java.io.StringReader"%>
<%@page import="site.util.EvXmlUtil"%>
<%@page import="site.DocubricksSite"%>
<%@page%>
<?xml version="1.0" encoding="UTF-8"?>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link href="src/css/mui.min.css" rel="stylesheet" type="text/css" />
	<link href="src/css/main.css" rel="stylesheet" type="text/css" />
	<link href="static/style.css" rel="stylesheet" type="text/css" />
	<script src="src/js/mui.min.js"></script>
	<script src="src/js/jquery-2.1.4.min.js"></script>
	<script src="static/script.js"></script>
	<script src="src/js/docubricks.js"></script>
</head>


<!-- The visible content -->
<body>

	<div id="sidedrawer" class="mui--no-user-select">
		<div id="sidedrawer-brand"
			class="mui--appbar-line-height mui--text-title">Docubricks</div>
		<div class="mui-divider"></div>
		<ul class="nodebrick" id="bricklist"></ul>
		<ul id="partlistx"></ul>
	</div>

	<header id="header">
		<div class="mui-appbar mui--appbar-line-height">
			<div class="mui-container-fluid">
				<a
					class="sidedrawer-toggle mui--visible-xs-inline-block js-show-sidedrawer">☰</a>
				<a class="sidedrawer-toggle mui--hidden-xs js-hide-sidedrawer">☰</a>
				<span class="mui--text-title mui--visible-xs-inline-block">Docubricks</span>
			</div>
		</div>
	</header>

	<div id="content-wrapper">
		<div class="mui--appbar-height"></div>
		<div id="ccentre" class="mui-container-fluid"></div>
	</div>

	<footer id="footer">
		<div class="mui-container-fluid">
			<br /> Visit <a href="https://www.docubricks.org">Docubricks!</a>
		</div>
	</footer>

	<script type="text/javascript">
		var link = document.createElement('link');
		$('head').append('<link href="src/dbicon.ico" rel="icon">');
		
		<% 
		String id=request.getParameter("id");

		out.println("var docubricksfilebase=\"./project/"+id+"/\";");
		%>

		loadxmlSite();
	</script>
</body>

</html>
