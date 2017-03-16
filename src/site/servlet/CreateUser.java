package site.servlet;

import java.io.IOException;
import java.sql.SQLException;

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
public class CreateUser extends HttpServlet
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
	
	
	public static String encPass(String pass)
		{
		return DigestUtils.sha1Hex(pass+"salteououeueooue");
		}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		try
			{
			RecordUser docrec=new RecordUser();
			//docrec.allocate(session.getConn());
			
			docrec.firstName=request.getParameter("name");
			docrec.lastName=request.getParameter("surname");
			docrec.emailPrimary=request.getParameter("email");
			docrec.passwordHashed=encPass(request.getParameter("password"));

			//TODO check if user exists already
			docrec.store(session.getConn());

			//Log in the new user
    	Session session=Session.fromSession(request.getSession());
    	session.userEmail=docrec.emailPrimary;
    	session.toSession();

    	//Return response
			JSONObject retob=new JSONObject();
    	retob.put("status","1");
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
