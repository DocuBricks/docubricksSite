package site;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import site.util.EvFileUtil;

public class DocumentDirectory
	{
	public long id;

	
	public DocumentDirectory(long id)
		{
		this.id=id;
		}

	public File getRoot()
		{
		File f=new File(new File("./docubricks"),""+id);
		f.mkdirs();
		return f;
		}
	
	
	public File getDocubricksXML()
		{
		File root=getRoot();
		return findXMLrecursive(root);
		}
	private File findXMLrecursive(File root)
		{
		for(File c:root.listFiles())
			{
			if(c.isDirectory())
				{
				File o=findXMLrecursive(c);
				if(o!=null)
					return o;
				}
			else if(c.getName().endsWith(".docubricks.xml"))
				return c;
			}
		return null;
		}

	public void delete() throws IOException
		{
  	EvFileUtil.deleteRecursive(getRoot());
		}

	
	/**
	 * If everything is one subdir then move everything one step up
	 */
	public void removeTopDir() throws IOException
		{
		File root=getRoot();
		if(getRoot().listFiles().length==1)
			{
			File r=getRoot().listFiles()[0];
			if(r.isDirectory())
				{

				File newr=new File(root.getParentFile(),root.getName()+"-temp");
				r.renameTo(newr);
				EvFileUtil.deleteRecursive(root);
				newr.renameTo(root);
				
				//TODO on mac you might end up with stupid .-files in the root. these should be ignored
				}
			}
		}

	public List<File> listAllFiles()
		{
		ArrayList<File> list=new ArrayList<File>();
		listAllFilesRec(getRoot(),list);
		return list;
		}
		
	private void listAllFilesRec(File root, List<File> list)
		{
		for(File c:root.listFiles())
			{
			if(c.isDirectory())
				listAllFilesRec(c, list);
			else if(c.isFile())
				list.add(c);
			}
		}

	}
