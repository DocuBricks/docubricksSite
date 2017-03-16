//jsx plugin for eclipse?

var CommentBox = React.createClass({displayName: "CommentBox",
	render: function(){
		return React.createElement("h1", null, "whee");	
	}
});

	
	

	
//Bill of materials table for one brick 
var BrickBOM = React.createClass({displayName: "BrickBOM",
	render: function(){
	
		return 
		React.createElement("div", null, 
		React.createElement("div", {class: "col12 colExample", id: "brickbomtable"}, 
			React.createElement("div", {class: "divbom"}, 
				React.createElement("p", {class: "p_h2", id: "brickbomname"}, 
					"Subcomponents"
				)
			), 
			React.createElement("p", {align: "center"}, 
				React.createElement("table", {width: "100%"}, 
					React.createElement("thead", null, 
						React.createElement("tr", null, 
							React.createElement("th", null, "#"), 
							React.createElement("th", null, "DESCRIPTION"), 
							React.createElement("th", null, "QUANTITY"), 
							React.createElement("th", null, "SUPPLIER")
						)
					), 
					React.createElement("tbody", {id: "brickbombody"}
					)
				)
			)
		), 
		React.createElement("tr", {id: "brickbomrow"}, 
			React.createElement("td", {id: "partnum"}), 
			React.createElement("td", null, React.createElement("a", {id: "description"}, " ")), 
			React.createElement("td", {id: "quantity"}), 
			React.createElement("td", {id: "supplier"})
		)
		);
	}
});


	

//Bill of materials table for the total project 
var TotalBOM = React.createClass({displayName: "TotalBOM",
	render: function(){
		return 
		React.createElement("div", null, 
		React.createElement("div", {id: "totalbomtable"}, 
			React.createElement("div", {class: "project_title"}, 
				React.createElement("h1", null, 
					"Bill of materials"
				)
			), 
			React.createElement("div", {class: "col12 colExample"}, 
				React.createElement("p", {align: "center"}, 
					React.createElement("table", {width: "100%"}, 
						React.createElement("thead", null, 
							React.createElement("tr", null, 
								React.createElement("th", null, "#"), 
								React.createElement("th", null, "DESCRIPTION"), 
								React.createElement("th", null, "QUANTITY"), 
								React.createElement("th", null, "SUPPLIER")
							)
						), 
						React.createElement("tbody", {id: "totalbombody"}
						)
					)
				)
			)
		), 
		React.createElement("tr", {id: "totalbomrow"}, 
			React.createElement("td", {id: "partnum"}), 
			React.createElement("td", null, React.createElement("a", {id: "description"}, " ")), 
			React.createElement("td", {id: "quantity"}), 
			React.createElement("td", {id: "supplier"})
		)
		);
	}
});
	
	
	

//Bill of materials table for one step 
var StepBOM = React.createClass({displayName: "StepBOM",
	render: function(){

		return 
		React.createElement("div", null, 
		React.createElement("div", {id: "stepbomtable"}, 
			React.createElement("div", {class: "divbom"}, 
				React.createElement("p", {class: "p_h2", id: "stepbomname"}, 
					"Referenced components"
				)
			), 
			React.createElement("p", {align: "center"}, 
				React.createElement("table", null, 
					React.createElement("thead", null, 
						React.createElement("tr", null, 
							React.createElement("th", null, "DESCRIPTION"), 
							React.createElement("th", null, "QUANTITY")
						)
					), 
					React.createElement("tbody", {id: "stepbombody"}
					)
				)
			)
		), 
		React.createElement("tr", {id: "stepbomrow"}, 
			React.createElement("td", null, React.createElement("a", {id: "description"}, " ")), 
			React.createElement("td", {id: "quantity"})
		)
		);
	}
});


//Instruction table 
var StepBOM = React.createClass({displayName: "StepBOM",
	render: function(){
		return 
		React.createElement("div", null, 
		React.createElement("div", {class: "col12 colExample", id: "instructiontable"}, 
			React.createElement("div", {class: "divbom"}, 
				React.createElement("p", {class: "p_h2", id: "instructionname"})
			)
		), 
		React.createElement("div", {id: "instructionstep"}
		)
		);
	}
});

//one brick 
var Brick = React.createClass({displayName: "Brick",
	render: function(){
		React.createElement("div", {class: "mui-panel", id: "brickcontent"}
		);
	}
});


var run2 = function(){
ReactDOM.render(
	React.createElement(CommentBox, null),
	document.getElementById('ccentre')
	);
	alert("foo");
	}
