import Common.LogLevel;
import Core.GlobalInstance;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.Controller.SelectDatabaseController;
import ui.Dialog.ModalEx.UiAlert;
import ui.Helper.UiBaseContainer;
import ui.Helper.UiEvent;

import java.util.logging.Logger;

public class App extends Application
{
	private final Logger log = Logger.getLogger( this.getClass( ).getName( ) );



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
			addCriticalErrorHandler( primaryStage );
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
			log.info( "startup failed!" + ex );

			UiAlert msgBox = new UiAlert( Alert.AlertType.ERROR );
			{
				msgBox.setHeaderText( "Startup failed! :( " );
				msgBox.setContentText( ex.toString( ) );
				msgBox.addStackTraceArea( ex );
			}
			msgBox.showAndWait( );
		}
	}

	private void addCriticalErrorHandler( final Stage primaryStage )
	{
		primaryStage.addEventHandler( UiEvent.CORE_EXCEPTION, event -> {
			assert event.getEventData( ) instanceof Exception;


			Exception e = ( Exception ) event.getEventData( );

			log.log( LogLevel.ERROR, "Core error! " + e.getMessage( ) );

			UiAlert msgBox = new UiAlert( Alert.AlertType.ERROR );
			{
				msgBox.setHeaderText( "something failed! :( " );
				msgBox.setContentText( e.getMessage( ) );
				msgBox.addStackTraceArea( e );
			}
			msgBox.showAndWait( );

			Platform.exit( );
			System.exit( 0 );
		} );
	}
}
