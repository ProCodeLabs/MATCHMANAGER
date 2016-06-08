import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.Helper.UiBaseContainer;

import java.util.logging.Logger;

public class App extends Application
{
	final Logger log = Logger.getLogger( this.getClass( ).getName( ) );


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
				container.setTitle( "Select Database" );
				container.setCenter( FXMLLoader.load( getClass( ).getResource( "fxml/centerContent/selectDatabase.fxml" ) ) );
				//container.setCenter( FXMLLoader.load( getClass().getResource( "fxml/dialogs/editPlayerDialog.fxml" ) ) );
			}


			Scene scene = new Scene( container, 800, 400 );



			String css = App.class.getResource( "styles/style.css" ).toExternalForm( );
			scene.getStylesheets( ).clear( );
			scene.getStylesheets( ).add( css );

			primaryStage.setScene( scene );
			primaryStage.show( );

			/*Stage dialog = new Stage();
			dialog.initStyle(StageStyle.UTILITY);
			Scene dialogScene = new Scene(FXMLLoader.load( getClass().getResource( "fxml/dialogs/editPlayerDialog.fxml" ) ));
			dialogScene.getStylesheets().add( css2 );
			dialog.setScene(dialogScene);
			dialog.show();
			*/
			Dialog<ButtonType> dlg = new Dialog<>();
			dlg.setTitle( "ss" );
			dlg.initStyle( StageStyle.UNDECORATED );
			dlg.setDialogPane( FXMLLoader.load( getClass().getResource( "fxml/dialogs/editTeamDialog.fxml" ) ) );

			dlg.showAndWait().ifPresent( result -> {
				if (result == ButtonType.CLOSE) {
					dlg.close();
				}
			} );


		}
		catch( Exception ex )
		{
			log.info( "Error in start: " + ex );
		}
	}
}
