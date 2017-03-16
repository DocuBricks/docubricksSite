//jsx plugin for eclipse?

var CommentBox = React.createClass({
	render: function(){
		return <h1>whee</h1>;	
	}
});

	
	

	
//Bill of materials table for one brick 
var BrickBOM = React.createClass({
	render: function(){
	
		return 
		<div>
		<div class="col12 colExample" id="brickbomtable">
			<div class="divbom">
				<p class="p_h2" id="brickbomname">
					Subcomponents
				</p>
			</div>
			<p align="center">
				<table width="100%">
					<thead>
						<tr>
							<th>#</th>
							<th>DESCRIPTION</th>
							<th>QUANTITY</th>
							<th>SUPPLIER</th>
						</tr>
					</thead>
					<tbody id="brickbombody">
					</tbody>
				</table>
			</p>
		</div>
		<tr id="brickbomrow">
			<td id="partnum"></td>
			<td><a id="description"> </a></td>
			<td id="quantity"></td>
			<td id="supplier"></td>
		</tr>
		</div>;
	}
});


	

//Bill of materials table for the total project 
var TotalBOM = React.createClass({
	render: function(){
		return 
		<div>
		<div id="totalbomtable">
			<div class="project_title">
				<h1>
					Bill of materials
				</h1>
			</div>
			<div class="col12 colExample">
				<p align="center">
					<table width="100%">
						<thead>
							<tr>
								<th>#</th>
								<th>DESCRIPTION</th>
								<th>QUANTITY</th>
								<th>SUPPLIER</th>
							</tr>
						</thead>
						<tbody id="totalbombody">
						</tbody>
					</table>
				</p>
			</div>
		</div>
		<tr id="totalbomrow">
			<td id="partnum"></td>
			<td><a id="description"> </a></td>
			<td id="quantity"></td>
			<td id="supplier"></td>
		</tr>
		</div>;
	}
});
	
	
	

//Bill of materials table for one step 
var StepBOM = React.createClass({
	render: function(){

		return 
		<div>
		<div id="stepbomtable">
			<div class="divbom">
				<p class="p_h2" id="stepbomname">
					Referenced components
				</p>
			</div>
			<p align="center">
				<table>
					<thead>
						<tr>
							<th>DESCRIPTION</th>
							<th>QUANTITY</th>
						</tr>
					</thead>
					<tbody id="stepbombody">
					</tbody>
				</table>
			</p>
		</div>
		<tr id="stepbomrow">
			<td><a id="description"> </a></td>
			<td id="quantity"></td>
		</tr>
		</div>;
	}
});


//Instruction table 
var StepBOM = React.createClass({
	render: function(){
		return 
		<div>
		<div class="col12 colExample" id="instructiontable">
			<div class="divbom">
				<p class="p_h2" id="instructionname"></p>
			</div>
		</div>
		<div id="instructionstep">
		</div>
		</div>;
	}
});

//one brick 
var Brick = React.createClass({
	render: function(){
		<div class="mui-panel" id="brickcontent">
		</div>;
	}
});


var run2 = function(){
ReactDOM.render(
	<CommentBox />,
	document.getElementById('ccentre')
	);
	alert("foo");
	}