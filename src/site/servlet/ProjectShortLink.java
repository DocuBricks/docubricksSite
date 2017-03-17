package site.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import site.DocubricksSite;


/**
 * Project short links in projects/****
 */
@WebServlet("/projects/*")
public class ProjectShortLink extends HttpServlet
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
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		try
			{
			//Parse out short name
			String url=request.getRequestURL().toString();
			if(url.endsWith("/"))
				url=url.substring(0,url.length()-1);
			int i=url.lastIndexOf("projects/");
			url=url.substring(i+"projects/".length());

			//Find the corresponding project id
			Long projectid=null;
			PreparedStatement ps=session.getConn().prepareStatement(
					"SELECT * FROM docubricks_document WHERE document_shortlink=?;");
			ps.setString(1, url);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
				projectid=rs.getLong("document_id");
			ps.close();
			rs.close();
			
			//Redirect
			if(projectid!=null) 
				response.sendRedirect("../viewer.jsp?id="+projectid);
			else
				response.sendError(500, "No such short name: "+url);
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
