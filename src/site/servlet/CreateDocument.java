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
 * Create a document
 */
@WebServlet("/CreateDocument")
public class CreateDocument extends DocubricksServlet
	{
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		try(DocubricksSite session=new DocubricksSite())
			{
			RecordDocument docrec=new RecordDocument();

			session.fromSession(request.getSession());
			docrec.documentOwnerID=session.session.userID;
//			docrec.documentOwner=session.session.userEmail;
			docrec.allocate(session);
			
			JSONObject retob=new JSONObject();
    	retob.put("id",""+docrec.id);
//    	retob.put("error", "Zip does not contain a docubrick");
    	response.getWriter().append(retob.toJSONString());
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}
		}

	}
