import Common.GlobalInstance;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.Controller.SelectDatabaseController;
import ui.Dialog.ModalEx.UiAlert;
import ui.Helper.UiBaseContainer;
import ui.UiCoreEventHandler;

import java.util.logging.Logger;

public class App extends Application
{
	private final Logger logger = Logger.getLogger( this.getClass( ).getName( ) );

	private final UiCoreEventHandler eventHandler = new UiCoreEventHandler();

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
			eventHandler.registerEvents( primaryStage );
		}
		primaryStage.initStyle( StageStyle.UNDECORATED );

		try
		{
			if( Font.loadFont( GlobalInstance.getResourceUrl( "fonts/fontawesome-webfont.ttf" ), 12 ) == null )
			{
				throw new Exception( "Failed to load font!" );
			}

			UiBaseContainer container = new UiBaseContainer( );
			{
				container.setCenter( "Select Database", SelectDatabaseController.RESOURCE_ID );
			}

			Scene scene = new Scene( container, 800, 400 );

			scene.getStylesheets( ).clear( );
			scene.getStylesheets( ).add( GlobalInstance.getResourceUrl( "styles/style.css" ) );

			primaryStage.setScene( scene );
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
