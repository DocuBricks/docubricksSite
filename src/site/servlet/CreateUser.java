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
			boolean isEditing=request.getParameter("edit").equals("1");
			String newEmail=request.getParameter("email");
			RecordUser prevuser=session.getUserByEmail(newEmail);
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
			
			
			RecordUser rec=new RecordUser();
			if(prevuser!=null)
				rec=prevuser;
			
			rec.firstName=request.getParameter("name");
			rec.lastName=request.getParameter("surname");
			rec.emailPrimary=newEmail;//request.getParameter("email");
			if(!request.getParameter("password").equals(""))
				rec.setPassword(request.getParameter("password"));

			if(isEditing)
				session.daoUser.update(rec);
			else
				{
				session.daoUser.create(rec);
				
				//Log in the new user
				session.session=Session.fromSession(request.getSession());
	    	session.session.userID=rec.id;
//	    	session.session.userEmail=docrec.emailPrimary;
	    	session.session.toSession();
				}


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
