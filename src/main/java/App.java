import java.util.logging.*;

import Common.LoggerExLevel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.File;

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
						primaryStage.setX( event.getScreenX( ) - xOffset  );
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
		}
		catch( Exception ex )
		{
			log.info( "Error in start: " + ex );
		}

		databaseSetup( );

	}

	public void databaseSetup( ) throws SqlJetException
	{
		String DB_NAME = "DATABASE.sqlite";

		File dbFile = new File( DB_NAME );
		dbFile.delete( );

		SqlJetDb db = SqlJetDb.open( dbFile, true );
		db.getOptions( ).setAutovacuum( true );
		db.beginTransaction( SqlJetTransactionMode.WRITE );

		try
		{
			db.getOptions( ).setUserVersion( 1 );
		}
		finally
		{
			db.commit( );
		}
	}
}
