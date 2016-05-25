package Database;

import Core.*;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.File;
import java.util.logging.Logger;

//TODO Add Methods for Inputs

public class DBC
{
	private static final Logger log = Logger.getLogger( DBC.class.getClass( ).getName( ) );

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
		log.info( "SQL STRING (ADDPLAYER) : " + sqlString );
	}

	public void addTeam( Team team )
	{
		String sqlString = "INSERT INTO Team " +
				"VALUES(" +
				team.getName( ) + ")";
		log.info( "SQL STRING (ADDTEAM) : " + sqlString );
	}

	public void addMatch( Match match) {
		String sqlString = "INSERT INTO Team " +
				"VALUES(" +
				match.getDate() + ")";
		log.info( "SQL STRING (ADDMATCH) : " + sqlString );
	}
}

