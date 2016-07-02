package Core.Database.Storage;

import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Data.Player;
import Core.Database.Storage.Helper.DbStorage;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionException;

public class PlayerStorage extends DbStorage
{
	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	public PlayerStorage( SqlJetDb db )
	{
		super( db );
	}

	public Player addPlayer( long teamId, Player player )
	{
		try
		{
			getDb( ).beginTransaction( SqlJetTransactionMode.WRITE );

			try
			{
				long rowId = getTable( ).insert( teamId, player.getForename( ), player.getSurname( ) );

				logger.success( "added new player( " + player.getFullName( ) + " )to team " + teamId );

				return new Player( rowId, player );
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

	public List<Player> getAllTeamPlayers( long teamId )
	{
		try
		{
			ArrayList<Player> playerList = new ArrayList<>( );
			{
				fetchRows( "teamid_index", r -> {
					if( r.getInteger( 1 ) == teamId )
					{
						playerList.add( new Player( r.getRowId( ), r.getString( 2 ), r.getString( 3 ) ) );
					}
				} );
			}

			return playerList;
		}
		catch( SqlJetException e )
		{
			throw new CompletionException( e );
		}
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