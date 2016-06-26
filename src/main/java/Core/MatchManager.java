package Core;

import Core.Data.Team;
import Core.Database.DbStorage;

import java.io.File;
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






}
