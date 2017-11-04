package site.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import site.DocubricksSite;
import site.record.RecordDocument;

/**
 * Query for a list of documents
 */
@WebServlet("/GetDocumentList")
public class GetDocumentList extends DocubricksServlet
	{
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
		{
		try(DocubricksSite session=new DocubricksSite())
			{
			session.fromSession(request.getSession());
			if(session.loggedIn())
				{
				//Write all the simple variables
				List<RecordDocument> docs=RecordDocument.getUserDocuments(session);
				JSONArray arr=new JSONArray();
				for(RecordDocument d:docs)
					{
					JSONObject ob=new JSONObject();
					ob.put("id", ""+d.id); //Has to be a string since JS only has floating-point numbers
					ob.put("created", ""+d.timeCreated);
					//ob.put("owner", d.documentOwner);
					ob.put("ownerid", d.documentOwnerID);
					
					ob.put("name", d.documentName);
					ob.put("image", d.documentImage);
					ob.put("description", d.documentDesc);
					ob.put("tags", d.getTagListComma());
					ob.put("ispublic", d.isPublic);

					arr.add(ob);
					}
				response.setContentType("application/json");
				response.getWriter().append(arr.toJSONString());
				}
			else
				{
				System.err.println("User not logged in tried to show his/her projects");
				response.setContentType("application/json");
				JSONArray arr=new JSONArray();
				response.getWriter().append(arr.toJSONString());
				}
			
			
			

			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}
		}


	}
