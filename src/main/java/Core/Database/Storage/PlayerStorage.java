package Core.Database.Storage;

import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Data.Player;
import Core.Database.Storage.Helper.DbStorage;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.util.ArrayList;
import java.util.List;

public class PlayerStorage extends DbStorage
{
	private static final String TEAMID_INDEX = "teamid_index";

	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );


	public PlayerStorage( SqlJetDb db )
	{
		super( db );
	}

	public Player addPlayer( long teamId, Player player )
	{
		return transactionCommit( SqlJetTransactionMode.WRITE, ( ) -> {
			long rowId = getTable( ).insert( teamId, player.getForename( ), player.getSurname( ) );

			logger.success( "added new player( " + player.getFullName( ) + " )to team " + teamId );

			return new Player( rowId, player );
		} );
	}

	public Player getPlayer( long id )
	{
		return transactionCommit( SqlJetTransactionMode.READ_ONLY, ( ) -> {
			ISqlJetCursor cursor = getTable( ).lookup( getTable( ).getPrimaryKeyIndexName( ) );

			if( cursor.eof( ) )
			{
				logger.error( "Player " + id + " not found!" );
				return null;
			}

			return serializePlayer( cursor );
		} );
	}

	public Void removePlayer( long id )
	{
		getCursorById( id, c -> c.delete( ) );
		return null;
	}

	public Void updatePlayer( long id, Player newPlayer )
	{
		getCursorById( id, c -> c.update( c.getRowId( ), c.getInteger( 1 ), newPlayer.getForename( ), newPlayer.getSurname( ) ) );
		return null;
	}

	public List<Player> getAllTeamPlayers( long teamId )
	{
		return reflectException( ( ) -> {
			ArrayList<Player> playerList = new ArrayList<>( );
			{
				fetchRows( TEAMID_INDEX, c -> {
					if( c.getInteger( 1 ) == teamId )
					{
						playerList.add( serializePlayer( c ) );
					}
				} );
			}

			return playerList;
		} );
	}

	public List<Player> getAllPlayers( )
	{
		return reflectException( ( ) -> {
			ArrayList<Player> playerList = new ArrayList<>( );
			{
				fetchRows( TEAMID_INDEX, c -> playerList.add( serializePlayer( c ) ) );
			}
			return playerList;
		} );
	}

	private Player serializePlayer( ISqlJetCursor cursor ) throws SqlJetException
	{
		long id = cursor.getRowId( );
		long teamId = cursor.getInteger( 1 );

		String forename = cursor.getString( 2 );
		String surname = cursor.getString( 3 );

		return new Player( id, teamId, forename, surname );
	}

	@Override
	protected void onSetupTable( ) throws SqlJetException
	{
		getDb( ).createIndex( "CREATE INDEX teamid_index ON user_storage( teamId );" );
	}

	@Override
	protected String getTableName( )
	{
		return "user_storage";
	}

	@Override
	protected String getResourceName( )
	{
		return "sql/playerStorage.sql";
	}


}