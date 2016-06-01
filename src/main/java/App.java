import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;
import ui.Helper.UiBaseContainer;

import java.io.File;
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
		try
		{
			primaryStage.initStyle( StageStyle.UNDECORATED );


			UiBaseContainer container = new UiBaseContainer( );
			{
				container.setTitle( "Select Database" );
				container.setCenter( FXMLLoader.load( getClass( ).getResource( "fxml/panes/selectDatabase.fxml" ) ) );
			}


			//BorderPane pane = FXMLLoader.load( getClass( ).getResource( "fxml/uiBase.fxml" ) );

			//pane.setCenter( FXMLLoader.load( getClass( ).getResource( "fxml/panes/selectDatabase.fxml" ) ) );


			Scene scene = new Scene( container );

			primaryStage.setScene( scene );
			primaryStage.show( );
		}
		catch( Exception ex )
		{
			log.info( "Error in start: " + ex );
		}
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



			/*
				private static double xOffset = 0;
	private static double yOffset = 0;

	private static double BORDER_HEIGHT = 18;
	private static boolean isDragged = false;
			scene.getStylesheets().add( getClass().getResource( "styles/form-style.css" ).toExternalForm() );

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
			} );*/