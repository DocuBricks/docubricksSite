package site;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 
 * A general JDBC layer. then hsql specifics on top?
 * a config file, just connection and jdbc driver
 * user and pass
 * 
 * an alternative is apache Derby
 * 
 * 
 * @author Johan Henriksson
 *
 */
public class DocubricksSqlConnection
	{
  private Connection connection;

  private boolean isHsql;
  
  /**
   * 
   * 
   * http://hsqldb.org/doc/2.0/guide/deployment-chapt.html
   * 
   * CREATE CACHED TABLE     this will not keep *all* the table in memory. might be needed on android
   * 
   * 
   * hsqldb.jar contains an entire GUI. this code could be removed for android!
   * 
   */

  public DocubricksSqlConnection()
  	{
  	}
  
  public DocubricksSqlConnection(String driverClass, Connection conn) throws IOException
		{
		this.connection=conn;
		
	  try
			{
			//Force HSQL to synch on each commit
			if(isHsql)
			  execOneStatement("SET FILES WRITE DELAY FALSE;");
			}
		catch (SQLException e)
			{
			throw new IOException(e);
			}
		}


	/**
   * Initialize database
   */
  public void connect(String driverClass, String url, String user,	String pass) throws IOException
  	{
    try
			{
			Class.forName(driverClass);
		  connection = DriverManager.getConnection(url, user, pass);

		  isHsql=driverClass.contains("hsql");
		  
		  //Force HSQL to synch on each commit
		  if(isHsql)
		    execOneStatement("SET FILES WRITE DELAY FALSE;");
			}
		catch (ClassNotFoundException e)
			{
			throw new IOException("Unable to load JDBC driver - class not found: "+driverClass);
			}
		catch (SQLException e)
			{
			e.printStackTrace();
			throw new IOException("Unable to connect to SQL database");
			}
  	}

  
  /**
   * Run one statement - for one-line statements only, otherwise use the raw interface
   */
  public void execOneStatement(String sql) throws SQLException
	  {
		Statement statement = connection.createStatement();
		statement.execute(sql);
		statement.close();
	  }

  
  
  
  
  public PreparedStatement prepareStatement(String sql) throws SQLException
	  {
	  PreparedStatement st=connection.prepareStatement(sql);
	  return st;
	  }
  
  public Statement createStatement() throws SQLException
  	{
		Statement statement = connection.createStatement();
  	return statement;
  	}

  
  
  /**
   * Close connection to database
   */
  public void close() throws IOException
  	{
  	try
			{
			//Shut down the hsql server. If we wish to hand over a JDBC handle to e.g. R, then this code has to
			//be executed somewhere else
			if(isHsql)
				execOneStatement("SHUTDOWN;");
			
			connection.close();
			}
		catch (SQLException e)
			{
			throw new IOException("Could not close JDBC connection");
			}
  	}


  /**
	 * Check if a table exists
	 */
	public boolean tableExists(String s)
		{
		try
			{
			Statement st=createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM "+s+" LIMIT 0");
			rs.close();
			st.close();
			return true;
			}
		catch (SQLException e)
			{
			return false;
			}
		}
	

	/**
	 * Check if a table column exists
	 */
	public boolean tableColumnExists(String s, String column)
		{
		try
			{
			Statement st=createStatement();
			ResultSet rs=st.executeQuery("SELECT "+column+" FROM "+s+" LIMIT 0");
			rs.close();
			st.close();
			return true;
			}
		catch (SQLException e)
			{
			return false;
			}
		}
	
	
	public synchronized void runTransaction(Runnable r) throws IOException
		{
		
		r.run();
		
		//Not runnable, something else, to pass on IO-errors
		
		}

	/**
	 * Print one SQL table
	 */
	public void printTable(String tableName) throws SQLException
		{
		PreparedStatement st=prepareStatement("select * from "+tableName+";");
		ResultSet rs=st.executeQuery();

		ResultSetMetaData rsmd = rs.getMetaData();
		int numcol=rsmd.getColumnCount();
		for(int i=1;i<=numcol;i++)
			System.out.print(rsmd.getColumnName(i)+"\t");
		System.out.println();
		
		while(rs.next())
			{
			for(int i=1;i<=numcol;i++)
				{
				String data=rs.getString(i);
				if(rs.wasNull())
					data="NULL";
				System.out.print(data+"\t");
				}
			System.out.println();
			}
		rs.close();
		st.close();
		}


	
	public void todo() throws SQLException
		{
		connection.setAutoCommit(false);
		connection.commit();
		}

	public PreparedStatement prepareInsert(String table, String... arg) throws SQLException
		{
		StringBuilder sb=new StringBuilder();
		sb.append("INSERT INTO "+table+"(");
		sb.append(arg[0]);
		for(String s:arg)
			{
			sb.append(",");
			sb.append(s);
			}
		sb.append(") VALUES (");
		sb.append("?");
		for(int i=0;i<arg.length-1;i++)
			sb.append(",?");
		sb.append(");");
		
		return prepareStatement(sb.toString());
		}
	
	}
