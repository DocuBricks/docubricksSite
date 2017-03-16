package site.record;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import site.DocubricksSqlConnection;
import site.DocumentDirectory;

/**
 * One document comment
 * 
 * @author Johan Henriksson
 *
 */
public class RecordComment
	{
	public long commentId;
	public long timeCreated=System.currentTimeMillis();
	public String brickID="";
	public long documentID;
	public String commentUser="";
	public String commentText="";

	
	public static List<RecordComment> query(DocubricksSqlConnection conn, long documentID) throws SQLException
		{
		PreparedStatement ps=conn.prepareStatement("SELECT * FROM docubricks_document_comment WHERE document_id=?;");
		ps.setLong(1, documentID);
		ResultSet rs=ps.executeQuery();
		
		ArrayList<RecordComment> list=new ArrayList<RecordComment>();
		while(rs.next())
			{
			RecordComment info=null;
			info=new RecordComment();
			
			info.commentId=rs.getLong("comment_id");
			info.documentID=rs.getLong("document_id");
			info.brickID=rs.getString("brick_id");
			info.timeCreated=rs.getLong("document_timecreated");
			info.commentUser=rs.getString("comment_user");
			info.commentText=rs.getString("comment_text");
			list.add(info);
			}
		
		ps.close();
		rs.close();
		return list;
		}

	/**
	 * Store info in database
	 */
	public void store(DocubricksSqlConnection conn) throws SQLException
		{
		//Transaction: delete,insert
		//conn.todo();
	
		PreparedStatement stDel=conn.prepareStatement("DELETE FROM docubricks_document_comment WHERE comment_id=?;");
		stDel.setLong(1, commentId);
		stDel.execute();
		
		//TODO this should be atomic so the ID is not reused in this time
		
		PreparedStatement stIns=conn.prepareStatement("INSERT INTO docubricks_document_comment VALUES (?,?,?,?,?,?);");

		stIns.setLong  (1, commentId);
		stIns.setLong  (2, documentID);
		stIns.setString(3, brickID);
		stIns.setLong(4, timeCreated);
		stIns.setString(5, commentUser);
		stIns.setString(6, commentText);

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
			commentId=(long)(Long.MAX_VALUE*Math.random());
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
		return new DocumentDirectory(commentId);
		}

	public void delete(DocubricksSqlConnection conn) throws SQLException, IOException
		{
		PreparedStatement stDel=conn.prepareStatement("DELETE FROM docubricks_document_comment WHERE comment_id=?;");
		stDel.setLong(1, commentId);
		stDel.execute();
		getDir().delete();
		}

	
		 
	

	
	}
