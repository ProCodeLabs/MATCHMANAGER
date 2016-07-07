package Core.Database.Storage.Helper;

import Common.GlobalInstance;
import Common.LambdaExt.ReflectFunction;
import Common.Util;
import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public abstract class DbStorage
{
	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );
	private SqlJetDb db;

	//> database sync bug in transaction?
	private static final ReentrantLock lock = new ReentrantLock( );

	public DbStorage( SqlJetDb db )
	{
		this.db = db;
	}


	protected abstract String getTableName( );

	protected abstract String getResourceName( );

	protected /*virtual*/ void onSetupTable( ) throws SqlJetException
	{
	}


	public void createTable( ) throws IOException, SqlJetException
	{
		logger.info( "initializing table " + getTableName( ) );

		InputStream stream = GlobalInstance.getAppClass( ).getResourceAsStream( getResourceName( ) );

		String query = Util.drainInputStream( stream );
		{
			getDb( ).createTable( query );
		}
		onSetupTable( );
	}

	protected void fetchRows( String indexName, ThrowableParamFunction<ISqlJetCursor> callback )
	{
		transactionCommit( SqlJetTransactionMode.READ_ONLY, ( ) -> {
			ISqlJetCursor cursor = getTable( ).order( indexName );

			if( !cursor.eof( ) )
			{
				do
				{
					callback.apply( cursor );
				} while( cursor.next( ) );
			}

			return null;
		} );
	}

	protected void fetchRowEx( ThrowableResultFunction<ISqlJetCursor, Boolean> comp, ThrowableParamFunction<ISqlJetCursor> updateFunction )
	{
		fetchRows( reflectException( ( ) -> getTable( ).getPrimaryKeyIndexName( ) ), c -> {
			if( comp.apply( c ) )
			{
				updateFunction.apply( c );
			}
		} );
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

	//helper to turn sql exceptions into completion exceptions
	protected <T> T reflectException( ReflectFunction<T> func ) throws CompletionException
	{
		try
		{
			return func.apply( );
		}
		catch( SqlJetException e )
		{
			throw new CompletionException( e );
		}
	}

	protected <T> T transactionCommit( SqlJetTransactionMode mode, ReflectFunction<T> f )
	{
		return reflectException( ( ) -> {
			lock.lock( );
			getDb( ).beginTransaction( mode );
			try
			{
				return f.apply( );
			}
			finally
			{
				getDb( ).commit( );
				lock.unlock( );
			}
		} );
	}

	protected void getCursorById( long id, ThrowableParamFunction<ISqlJetCursor> cursorFunction )
	{
		getCursorEx( reflectException( ( ) -> getTable( ).getPrimaryKeyIndexName( ) ), new Object[]{ id }, cursorFunction );
	}

	protected void getCursorEx( String indexName, Object[] firstKey, ThrowableParamFunction<ISqlJetCursor> cursorFunction )
	{
		transactionCommit( SqlJetTransactionMode.WRITE, ( ) -> {
			ISqlJetCursor cursor = getTable( ).scope( indexName, firstKey, null );

			if( !cursor.eof( ) )
			{
				cursorFunction.apply( cursor );
			}
			else
			{
				throw new SqlJetException( getTableName() + " Cannot scope cursor: " + indexName + firstKey.toString( ) );
			}

			return null;
		} );
	}


	public boolean isTableEmpty( )
	{
		try
		{
			return getRowCount( ) <= 0;
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

	protected int getRowCount( ) throws SqlJetException
	{
		AtomicInteger count = new AtomicInteger( 0 );
		{
			fetchRows( null, cursor -> count.incrementAndGet( ) );
		}
		return count.get( );
	}


	@Override
	public String toString( )
	{
		return getTableName( ) + " " + getResourceName( );
	}
}


