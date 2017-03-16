package site.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import site.DocubricksSite;
import site.record.RecordDocument;

/**
 * Get a single document XML
 */
@WebServlet("/GetDocumentXML")
public class GetDocumentXML extends HttpServlet
	{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		try
			{
			DocubricksSite session=new DocubricksSite();
			String id=request.getParameter("id");
			if(id==null)
				response.sendError(404, "No id specified");
			else
				{
				RecordDocument doc=session.getDocument(Long.parseLong(id.trim()));
				if(doc!=null)
					{
					//not quite!
					response.getWriter().append(doc.documentXML);
					}
				else
					response.getWriter().append("null");
				}
			}
		catch (SQLException e)
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
