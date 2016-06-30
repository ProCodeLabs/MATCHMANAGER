package Core.Database.Storage;


import Core.Data.Match;
import Core.Data.Team;
import Core.Database.Storage.Helper.StorageHelper;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;


import java.util.Calendar;
import java.util.Date;

public class MatchStorage extends StorageHelper
{
	private static final String TABLE_NAME = "match_tbl";

	public MatchStorage( )
	{
	}


	@Override
	protected void onInitializeStorage( ) throws SqlJetException
	{

	}

	@Override
	public boolean tableExists( )
	{
		return super.checkTableExists( TABLE_NAME );
	}

	public void addMatch( String matchName, Date date, Team teamA, Team teamB )
	{
		try
		{
			Calendar calendar = Calendar.getInstance( );
			calendar.clear( );
			getDatabase().beginTransaction( SqlJetTransactionMode.WRITE );
			try
			{
				ISqlJetTable table = getDatabase().getTable( "match_storage" );
				calendar.set( 1991, 4, 19 );
				table.insert( "Prochaskova", "Elena", calendar.getTimeInMillis( ) );
				calendar.set( 1967, 5, 19 );
				table.insert( "Scherbina", "Sergei", calendar.getTimeInMillis( ) );
				calendar.set( 1987, 6, 19 );
				table.insert( "Vadishev", "Semen", calendar.getTimeInMillis( ) );
				calendar.set( 1982, 7, 19 );
				table.insert( "Sinjushkin", "Alexander", calendar.getTimeInMillis( ) );
				calendar.set( 1979, 8, 19 );
				table.insert( "Stadnik", "Dmitry", calendar.getTimeInMillis( ) );
				calendar.set( 1977, 9, 19 );
				table.insert( "Kitaev", "Alexander", calendar.getTimeInMillis( ) );
			}
			finally
			{
				getDatabase().commit( );
			}
		}
		catch( SqlJetException e )
		{

		}
	}

	public Match getMatch( int matchId )
	{
		return null;
	}


}
