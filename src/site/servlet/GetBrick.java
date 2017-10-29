package site.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import site.DocubricksSite;
import site.record.RecordDocument;

/**
 * Get XML for a brick. Well. Do we need this one at all? Can easily use the full document and pull out from there
 */
@WebServlet("/GetBrick")
public class GetBrick extends DocubricksServlet
	{
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		try(DocubricksSite session=new DocubricksSite())
			{
			String id=request.getParameter("id");
			if(id==null)
				response.sendError(404, "No id specified");
			else
				{
				RecordDocument doc=session.getDocument(Long.parseLong(id));
				if(doc!=null)
					{
					//not quite!
					response.getWriter().append(doc.documentXML);
					}
				else
					response.getWriter().append("null");
				}
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}
		}


	}
