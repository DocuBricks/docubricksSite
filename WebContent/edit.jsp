<%@page import="site.DocubricksSite"%>
<%@page%>


<%
	DocubricksSite ws=new DocubricksSite();
	ws.fromSession(request.getSession());
%>


<jsp:include page="header.html" />
<script src="edit.js"></script>
<!--  <script src="js/autosize.js"></script>  -->
<jsp:include page="top.jsp" />





<div id="sidedrawer" class="mui--no-user-select">
	<ul class="nodebrick" id="bricklist"></ul>
	<ul id="partlistx"></ul>
	<button type="submit" class="btn btn-primary">New part</button>
</div>



<div id="main">
	<div class="mui-container-fluid">
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-default">
					<div class="panel-heading">Brick overview</div>
					<div class="panel-body">
					
						<div class="form-group">
							<label class="col-md-4 control-label">Name</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="brick_name" value="" id="brick_name">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Abstract</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="brick_abstract" value="" id="brick_abstract">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Description</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="brick_description" value="" id="brick_description">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Notes</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="brick_notes" value="" id="brick_notes">
							</div>
						</div>
					
					
						<div class="col-md-6">
							<button type="submit" class="btn btn-primary">Add media/file</button>
						</div>
				
					</div>
					<div class="panel-heading">Copyright</div>
					<div class="panel-body">
						<div class="form-group">
							<label class="col-md-4 control-label">License</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="brick_license" value="" id="brick_license">
							</div>
						</div>
					

						<div class="form-group">
							<label class="col-md-4 control-label">Authors</label>
							<div class="col-md-6" id="brick_authors">
								<button class="btn btn-primary">Johan Henriksson</button>
							</div>
							
							<div class="col-md-6">
								<button type="submit" class="btn btn-primary">Add author</button>
							</div>
						</div>

						
						
						

					</div>
					<div class="panel-heading">Contents of this brick</div>
					<div class="panel-body" id="brick_functions">
					</div>
					
					
					
					<div class="panel-heading">
						Instruction for:						
						<input type="text" list="instructiontypes" />
						<datalist id="instructiontypes">
						  <option>Assembly</option>
						  <option>Repair</option>
						  <option>Testing</option>
						  <option>Calibration</option>						  
						</datalist>
					</div>
					<div class="panel-body">



						<div class="form-group">
							<div class="col-md-4 control-label">
								<div>
									<p align="left">
										<img src="./project/8771009436260543488/img/manufacturing1.jpg" width="100%">
									</p>
								</div>
							</div>
							<textarea class="col-md-6" name="description" id="projprop_description">
								Begin by manufacturing all 3D printed parts.
								Follow the instructions in the Manufacturing section. If using
								KORUZA DIY kit, 3D printed parts are already included.
							</textarea>
						</div>

						<button type="submit" class="btn btn-primary">Add new type of instruction</button>
						<button type="submit" class="btn btn-primary">Add step</button>

						<button type="submit" class="btn btn-primary">Move up/down/delete/insert before/add component ref</button>

				
					
					
					</div>
				</div>
			</div>
		</div>
	</div>
</div>




<hiddendata class="hideclass" id="template_function">
	<div>

		<div class="form-group">
			<label class="col-md-4 control-label">Name of function (optional):</label>
			<div class="col-md-6">
				<input type="text" class="form-control" name="brick_notes" value="" id="brick_notes">
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label">Designator:</label>
			<div class="col-md-6">
				<input type="text" class="form-control" name="brick_notes" value="" id="brick_notes">
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label">Quantity:</label>
			<div class="col-md-6">
				<input type="text" class="form-control" name="brick_notes" value="" id="brick_notes">
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label">Parts/Bricks implementing this function:</label>
			<div class="col-md-6" id="brick_implementations">
				<button class="btn btn-primary">Part: </button>
			</div>
			<div class="col-md-6">
				<button type="submit" class="btn btn-primary">Add implementation</button>
			</div>
		</div>
		<div class="col-md-6">
			<button type="submit" class="btn btn-primary">Add function</button>
		</div>
	</div>

</hiddendata>


<jsp:include page="bottom.html" />

<%
	ws.close();
%>
