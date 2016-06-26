package Core.Database;

import Common.TaskManager;
import Core.GlobalInstance;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;
import ui.Helper.UiEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;


public class StorageManager
{
	private static final String _storageFolder = System.getProperty( "user.home" ) + File.separator + "Matchmanager";
	private static final String _fileStuffix = ".sqlite";
	private static final Logger log = Logger.getLogger( StorageManager.class.getName( ) );


	public static void createDatabase( String name ) throws IOException
	{
		String path = _storageFolder + File.separator + name + _fileStuffix;

		try
		{
			Paths.get( path );
		}
		catch( InvalidPathException | NullPointerException e )
		{
			throw new IOException( "Path is malformed Path: " + path + " " + e.getMessage( ) );
		}


		File f = new File( path );

		if( f.exists( ) )
		{
			throw new IOException( "File already exists" );
		}

		try
		{
			SqlJetDb db = SqlJetDb.open( f, true );
			db.beginTransaction( SqlJetTransactionMode.WRITE );
			try
			{
				db.getOptions( ).setUserVersion( 1 );
			}
			finally
			{
				db.commit( );
			}
			db.close( );
		}
		catch( SqlJetException e )
		{
			throw new IOException( "Failed to open db file! File corrupted? " + e.getMessage( ) );
		}
	}

	public static CompletableFuture<SqlJetDb> loadStorageFile( String name )
	{

		return null;
	}


	public static void scanStorageFolderAsync( ObservableList<String> target )
	{
		TaskManager.runTask( ( ) -> {
			File f = new File( _storageFolder );

			if( !f.exists( ) )
			{
				f.mkdir( );
			}
			else if( !f.isDirectory( ) )
			{
				GlobalInstance.getPrimaryStage( ).fireEvent(
						new UiEvent( UiEvent.CORE_EXCEPTION, new IOException( "Storage folder is a file ( " + _storageFolder + " )" ) )
				);
				return;
			}

			try
			{
				for( File i : f.listFiles( ) )
				{
					String name = i.getName( );

					if( name.lastIndexOf( _fileStuffix ) > 0 )
					{
						Platform.runLater( ( ) -> target.add( name.replace( _fileStuffix, "" ) ) );
					}
				}
			}
			catch( NullPointerException e )
			{
				GlobalInstance.getPrimaryStage( ).fireEvent( new UiEvent( UiEvent.CORE_EXCEPTION, e ) );
			}
		} );
	}

}
