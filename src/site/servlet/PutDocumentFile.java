package site.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import net.minidev.json.JSONObject;
import site.DocubricksSite;
import site.DocumentDirectory;
import site.record.RecordDocument;

/**
 * 
 */
@WebServlet("/PutDocumentFile")
@MultipartConfig
public class PutDocumentFile extends DocubricksServlet
	{
	private static final long serialVersionUID = 1L;

	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		try(DocubricksSite session=new DocubricksSite())
			{
			JSONObject retob=new JSONObject();

			session.fromSession(request.getSession());
			String id=request.getParameter("id");
			if(id==null)
				response.sendError(500, "No id specified");
			else
				{
				RecordDocument doc=session.getDocument(Long.parseLong(id.trim()));
				if(doc!=null)
					{
					DocumentDirectory docdir=doc.getDir();
					//TODO delete entry if it fails
					
					Part filePart = request.getPart("file"); 
			    String fileName = filePart.getName();//getSubmittedFileName();
			    System.out.println("+++++++++++++++++++++++"+fileName);  //file
			    InputStream fis = filePart.getInputStream();
			    
					final int BUFFER = 2048;
		      BufferedOutputStream dest = null;
	      	int count;
	      	byte data[] = new byte[BUFFER];
      		File f=new File(docdir.getRoot(),fileName);  //TODO check that the file is free, and in the right directory!!!!
	      	FileOutputStream fos = new FileOutputStream(f);
	      	dest = new BufferedOutputStream(fos, BUFFER);
	      	while ((count = fis.read(data, 0, BUFFER)) != -1) 
	      		dest.write(data, 0, count);
	      	dest.flush();
	      	dest.close();
	      	fis.close();

	      	retob.put("status","1");
	      	retob.put("filename", fileName);
					}
				else
					{
	      	retob.put("status","0");
	      	System.out.println("No such document to upload to");
					}
					
      	response.getWriter().append(retob.toJSONString());
				}
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}
		}


	}
