package site;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import site.record.RecordDocument;
import site.record.RecordUser;

/**
 * 
 * 
 * @author Johan Henriksson
 *
 */
public class DocubricksSite implements AutoCloseable
	{
	public Session session=new Session();
	private DocubricksSqlConnection conn=new DocubricksSqlConnection();

	private static ConnPool pc=new ConnPool();
	
	public DocubricksSite() throws IOException, SQLException
		{
		conn=pc.get();
	  RecordUser.createTable(conn);
	  RecordDocument.createTable(conn);
	  }

	
	
	public void fromSession(HttpSession hsession)
		{
//		this.httpSession=session;
		
		session=Session.fromSession(hsession);
		//userEmail=(String)session.getAttribute("useremail");
		
		}
	
	public void toSession(HttpSession hsession)
		{
		session.toSession();
		//session.setAttribute("useremail", userEmail);
		}
	
	/*
	public boolean authenticate(String user, String pass) throws SQLException
		{
		RecordUser info=getUserInfo(user);
		if(info!=null && info.authenticate(pass))
			{
			this.userEmail=user;
			toSession(session);
			return true;
			}
		else
			return false;
		}
		
	public void logout()
		{
		userEmail=null;
		toSession(session);
		}
	
	public RecordUser getUserInfo() throws SQLException
		{
		return RecordUser.query(conn, userEmail);
		}
	
	public RecordUser getUserInfo(String userID) throws SQLException
		{
		return new RecordUser();
//		return UserInfo.query(conn, userID);
		}
	
	public void setUserInfo(RecordUser info) throws SQLException
		{
		if(userEmail==null)
			throw new RuntimeException("User is null");
		info.store(conn);
		}
	*/
	
	public RecordDocument getDocument(long id) throws SQLException
		{
		return RecordDocument.query(conn, id);
		}



	public DocubricksSqlConnection getConn()
		{
		return conn;
		}
	
	@Override
	protected void finalize() throws Throwable
		{
		//this might not be forceful enough
		close();
		super.finalize();
		}


	public RecordUser getUserInfo() throws SQLException
		{
		return session.getUserInfo(this);
		}
	
	public void close()
		{
		if(conn!=null)
			{
			pc.put(conn);
			conn=null;
			}
		}
	}
