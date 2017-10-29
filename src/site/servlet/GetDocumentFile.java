package site.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import site.DocubricksSite;
import site.DocumentDirectory;
import site.record.RecordDocument;

/**
 * Get a single document XML
 */
@WebServlet("/project/*")
public class GetDocumentFile extends DocubricksServlet
	{
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		try(DocubricksSite session=new DocubricksSite())
			{
			String url=request.getRequestURI().toString();
			url=url.substring(url.indexOf("project/")+"project/".length());
			
			int pid=url.indexOf("/");
			if(pid!=-1)
				{
				long projectid=Long.parseLong(url.substring(0, pid));
				url=url.substring(pid+1);
				
				if(url.equals("project.xml"))
					{
					////// Pull out XML from database
					RecordDocument doc=session.getDocument(projectid);
					if(doc!=null)
						{
						//not quite!
						response.getWriter().append(doc.documentXML);
						}
					else
						response.getWriter().append("null");
					}
				else if(url.startsWith("src/") || url.equals("docubricks.xsl"))
					{
					
					}
				else
					{
					/////// Pull file from disk
					DocumentDirectory dir=new DocumentDirectory(projectid);
					
					//TODO ensure no .. or crap!
	        File file = new File(dir.getRoot(), URLDecoder.decode(url, "UTF-8"));
	        if(!file.exists()) 
	        	{
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return;
	        	}
	        if(!file.isFile())
	        	{
            response.sendError(HttpServletResponse.SC_FORBIDDEN,"Listing of files not permitted");
            return;
	        	}

	        // Get content type by filename.
	        String contentType = getServletContext().getMimeType(file.getName());
	        if (contentType == null)
	            contentType = "application/octet-stream";

	        // Init servlet response.
	        response.reset();
	        int DEFAULT_BUFFER_SIZE=1024;
	        response.setBufferSize(DEFAULT_BUFFER_SIZE);
	        response.setContentType(contentType);
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        response.setHeader("Content-Disposition", "filename=\"" + file.getName() + "\"");
	        //attachment; 
	        
	        // Prepare streams.
	        BufferedInputStream input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
	        BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

	        try 
	        	{
	        	byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
	        	int length;
	        	while ((length = input.read(buffer)) > 0) 
	        		output.write(buffer, 0, length);
	        	} 
	        finally 
		        {
		        if(output!=null)
		        	output.close();
		        if(input!=null)
		        	input.close();
		        }

					}
				}
			else
				response.sendError(HttpServletResponse.SC_NOT_FOUND,"Missing project ID");
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}
		}


	}
