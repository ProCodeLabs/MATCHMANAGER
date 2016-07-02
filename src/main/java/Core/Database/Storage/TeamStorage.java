package Core.Database.Storage;


import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Data.Team;
import Core.Database.Storage.Helper.DbStorage;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.schema.ISqlJetIndexDef;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.util.concurrent.CompletionException;

public class TeamStorage extends DbStorage
{
	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	private ISqlJetIndexDef nameIndex;

	public TeamStorage( SqlJetDb db )
	{
		super( db );
	}

	public Team createTeam( String teamName ) throws CompletionException
	{
		try
		{
			getDb( ).beginTransaction( SqlJetTransactionMode.WRITE );
			try
			{
				long rowId = getTable( ).insert( teamName );
				{
					logger.success( "Added new team " + teamName + " id: " + rowId );

					return new Team( rowId, teamName );
				}
			}
			finally
			{
				getDb( ).commit( );
			}
		}
		catch( SqlJetException e )
		{
			throw new CompletionException( e );
		}
	}

	public Team getTeam( String teamName ) throws CompletionException
	{
		try
		{
			getDb( ).beginTransaction( SqlJetTransactionMode.READ_ONLY );
			try
			{
				ISqlJetCursor cursor = getTable( ).lookup( "name_index", teamName );

				if( cursor.eof( ) )
				{
					logger.error( "team not found!: " + teamName );
					//#broken
					return null;
				}

				logger.success( "found team: " + teamName + " with id: " + cursor.getRowId( ) );
				return new Team( cursor.getRowId( ), cursor.getString( 1 ) );
			}
			finally
			{
				getDb( ).commit( );
			}
		}
		catch( SqlJetException e )
		{
			throw new CompletionException( e );
		}
	}

	@Override
	protected void onSetupTable( ) throws SqlJetException
	{
		nameIndex = getDb( ).createIndex( "CREATE INDEX name_index ON team_storage( name );" );
	}

	@Override
	protected String getTableName( )
	{
		return "team_storage";
	}

	@Override
	protected String getResourceName( )
	{
		return "sql/teamStorage.sql";
	}
}
