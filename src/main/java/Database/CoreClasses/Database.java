package Database.CoreClasses;

import Database.Connection.DatabaseConnector;

/**
 * Created by JOHNY on 02.06.2016.
 */
public class Database
{
	protected static DatabaseConnector dbc = new DatabaseConnector( );

	public static void setDatabase( String database )
	{
		dbc.connectDatabase( database + ".sqlite" );
	}

	public static void removePlayer( Player player )
	{
		dbc.removePlayer( player );
	}

	public static void removeTeam( Team team )
	{
		dbc.removeTeam( team );
	}

	public static void removeMatch( Match match )
	{
		dbc.removeMatch( match );
	}

	public static void dropDataFromPlayerTable( )
	{
		dbc.clearTable( "Player" );
	}

	public static void dropDataFromTeamTable( )
	{
		dbc.clearTable( "Team" );
	}

	public static void dropDataFromMatchTable( )
	{
		dbc.clearTable( "Match" );
	}
}
