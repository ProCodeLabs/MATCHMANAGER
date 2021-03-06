package Core;

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
import Core.Event.Manager.CoreEvent;
import Core.Event.Manager.CoreEventDispatcher;
import Core.Helper.StorageException;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.supplyAsync;


public class MatchManager implements IStorageImpl
{
	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );

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
			CoreEventDispatcher.fireEvent( CoreEvent.CORE_EXCEPTION, e );
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
		return supplyAsync( ( ) -> playerStorage.updatePlayer( id, newPlayer ) );
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
	public CompletableFuture<List<Player>> getAllPlayers( )
	{
		return supplyAsync( ( ) -> playerStorage.getAllPlayers( ) );
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

					return null;
				} )
				.thenApply( r -> teamStorage.createTeam( teamName ) );
	}


	@Override
	public CompletableFuture<Team> getTeam( String teamName )
	{
		return supplyAsync( ( ) -> teamStorage.getTeam( teamName ) );
	}

	@Override
	public CompletableFuture<Team> getTeam( long id )
	{
		return supplyAsync( ( ) -> teamStorage.getTeam( id ) );
	}

	@Override
	public CompletableFuture<Void> removeTeam( String teamName )
	{
		return getAllTeamPlayers( teamName ).thenApply( players -> {
			for( Player i : players )
			{
				try
				{
					removePlayer( i.getId( ) ).get( );
				}
				catch( InterruptedException | ExecutionException e )
				{
					throw new CompletionException( e );
				}
			}

			return null;
		} ).thenApply( r -> teamStorage.removeTeam( teamName ) );
	}

	@Override
	public CompletableFuture<Void> updateTeam( String teamName, Team newTeam )
	{
		return supplyAsync( ( ) -> teamStorage.updateTeam( teamName, newTeam ) );
	}

	@Override
	public CompletableFuture<List<Team>> getAllTeams( )
	{
		return supplyAsync( ( ) -> teamStorage.getAllTeams( ) );
	}


	@Override
	public CompletableFuture<Match> addMatch( Team teamA, Team teamB, Team resultTeam )
	{
		return supplyAsync( ( ) -> matchStorage.addMatch( teamA, teamB, resultTeam ) );
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
	public CompletableFuture<Integer> getMatchCount( )
	{
		return supplyAsync( ( ) -> matchStorage.getMatchCount( ) );
	}


	@Override
	public CompletableFuture<List<Match>> getAllMatches( )
	{
		return supplyAsync( ( ) -> matchStorage.getAllMatches( ) );
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

	public CompletableFuture<Integer> calculateMaxMatchNumber( )
	{
		return getAllTeams( ).thenApply( r -> calculateMatchCount( r.size( ) ) );
	}

	public static int calculateMatchCount( int teamCount )
	{
		assert teamCount >= 0;

		return teamCount == 0 ? 0 : ( teamCount - 1 );
	}


	public String getFileName( )
	{
		return db.getFile( ).getName( );
	}


}
