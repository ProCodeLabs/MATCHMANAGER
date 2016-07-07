import Common.Constants;
import Common.GlobalInstance;
import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Event.Manager.CoreEvent;
import Core.Event.Manager.CoreEventDispatcher;
import Core.Event.Manager.EventRegistrar;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.Container.UiCoreWindow;
import ui.Controller.SelectDatabaseController;

public class App extends Application
{
	private static final String APP_TITLE = "Matchmanager v0.1";
	private static final EventRegistrar registrar = new EventRegistrar( );

	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );


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
			registrar.registerEvents( );
		}
		primaryStage.initStyle( StageStyle.UNDECORATED );
		primaryStage.setTitle( APP_TITLE );

		try
		{

			if( Font.loadFont( GlobalInstance.getResourceUrl( Constants.PATH_FONT_RESOURCE ), 12 ) == null )
			{
				throw new Exception( "Failed to load font!" );
			}

			UiCoreWindow container = new UiCoreWindow( );
			{
				SelectDatabaseController.updateContainerStage( container );
			}
			primaryStage.setScene( container.createScene( 800, 400 ) );
			primaryStage.show( );
		}
		catch( Exception ex )
		{
			logger.info( "startup failed!" + ex );

			CoreEventDispatcher.fireEvent( CoreEvent.CORE_EXCEPTION, ex );

		}
	}
}
