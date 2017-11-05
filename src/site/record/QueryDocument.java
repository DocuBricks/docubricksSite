package site.record;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import site.DocubricksSite;

/**
 * 
 * @author Johan Henriksson
 *
 */
public class QueryDocument extends HashMap<String,Object>
	{
	private static final long serialVersionUID = 1L;

	
	
	public List<RecordDocument> get(DocubricksSite session) throws SQLException
		{
		System.out.println(this);
		List<RecordDocument> docs;
		if(isEmpty())
			docs=session.daoDocument.queryForAll();
		else
			docs=session.daoDocument.queryForFieldValues(this);
		//Tags - would be better to do a single join and pull from there
		//Make optional in query?
		for(RecordDocument doc:docs)
			doc.loadTags(session);
		return docs;
		}

	public void setOwner(long userID)
		{
		put("document_ownerid", userID); 
		}
	

	public void setShowOnlyPublic()
		{
		put("document_ispublic", true); 
		}

	}
