package Database.Connection;

import Common.LogLevel;
import Database.CoreClasses.Match;
import Database.CoreClasses.Player;
import Database.CoreClasses.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//TODO: create static statement.

public class DatabaseConnector
{
	private static final Logger log = Logger.getLogger( DatabaseConnector.class.getClass( ).getName( ) );
	private static Connection connection;
	private static Statement statement;

	public DatabaseConnector( )
	{

	}

	private void createStatement( )
	{
		try
		{
			statement = connection.createStatement( );
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "Could not create SQL Statement -> " + ex );
		}

	}

	public void connectDatabase( String DatabaseName )
	{
		try
		{
			if( connection != null )
			{
				return;
			}

			System.out.println( "Creating Connection to Database..." );
			connection = DriverManager.getConnection( "jdbc:sqlite:" + DatabaseName );
			if( !connection.isClosed( ) )
			{
				System.out.println( "...Connection established" );
				createStatement( );
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

	public void addPlayer( Player player )
	{
		String id = Integer.toString( getNextId( "Player" ) );

		String sqlString = String.format( "INSERT INTO Player VALUES ( '%s' ,'%s' , '%s' , '%s' , '%s' )",
										  id,
										  player.getSurname( ),
										  player.getLastname( ),
										  player.getNickname( ),
										  player.getImage( ) );

		log.log( LogLevel.SUCCESS, "SQL STRING (ADDPLAYER) short : " + sqlString );

		try
		{
			statement.execute( sqlString );
			log.log( LogLevel.SUCCESS, "SQL STRING (ADDPLAYER) PASSED." );
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "SQL STRING (ADDPLAYER) FAILED. -> " + ex );
		}
	}

	public void addTeam( Team team )
	{
		String id = Integer.toString( getNextId( "Team" ) );

		String sqlString = String.format( "INSERT INTO Team VALUES ( '%s' , '%s' )",
										  id,
										  team.getName( ) );

		log.log( LogLevel.INFO, "SQL STRING (ADDTEAM) : " + sqlString );

		try
		{
			statement.execute( sqlString );
			log.log( LogLevel.SUCCESS, "SQL STRING (ADDTEAM) PASSED." );
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "SQL STRING (ADDTEAM) FAILED. -> " + ex );
		}
	}

	public void addMatch( Match match )
	{
		String id = Integer.toString( getNextId( "Match" ) );

		String sqlString = String.format( "INSERT INTO Match VALUES ( '%s' , '%s' , '%s' , '%s')",
										  id,
										  match.getDate( ),
										  match.getTeamOne( ),
										  match.getTeamTwo( ) );

		log.log( LogLevel.INFO, "SQL STRING (ADDMATCH) : " + sqlString );

		try
		{
			statement.execute( sqlString );
			log.log( LogLevel.SUCCESS, "SQL STRING (ADDMATCH) PASSED." );
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "SQL STRING (ADDMATCH) FAILED. -> " + ex );
		}

	}

	public List<Player> getPlayers( )
	{
		List<Player> list = new ArrayList<>( );

		String sqlString = "SELECT * FROM Player";
		try
		{
			ResultSet result = statement.executeQuery( sqlString );
			while( result.next( ) )
			{
				list.add( new Player(
						result.getInt( "Id" ),
						result.getString( "Surname" ),
						result.getString( "Lastname" ),
						result.getString( "Nickname" ),
						result.getString( "Image" )
				) );
			}
			log.log( LogLevel.SUCCESS, "Got Player Data from Table" );
			return list;
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "Could not get Player Data from Table" + ex );
			return null;
		}
	}

	public List<Team> getTeams( )
	{
		List<Team> list = new ArrayList<>( );

		String sqlString = "SELECT * FROM Team";
		try
		{
			ResultSet result = statement.executeQuery( sqlString );
			while( result.next( ) )
			{
				list.add( new Team(
						result.getInt( "Id" ),
						result.getString( "Name" )
				) );
			}
			log.log( LogLevel.SUCCESS, "Got Team Data from Table" );
			return list;
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "Could not get Team Data from Table" + ex );
			return null;
		}
	}

	public List<Match> getMatches( )
	{
		List<Match> list = new ArrayList<>( );

		String sqlString = "SELECT * FROM Match";
		try
		{
			ResultSet result = statement.executeQuery( sqlString );
			while( result.next( ) )
			{
				list.add( new Match(
						result.getInt( "Id" ),
						result.getString( "Time" ),
						result.getString( "TeamOne" ),
						result.getString( "TeamTwo" )
				) );
			}
			log.log( LogLevel.SUCCESS, "Got Player Data from Table" );
			return list;
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "Could not get Player Data from Table" + ex );
			return null;
		}
	}

	public void removePlayer( Player player )
	{
		try
		{
			deleteRow( "Player", player.getId( ) );
			log.log( LogLevel.SUCCESS, "Player successfully deleted | ID = " + player.getId( ) );
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "Player could not be deleted | ID = " + player.getId( ) + "\n" + ex );
		}
	}

	public void removeTeam( Team team )
	{
		try
		{
			deleteRow( "Team", team.getId( ) );
			log.log( LogLevel.SUCCESS, "Team successfully deleted | ID = " + team.getId( ) );
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "Team could not be deleted | ID = " + team.getId( ) + "\n" + ex );
		}
	}

	public void removeMatch( Match match )
	{
		try
		{
			deleteRow( "Match", match.getId( ) );
			log.log( LogLevel.SUCCESS, "Player successfully deleted | ID = " + match.getId( ) );
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "Player could not be deleted | ID = " + match.getId( ) + "\n" + ex );
		}
	}

	public void clearTable( String tableName )
	{
		try
		{
			statement.executeUpdate( "DELETE FROM " + tableName );
			log.log( LogLevel.SUCCESS, "Deleted data from " + tableName );
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "Could not delete data from " + tableName + "\n" + ex );
		}

	}

	public int getNextId( String tableName )
	{
		String sqlString = "SELECT id FROM " + tableName;
		try
		{
			ResultSet s = statement.executeQuery( sqlString );
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

	private boolean deleteRow( String table, int id ) throws Exception
	{
		String sqlString = String.format( "DELETE FROM '%s' WHERE Id = '%s'",
										  table,
										  id );

		try
		{
			statement.executeUpdate( sqlString );
			return true;
		} catch( Exception ex )
		{
			throw new Exception( ex );
		}
	}

}

