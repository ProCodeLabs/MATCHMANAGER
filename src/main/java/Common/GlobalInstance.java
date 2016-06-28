package Common;


import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class GlobalInstance
{
	private static Stage _primaryStage;
	private static Class _appClass;


	public static void setAppClass( Class appClass )
	{
		_appClass = appClass;
	}

	public static Class getAppClass( )
	{
		return _appClass;
	}

	public static void setPrimaryStage( Stage primaryStage )
	{
		_primaryStage = primaryStage;
	}

	public static Stage getPrimaryStage( )
	{
		return _primaryStage;
	}


	public static java.net.URL getResource( String resourceName )
	{
		return getAppClass( ).getResource( resourceName );
	}

	public static String getResourceUrl( String resourceName )
	{
		return getResource( resourceName ).toExternalForm( );
	}

	public static String readResource( String resourceName ) throws IOException
	{
		InputStream stream = getAppClass( ).getResourceAsStream( resourceName );

		if( stream == null )
		{
			throw new IOException( "Failed to create stream! resource: " + resourceName );
		}

		return Util.drainInputStream( stream );
	}

}
