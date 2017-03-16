package site.servlet;

import java.io.IOException;
import java.sql.SQLException;

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
public class Login extends HttpServlet
	{
	private static final long serialVersionUID = 1L;

	DocubricksSite site;
	
	@Override
	public void init() throws ServletException
		{
		super.init();
		try
			{
			site=new DocubricksSite();
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
			site.getConn().close();
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		try
			{
			JSONObject retob=new JSONObject();
			RecordUser docrec=RecordUser.query(site.getConn(), request.getParameter("email"));
			if(docrec!=null)
				{
				if(docrec.passwordHashed.equals(CreateUser.encPass(request.getParameter("password"))))
					{
		    	retob.put("status","1");
		    	Session session=Session.fromSession(request.getSession());
		    	session.userEmail=docrec.emailPrimary;
		    	session.toSession();
					}
				else
					retob.put("status","0");
				}
			else
	    	retob.put("status","0");
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
