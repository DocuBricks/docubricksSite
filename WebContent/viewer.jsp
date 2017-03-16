<%@page import="net.minidev.json.JSONObject"%>
<%@page import="docubricks.data.DocubricksProject"%>
<%@page import="java.io.File"%>
<%@page import="org.jdom2.Element"%>
<%@page import="org.jdom2.output.XMLOutputter"%>
<%@page import="site.record.RecordDocument"%>
<%@page import="org.jdom2.Document"%>
<%@page import="java.io.StringReader"%>
<%@page import="site.util.EvXmlUtil"%>
<%@page import="site.DocubricksSite"%>
<%@page%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title></title>
        <link rel="stylesheet" href="css/docubricksViewer.css">
        <link rel="stylesheet" href="css/lightbox.css">
    </head>
    
    <body>
    
    
    
<!--  Original XML data goes in here  -->
<hiddendata class="hideclass" id="hiddendata"> 
	<%
	
	DocubricksSite ws=new DocubricksSite();
	ws.fromSession(request.getSession());
	


	String id=request.getParameter("id");
	if(id!=null)
		{
		RecordDocument doc=RecordDocument.query(ws.getConn(), Long.parseLong(id));
		if(doc!=null)
			{
			Element e=EvXmlUtil.stringToXml(doc.documentXML);
			File basepath=new File("."); //might not even matter here
			DocubricksProject p=DocubricksProject.fromXML(basepath, e);
			JSONObject ob=p.toJSON(basepath);
			out.println(ob.toJSONString());
			}
		else
			{
			out.println("Missing document");
			System.out.println("Missing document");
			}
		}
	else
		{
		out.println("Missing id");
		}


	%>
</hiddendata>
    
    
    
        <div id="example"></div>
        
        <script src="jquery-2.1.4.min.js"></script>
        <script src="./node_modules/react/dist/react.js"></script>
        <script src="./node_modules/react-dom/dist/react-dom.js"></script>
        
        
		<footer id="footer">
			<div class="footer">
			
				<div class="pull-left"></div>
				<div class="pull-right">
					<a href="terms-and-conditions.jsp">Terms and Conditions</a> | 
					<a href="privacy-policy.jsp">Privacy Policy</a> | &copy; 2015 <a href="/">Docubricks.com</a>
				</div>
	
			</div>
		</footer>
	
		<script type="text/javascript">
			var link = document.createElement('link');
			$('head').append('<link href="src/dbicon.ico" rel="icon">');
			
			<% 
			out.println("var projectid=\""+id+"/\";");
			out.println("var docubricksfilebase=\"./project/"+id+"/\";");
			%>
	
		</script>
		
		<script src="js/viewerbundle.js"></script>
		<script src="js/lightbox.min.js"></script>
		<script>
		    lightbox.option({
		      'resizeDuration': 200,
		      'fadeDuration': 	200,
		      'wrapAround': true
		    })
		</script>
        
    </body>
</html>



