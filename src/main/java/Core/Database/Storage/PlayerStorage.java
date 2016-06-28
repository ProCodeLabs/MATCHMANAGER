package Core.Database.Storage;


import Common.Util;
import Core.Database.Storage.Helper.IStorage;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.IOException;
import java.io.InputStream;

public class PlayerStorage implements IStorage
{
	static final String RESOURCE_ID = "sql/userStorage.sql";

	@Override
	public void initializeStorage( SqlJetDb db ) throws SqlJetException
	{
		InputStream stream = getClass( ).getResourceAsStream( RESOURCE_ID );


		String query;
		try
		{
			query = Util.drainInputStream( stream );
		}
		catch( IOException e )
		{
			throw new SqlJetException( "IO: Error " + e.getMessage() );
		}

		db.createTable( query );
	}
}

