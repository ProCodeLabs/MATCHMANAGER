package Database;

import Common.LoggerExLevel;
import Core.*;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.File;
import java.util.logging.Logger;

//TODO Add Methods for Inputs

public class DatabaseConnector
{
	private static final Logger log = Logger.getLogger( DatabaseConnector.class.getClass( ).getName( ) );

	public void databaseSetup( ) throws SqlJetException
	{
		String DB_NAME = "DATABASE.sqlite";

		File dbFile = new File( DB_NAME );
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
}

