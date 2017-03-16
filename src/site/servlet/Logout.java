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


/**
 * Log out
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet
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
		JSONObject retob=new JSONObject();
  	Session session=Session.fromSession(request.getSession());
  	session.userEmail=null;
  	session.toSession();
  	response.getWriter().append(retob.toJSONString());
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		doGet(request, response);
		}

	}
