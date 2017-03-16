package site.record;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import docubricks.data.DocubricksProject;
import site.DocubricksSite;
import site.DocubricksSqlConnection;
import site.DocumentDirectory;
import site.util.EvXmlUtil;

/**
 * Information about a user
 * 
 * @author Johan Henriksson
 *
 */
public class RecordDocument
	{
	public long id;

	public long timeCreated=System.currentTimeMillis();
	public String documentXML;
	public String documentOwner;

	//TODO .. maybe there should be multiple owners of a document?
	
	//TODO
	public boolean isPublic=true;

	public String documentName="unnamed";
	public String documentImage="";
	public String documentDesc="";
	
	public String documentTags="";  //TODO store in database
	
	
	
	public static RecordDocument query(DocubricksSqlConnection conn, long documentID) throws SQLException
		{
		PreparedStatement ps=conn.prepareStatement("SELECT * FROM docubricks_document WHERE document_id=?;");
		ps.setLong(1, documentID);
		ResultSet rs=ps.executeQuery();
		
		RecordDocument info=null;
		while(rs.next())
			info=read1(rs);
			
		ps.close();
		rs.close();
		return info;
		}

	private static RecordDocument read1(ResultSet rs) throws SQLException
		{
		RecordDocument info=new RecordDocument();
		info.id=rs.getLong("document_id");
		info.timeCreated=rs.getLong("document_timecreated");
		info.documentXML=rs.getString("document_xml");
		info.documentOwner=rs.getString("document_owner");
		info.documentName=rs.getString("document_name");
		info.documentImage=rs.getString("document_image");
		info.documentDesc=rs.getString("document_desc");
		return info;
		}
	
	
	/**
	 * Store info in database
	 */
	public void store(DocubricksSqlConnection conn) throws SQLException
		{
		//Transaction: delete,insert
		//conn.todo();
	
		PreparedStatement stDel=conn.prepareStatement("DELETE FROM docubricks_document WHERE document_id=?;");
		stDel.setLong(1, id);
		stDel.execute();
		
		//TODO this should be atomic so the ID is not reused in this time
		
		PreparedStatement stIns=conn.prepareStatement("INSERT INTO docubricks_document VALUES (?,?,?,?, ?,?,?);");

		stIns.setLong  (1, id);
		stIns.setLong  (2, timeCreated);
		stIns.setString(3, documentXML);
		stIns.setString(4, documentOwner);
		
		stIns.setString(5, documentName);
		stIns.setString(6, documentImage);
		stIns.setString(7, documentDesc);
		
		stIns.execute();
		}
	
	
	
	public static void createTable(DocubricksSqlConnection conn) throws SQLException
		{
		/*
		if(!conn.tableExists("cloud_users"))
			{
			Statement st=conn.createStatement();
			st.execute(
					"CREATE TABLE cloud_users (" +
					"userid VARCHAR(500) PRIMARY KEY,"+
					"user_password VARCHAR(500) NOT NULL,"+
					"user_email1 VARCHAR(500) NOT NULL,"+
					"user_email2 VARCHAR(500) NOT NULL,"+
					"user_tel VARCHAR(500) NOT NULL,"+
					"user_orgname VARCHAR(500) NOT NULL,"+
					"user_firstname VARCHAR(500) NOT NULL,"+
					"user_lastname VARCHAR(500) NOT NULL,"+
					"user_address VARCHAR(500) NOT NULL,"+
					"user_state VARCHAR(500) NOT NULL,"+
					"user_country VARCHAR(500) NOT NULL"+
					
					")");
			}
		*/
		}

	
	public void allocate(DocubricksSqlConnection conn) throws SQLException
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
			
			store(conn);
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

	public void delete(DocubricksSqlConnection conn) throws SQLException, IOException
		{
		PreparedStatement stDel=conn.prepareStatement("DELETE FROM docubricks_document WHERE document_id=?;");
		stDel.setLong(1, id);
		stDel.execute();
		getDir().delete();
		}

	

	//List only for this user, todo
	public static List<RecordDocument> getUserDocuments(DocubricksSite session) throws SQLException
		{
		PreparedStatement ps=session.getConn().prepareStatement("SELECT * FROM docubricks_document WHERE document_owner=?;");
		ps.setString(1, session.session.userEmail);
		ResultSet rs=ps.executeQuery();

		ArrayList<RecordDocument> list=new ArrayList<RecordDocument>();
		while(rs.next())
			list.add(read1(rs));
		
		ps.close();
		rs.close();
		return list;
		}
		 
	
	public static List<RecordDocument> searchDocuments(DocubricksSite session, String query) throws SQLException
		{
		PreparedStatement ps=session.getConn().prepareStatement("SELECT * FROM docubricks_document;");
		ResultSet rs=ps.executeQuery();

		ArrayList<RecordDocument> list=new ArrayList<RecordDocument>();
		while(rs.next())
			list.add(read1(rs));
		
		ps.close();
		rs.close();
		return list;
		}


	
	}
