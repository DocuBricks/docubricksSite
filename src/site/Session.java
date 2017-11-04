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
//	public String userEmail;
	transient HttpSession session;
	public Integer userID;
	
	public static Session fromSession(HttpSession session)
		{
		Session s=(Session)session.getAttribute("s");
		if(s==null)
			{
			s=new Session();
			System.out.println("new session");
			}
		else
			System.out.println("reusing session "+s.userID);
//			System.out.println("reusing session "+s.userEmail);
		s.session=session;
		return s;
		}
	
	public void toSession()
		{
		session.setAttribute("s", this);
		}
	
	/*
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
		}*/
	
	public void logout()
		{
		userID=null;
//		userEmail=null;
		toSession();
		}
	
	public RecordUser getUserInfo(DocubricksSite site) throws SQLException
		{
		System.out.println(site);
		System.out.println(userID);
		//Not quite sure when ID is null
		if(userID==null)
			return null;
		else
			return site.getUserByID(userID); 
//		return site.getUserByEmail(userEmail); //TODO: store userID instead!!!!
		}
	
	/*
	public RecordUser getUserInfo(String userID) throws SQLException
		{
		return new RecordUser(); //why?
//		return UserInfo.query(conn, userID);
		}*/
	
	/*
	public void setUserInfo(DocubricksSite site, RecordUser info) throws SQLException
		{
		if(userEmail==null)
			throw new RuntimeException("User is null");
		info.store(site.getConn());
		}*/

	}
