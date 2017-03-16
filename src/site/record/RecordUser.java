package site.record;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import site.DocubricksSqlConnection;

/**
 * Information about a user
 * 
 * @author Johan Henriksson
 *
 */
public class RecordUser
	{
	public String passwordHashed="";

	public String organization="";
	public String firstName, lastName;

	public String emailPrimary;
	
	public boolean isAdmin=false;
	
	/*
	public String telephone;
	public String address;
	public String postalCode;
	public String state; //name!   .."or region"
	public String country;
*/

	public long timeCreatedAccount=System.currentTimeMillis();

	private String orcid="";
	
	
	
	
	public static RecordUser query(DocubricksSqlConnection conn, String email) throws SQLException
		{
		PreparedStatement ps=conn.prepareStatement("SELECT * FROM docubricks_user WHERE user_email=?;");
		ps.setString(1, email);
		ResultSet rs=ps.executeQuery();
		
		RecordUser info=null;
		while(rs.next())
			{
			info=new RecordUser();
			
			info.passwordHashed=rs.getString("user_password");
			info.emailPrimary=rs.getString("user_email");

			info.firstName=rs.getString("user_name");
			info.lastName=rs.getString("user_surname");
			
			info.orcid=rs.getString("user_orcid");
			info.isAdmin=rs.getBoolean("user_isadmin");
			}
		
		ps.close();
		rs.close();
		return info;
		}

	/**
	 * Store info in database
	 */
	public void store(DocubricksSqlConnection conn) throws SQLException
		{
		//Transaction: delete,insert
		//conn.todo();
	
		PreparedStatement stDel=conn.prepareStatement("DELETE FROM docubricks_user WHERE user_email=?;");
		stDel.setString(1, emailPrimary);
		stDel.execute();
		
		
		PreparedStatement stIns=conn.prepareStatement("INSERT INTO docubricks_user VALUES (?,?,?,?,?,?,?);");

		stIns.setString(1, emailPrimary);
		stIns.setString(2, passwordHashed);
		stIns.setString(3, firstName);
		stIns.setString(4, lastName);
		stIns.setLong  (5, timeCreatedAccount);
		stIns.setString(6, orcid);
		stIns.setBoolean(7, isAdmin);

		stIns.execute();
		}
	
	

	public boolean setPassword(String pass1, String pass2)
		{
		if(pass1.equals(pass2))
			{
			return false;
			}
		
		passwordHashed=pass1; //TODO
		return true;
		}
	
	
	public boolean authenticate(String pass)
		{
		return pass.equals(passwordHashed); //TODO
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


	
	}
