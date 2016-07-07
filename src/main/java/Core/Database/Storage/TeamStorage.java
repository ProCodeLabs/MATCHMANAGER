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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionException;

public class TeamStorage extends DbStorage
{
	private static final String NAME_INDEX = "name_index";
	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	private ISqlJetIndexDef nameIndex;


	public TeamStorage( SqlJetDb db )
	{
		super( db );
	}


	public Team createTeam( String teamName ) throws CompletionException
	{
		return transactionCommit( SqlJetTransactionMode.WRITE, ( ) -> {
			long rowId = getTable( ).insert( teamName );
			{
				logger.success( "Added new team " + teamName + " id: " + rowId );

				return new Team( rowId, teamName );
			}
		} );
	}

	public Team getTeam( String teamName ) throws CompletionException
	{
		return transactionCommit( SqlJetTransactionMode.READ_ONLY, ( ) -> {
			ISqlJetCursor cursor = getTable( ).lookup( NAME_INDEX, teamName );

			if( cursor.eof( ) )
			{
				logger.error( "team not found!: " + teamName );
				return null;
			}

			logger.success( "found team: " + teamName + " with id: " + cursor.getRowId( ) );
			return new Team( cursor.getRowId( ), cursor.getString( 1 ) );
		} );
	}

	public Team getTeam( long id ) throws CompletionException
	{
		return transactionCommit( SqlJetTransactionMode.READ_ONLY, ( ) -> {
			ISqlJetCursor cursor = getTable( ).lookup( getTable( ).getPrimaryKeyIndexName( ), id );

			if( cursor.eof( ) )
			{
				logger.error( "team not found!: " + id );
				return null;
			}

			logger.success( "found team: " + id );
			return new Team( cursor.getRowId( ), cursor.getString( 1 ) );
		} );
	}

	public Void removeTeam( String teamName )
	{
		getCursorEx( NAME_INDEX, new Object[]{ teamName }, c -> c.delete( ) );
		return null;
	}

	public Void updateTeam( String teamName, Team newTeam )
	{
		getCursorEx( NAME_INDEX, new Object[]{ teamName }, c -> c.update( c.getRowId( ), newTeam.getTeamName( ) ) );
		return null;
	}

	public List<Team> getAllTeams( ) throws CompletionException
	{
		return reflectException( ( ) -> {
			ArrayList<Team> teamList = new ArrayList<>( );
			{
				fetchRows( getTable( ).getPrimaryKeyIndexName( ), c -> teamList.add( serializeTeam( c ) ) );
			}
			return teamList;

		} );
	}


	private Team serializeTeam( ISqlJetCursor cursor ) throws SqlJetException
	{
		long id = cursor.getRowId( );
		String name = cursor.getString( 1 );

		return new Team( id, name );
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
