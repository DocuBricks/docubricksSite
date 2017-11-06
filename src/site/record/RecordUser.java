package site.record;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import site.servlet.CreateUser;

/**
 * Information about a user
 * 
 * @author Johan Henriksson
 *
 */
@DatabaseTable(tableName = "docubricks_user")
public class RecordUser
	{
	@DatabaseField(columnName="user_id", generatedId = true)//id = true,   ,allowGeneratedIdInsert=true)
	public int id;
	
	@DatabaseField(columnName="user_password")
	public String passwordHashed="";

	//@DatabaseField(columnName="user_password")
	//public String organization="";
	
	@DatabaseField(columnName="user_name")
	public String firstName;
	
	@DatabaseField(columnName="user_surname")
	public String lastName;

	@DatabaseField(columnName="user_email")  //,index = true
	public String emailPrimary;
	
	@DatabaseField(columnName="user_isadmin")
	public boolean isAdmin=false;
	
	@DatabaseField(columnName="user_passresetcode")
	public String passResetCode="";
	
	@DatabaseField(columnName="user_passresettime")
	public long passResetTime=0;

	
	/*
	public String telephone;
	public String address;
	public String postalCode;
	public String state; //name!   .."or region"
	public String country;
*/

	@DatabaseField(columnName="user_timecreated")
	public long timeCreated=System.currentTimeMillis();

	@DatabaseField(columnName="user_orcid")
	private String orcid="";
	
	
	
	public void setPassword(String pass)
		{
		passwordHashed=CreateUser.encPass(pass);
		}
	
	public boolean authenticate(String pass)
		{
		return CreateUser.encPass(pass).equals(passwordHashed);
		}
		
	}
