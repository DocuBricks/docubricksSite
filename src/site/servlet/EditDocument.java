package site.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import site.Session;
import site.DocubricksSite;
import site.record.RecordDocument;

/**
 * 
 */
@WebServlet("/EditDocument")
@MultipartConfig
public class EditDocument extends HttpServlet
	{
	private static final long serialVersionUID = 1L;

	
	DocubricksSite session;
	
	@Override
	public void init() throws ServletException
		{
		super.init();
		try
			{
			session=new DocubricksSite();
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}
		}

	
	@Override
	public void destroy()
		{
		super.destroy();
		try
			{
			session.getConn().close();
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		}

	
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		try
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
	    	Session session=Session.fromSession(request.getSession());
	    	doc.documentOwner=session.userEmail;
      	
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
