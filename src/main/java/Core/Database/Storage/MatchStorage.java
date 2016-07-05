package Core.Database.Storage;

import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Data.Match;
import Core.Data.Team;
import Core.Database.Storage.Helper.DbStorage;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchStorage extends DbStorage
{
	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	public MatchStorage( SqlJetDb db )
	{
		super( db );
	}


	public Match addMatch( Team teamA, Team teamB, Team resultTeam )
	{
		return transactionCommit( SqlJetTransactionMode.WRITE, ( ) -> {
			long rowId = getTable( ).insert( teamA.getId( ), teamB.getId( ), resultTeam.getId( ) );

			logger.success( "added new match " + teamA.getTeamName( ) + " vs " +
									teamB.getTeamName( ) + " won: " + resultTeam.getTeamName( )
			);

			return new Match( rowId, teamA, teamB, resultTeam );
		} );
	}

	public List<Match> getAllMatches( )
	{
		return reflectException( ( ) -> {
			ArrayList<Match> matchList = new ArrayList<>( );
			{
				fetchRows( getTable( ).getPrimaryKeyIndexName( ), c -> matchList.add( serializeMatch( c ) ) );
			}
			return matchList;
		} );
	}

	public int getMatchCount( )
	{
		return reflectException( ( ) -> {
			AtomicInteger i = new AtomicInteger( );
			{
				fetchRows( getTable( ).getPrimaryKeyIndexName( ), c -> i.incrementAndGet( ) );
			}
			return i.get( );
		} );
	}

	private Match serializeMatch( ISqlJetCursor cursor ) throws SqlJetException
	{
		long id = cursor.getRowId( );
		long teamAId = cursor.getInteger( 1 );
		long teamBId = cursor.getInteger( 2 );
		long resultTeam = cursor.getInteger( 3 );


		return new Match( id, teamAId, teamBId, resultTeam );
	}

	@Override
	protected String getTableName( )
	{
		return "match_storage";
	}

	@Override
	protected String getResourceName( )
	{
		return "sql/matchStorage.sql";
	}
}
