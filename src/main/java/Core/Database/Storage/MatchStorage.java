package Core.Database.Storage;

import Core.Database.Storage.Helper.DbStorage;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class MatchStorage extends DbStorage
{
	public MatchStorage( SqlJetDb db )
	{
		super( db );
	}



	@Override
	protected String getTableName( )
	{
		return "match_storage";
	}

	@Override
	protected String getResourceName( )
	{
		return "sql/matchStorage.sql";
	}
}
