list_projects = function() {
	var query_url, req;
	query_url = "GetDocumentList";
	req = $.getJSON(query_url,{user:""});
	req.success(function(data) {
		return list_projects_disp(data);
	});
	return req.fail(function(data) {
		return alert("failed to query data, " + query_url);
	});
};


list_projects_disp = function(data) {
	var link, rec, table, _i, _len;
	table = $("#table_projects");
	table.empty();
	for (_i = 0, _len = data.length; _i < _len; _i++) {
		rec = data[_i];
		var row = $("<tr>");
		table.append(row);
		
		/////////// view
		var td = $("<td>");
		row.append(td);
		var link = $("<a>");
		td.append(link);
		link.append(rec.name);
		link.attr({href:"viewer.jsp?id="+rec.id});
		table.append(row);

		/////////// edit prop
		td = $("<td>");
		link = $("<form action=\"projectproperties.jsp\">" +
				"<input type=\"hidden\" name=\"id\" value=\"" + rec.id +"\"/>"+
				"<button type=\"submit\">Edit</button>" +
				"</form>");
		row.append(td);
		td.append(link);

		////////////// edit content
		td = $("<td>");
		link = $("<form action=\"edit.jsp\">" +
				"<input type=\"hidden\" name=\"id\" value=\"" + rec.id +"\"/>"+
				"<button type=\"submit\">Edit</button>" +
				"</form>");
		row.append(td);
		td.append(link);

		
		
		//////////////// desc
		td = $("<td>");
		row.append(td);
		td.append(rec.description)
		
		
	}
};






$(function() 
	{
	list_projects();
	});




