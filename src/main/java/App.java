import Common.ResourceLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.Controller.SelectDatabaseController;
import ui.Helper.UiBaseContainer;

import java.util.logging.Logger;

public class App extends Application
{
	final Logger log = Logger.getLogger( this.getClass( ).getName( ) );
	final ResourceLoader loader = new ResourceLoader(getClass());

	public static void main( String[] args )
	{
		launch( args );
	}

	@Override
	public void start( final Stage primaryStage )
	{
		try
		{
			primaryStage.initStyle( StageStyle.UNDECORATED );

			Font.loadFont(
					getClass( ).getResource( "fonts/fontawesome-webfont.ttf" ).toExternalForm( ),
					12
			);

			UiBaseContainer container = new UiBaseContainer( );
			{
				container.setCenter( "Select Database", SelectDatabaseController.RESOURCE_ID );
			}


			Scene scene = new Scene( container, 800, 400 );


			String css = App.class.getResource( "styles/style.css" ).toExternalForm( );
			scene.getStylesheets( ).clear( );
			scene.getStylesheets( ).add( css );

			primaryStage.setScene( scene );
			primaryStage.show( );


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
		}
		catch( Exception ex )
		{
			log.info( "Error in start: " + ex );
		}
	}
}
