package site.servlet;

import java.io.BufferedInputStream;
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
				RecordDocument recdoc=session.getDocument(Long.parseLong(id));
				DocumentDirectory dir=recdoc.getDir();
				
				

		    tempzip=File.createTempFile("foo", "zip");
		    
		    // output file 
		    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tempzip));
		  	zipDirectoryHelper(dir.getRoot(), dir.getRoot(), zos);

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











	private void zipDirectoryHelper(File rootDirectory, File currentDirectory, ZipOutputStream out) throws Exception
		{
		byte[] data = new byte[2048];
		File[] files = currentDirectory.listFiles();
		if(files == null) 
			{
			// no files were found or this is not a directory
			} 
		else 
			{
			for(File file : files) 
				{
				if(file.isDirectory()) 
					{
					zipDirectoryHelper(rootDirectory, file, out);
					} 
				else 
					{
					FileInputStream fi = new FileInputStream(file);

					//creating structure and avoiding duplicate file names
					String name = file.getAbsolutePath().replace(rootDirectory.getAbsolutePath(), "");

					ZipEntry entry = new ZipEntry(name);
					out.putNextEntry(entry);
					int count;
					BufferedInputStream origin = new BufferedInputStream(fi,2048);
					while ((count = origin.read(data, 0 , 2048)) != -1)
						out.write(data, 0, count);
					origin.close();
					}
				}
			}
		}




	}
