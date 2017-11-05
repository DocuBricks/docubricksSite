package site.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import site.DocubricksSite;
import site.Session;
import site.record.RecordUser;


/**
 * Create a user
 */
@WebServlet("/EditUser")
public class EditUser extends DocubricksServlet
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
			session.session=Session.fromSession(request.getSession());
			RecordUser curuser=session.getUserInfo();
			if(curuser!=null)
				{
				int uid=Integer.parseInt(request.getParameter("uid").trim());
				if(curuser.isAdmin || curuser.id==uid)
					{
					RecordUser rec=session.getUserByID(uid);
					if(rec!=null)
						{
						rec.firstName=request.getParameter("name");
						rec.lastName=request.getParameter("surname");
						rec.emailPrimary=request.getParameter("email");
						if(!request.getParameter("password").equals(""))
							rec.setPassword(request.getParameter("password"));
						session.daoUser.update(rec);
			    	retob.put("status","1");
						}
					else
			    	retob.put("status","nosuchuser");
					}
				else
		    	retob.put("status","nopermission");
				}
			else
	    	retob.put("status","notloggedin");
    	response.getWriter().append(retob.toJSONString());
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}

		
		}

	}
