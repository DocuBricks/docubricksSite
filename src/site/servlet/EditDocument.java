package site.servlet;

import java.io.IOException;
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
			System.out.println("-----"+id.trim()+"----");
			
			RecordDocument doc;
			if(id.trim().equals(""))
				{
				doc=new RecordDocument();
				doc.allocate(session.getConn());
				//TODO set owner here
	    	session.fromSession(request.getSession());
	    	doc.documentOwner=session.session.userEmail;
      	
				}
			else
				{
				doc=RecordDocument.query(session.getConn(), Long.parseLong(id.trim()));
				}
			
			doc.documentDesc=request.getParameter("description");
			doc.documentName=request.getParameter("name");
			doc.documentImage=request.getParameter("image");
					
			//TODO
			//doc.document=request.getParameter("tags");
			doc.isPublic=request.getParameter("published")!=null; //no idea???
			
			doc.store(session.getConn());
			
			retob.put("id", ""+doc.id);
			response.getWriter().append(retob.toJSONString());

			
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}
		}


	}
