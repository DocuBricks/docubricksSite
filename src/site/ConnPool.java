package site;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * Pool of database connections
 * 
 * @author Johan Henriksson
 *
 */
public class ConnPool
	{
	private ArrayList<DocubricksSqlConnection> pool=new ArrayList<>();
	private int nc=0;
	
	public synchronized DocubricksSqlConnection get() throws IOException, SQLException
		{
		if(!pool.isEmpty())
			{
			DocubricksSqlConnection conn=pool.remove(pool.size()-1);
			System.out.println("# sql conn: "+nc);
			return conn;
			}
		else
			{
			nc++;
			System.out.println("# sql conn: "+nc);
			DocubricksSqlConnection conn=new DocubricksSqlConnection();
			conn.connect("org.postgresql.Driver", "jdbc:postgresql://localhost/docubricks", "mahogny",	"hej");
			return conn;
			}
		}

	public synchronized void put(DocubricksSqlConnection conn)
		{
		if(pool.size()>5)
			{
			nc--;
			try
				{
				conn.close();
				}
			catch (IOException e)
				{
				e.printStackTrace();
				}
			System.out.println("Reducing connections in pool");
			}
		else
			{
			pool.add(conn);
			System.out.println("Putting back connection to pool");
			}
		}
	
	
	}
