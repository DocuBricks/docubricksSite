<jsp:include page="header.html" />

<jsp:include page="top.jsp" />

<style type="text/css">
.bg-img-gosh { /* does not call image within CSS-file on local server */
	background: url("images/gosh-group_slim.jpg") no-repeat center center;
	width: 100%;
	z-index: -1;
}
</style>

<div id="main">
	<!-- comment: Main page with slogan and donate button -->
	<div class="container-top">
		<div class="row page-max-width">
			<div class="col-xs-9 h4">			
				<div class="lead">
					DocuBricks - high quality
					<a href="http://openhardware.science/about/why-gosh/"> Open Science</a>
					<a href="http://www.oshwa.org/definition/"> Hardware</a> documentations
				</div>
			</div>
			<div class="col-xs-3">
				<form action="https://www.paypal.com/cgi-bin/webscr" method="post">
					<input type="hidden" name="cmd" value="_donations">
					<input type="hidden" name="item_name" value="Donation">
					<input type="hidden" name="business" value="docubricks@gmail.com">
					<input type="submit" class="btn btn-info pull-right donate"
						value="DONATE">
				</form>
			</div>
		</div>
	</div>
</div>
	<br>

	<!-- 4 carousel containers with function, goals and examples  -->
	
	<div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="4000">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			<li data-target="#myCarousel" data-slide-to="1"></li>
			<li data-target="#myCarousel" data-slide-to="2"></li>
			<li data-target="#myCarousel" data-slide-to="3"></li>
		</ol>
	
		<!-- Wrapper for slides -->
		<div class="carousel-inner" role="listbox">
			<div class="item active">
				<div class="container-fluid container-carousel">
					<div class="row page-max-width">
						<div class="col-sm-7">
							<h2>Share your research tools</h2>
							<h4>
								The <a href="software.jsp"> DocuBricks editor</a> 
								helps you to easily create a good documentation that is modular and explained along functionality.
								Consider also the <a href="best-practise-guide.jsp"> best practise guide</a>.
								All uploads to this repository must be based on the DocuBricks format. This enables modular sharing,
								better readability and community quality management.
							</h4>
							<p>
								<strong>Example:</strong> Ball bearing dispenser
							</p>
						</div>
						<div class="col-sm-5">
							<div class="thumbnail-mod">
								<a href="http://docubricks.com/projects/ball-bearing-dispenser-4mm-for-dna-extraction-library-prep">
									<img class="img-responsive" src="images/Ball_bearing_dispenserS.JPG" alt=" Documentation ball dispenser"/>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
	
			<div class="item">
				<div class="container-fluid container-carousel">
					<div class="row page-max-width">
						<div class="col-sm-5">
							<div class="thumbnail-mod pull-right">
								<a href="http://docubricks.com/projects/openflexure-microscope">
									<img class="img-responsive" src="images/Open_flexture_microscopeS.jpg" alt=" WaterScope"/>
								</a>
							</div>
						</div>
						<div class="col-sm-7" style="clear: right">
							<h2>Cite and track impact</h2>
							<h4>
								The projects in our repository can receive a free <a href="http://www.doi.org/"> DOI</a>,
								to cite them in a scientific context. Soon you will be able to monitor the impact and reuse of your project
								via <a href="https://www.altmetric.com/"> Altmetrics</a>.
								Make your project more detectable by linking it to other web pages. You may also read the collection of
								<a href="impact-tools.jsp"> Impact Tools for Open Science Hardware</a>.
							</h4>
							<p>
								<strong>Example:</strong> Open Microscope and Publication
							</p>
						</div>
					</div>
				</div>
			</div>
	
			<div class="item">
				<div class="container-fluid container-carousel">
					<div class="row page-max-width">
						<div class="col-sm-7">
							<h2>Create hardware successfully</h2>
							<h4>
								Inventors submitting to this repository care about quality documentations, enabling you to recreate the
								hardware or protocol successfully. We manage user expectations with tags and a
								<a href="label-scheme.jsp"> label scheme</a>.
							</h4>
							<p>
								<strong>Example:</strong> Open Gelbox
							</p>
						</div>
						<div class="col-sm-5">
							<div class="thumbnail-mod">
								<a href="http://docubricks.com/projects/parametric-gel-electrophoresis-system">
									<img class="img-responsive" src="images/parametric_gelboxS.JPG" alt=" Parametric Gel Box" />
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>

	
			<div class="item">
				<div class="container-fluid container-carousel">
					<div class="row page-max-width">
						<div class="col-sm-5">
							<div class="thumbnail-mod pull-right">
								<a href="http://docubricks.com/projects/enclosure-for-the-rostock-max-v2-3d-printer">
									<img class="img-responsive" src="images/Enclosure_RMV2S2.jpg" alt=" Enclosure RM V2"/>
								</a>
							</div>
						</div>
						<div class="col-sm-7" style="clear: right">
							<h2>Improve and reuse projects</h2>
							<h4>
								With the DocuBricks <a href="software.jsp"> editor</a>,
								you can reuse modular components of other projects, extend them or easily rewrite a documentation
								for a different target group. You will also soon be able to add your comments to an existing documentation
								online. We are a supportive community: Most our users inform inventors when they use their project and thus
								make sharing worthwhile.
							</h4>
							<p>
								<strong>Example:</strong> Printer enclosure
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<!-- Left and right controls -->
		<a class="left carousel-control" href="#myCarousel" role="button"
			data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"
			aria-hidden="true"></span> <span class="sr-only">Previous</span>
		</a>
		<a class="right carousel-control" href="#myCarousel" role="button"
			data-slide="next"> <span class="glyphicon glyphicon-chevron-right"
			aria-hidden="true"></span> <span class="sr-only">Next</span>
		</a>
	</div>

		
	<!-- this fifth container has a background image  -->
	<div class="container-fluid-bg bg-img-gosh">
		<div class="row page-max-width">
			<div class="col-sm-9">
				<h4>
					<strong>Collaborate:</strong> Share your design files, images and usage protocols with your collaborators and
					support each other with comments during design iteration cycles. Allow the community to provide feedback and
					help you with your work.
				</h4>
					<br>
				<h4>
					<strong>Join and support our open source initiative:</strong> We develop our tools open source. Join us, contribute
					tools and let us know if you have improvement suggestions. We also appreciate <a href="#"> donations</a>
					to support our effort.
				</h4>
					<br>
				<p> 
				<a href="http://openhardware.science/about/why-gosh/">
				Image: GOSH participants </a></p>
			</div>
			<div class="col-sm-3">
				<a href="http://openhardware.science/about/why-gosh/">
					<span class="glyphicon glyphicon-globe globe-logo"></span>
				</a>
			</div>
		</div>
	</div>


	<!-- Container (Media Section) -->
	
	<div id="portfolio" class="container-fluid text-center page-max-width">
		<h2>In the media</h2>
		<h4>selected mentions of DocuBricks or its founders</h4>
			<br> 
			
		<!-- row1 -->
		<div class="row text-center">
			<div class="col-sm-4">
				<div class="thumbnail">
					<a href="http://www.nature.com/news/open-hardware-pioneers-push-for-low-cost-lab-kit-1.19518 "><img
					src="images/1 nature news.png" alt="Nature news" width="400" height="300"/></a>
					<p>
						<strong>Nature news</strong>
					</p>
					<p>Open Hardware pioneers</p>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="thumbnail">
					<a href="http://blogs.plos.org/synbio/2016/03/01/open-source-hardware-is-an-opportunity-for-synthetic-biology-research-the-docubricks-approach-by-tobias-wenzel/ "><img
					src="images/2 PLoS.png" alt="PLOS Synthetic Biology" width="400" height="300"/></a>
					<p>
						<strong>PLOS publisher</strong>
					</p>
					<p>Open Hardware for Synthetic Biology</p>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="thumbnail">
					<a href="http://irnas.eu/koruza/2015/07/26/open-plant-open-technology-week"><img
					src="images/3 IRNAS.png" alt="IRNAS institute" width="400" height="300"/></a>
					<p>
						<strong>IRNAS institute</strong>
					</p>
					<p>DocuBricks as Useful Source implementation</p>
				</div>
			</div>
		</div>
		
		<!-- row2 -->
		<div class="row text-center">
			<div class="col-sm-4">
				<div class="thumbnail">
					<a href="http://globalyoungacademy.net/report-gosh-2016-conference-gathering-for-open-science-hardware"><img
					src="images/4 GYA.png" alt="GYA" width="400" height="300"/></a>
					<p>
						<strong>Global Young Academy</strong>
					</p>
					<p>Report on Open Science Hardware Movement</p>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="thumbnail">
					<a href="http://openhardware.science/2016/03/06/open-source-hardware-from-the-plos-synbio-community"><img
					src="images/5 GOSH.png" alt="GOSH" width="400" height="300"/></a>
					<p>
						<strong>Open Science Hardware</strong>
					</p>
					<p>GOSH news</p>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="thumbnail">
					<a href="https://opencon2015cambridge.sched.org/event/4dvn/lightning-talks"><img
					src="images/6 OpenCon.png" alt="OpenCon15" width="400" height="300"/></a>
					<p>
						<strong>OpenCon 2015</strong>
					</p>
					<p>Lightning talks</p>
				</div>
			</div>
		</div>
		
		<!-- row3 -->
		<div class="row text-center">
			<div class="col-sm-4">
				<div class="thumbnail">
					<a href="https://www.cambridgenetwork.co.uk/events/caf-synthetique"><img
					src="images/7 Cambridge Network.png" alt="Cambridge Network" width="400" height="300"/></a>
					<p>
						<strong>Cambridge Network</strong>
					</p>
					<p>News and talks</p>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="thumbnail">
					<a href="http://2015.wutheringbytes.com/days/oshcamp/talks.html"><img
					src="images/10 wythering bytes.png" alt="wythering bytes" width="400" height="300"/></a>
					<p>
						<strong>Wythering Bytes</strong>
					</p>
					<p>Conference talks</p>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="thumbnail">
					<a href="http://www.synbio.cam.ac.uk/news/oshw-synbio"><img
					src="images/12 SynBio SRI.png" alt="SynBio SRI" width="400" height="300"/></a>
					<p>
						<strong>Cambridge SynBio SRI</strong>
					</p>
					<p>Synthetic Biology in Cambridge</p>
				</div>
			</div>
		</div>	
	</div>


	<!-- Add Google Maps -->
	<!-- 
	<div id="googleMap" style="height: 400px; width: 100%;"></div>
	
	<script src="http://maps.googleapis.com/maps/api/js" type="text/javascript"></script>
	<script type="text/javascript">
	var myCenter = new google.maps.LatLng(52.205798, 0.1133203);
	
	function initialize() {
	var mapProp = {
	  center:myCenter,
	  zoom:12,
	  scrollwheel:false,
	  draggable:false,
	  mapTypeId:google.maps.MapTypeId.ROADMAP
	  };
	
	var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
	
	var marker = new google.maps.Marker({
	  position:myCenter,
	  });
	
	marker.setMap(map);
	}
	
	google.maps.event.addDomListener(window, 'load', initialize);
	</script>
	 -->

	<!-- Container (Contact Section) -->
	
	<div id="contact" class="container-fluid">
	  <h2 class="text-center">CONTACT</h2>
	<!--   <div class="row"> -->
	<!--     <div class="col-sm-5"> -->
		<div class="container-fluid text-center">
	      <p>Contact us for information and suggestions.</p>
	      <p><span class="glyphicon glyphicon-map-marker"></span> Cambridge, UK</p>
	      <p><span class="glyphicon glyphicon-envelope"></span> docubricks[at]gmail.com</p>	   
<!-- 	    </div>
	    <div class="col-sm-7 slideanim">
	      <div class="row">
	        <div class="col-sm-6 form-group">
	          <input class="form-control" id="name" name="name" placeholder="Name" type="text" required>
	        </div>
	        <div class="col-sm-6 form-group">
	          <input class="form-control" id="email" name="email" placeholder="Email" type="email" required>
	        </div>
	      </div>
	      <textarea class="form-control" id="comments" name="comments" placeholder="Comment" rows="5"></textarea><br>
	      <div class="row">
	        <div class="col-sm-12 form-group">
	          <button class="btn btn-default pull-right" type="submit">Send</button>
	        </div>
	      </div>	
	    </div>
	  </div> -->
		</div>
	</div>

<jsp:include page="bottom.html" />
