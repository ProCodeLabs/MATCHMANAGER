package Core.Database.Storage.Helper;

import Common.GlobalInstance;
import Common.ParamFunction;
import Common.Util;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class DbStorage
{
	private SqlJetDb db;

	public DbStorage( SqlJetDb db )
	{
		this.db = db;
	}


	protected abstract String getTableName( );

	protected abstract String getResourceName( );



	public void createTable( ) throws IOException, SqlJetException
	{
		InputStream stream = GlobalInstance.getAppClass( ).getResourceAsStream( getResourceName( ) );

		String query = Util.drainInputStream( stream );
		{
			getDb( ).createTable( query );
		}
	}

	private void fetchRows( ParamFunction<ISqlJetCursor> callback ) throws SqlJetException
	{
		getDb( ).beginTransaction( SqlJetTransactionMode.READ_ONLY );

		try
		{
			ISqlJetCursor cursor = getTable( ).order( getTable( ).getPrimaryKeyIndexName( ) );

			if( !cursor.eof( ) )
			{
				do
				{
					callback.apply( cursor );
				} while( cursor.next( ) );
			}
		}
		finally
		{
			getDb( ).commit( );
		}
	}

	public boolean tableExists( )
	{
		try
		{
			return getTable( ) != null;
		}
		catch( SqlJetException e )
		{
			return false;
		}
	}

	public boolean isTableEmpty( )
	{
		try
		{
			return getRowCount() <= 0;
		}
		catch( SqlJetException e )
		{
			return false;
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

	private int getRowCount( ) throws SqlJetException
	{
		AtomicInteger count = new AtomicInteger( 0 );

		fetchRows( cursor -> count.incrementAndGet( ) );

		return count.get( );
	}
}


