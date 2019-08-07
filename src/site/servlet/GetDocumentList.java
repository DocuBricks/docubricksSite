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
import site.record.QueryDocument;
import site.record.RecordDocument;
import site.record.RecordDocumentTag;
import site.record.RecordUser;

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
		response.setHeader("Access-Control-Allow-Origin","*");
		try(DocubricksSite session=new DocubricksSite())
			{
			session.fromSession(request.getSession());
			RecordUser user=session.getUserInfo();
			
			QueryDocument q=new QueryDocument();
			
			//Parameter: owner
			boolean isRootOrOwner=false;
			if(request.getParameter("ownerid")!=null)
				{
				int ownerid=Integer.parseInt(request.getParameter("ownerid"));
				q.setOwner(ownerid);
				if(user!=null)
					if(user.id==ownerid || user.isAdmin)
						isRootOrOwner=true;
				}
			
			//Parameter: also show nonpublic?
			if(request.getParameter("alsoprivate")!=null && isRootOrOwner)
				q.setShowOnlyPublic();
			
			//Write all the simple variables
			List<RecordDocument> docs=q.get(session);
			JSONArray arr=new JSONArray();
			for(RecordDocument d:docs)
				{
				JSONObject ob=new JSONObject();
				ob.put("id", ""+d.id); //Has to be a string since JS only has floating-point numbers
				ob.put("created", ""+d.timeCreated);
				ob.put("ownerid", d.documentOwnerID);
				
				ob.put("name", d.documentName);
				ob.put("image", d.documentImage);
				ob.put("description", d.documentDesc);
				ob.put("tags", d.getTagListComma());
				ob.put("ispublic", d.isPublic);
				ob.put("isfrozen", d.isFrozen);
				ob.put("shortlink", d.documentShortLink);

				JSONArray arrtags=new JSONArray();
				for(RecordDocumentTag t:d.tags)
					arrtags.add(t.documentTag);
				ob.put("tags", arrtags);
				
				arr.add(ob);
				}
			response.setContentType("application/json");
			response.getWriter().append(arr.toJSONString());
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}
		}


	}
