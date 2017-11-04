package site.record;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * A tag for a document
 * 
 * @author Johan Henriksson
 *
 */
@DatabaseTable(tableName = "docubricks_document_tags")
public class RecordDocumentTag
	{
	@DatabaseField(columnName="tag_id", generatedId = true)
	public long tag_id;

	@DatabaseField(columnName="document_id")
	public long document_id;

	@DatabaseField(columnName="document_tag")
	public String documentTag="";

	public RecordDocumentTag()
		{
		}
	
	public RecordDocumentTag(long document_id, String tag)
		{
		this.document_id=document_id;
		this.documentTag=tag;
		}

	}
