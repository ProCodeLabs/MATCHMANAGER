package Database.Connection;

import Common.LogLevel;
import Database.CoreClasses.Match;
import Database.CoreClasses.Player;
import Database.CoreClasses.Team;

import java.sql.*;
import java.util.logging.Logger;

//TODO: create static statement.

public class DatabaseConnector
{
	private static final Logger log = Logger.getLogger( DatabaseConnector.class.getClass( ).getName( ) );
	private static Connection connection;

	private String _DatabaseName;

	public DatabaseConnector( String _DatabaseName )
	{
		this._DatabaseName = _DatabaseName + ".sqlite";
	}

	public void connectDatabase( )
	{
		try
		{
			if( connection != null )
			{
				return;
			}

			System.out.println( "Creating Connection to Database..." );
			connection = DriverManager.getConnection( "jdbc:sqlite:" + _DatabaseName );
			if( !connection.isClosed( ) )
			{
				System.out.println( "...Connection established" );
				try
				{
					Statement stmt = connection.createStatement( );

				} catch( Exception ex )
				{
					log.log( LogLevel.ERROR, "FAIL WITH STATEMENT" + ex );
				}
			}
			else
			{
				System.out.println( "... Connection refused" );
			}
		} catch( SQLException e )
		{
			throw new RuntimeException( e );
		}

		Runtime.getRuntime( ).addShutdownHook( new Thread( )
		{
			public void run( )
			{
				try
				{
					if( !connection.isClosed( ) && connection != null )
					{
						connection.close( );
						if( connection.isClosed( ) )
							System.out.println( "Connection to Database closed" );
					}
				} catch( SQLException e )
				{
					e.printStackTrace( );
				}
			}
		} );
	}

	//TODO: shorten sql string build -> String string = String.format("A string %s", aVariable);
	public void addPlayer( Player player )
	{
		String id = Integer.toString( getNextId( "Player" ) );

		String sqlString = "INSERT INTO Player " +
				"VALUES ('" +
				id + "','" +
				player.getSurname( ) + "','" +
				player.getLastname( ) + "','" +
				player.getNickname( ) + "','" +
				player.getImage( ) + "')";
		log.log( LogLevel.SUCCESS, "SQL STRING (ADDPLAYER) : " + sqlString );

		try
		{
			Statement stm = connection.createStatement( );
			stm.execute( sqlString );
			log.log( LogLevel.SUCCESS, "SQL STRING (ADDPLAYER) PASSED." );
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "SQL STRING (ADDPLAYER) FAILED. -> " + ex );
		}


	}

	//TODO: shorten sql string build -> String string = String.format("A string %s", aVariable);
	public void addTeam( Team team )
	{
		String id = Integer.toString( getNextId( "Team" ) );

		String sqlString = "INSERT INTO Team " +
				"VALUES('" +
				id + "','" +
				team.getName( ) + "')";
		log.log( LogLevel.INFO, "SQL STRING (ADDTEAM) : " + sqlString );

		try
		{
			Statement stm = connection.createStatement( );
			stm.execute( sqlString );
			log.log( LogLevel.SUCCESS, "SQL STRING (ADDTEAM) PASSED." );
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "SQL STRING (ADDTEAM) FAILED. -> " + ex );
		}
	}

	//TODO: shorten sql string build -> String string = String.format("A string %s", aVariable);
	public void addMatch( Match match )
	{
		String id = Integer.toString( getNextId( "Match" ) );

		String sqlString = "INSERT INTO Match " +
				"VALUES('" +
				id + "','" +
				match.getDate( ) + "','" +
				match.getTeamOne( ).toString( ) + "','" +
				match.getTeamTwo( ).toString( ) + "')";
		log.log( LogLevel.INFO, "SQL STRING (ADDMATCH) : " + sqlString );

		try
		{
			Statement stm = connection.createStatement( );
			stm.execute( sqlString );
			log.log( LogLevel.SUCCESS, "SQL STRING (ADDMATCH) PASSED." );
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "SQL STRING (ADDMATCH) FAILED. -> " + ex );
		}

	}


	public void removePlayer( Player player )
	{
		//TODO: Implement me
	}

	public void removeTeam( Team team )
	{
		//TODO: Implement me
	}

	public void removeMatch( Match match )
	{
		//TODO: Implement me
	}

	public int getNextId( String tableName )
	{
		String sqlString = "SELECT id FROM " + tableName;
		try
		{
			Statement stm = connection.createStatement( );
			ResultSet s = stm.executeQuery( sqlString );
			int maxID = 0;
			while( s.next( ) )
			{
				maxID = s.getInt( "Id" );
			}
			return maxID + 1;
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "Cant find Table" + ex );
			return 0;
		}
	}

}

