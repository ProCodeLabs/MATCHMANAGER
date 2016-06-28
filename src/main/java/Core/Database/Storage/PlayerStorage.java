package Core.Database.Storage;


import Common.GlobalInstance;
import Core.Database.Storage.Helper.IStorage;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.IOException;

public class PlayerStorage implements IStorage
{
	static final String RESOURCE_ID = "sql/playerStorage.sql";

	@Override
	public void initializeStorage( SqlJetDb db ) throws SqlJetException
	{
		String query;
		try
		{
			query = GlobalInstance.readResource( RESOURCE_ID );
		}
		catch( IOException e )
		{
			throw new SqlJetException( "IO: Error " + e.getMessage() );
		}


		db.createTable( query );
	}
}

