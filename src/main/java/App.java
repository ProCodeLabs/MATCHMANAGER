import Database.CoreClasses.Database;
import Database.CoreClasses.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tmatesoft.sqljet.core.SqlJetException;
import ui.Helper.UiBaseContainer;

import java.util.logging.Logger;

//Test

public class App extends Application
{

	Logger log = Logger.getLogger( this.getClass( ).getName( ) );

	private static double xOffset = 0;
	private static double yOffset = 0;

	private static double BORDER_HEIGHT = 18;
	private static boolean isDragged = false;


	public static void main( String[] args )
	{
		launch( args );
	}

	@Override
	public void start( final Stage primaryStage ) throws SqlJetException
	{
		Database.setDatabase( "STARTUP" );
		//DATABASE TEST
		Player p = new Player( "Test", "123", "YOLO", "NULL" );
		//Team t = new Team( "Astralis" );
		//Team t2 = new Team( "Cloud9" );
		//Match m = new Match( "2000-05-05",t,t2 );

		//Database.removePlayer(new Player( 12 ));
		//Database.removeTeam( new Team( 8 ) );
		//Database.dropDataFromPlayerTable();

		try
		{
			primaryStage.initStyle( StageStyle.UNDECORATED );

			Font.loadFont(
					getClass( ).getResource( "fonts/fontawesome-webfont.ttf").toExternalForm(),
					10
			);

			UiBaseContainer container = new UiBaseContainer( );
			{
				container.setTitle( "Select Database" );
				container.setCenter( FXMLLoader.load( getClass( ).getResource( "fxml/panes/selectDatabase.fxml" ) ) );
			}


			Scene scene = new Scene( container );

			scene.getStylesheets().add("styles/metroTheme.css");
			scene.getStylesheets().add("styles/styles.css");

			primaryStage.setScene( scene );
			primaryStage.show( );
		}
		catch( Exception ex )
		{
			log.info( "Error in start: " + ex );
		}


	}


}
