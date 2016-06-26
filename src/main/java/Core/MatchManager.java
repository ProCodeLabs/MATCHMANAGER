package Core;

import Core.Data.Team;
import Core.Database.DbStorage;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Logger;


public class MatchManager
{
	private static final String _storageFolder = System.getProperty( "user.home" ) + File.separator + "Matchmanager";

	final Logger log = Logger.getLogger( this.getClass( ).getName( ) );


	private DbStorage _storage = new DbStorage( );
	private Vector<Team> _teams = new Vector<>( );

	public MatchManager( )
	{
	}

	public void loadDatabase( String path ) throws IOException
	{
		File f = new File( path );

		if( !f.exists( ) || f.isDirectory( ) )
		{
			throw new IOException( "file path is not valid!" );
		}

		try
		{
			SqlJetDb db = SqlJetDb.open( f, true );
			db.beginTransaction( SqlJetTransactionMode.WRITE );
			try
			{
				db.getOptions( ).setUserVersion( 1 );
			}
			finally
			{
				db.commit( );
			}
		}
		catch( SqlJetException e )
		{
			throw new IOException( "Failed to open db file! File corrupted? " + e.getMessage( ) );
		}

	}

	public void createDatabase( String name ) throws IOException
	{

	}



}
