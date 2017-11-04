package site.record;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.jdom2.Document;
import org.jdom2.Element;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import docubricks.data.DocubricksProject;
import site.DocubricksSite;
import site.DocumentDirectory;
import site.util.EvXmlUtil;

/**
 * Information about a user
 * 
 * @author Johan Henriksson
 *
 */
@DatabaseTable(tableName = "docubricks_document")
public class RecordDocument
	{
	@DatabaseField(columnName="document_id",id = true) //generatedId = true,allowGeneratedIdInsert=true)
	public long id;

	@DatabaseField(columnName="document_timecreated")
	public long timeCreated=System.currentTimeMillis();
	
	
	@DatabaseField(columnName="document_xml")
	public String documentXML;
	
	@DatabaseField(columnName="document_owner")
	public String documentOwner="";

	@DatabaseField(columnName="document_ownerid")
	public int documentOwnerID;

	//TODO .. maybe there should be multiple owners of a document?
	
	@DatabaseField(columnName="document_ispublic")
	public boolean isPublic=true;

	@DatabaseField(columnName="document_isfrozen")
	public boolean isFrozen=false;

	@DatabaseField(columnName="document_name")
	public String documentName="unnamed";
	
	@DatabaseField(columnName="document_image")
	public String documentImage="";
	
	@DatabaseField(columnName="document_desc")
	public String documentDesc="";
	
//	@DatabaseField(columnName="document_tags")
//	public String documentTags="";  
	
	public List<RecordDocumentTag> tags=new ArrayList<>();
	
	@DatabaseField(columnName="document_shortlink")
	public String documentShortLink=""; //Allow project to be looked up as project/shortlink
	
	
	
	public void allocate(DocubricksSite conn) throws SQLException
		{
		try
			{
			//TODO ensure id does not exist before
			
			id=(long)(Long.MAX_VALUE*Math.random());
			DocumentDirectory dir=new DocumentDirectory(id);
			dir.getRoot().mkdirs();
			
			DocubricksProject proj=new DocubricksProject();
			Element e=proj.toXML(dir.getRoot());
			Document xmldoc=new Document(e);
			
			documentXML=EvXmlUtil.xmlToString(xmldoc);
			
			conn.daoDocument.create(this);
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new SQLException("wtf");
			}
		}

	public DocumentDirectory getDir()
		{
		return new DocumentDirectory(id);
		}

	public void delete(DocubricksSite conn) throws SQLException, IOException
		{
		conn.daoDocument.delete(this); //if no ID then problematic
/*		PreparedStatement stDel=conn.prepareStatement("DELETE FROM docubricks_document WHERE document_id=?;");
		stDel.setLong(1, id);
		stDel.execute();*/
		
		getDir().delete();
		}

	

	//List only for this user, todo
	public static List<RecordDocument> getUserDocuments(DocubricksSite session) throws SQLException
		{
		HashMap<String,Object> m=new HashMap<>();
		m.put("document_ownerid", session.session.userID); 
//		m.put("document_owner", session.session.userEmail);   //Should not be email!!!
		List<RecordDocument> docs=session.daoDocument.queryForFieldValues(m);
		
		//Tags - would be better to do a single join and pull from there
		for(RecordDocument doc:docs)
			doc.loadTags(session);
		
		return docs;
		}
		
	
	public static List<RecordDocument> searchDocuments(DocubricksSite session, String query) throws SQLException
		{
		//This also pulls out the text in them - better if we did not
		List<RecordDocument> docs=session.daoDocument.queryForAll();
		
		//Tags - would be better to do a single join and pull from there
		for(RecordDocument doc:docs)
			doc.loadTags(session);
		
		return docs;
		}


	
	public void loadTags(DocubricksSite session) throws SQLException
		{
		tags.clear();
		HashMap<String,Object> m=new HashMap<>();
		m.put("document_id", id); 
		tags.addAll(session.daoDocumentTag.queryForFieldValues(m));
		}
	
	public void saveTags(DocubricksSite session) throws SQLException
		{
		HashMap<String,Object> m=new HashMap<>();
		m.put("document_id", id); 
		List<RecordDocumentTag> oldtags=session.daoDocumentTag.queryForFieldValues(m);
		session.daoDocumentTag.delete(oldtags);
		
		session.daoDocumentTag.create(tags);
		}

	public void setTagsFromComma(String parameter)
		{
		tags.clear();
		StringTokenizer stok=new StringTokenizer(parameter, ",");
		while(stok.hasMoreTokens())
			tags.add(new RecordDocumentTag(id,stok.nextToken().trim()));
		}

	public String getTagListComma()
		{
		StringBuilder sb=new StringBuilder();
		boolean fst=true;
		for(RecordDocumentTag t:tags)
			{
			if(!fst)
				sb.append(",");
			sb.append(t.documentTag);
			fst=false;
			}
		return sb.toString();
		}

	}
