<%@page import="org.jdom2.output.XMLOutputter"%>
<%@page import="site.util.EvXmlUtil"%>
<%@page import="org.jdom2.Document"%>
<%@page import="java.io.StringReader"%>
<%@page import="site.record.RecordDocument"%>
<%@page import="site.DocubricksSite"%>

<jsp:include page="header.html" />
<jsp:include page="top.jsp" />


<%
DocubricksSite ws=new DocubricksSite();
ws.fromSession(request.getSession());

RecordDocument doc=null;

String id=request.getParameter("id");
if(id!=null)
	{
	doc=RecordDocument.query(ws.getConn(), Long.parseLong(id));
	if(doc!=null)
		{
		/*
		StringReader r=new StringReader(doc.documentXML);
		Document xml=EvXmlUtil.readXML(r);
		r.close();
		
		XMLOutputter outp = new XMLOutputter();
		out.println(outp.outputString(xml.getRootElement()));
		*/
		}
	else
		{
		//out.println("Missing document");
		}
	}
else
	{
	//out.println("Missing id");
	id="";
	doc=new RecordDocument();
	//Fill in some defaults?
	}
%>


<div id="main">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-default">
					<div class="panel-heading">Project properties</div>
					<div class="panel-body">

						<form class="form-horizontal" role="form" method="POST" onsubmit="return do_editproject()">

							<input type="hidden" name="id" value="<% out.println(id); %>" id="projprop_id">
							<input type="hidden" name="image" value="<% out.println(doc.documentImage); %>" id="projprop_image">

							<div class="form-group">
								<label class="col-md-4 control-label">Name</label>
								<div class="col-md-6">
									<input type="text" class="form-control" name="name" value="<% out.println(doc.documentName); %>" id="projprop_name">
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Description</label>
								<div class="col-md-6">
									<textarea class="form-control" name="description" id="projprop_description"><% out.println(doc.documentDesc); %></textarea>
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Tags</label>
								<div class="col-md-6">
									<input type="text" class="form-control" name="tags" value="<% out.println(doc.documentTags); %>" id="projprop_tags">
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Published</label>
								<div class="col-md-6">
									<input type="checkbox" class="form-control" name="published" <% out.println(doc.isPublic ? "checked":""); %> id="projprop_published">
								</div>
							</div>



							<div class="form-group">
								<div class="col-md-6 col-md-offset-4">
									<button type="submit" class="btn btn-primary">Save</button>
								</div>
							</div>
							
							
							
						</form>

						<!-- ##################################################################################### -->
						<hr/>

						<form onsubmit="return do_uploadprojimage()" method="post" enctype="multipart/form-data">
							<div class="form-group">
								<label class="col-md-4 control-label">Representative image</label>
								<div class="col-md-6">
									<img width=200
										src="<% 
										if(doc.documentImage.equals("")) 
											out.println("images/open_hardware_logo.png");
										else
											out.println("project/"+doc.id+"/"+doc.documentImage); 
												%>"
										class="thumbnail center-block img-responsive" />
								</div>
							</div>


						    <input type="file" name="file" id="uploadprojimage_file"/>
							<button type="submit" class="btn btn-primary">Upload image</button>
						</form>

						
						<!-- ##################################################################################### -->
						<hr/>
						
						<form class="form-horizontal" role="form" method="POST" onsubmit="return do_deletedocument()">
							<input type="hidden" name="id" value="<% out.println(id); %>" id="deletedocument_id">
							<div class="form-group">
								<div class="col-md-6 col-md-offset-4">
									<button type="submit" class="btn btn-primary">Delete document</button>
								</div>
							</div>
						</form>
						
						<hr/>
						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="bottom.html" />
