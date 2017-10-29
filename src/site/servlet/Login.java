package site.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import site.Session;
import site.DocubricksSite;
import site.record.RecordUser;


/**
 * Log in
 */
@WebServlet("/Login")
public class Login extends DocubricksServlet
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
			RecordUser docrec=RecordUser.query(session.getConn(), request.getParameter("email"));
			if(docrec!=null)
				{
				if(docrec.passwordHashed.equals(CreateUser.encPass(request.getParameter("password"))) ||
						docrec.passwordHashed.equals("")) //Password removed - no password needed. quick reset
					{
		    	retob.put("status","1");
		    	session.session=Session.fromSession(request.getSession());
		    	session.session.userEmail=docrec.emailPrimary;
		    	session.session.toSession();
					}
				else
					retob.put("status","0");
				}
			else
	    	retob.put("status","0");
    	response.getWriter().append(retob.toJSONString());
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}
		}

	}
