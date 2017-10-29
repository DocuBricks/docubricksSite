package site.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import site.DocubricksSite;
import site.record.RecordDocument;

/**
 * Delete a document
 */
@WebServlet("/DeleteDocument")
public class DeleteDocument extends DocubricksServlet
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
			String id=request.getParameter("id");
			if(id==null)
				response.sendError(404, "No id specified");
			else
				{
				RecordDocument doc=session.getDocument(Long.parseLong(id.trim()));
				if(doc!=null)
					{
					//not quite!
					doc.delete(session.getConn());
			  	retob.put("status","1");
					}
				else
			  	retob.put("status","0");
				}
			response.getWriter().append(retob.toJSONString());
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		doGet(request, response);
		}

	}
