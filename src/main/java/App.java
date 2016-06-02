import Database.Connection.DatabaseConnector;
import Database.CoreClasses.Match;
import Database.CoreClasses.Player;
import Database.CoreClasses.Team;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.Helper.UiBaseContainer;

import java.util.logging.Logger;

public class App extends Application
{
	Logger log = Logger.getLogger( this.getClass( ).getName( ) );


	public static void main( String[] args )
	{
		launch( args );
	}

	@Override
	public void start( final Stage primaryStage )
	{
		Player p = new Player( "x", "y", "z", "NULL" );
		Team t = new Team( "Fnatic" );
		Match m = new Match( "2016-10-05",t,t );

		DatabaseConnector dbc = new DatabaseConnector( "STARTUP" );
		dbc.connectDatabase( );
		dbc.addPlayer( p );
		dbc.addTeam( t );
		dbc.addMatch( m );
		//dbc.testStatement();


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
