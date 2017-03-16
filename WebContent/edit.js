var currentBrickID="";
var datachanged=false;
var currentBrickContent={};


function getParameterByName() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi,    
    function(m,key,value) {
      vars[key] = value;
    });
    return vars;
  }

var documentID=getParameterByName()["id"];

//TODO pick the top brick to edit?


populate_tree = function(data){
	document.getElementById("bricklist").innerHTML="";
	for(var bid in data.bricks){
		var dbrick = data.bricks[bid];
		
		var Mli=document.createElement("li");
		var Mlia=document.createElement("a");
	
		Mlia.setAttribute("onclick","showbrick("+bid+")");
		
		var pplisttxt = document.createTextNode(dbrick.name);
		Mlia.appendChild(pplisttxt);
		Mli.appendChild(Mlia);

		document.getElementById("bricklist").appendChild(Mli);
		
	}
	
}

showbrick = function(brickID){
	
	currentBrickID=brickID;
	
	getdata(true,true);
}



//TODO Autosave every N seconds. or at least when leaving page!

getdata = function(updateBrick, updateTree){
	var query_url = "GetDocumentJSON";
	var req = $.getJSON(query_url,{user:"", id:documentID});
	req.success(function(data) {
		populate_brick(data);
		populate_tree(data);
		if(datachanged){
			
		}

		return null; //what?
	});
	return req.fail(function(data) {
		return alert("failed to query data, " + query_url);
	});
	
	

}




populate_brick = function(data) {
	
	
//	currentBrickID="864571752";
	var dbrick = data.bricks[currentBrickID];
	currentBrickContent=dbrick;
//	alert(JSON.stringify(dbrick));

	if(currentBrickID!=""){
		$("#brick_name").attr({value:dbrick.name});
		$("#brick_abstract").attr({value:dbrick["abstract"]});
		$("#brick_description").attr({value:dbrick.description});
		$("#brick_notes").attr({value:dbrick.notes});
		$("#brick_license").attr({value:dbrick.license});
		
		//todo list of authors
		
		
		
		//Contents of this brick
		
		
		
		
		var xmls = document.getElementById("template_function").children[0];
		xmls = new XMLSerializer().serializeToString(xmls);
		var xmlDoc = $.parseXML( xmls )
		$xml = $( xmlDoc )

		for(var funci in dbrick.functions){ //maybe put id there instead? hm
			var func=dbrick.functions[funci];
			alert(JSON.stringify(func));
		}
		
		
		
		
		
		//todo all instructions
		
		
		
		//todo the BOM
		
		
	}
	
	
};







$(function() {
	getdata(true,true);
	});




