import Common.ResourceLoader;
import Core.GlobalInstance;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.Controller.SelectDatabaseController;
import ui.Helper.UiBaseContainer;
import ui.Helper.UiEvent;

import java.util.logging.Logger;

public class App extends Application
{
	private final Logger log = Logger.getLogger( this.getClass( ).getName( ) );
	private final ResourceLoader loader = new ResourceLoader( getClass( ) );

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
			scene.getStylesheets( ).add( ResourceLoader.getResourceUrl( "styles/style.css" ) );

			primaryStage.setScene( scene );
			primaryStage.show( );
		}
		catch( Exception ex )
		{
			log.info( "startup failed!" + ex );

			Alert msgBox = new Alert( Alert.AlertType.ERROR );
			{
				msgBox.setHeaderText( "Startup failed! :( " );
				msgBox.setContentText( ex.toString( ) );
			}
			msgBox.showAndWait( );
		}
	}

	private void addCriticalErrorHandler( final Stage primaryStage )
	{
		primaryStage.addEventHandler( UiEvent.CORE_EXCEPTION, event -> {
			Alert msgBox = new Alert( Alert.AlertType.ERROR );
			{
				msgBox.setHeaderText( "something failed! :( " );
				msgBox.setContentText( ( ( Exception ) event.getEventData() ).getMessage( ) );
			}
			msgBox.showAndWait( );

			Platform.exit( );
			System.exit( 0 );
		} );
	}

}

			/*
			UiBaseDialog dlg = new UiBaseDialog( );
			FXMLLoader loader = new FXMLLoader( getClass( ).getResource( "fxml/dialogs/editPlayerDialog.fxml" ) );
			DialogPane pane = loader.load( );

			dlg.getDialogPane( ).setContent( pane );
			dlg.initOwner( primaryStage );
			dlg.setDialogTitle( pain.getHeaderText( ) );


			pane.lookupButton( ButtonType.CLOSE ).addEventFilter( ActionEvent.ACTION, e -> {
				System.out.println( "TOPKEK" );
			} );

			dlg.show( );
			*/