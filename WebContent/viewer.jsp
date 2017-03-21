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
        <link rel="stylesheet" href="css/docubricks.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.9.0/css/lightbox.min.css" />
        <link href="src/dbicon.ico" rel="icon" />
    </head>
    
    <body>
    
		    
		    
		<!--  Original XML data goes in here  -->
		<div class="hideclass" id="hiddendata"> 
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
		</div>
		    
	    
    
        <div id="example">
          <h1 id="loading-text">Loading...</h1>
        </div>
        
        <script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="js/react.js"></script>
        <script src="js/react-dom.js"></script>
		<script src="js/viewerbundle.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.9.0/js/lightbox.min.js"></script>
		<script>
		    lightbox.option({
		      'resizeDuration': 200,
		      'fadeDuration': 	200,
		      'wrapAround': true
		    })
		</script>
        
		<jsp:include page="ganalytics.html" />
        
		<footer id="footer">
			<div class="footer">
			
				<div class="pull-left"></div>
				<div class="pull-right">
					<a href="terms-and-conditions.jsp">Terms and Conditions</a> | 
					<a href="privacy-policy.jsp">Privacy Policy</a> | &copy; 2017 <a href="/">Docubricks.com</a>
				</div>
	
			</div>
		</footer>
	
        
    </body>
</html>



