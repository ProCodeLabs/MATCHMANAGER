package Database;

import Common.LoggerExLevel;
import Core.Match;
import Core.Player;
import Core.Team;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.File;
import java.sql.*;
import java.util.logging.Logger;

//TODO Add Methods for Inputs

public class DatabaseConnector
{
	private static final Logger log = Logger.getLogger( DatabaseConnector.class.getClass( ).getName( ) );
	private static Connection connection;

	private String _DatabaseName;

	public DatabaseConnector( String _DatabaseName )
	{
		this._DatabaseName = _DatabaseName+".sqlite";
	}

	public void databaseSetup( ) throws SqlJetException
	{
		File dbFile = new File( _DatabaseName );
		dbFile.delete( );

		SqlJetDb db = SqlJetDb.open( dbFile, true );
		db.getOptions( ).setAutovacuum( true );
		db.beginTransaction( SqlJetTransactionMode.WRITE );

		try
		{
			db.getOptions( ).setUserVersion( 1 );
		} finally
		{
			db.commit( );
		}
	}

	public void connectDatabase ( ) {
		try {
			if (connection != null)
				return;
			System.out.println("Creating Connection to Database...");
			connection = DriverManager.getConnection( "jdbc:sqlite:" + _DatabaseName);
			if (!connection.isClosed())
			{
				System.out.println( "...Connection established" );
				try
				{
					Statement stmt = connection.createStatement( );

				} catch( Exception ex ) {
					log.log( LoggerExLevel.ERROR, "FAIL WITH STATEMENT" + ex );
				}
			}
			else
			{
				System.out.println("... Connection refused");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					if (!connection.isClosed() && connection != null) {
						connection.close();
						if (connection.isClosed())
							System.out.println("Connection to Database closed");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void createNewDatabase( String name )
	{
		File f = new File( name + ".sqlite" );
		try
		{
			f.createNewFile( );
		} catch( Exception ex )
		{
			log.info( "Cannot create Database" );
		}
	}

	public void addPlayer( Player player )
	{
		String sqlString = "INSERT INTO Player " +
				"VALUES(" +
				player.getSurname( ) + "," +
				player.getLastname( ) + "," +
				player.getNickname( ) + "," +
				player.getImage( ) + ")";
		log.log( LoggerExLevel.SUCCESS, "SQL STRING (ADDPLAYER) : " + sqlString );
	}

	public void addTeam( Team team )
	{
		String sqlString = "INSERT INTO Team " +
				"VALUES(" +
				team.getName( ) + ")";
		log.log( LoggerExLevel.SUCCESS, "SQL STRING (ADDTEAM) : " + sqlString );
	}

	public void addMatch( Match match )
	{
		String sqlString = "INSERT INTO Team " +
				"VALUES(" +
				match.getDate( ) + "," +
				match.getTeamOne( ).toString() + "," +
				match.getTeamTwo( ).toString() +
				")";
		log.log( LoggerExLevel.SUCCESS, "SQL STRING (ADDMATCH) : " + sqlString );
	}

	public void testStatement() {
		try
		{
			Statement stmt = connection.createStatement( );
			stmt.execute( "INSERT INTO Player VALUES ('Jonathan', 'Klaiber', 'J O H N Y #', 'NULL')" );
		} catch( Exception ex ) {
			log.log( LoggerExLevel.ERROR, "FAIL WITH STATEMENT" + ex );
		}
	}
}

