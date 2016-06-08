import Common.ResourceLoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
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
				//container.setCenter( "Select Database", FXMLLoader.load( getClass( ).getResource( SelectDatabaseController.RESOURCE_ID ) ) );
			}


			Scene scene = new Scene( container, 800, 400 );

			String css2 = getClass( ).getResource( "styles/buttonStyles.css" ).toExternalForm( );
			scene.getStylesheets( ).clear( );
			scene.getStylesheets( ).add( css2 );


			primaryStage.setScene( scene );
			primaryStage.show( );



			Dialog dlg = new Dialog();

			FXMLLoader loader = new FXMLLoader( getClass().getResource( "fxml/dialogs/editMatchDialog.fxml" ) );
			DialogPane pain = loader.load( );

			dlg.getDialogPane().setContent( pain );
			dlg.initOwner( primaryStage );
			dlg.initModality( Modality.APPLICATION_MODAL );
			//dlg.initStyle( StageStyle.UNDECORATED );
			dlg.setTitle( pain.getHeaderText() );
			dlg.setResizable( false );

			pain.lookupButton( ButtonType.CLOSE ).addEventFilter( ActionEvent.ACTION, e ->{
				System.out.println( "TOPKEK" );
			} );

			dlg.show();

		}
		catch( Exception ex )
		{
			log.info( "Error in start: " + ex );
		}
	}


}
