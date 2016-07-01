package Core.Database.Storage.Helper;

import Common.GlobalInstance;
import Common.Util;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.IOException;
import java.io.InputStream;

public abstract class SingleStorage
{
	private SqlJetDb db;

	public SingleStorage( SqlJetDb db )
	{
		this.db = db;
	}

	protected abstract String getTableName( );

	protected abstract String getResourceName( );

	private void createTable() throws IOException, SqlJetException
	{
		InputStream stream = GlobalInstance.getAppClass().getResourceAsStream( getResourceName() );

		String query = Util.drainInputStream( stream );
		{
			getDb( ).createTable( query );
		}
	}


	protected ISqlJetTable getTable( ) throws SqlJetException
	{
		return db.getTable( getTableName( ) );
	}

	protected SqlJetDb getDb( )
	{
		return db;
	}


	protected boolean tableExists()
	{
		try
		{
			if( getTable() != null )
			{
				return true;
			}

			return false;
		}
		catch( SqlJetException e )
		{
			return false;
		}
	}

}


