package site.servlet;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import site.DocubricksSite;
import site.record.RecordDocument;

/**
 * 
 */
@WebServlet("/EditDocument")
@MultipartConfig
public class EditDocument extends DocubricksServlet
	{
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		try(DocubricksSite session=new DocubricksSite())
			{
			JSONObject retob=new JSONObject();

			String id=request.getParameter("id").trim();
			
			RecordDocument doc;
			if(id.trim().equals(""))
				{
				doc=new RecordDocument();
				doc.allocate(session);
				//TODO set owner here
	    	session.fromSession(request.getSession());
	    	doc.documentOwnerID=session.session.userID;
//	    	doc.documentOwner=session.session.userEmail;
      	
				}
			else
				{
				doc=session.getDocument(Long.parseLong(id.trim()));
				}

			//Check which shortlinks are used already
			HashSet<String> usedShortLinks=new HashSet<>();
			for(RecordDocument o:session.daoDocument.queryForAll()) 
				{
				//TODO: only pull out the relevant info
				if(o.id!=doc.id)
					usedShortLinks.add(o.documentShortLink);
				}
			
			String newshortlink=request.getParameter("shortlink");
			if(!newshortlink.equals("") && usedShortLinks.contains(newshortlink))
				{
				retob.put("id", ""+doc.id);
				retob.put("success", "0");
				response.getWriter().append(retob.toJSONString());
				System.err.println("Tried to set the same short link as another project");
				}
			else
				{
				doc.documentDesc=request.getParameter("description").trim();
				doc.documentName=request.getParameter("name").trim();
				doc.documentImage=request.getParameter("image").trim();
				doc.setTagsFromComma(request.getParameter("tags").trim());
				doc.documentShortLink=newshortlink.trim();
				
				
//				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//				System.out.println(request.getParameter("ispublic"));
				doc.isPublic=request.getParameter("ispublic").trim().equals("true");
				session.daoDocument.update(doc);
				doc.saveTags(session);
				
				retob.put("id", ""+doc.id);
				retob.put("success", "1");
				response.getWriter().append(retob.toJSONString());
				}
			

			
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}
		}


	}
