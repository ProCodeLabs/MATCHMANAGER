package Core.Database.Storage.Helper;


import Common.GlobalInstance;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.IOException;

public abstract class StorageHelper implements IStorage
{
	private SqlJetDb db;

	//>
	protected abstract void onInitializeStorage( ) throws SqlJetException;


	@Override
	public void initializeStorage( SqlJetDb db ) throws SqlJetException
	{
		this.db = db;
		{
			onInitializeStorage();
		}
	}



	protected boolean checkTableExists( String tableName )
	{
		try
		{
			db.getTable( tableName );
			return true;
		}
		catch( SqlJetException e )
		{
			return false;
		}
	}

	protected void createTableFromResource( String resourceName ) throws SqlJetException
	{
		String query;
		try
		{
			query = GlobalInstance.readResource( resourceName );
		}
		catch( IOException e )
		{
			throw new SqlJetException( "IO: Error " + e.getMessage( ) );
		}

		db.beginTransaction( SqlJetTransactionMode.WRITE );
		try
		{
			db.createTable( query );
		}
		finally
		{
			db.commit( );
		}
	}

	@Override
	public SqlJetDb getDatabase( )
	{
		return db;
	}
}
