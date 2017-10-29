package site.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import net.minidev.json.JSONObject;
import site.DocubricksSite;
import site.Session;
import site.record.RecordUser;


/**
 * Create a user
 */
@WebServlet("/CreateUser")
public class CreateUser extends DocubricksServlet
	{
	private static final long serialVersionUID = 1L;

	
	
	public static String encPass(String pass)
		{
		return DigestUtils.sha1Hex(pass+"salteououeueooue");
		}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		try(DocubricksSite session=new DocubricksSite())
			{
			//If creating new user: check if user exists already
			boolean isEditing=request.getParameter("name").equals("1");
			String newEmail=request.getParameter("email");
			RecordUser prevuser=RecordUser.query(session.getConn(), newEmail);
			if(isEditing)
				{
				//Check if allowed to edit this user
				/*
				if(!session.session.userEmail.equals(request.getParameter("email")))
					{
					JSONObject retob=new JSONObject();
		    	retob.put("status","notallowed");
		    	response.getWriter().append(retob.toJSONString());
					}
					*/
				newEmail=prevuser.emailPrimary;  //create a new user id SEPARATE from email to allow easy updating
				}
			else
				{
				if(prevuser!=null)
					{
					JSONObject retob=new JSONObject();
		    	retob.put("status","alreadyexists");
		    	response.getWriter().append(retob.toJSONString());
					}
				}
			
			
			RecordUser docrec=new RecordUser();
			if(prevuser!=null)
				docrec=prevuser;
			
			docrec.firstName=request.getParameter("name");
			docrec.lastName=request.getParameter("surname");
			docrec.emailPrimary=newEmail;//request.getParameter("email");
			if(!request.getParameter("password").equals(""))
				docrec.passwordHashed=encPass(request.getParameter("password"));

			docrec.store(session.getConn());

			//Log in the new user
			session.session=Session.fromSession(request.getSession());
    	session.session.userEmail=docrec.emailPrimary;
    	session.session.toSession();

    	//Return response
			JSONObject retob=new JSONObject();
    	retob.put("status","1");
    	response.getWriter().append(retob.toJSONString());
			
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}

		
		}

	}
