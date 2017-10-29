package site.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import site.DocubricksSite;
import site.DocumentDirectory;
import site.record.RecordDocument;

/**
 * Download project as a ZIP-file
 */
@WebServlet("/DownloadZip")
@MultipartConfig
public class DownloadZip extends DocubricksServlet
	{
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
		{
		//TODO: option for no XML and no videos
		File tempzip=null;
		try(DocubricksSite session=new DocubricksSite())
			{
			String id=request.getParameter("id");
			if(id==null)
				response.sendError(404, "No id given");
			else
				{
				RecordDocument recdoc=RecordDocument.query(session.getConn(), Long.parseLong(id));
				DocumentDirectory dir=recdoc.getDir();
				
				

		    tempzip=File.createTempFile("foo", "zip");
//      File tempzip=new File("/home/mahogny/wtf.zip");
		    
		    // output file 
		    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tempzip));
				for(File f:dir.listAllFiles())
					{
					//TODO a zip entry that ends with / and has no content is a directory!
					
					//TODO it might be better to merge with listallfiles code to pull out paths!
					ZipEntry ze=new ZipEntry(f.getName());
		      zos.putNextEntry(ze); //relative path!
		      FileInputStream in = new FileInputStream(f);
		      byte[] b = new byte[1024];
		      int count;
		      while ((count = in.read(b)) > 0) 
		        zos.write(b, 0, count);
		      in.close();
		      zos.closeEntry();
					}

				//Write the main index file
				byte[] projarr=recdoc.documentXML.getBytes(Charset.forName("UTF-8"));
		    zos.putNextEntry(new ZipEntry("project.docubricks.xml")); 
		  	zos.write(projarr);
		    zos.closeEntry();
		    
		    //TODO what about the XSLT? put it in too?

				
				
		    zos.close();

		    
		    ///////// Send file to user
		    response.setContentType("application/zip");
		    response.setHeader("Content-Disposition", "filename=\"project.zip\"");
		    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		    response.setHeader("Pragma","no-cache");
		    response.setHeader("Expires","0");

		    
		    FileUtils.copyFile(tempzip, response.getOutputStream());
		    tempzip.delete();
				}
			}
		catch (Exception e)
			{
			//Try really hard to delete that file
			if(tempzip!=null)
				tempzip.delete();
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}

		
		}

	}
