package site.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.Element;

import docubricks.data.DocubricksProject;
import net.minidev.json.JSONObject;
import site.DocubricksSite;
import site.record.RecordDocument;
import site.util.EvXmlUtil;

/**
 * Get a single document XML
 */
@WebServlet("/GetDocumentJSON")
public class GetDocumentJSON extends DocubricksServlet
	{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		response.setHeader("Access-Control-Allow-Origin","*");
		try(DocubricksSite session=new DocubricksSite())
			{
			String id=request.getParameter("id");
			if(id==null)
				response.sendError(404, "No id specified");
			else
				{
				RecordDocument doc=session.getDocument(Long.parseLong(id.trim()));
				if(doc!=null)
					{
					Element e=EvXmlUtil.stringToXml(doc.documentXML);
					
					File basepath=new File("."); //might not even matter here
					DocubricksProject p=DocubricksProject.fromXML(basepath, e);
					JSONObject ob=p.toJSON(basepath);
					
					response.getWriter().append(ob.toJSONString());
					}
				else
					response.getWriter().append("null");
				}
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}
		}


	}
