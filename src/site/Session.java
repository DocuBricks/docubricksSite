package site;


import java.io.Serializable;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import site.record.RecordUser;

/**
 * 
 * 
 * @author Johan Henriksson
 *
 */
public class Session implements Serializable
	{
	private static final long serialVersionUID = 1L;
	public String userEmail;
	transient HttpSession session;
	
	public static Session fromSession(HttpSession session)
		{
		Session s=(Session)session.getAttribute("s");
		if(s==null)
			{
			s=new Session();
			System.out.println("new session");
			}
		else
			System.out.println("reusing session "+s.userEmail);
		s.session=session;
		return s;
		}
	
	public void toSession()
		{
		session.setAttribute("s", this);
		}
	
	
	public boolean authenticate(String user, String pass) throws SQLException
		{
		RecordUser info=getUserInfo(user);
		if(info!=null && info.authenticate(pass))
			{
			this.userEmail=user;
			toSession();
			return true;
			}
		else
			return false;
		}
	
	public void logout()
		{
		userEmail=null;
		toSession();
		}
	
	public RecordUser getUserInfo(DocubricksSite site) throws SQLException
		{
		return RecordUser.query(site.getConn(), userEmail);
		}
	
	public RecordUser getUserInfo(String userID) throws SQLException
		{
		return new RecordUser();
//		return UserInfo.query(conn, userID);
		}
	
	public void setUserInfo(DocubricksSite site, RecordUser info) throws SQLException
		{
		if(userEmail==null)
			throw new RuntimeException("User is null");
		info.store(site.getConn());
		}

	}
