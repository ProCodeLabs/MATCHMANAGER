package Database.Connection;

import Common.LogLevel;

import java.io.File;
import java.util.logging.Logger;

import static Common.Files.path;

public class DatabaseHandler
{
	private static final Logger log = Logger.getLogger( DatabaseHandler.class.getName( ) );


	public static void createNewDatabase( String name )
	{
		File f = new File( path+File.separator+name + ".sqlite" );
		System.out.println(path+File.separator+name + ".sqlite");
		try
		{
			f.createNewFile( );
			log.log( LogLevel.SUCCESS, "Database created" );
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "Cannot create Database" );
		}
	}

	public static void deleteDatabase( String name )
	{
		File f = new File( path+File.separator+name+".sqlite"  );
		try
		{
			f.delete( );
			log.log( LogLevel.SUCCESS, "Database deleted" );
		} catch( Exception ex )
		{
			log.log( LogLevel.ERROR, "Cannot delete Database" + ex );
		}
	}

}
