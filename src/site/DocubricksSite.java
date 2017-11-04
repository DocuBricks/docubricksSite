package site;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

import site.record.RecordDocument;
import site.record.RecordDocumentTag;
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

	
	JdbcPooledConnectionSource connectionSource;
	public Dao<RecordDocument, Long> daoDocument;
	public Dao<RecordDocumentTag, Long> daoDocumentTag;
	public Dao<RecordUser, Integer> daoUser;

	public DocubricksSite() throws IOException, SQLException
		{
		conn=ConnPool.getInstance().get();
		
		connectionSource = new JdbcPooledConnectionSource(
				"jdbc:postgresql://localhost/docubricks", "mahogny",	"hej");

		
		System.out.println("Connsorc #conn: "+connectionSource.getOpenCount());

		//only keep the connections open for 5 minutes
		connectionSource.setMaxConnectionAgeMillis(5 * 60 * 1000);
	
		//change the check-every milliseconds from 30 seconds to 60
		connectionSource.setCheckConnectionsEveryMillis(60 * 1000);
		
		//for extra protection, enable the testing of connections right before they are handed to the user
		connectionSource.setTestBeforeGet(true);

		daoDocument=DaoManager.createDao(connectionSource, RecordDocument.class);
		daoDocumentTag=DaoManager.createDao(connectionSource, RecordDocumentTag.class);
		daoUser=DaoManager.createDao(connectionSource, RecordUser.class);
	  }

	public <E> E getFirstOrNull(List<E> list)
		{
		if(!list.isEmpty())
			return list.iterator().next();
		else
			return null;
		}
	
	public RecordUser getUserByEmail(String email) throws SQLException
		{
		if(email==null)
			throw new SQLException("get user: email is null");
		else
			{
			HashMap<String,Object> m=new HashMap<>();
			m.put("user_email", email);
			return getFirstOrNull(daoUser.queryForFieldValues(m));
			}
		}	
	
	public RecordUser getUserByID(int userID) throws SQLException
		{
		return daoUser.queryForId(userID);
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
		HashMap<String,Object> m=new HashMap<>();
		m.put("document_id", id);
		RecordDocument doc=getFirstOrNull(daoDocument.queryForFieldValues(m));
		if(doc!=null)
			doc.loadTags(this);
		return doc;
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
		try
			{
			connectionSource.close();
			}
		catch (IOException e1)
			{
			e1.printStackTrace();
			}
		
		
		if(conn!=null)
			{
			try
				{
				ConnPool.getInstance().put(conn);
				conn=null;
				}
			catch (SQLException e)
				{
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		}

	
	public boolean loggedIn()
		{
		return session.userID!=null;
		}
	}
