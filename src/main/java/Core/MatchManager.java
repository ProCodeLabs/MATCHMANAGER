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
			logger.info( "Initializing table " + i.toString( ) );

			if( !i.tableExists( ) )
			{
				i.createTable( );
			}
		}
	}

	@Override
	public CompletableFuture<Player> addPlayer( String teamName, Player player )
	{
		return getTeamExc( teamName ).thenApply( r -> playerStorage.addPlayer( r.getId( ), player ) );
	}

	@Override
	public CompletableFuture<Void> updatePlayer( long id, Player newPlayer )
	{
		return getPlayer( id ).thenApply( r -> playerStorage.updatePlayer( r, newPlayer ) );
	}

	@Override
	public CompletableFuture<Void> removePlayer( long id )
	{
		return supplyAsync( ( ) -> playerStorage.removePlayer( id ) );
	}

	@Override
	public CompletableFuture<Player> getPlayer( long id )
	{
		return supplyAsync( ( ) -> playerStorage.getPlayer( id ) );
	}

	@Override
	public CompletableFuture<List<Player>> getAllTeamPlayers( String teamName )
	{
		return getTeamExc( teamName ).thenApply( r -> playerStorage.getAllTeamPlayers( r.getId( ) ) );
	}

	@Override
	public CompletableFuture<Team> addTeam( String teamName )
	{
		return getTeam( teamName )
				.thenApply( team -> {
					if( team != null )
					{
						throw new CompletionException( new StorageException( "Team " + teamName + " already exists!" ) );
					}

					return team;
				} )
				.thenApply( r -> teamStorage.createTeam( teamName ) );
	}


	@Override
	public CompletableFuture<Team> getTeam( String teamName )
	{
		return supplyAsync( ( ) -> teamStorage.getTeam( teamName ) );
	}

	@Override
	public CompletableFuture<Void> removeTeam( String teamName )
	{
		return supplyAsync( ( ) -> teamStorage.removeTeam( teamName ) );
	}

	@Override
	public CompletableFuture<Void> updateTeam( String teamName, Team team )
	{
		return getTeamExc( teamName ).thenApply( r -> teamStorage.updateTeam( r, team ) );
	}

	@Override
	public CompletableFuture<Match> addMatch( Team teamA, Team teamB )
	{
		return null;
	}

	@Override
	public CompletableFuture<Void> endMatch( Match match, Team winner )
	{
		return null;
	}

	@Override
	public CompletableFuture<Void> updateMatch( Match match )
	{
		return null;
	}

	@Override
	public CompletableFuture<Match> getMatch( long id )
	{
		return null;
	}

	@Override
	public CompletableFuture<List<Player>> getAllPlayers( )
	{
		return supplyAsync( ( ) -> playerStorage.getAllPlayers( ) );
	}

	@Override
	public CompletableFuture<List<Team>> getAllTeams( )
	{
		return supplyAsync( ( ) -> teamStorage.getAllTeams( ) );
	}

	@Override
	public CompletableFuture<List<Match>> getAllMatches( )
	{
		return null;
	}


	public boolean isDatabaseInitialized( )
	{
		for( DbStorage i : activeStorage )
		{
			if( !i.tableExists( ) )
			{
				return false;
			}
		}
		return true;
	}

	public boolean isDatabaseEmpty( )
	{
		for( DbStorage i : activeStorage )
		{
			if( i.isTableEmpty( ) )
			{
				return true;
			}
		}

		return false;
	}


	public String getFileName( )
	{
		return db.getFile( ).getName( );
	}


	private CompletableFuture<Team> getTeamExc( String teamName ) throws CompletionException
	{
		return getTeam( teamName )
				.thenApply( r -> {
					if( r == null )
					{
						throw new CompletionException( new StorageException( "Team " + teamName + " not found!" ) );
					}

					return r;
				} );
	}

}
