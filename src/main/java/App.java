import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;
import ui.Helper.UIBaseContainer;

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

			Font.loadFont(
					getClass( ).getResource( "fonts/fontawesome-webfont.ttf").toExternalForm(),
					10
			);

			UIBaseContainer container = new UIBaseContainer( );
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
