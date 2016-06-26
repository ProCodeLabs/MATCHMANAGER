package Core.Database;

import Common.TaskManager;
import Core.GlobalInstance;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import ui.Helper.UiEvent;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.supplyAsync;


public class StorageManager
{
	private static final String _storageFolder = System.getProperty( "user.home" ) + File.separator + "Matchmanager";
	private static final String _fileStuffix = ".sqlite";


	public static CompletableFuture<Boolean> createStorageFile( String name )
	{
		return supplyAsync( ( ) -> {
			File f = new File( _storageFolder + File.separator + name + _fileStuffix );


			return true;

		} );
	}

	public static void loadStorageFile( String name )
	{

	}


	public static void scanStorageFolderAsync( ObservableList<String> target )
	{
		TaskManager.runUiTask( ( ) -> {
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
