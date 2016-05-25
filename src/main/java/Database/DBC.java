package Database;

import java.io.File;
import java.util.logging.Logger;


public class DBC
{
	private static final Logger log = Logger.getLogger( DBC.class.getClass( ).getName( ) );

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
}
