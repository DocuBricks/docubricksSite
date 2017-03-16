package site.servlet;

import java.io.IOException;
import java.sql.SQLException;
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
public class GetDocumentList extends HttpServlet
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
		{
		try
			{
			JSONArray arr=new JSONArray();

			//TODO allow to query somehow. ... how?
			
			session.fromSession(request.getSession());
			List<RecordDocument> docs=RecordDocument.getUserDocuments(session);
			
			
			//Write all the simple variables
			for(RecordDocument d:docs)
				{
				JSONObject ob=new JSONObject();
				ob.put("id", ""+d.id); //Has to be a string since JS only has floating-point numbers
				ob.put("created", ""+d.timeCreated);
				ob.put("owner", d.documentOwner);
				
				ob.put("name", d.documentName);
				ob.put("image", d.documentImage);
				ob.put("description", d.documentDesc);
				ob.put("tags", d.documentTags);
				ob.put("ispublic", d.isPublic);

				arr.add(ob);
				}

			response.setContentType("application/json");
			response.getWriter().append(arr.toJSONString());
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
