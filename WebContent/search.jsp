<%@page import="java.util.List"%>
<%@page import="site.DocubricksSite"%>
<%@page import="site.record.RecordDocument"%>

<jsp:include page="header.html" />
<jsp:include page="top.jsp" />


<div id="main">
	<div class="container">
		<hgroup class="mb20">
			<h1>Projects</h1>
		</hgroup>
		<br/>
		
		<section class="col-xs-12 col-sm-6 col-md-12">
			
			<%
			DocubricksSite ws=new DocubricksSite();
			ws.fromSession(request.getSession());
			List<RecordDocument> listdocs=RecordDocument.searchDocuments(ws,"");
			
			for(RecordDocument doc:listdocs)
				{
				String docurl="viewer.jsp?id="+doc.id;
				%>
				

				<article class="search-result row">
					<div class="col-xs-12 col-sm-12 col-md-3">
						<a href="<% out.println(docurl); %>" title="">
						
						<img width=200
							src="<% 
							if(doc.documentImage.equals("")) 
								out.println("images/open_hardware_logo.png");
							else
								out.println("project/"+doc.id+"/"+doc.documentImage); 
									%>"
							class="thumbnail center-block img-responsive" />
						
							
						</a>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-9">
						<h3>
							<a href="<% out.println(docurl); %>"
								title=""><% out.println(doc.documentName); %></a>
						</h3>
						<div class="excerpt"><% out.println(doc.documentDesc); %></div>
					</div>
					<span class="clearfix borda"></span>
				</article>


				
				<%
				}
			
			%>
			
			<!-- 
			
			
			<article class="search-result row">
				<div class="col-xs-12 col-sm-12 col-md-3">
					<a href="/projects/enclosure-for-the-rostock-max-v2-3d-printer"
						title="Lorem ipsum"><img width=200
						src="/img/projects/d3d9446802a44259755d38e6d163e82010/home/img.jpg"
						alt="Enclosure for the Rostock Max V2 3D-Printer"
						class="thumbnail center-block img-responsive" /></a>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-9">
					<h3>
						<a href="/projects/enclosure-for-the-rostock-max-v2-3d-printer"
							title="">Enclosure for the Rostock Max V2 3D-Printer</a>
					</h3>
					<div class="excerpt">This is a documentation to build a DIY
						enclosure for the 3D printer model Rostock Max V2 that is part of
						the RepRap family of 3d printers and sold by SeeMeCNC and through
						various resellers. Enclosures are typically self-built, because a
						USA patent (attached) seems to be an obstacle for most providers
						to sell them as standard accessory. This project uses PLA and ABS
						prints with the printer itself, laser cut acrylic sheets and a few
						metric of-the-shelf components only. Enclosures are desirable
						to...</div>
				</div>
				<span class="clearfix borda"></span>
			</article>
			
			
			
			
			
			
			<article class="search-result row">
				<div class="col-xs-12 col-sm-12 col-md-3">
					<a href="/projects/openflexure-microscope" title="Lorem ipsum"><img width=200
						src="/img/projects/c20ad4d76fe97759aa27a0c99bff671012/home/img.jpg"
						alt="OpenFlexure Microscope"
						class="thumbnail center-block img-responsive" /></a>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-9">
					<h3>
						<a href="/projects/openflexure-microscope" title="">OpenFlexure Microscope</a>
					</h3>
					<div class="excerpt">Optomechanics is a crucial part of any
						microscope; when working at high magnification, it is absolutely
						crucial to keep the sample steady and to be able to bring it into
						focus precisely. Accurate motion control is extremely difficult
						using printed mechanical parts, as good linear motion typically
						requires tight tolerances and a smooth surface finish. This design
						for a 3D printed microscope stage uses plastic flexures, meaning
						its motion is free from friction and vibration. It achieves
						steps...</div>
				</div>
				<span class="clearfix borda"></span>
			</article>
			
						 -->
			
		</section>
		<ul class="pagination pull-right">
		</ul>
	</div>
</div>

<jsp:include page="bottom.html" />
