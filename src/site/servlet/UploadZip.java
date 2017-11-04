package site.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
import site.util.EvFileUtil;

/**
 * Servlet implementation class Test
 */
@WebServlet("/UploadZip")
@MultipartConfig
public class UploadZip extends DocubricksServlet
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

			/*
			String file=request.getParameter("description");
			if(file==null)
				response.sendError(404, "No zip given");
			else*/
				{
				session.fromSession(request.getSession());

				
      	RecordDocument rec=new RecordDocument();
				rec.documentOwnerID=session.session.userID;
//				rec.documentOwner=session.session.userEmail;
				//rec.documentOwner="mahogny@areta.org";
				System.out.println("owner id "+rec.documentOwnerID);
      	rec.allocate(session);

				DocumentDirectory docdir=rec.getDir();
				//TODO delete entry if zip fails
				
				Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
//		    String fileName = filePart.getSubmittedFileName();
		    InputStream fis = filePart.getInputStream();
				
				final int BUFFER = 2048;
	      BufferedOutputStream dest = null;
	      CheckedInputStream checksum = new CheckedInputStream(fis, new Adler32());
	      ZipInputStream zis = new ZipInputStream(new BufferedInputStream(checksum));
	      ZipEntry entry;
	      while((entry = zis.getNextEntry()) != null) 
	      	{
	      	System.out.println("Extracting: " +entry);
	      	int count;
	      	byte data[] = new byte[BUFFER];
	      	// write the files to the disk
	      	//System.out.println("name: "+entry.getName());
	      	
      		File f=new File(docdir.getRoot(),entry.getName());
      		//System.out.println(f);
	      	if(entry.isDirectory())
	      		{
	      		f.mkdirs();
	      		}
	      	else
	      		{
		      	FileOutputStream fos = new FileOutputStream(f);
		      	dest = new BufferedOutputStream(fos, BUFFER);
		      	while ((count = zis.read(data, 0, BUFFER)) != -1) 
		      		dest.write(data, 0, count);
		      	dest.flush();
		      	dest.close();
	      		}
	      	}
	      zis.close();
	      System.out.println("Checksum: "+checksum.getChecksum().getValue());
	      
	      //scary idea: putting links into zip?
	      //scary idea: putting .. dirs into zip?
	      //hmm. what if the zip contains everything in one subdir? kind of logical. then everything should be shifted down

	      docdir.removeTopDir();
	      File fileXML=docdir.getDocubricksXML();
	      if(fileXML!=null)
	      	{
	      	
	      	
	      	//TODO should allocate a record FIRST.
	      	rec.documentXML=EvFileUtil.readFile(fileXML);
	      	fileXML.delete();
	      	
	      	session.daoDocument.update(rec);
	  			retob.put("id", ""+rec.id);
					response.getWriter().append(retob.toJSONString());
	      	}
	      else
	      	{
	      	rec.delete(session);
	      	retob.put("id","-1");
	      	retob.put("error", "Zip does not contain a docubrick");
	      	response.getWriter().append(retob.toJSONString());
//					response.sendError(404, "Zip does not contain a docubrick");
	      	}
	      
				}
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
			}
		}


	}
