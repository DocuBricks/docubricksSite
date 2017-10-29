package site.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import site.DocubricksSite;
import site.SendEmailAmazon;
import site.record.RecordUser;


/**
 * Email password to user
 */
@WebServlet("/EmailPassword")
public class EmailPassword extends DocubricksServlet
	{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		try(DocubricksSite session=new DocubricksSite())
			{
			String email=request.getParameter("email");
			RecordUser docrec=RecordUser.query(session.getConn(), email);
			String status="0";
			if(docrec!=null)
				{
				String newpass=""+(int)(Math.random()*1000000);
				
				SendEmailAmazon.send("noreply@docubricks.com", email, "Your password has been reset to: "+newpass, "DocuBricks password reset");
				docrec.passwordHashed=CreateUser.encPass(newpass);
				docrec.store(session.getConn());
				
				status="1";
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
