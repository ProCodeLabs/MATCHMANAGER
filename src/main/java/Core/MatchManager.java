package Core;

import Common.GlobalInstance;
import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Data.Match;
import Core.Data.Player;
import Core.Data.Team;
import Core.Database.Storage.Helper.DbStorage;
import Core.Database.Storage.Helper.IStorageImpl;
import Core.Database.Storage.MatchStorage;
import Core.Database.Storage.PlayerStorage;
import Core.Database.Storage.TeamStorage;
import Core.Helper.StorageException;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.SqlJetDb;
import ui.Helper.UiEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static java.util.concurrent.CompletableFuture.supplyAsync;


public class MatchManager implements IStorageImpl
{
	final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	private SqlJetDb db;

	//> used for easy iterations
	private List<DbStorage> activeStorage = new ArrayList<>( );

	private PlayerStorage playerStorage;
	private TeamStorage teamStorage;
	private MatchStorage matchStorage;


	public MatchManager( SqlJetDb db )
	{
		this.db = db;

		playerStorage = new PlayerStorage( db );
		teamStorage = new TeamStorage( db );
		matchStorage = new MatchStorage( db );
		{
			activeStorage.add( playerStorage );
			activeStorage.add( teamStorage );
			activeStorage.add( matchStorage );
		}
	}

	public void close( )
	{
		logger.info( "Closing database " + getFileName( ) );

		try
		{
			db.close( );
		}
		catch( SqlJetException e )
		{
			GlobalInstance.fireGlobalEvent( new UiEvent( UiEvent.CORE_EXCEPTION, e ) );
		}
	}

	public void initializeDatabase( ) throws IOException, SqlJetException
	{
		for( DbStorage i : activeStorage )
		{
			if( !i.tableExists( ) )
			{
				i.createTable( );
			}
		}
	}

	@Override
	public CompletableFuture<Player> addPlayer( String teamName, Player player )
	{
		return getTeam( teamName ).thenApply( r -> playerStorage.addPlayer( r.getId( ), player ) );
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
		return getTeam( teamName ).thenApply( r -> playerStorage.getAllTeamPlayers( r.getId( ) ) );
	}

	@Override
	public CompletableFuture<Team> addTeam( String teamName )
	{
		return getTeam( teamName ).thenApply( r -> teamStorage.createTeam( teamName ) );
	}

	@Override
	public CompletableFuture<Void> addPlayerToTeam( Match match, Player user )
	{
		return null;
	}

	@Override
	public CompletableFuture<Team> getTeam( String teamName )
	{
		return supplyAsync( ( ) -> teamStorage.getTeam( teamName ) );
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

	public boolean isDatabaseEmpty( )
	{
		for( DbStorage i : activeStorage )
		{
			if( i.tableExists( ) && !i.isTableEmpty( ) )
			{
				return false;
			}
		}

		return true;
	}

	public String getFileName( )
	{
		return db.getFile( ).getName( );
	}


	private void throwTeamNotFoundException( String teamName ) throws CompletionException
	{
		throw new CompletionException( new StorageException( "Team with the name " + teamName + " already exists!" ) );
	}

}
