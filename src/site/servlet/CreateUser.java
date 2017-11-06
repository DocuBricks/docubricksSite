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
			String newEmail=request.getParameter("email");
			RecordUser prevuser=session.getUserByEmail(newEmail);
			JSONObject retob=new JSONObject();
			if(prevuser!=null)
	    	retob.put("status","alreadyexists");
			else
				{
				RecordUser rec=new RecordUser();
				rec.firstName=request.getParameter("name").trim();
				rec.lastName=request.getParameter("surname").trim();
				rec.emailPrimary=newEmail.trim();
				String newpass=request.getParameter("password").trim();
				rec.setPassword(newpass);
				session.daoUser.create(rec);
					
				//Log in the new user
				session.session=Session.fromSession(request.getSession());
	    	session.session.userID=rec.id;
	    	session.session.toSession();
	    	retob.put("status","1");
				}

    	//Return response
    	response.getWriter().append(retob.toJSONString());
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}

		
		}

	}
