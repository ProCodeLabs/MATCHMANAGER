package Common;


import javafx.stage.Stage;

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


}