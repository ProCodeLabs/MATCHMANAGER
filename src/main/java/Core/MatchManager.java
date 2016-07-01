package Core;

import Common.GlobalInstance;
import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Data.Match;
import Core.Data.Player;
import Core.Data.Team;
import Core.Database.Storage.Helper.IStorageImpl;
import Core.Database.Storage.PlayerStorage;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.SqlJetDb;
import ui.Helper.UiEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static java.util.concurrent.CompletableFuture.supplyAsync;


public class MatchManager implements IStorageImpl
{
	final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	private SqlJetDb db;
	private PlayerStorage playerStorage;

	public MatchManager( SqlJetDb db )
	{
		this.db = db;

		playerStorage = new PlayerStorage( db );
	}

	public void close( )
	{
		try
		{
			db.close( );
		}
		catch( SqlJetException e )
		{
			GlobalInstance.fireGlobalEvent( new UiEvent( UiEvent.CORE_EXCEPTION, e ) );
		}
	}


	@Override
	public CompletableFuture<Player> addPlayer( String teamName, Player player )
	{
		return supplyAsync( ( ) -> {
			Player r;

			try
			{
				r = playerStorage.addPlayer( teamName, player );
			}
			catch( SqlJetException e )
			{
				throw new CompletionException( e );
			}

			return r;
		} );
	}

	@Override
	public CompletableFuture<Void> updatePlayer( int id, Player newPlayer )
	{
		return null;
	}

	@Override
	public CompletableFuture<Player> getPlayer( int id )
	{
		return null;
	}

	@Override
	public CompletableFuture<List<Player>> getAllTeamPlayers( String teamName )
	{
		return null;
	}

	@Override
	public CompletableFuture<Team> addTeam( String matchName )
	{
		return null;
	}

	@Override
	public CompletableFuture<Void> addPlayerToTeam( Match match, Player user )
	{
		return null;
	}

	@Override
	public CompletableFuture<Team> getTeam( String teamName )
	{
		return null;
	}

	@Override
	public CompletableFuture<Void> updateTeam( Team targetTeam, Team newTeam )
	{
		return null;
	}

	@Override
	public CompletableFuture<Match> addMatch( Team teamA, Team teamB )
	{
		return null;
	}

	@Override
	public CompletableFuture<Void> endMatch( Team winner )
	{
		return null;
	}

	@Override
	public CompletableFuture<Void> updateMatch( Match match )
	{
		return null;
	}

	@Override
	public CompletableFuture<Match> getMatch( int id )
	{
		return null;
	}

	@Override
	public CompletableFuture<List<Player>> getAllPlayers( )
	{
		return null;
	}

	@Override
	public CompletableFuture<List<Team>> getAllTeams( )
	{
		return null;
	}

	@Override
	public CompletableFuture<List<Match>> getAllMatches( )
	{
		return null;
	}
}
