/**
 * Created by JOHNY on 30.04.2016.
 */

//TODO DO STUFF!
//TODO commit me

//TODO last commit today xD

import Core.Match;
import Core.Player;
import Core.Team;
import Database.DatabaseConnector;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tmatesoft.sqljet.core.SqlJetException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

//Match

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

	//teeesta1234
	@Override
	public void start( final Stage primaryStage ) throws SqlJetException
	{
		// DATABASE ------------------------------------------------------
		DatabaseConnector db = new DatabaseConnector("STARTUP");
		db.connectDatabase();
		//db.testStatement();
		db.addPlayer( new Player( "Olof", "Meister", "OLOFMEISTER", "NULL"  ));
		db.addTeam( new Team( "Fnatic" ) );
		DateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		db.addMatch(new Match(dateFormat.format(cal.getTime()).toString(), new Team( "Fnatic" ), new Team( "Astralis" ) ) );
		// /DATABASE -----------------------------------------------------
		try
		{
			Parent root = FXMLLoader.load( getClass( ).getResource( "fxml/layout.fxml" ) );
			primaryStage.initStyle( StageStyle.UNDECORATED );

			Scene scene = new Scene( root );


			scene.addEventFilter( MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>( )
			{
				public void handle( MouseEvent event )
				{
					if( event.getSceneY( ) <= BORDER_HEIGHT )
					{
						xOffset = event.getSceneX( );
						yOffset = event.getSceneY( );

						//primaryStage.setX( event.getScreenX( ) - event.getSceneX( ) );
						//primaryStage.setY( event.getScreenY( ) - event.getSceneY( ) );

						isDragged = true;
						event.consume( );
					}
				}
			} );

			scene.addEventFilter( MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>( )
			{
				public void handle( MouseEvent event )
				{
					if( isDragged )
					{
						primaryStage.setX( event.getScreenX( ) - xOffset );
						primaryStage.setY( event.getScreenY( ) - yOffset );

						event.consume( );
					}
				}
			} );

			scene.addEventFilter( MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>( )
			{
				public void handle( MouseEvent event )
				{
					isDragged = false;
				}
			} );


			primaryStage.setScene( scene );
			primaryStage.show( );
		} catch( Exception ex )
		{
			log.info( "Error in start: " + ex );
		}


	}


}
