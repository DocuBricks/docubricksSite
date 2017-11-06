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
 * Reset the password, after email link
 */
@WebServlet("/EmailPasswordConfirm")
public class EmailPasswordConfirm extends DocubricksServlet
	{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		try(DocubricksSite session=new DocubricksSite())
			{
			String uid=request.getParameter("uid").trim();
			String resetcode=request.getParameter("code").trim();
			String newpass=request.getParameter("password").trim();
			RecordUser rec=session.getUserByID(Integer.parseInt(uid));
			String status="0";
			if(rec!=null)
				{
				System.out.println(rec.passResetTime);
				if(System.currentTimeMillis()<rec.passResetTime+24*3600*1000)
					{
					if(rec.passResetCode.equals(resetcode))
						{
						//Reset the password
						rec.passResetTime=0;
						rec.setPassword(newpass);
						session.daoUser.update(rec);

						//Log in user
			    	session.session=Session.fromSession(request.getSession());
			    	session.session.userID=rec.id;
			    	session.session.toSession();

						status="1";
						}
					else
						System.err.println("Trying to reset password for user with wrong code");
					}
				else
					System.err.println("Trying to reset password for user with too late request");
				}
			else
				{
				System.out.println("Trying to reset password for non-existing user");
				}
			
    	//Return response
			JSONObject retob=new JSONObject();
    	retob.put("status",status);
    	response.getWriter().append(retob.toJSONString());
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}
		}


	}
