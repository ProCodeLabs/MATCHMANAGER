import Common.Files;
import Common.GlobalInstance;
import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.Container.UiCoreWindow;
import ui.Controller.SelectDatabaseController;
import ui.Dialog.ModalEx.UiAlert;
import ui.StageCore.Helper.UiEventRegistrar;

public class App extends Application
{
	private static final String APP_TITLE = "Matchmanager v0.1";

	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );
	private final UiEventRegistrar registrar = new UiEventRegistrar();

	public App( )
	{
		GlobalInstance.setAppClass( getClass( ) );
	}

	public static void main( String[] args )
	{
		launch( args );
	}


	@Override
	public void start( final Stage primaryStage )
	{
		GlobalInstance.setPrimaryStage( primaryStage );
		{
			registrar.registerEvents( primaryStage );
		}
		primaryStage.initStyle( StageStyle.UNDECORATED );
		primaryStage.setTitle( APP_TITLE );

		try
		{
			if( Font.loadFont( GlobalInstance.getResourceUrl( Files.PATH_FONT_RESOURCE ), 12 ) == null )
			{
				throw new Exception( "Failed to load font!" );
			}

			UiCoreWindow container = new UiCoreWindow( );
			{
				container.setCenter( "Select Database", SelectDatabaseController.RESOURCE_ID );
			}
			primaryStage.setScene( container.createScene( 800, 400 ) );
			primaryStage.show( );
		}
		catch( Exception ex )
		{
			logger.info( "startup failed!" + ex );

			UiAlert msgBox = new UiAlert( Alert.AlertType.ERROR );
			{
				msgBox.setHeaderText( "Startup failed! :( " );
				msgBox.setContentText( ex.toString( ) );
				msgBox.addStackTraceArea( ex );
			}
			msgBox.showAndWait( );
		}
	}
}
