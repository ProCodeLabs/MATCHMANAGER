import Database.CoreClasses.Database;
import Database.CoreClasses.Player;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tmatesoft.sqljet.core.SqlJetException;

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
