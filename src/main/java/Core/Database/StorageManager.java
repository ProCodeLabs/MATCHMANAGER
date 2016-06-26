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


public class StorageManager
{
	private static final String STORAGE_FOLDER = System.getProperty( "user.home" ) + File.separator + "Matchmanager";
	private static final String FILE_EXT = ".sqlite";


	public static void createDatabase( String name ) throws IOException
	{
		String path = StorageManager.createFilePath( name );

		if( !StorageManager.isValidPath( name ) )
		{
			throw new IOException( "Filename is invalid: " + path );
		}

		File f = new File( path );

		if( f.exists( ) )
		{
			throw new IOException( "File already exists" );
		}

		try
		{
			SqlJetDb db = SqlJetDb.open( f, true );
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

			db.close( );
		}
		catch( SqlJetException e )
		{
			throw new IOException( "Failed to open db file! File corrupted? " + e.getMessage( ) );
		}
	}

	public static SqlJetDb loadDatabase( String name ) throws IOException
	{
		String path = StorageManager.createFilePath( name );

		File f = new File( path );

		if( !f.exists( ) || f.isDirectory( ) )
		{
			throw new IOException( "Failed to open database! file not found or file is directory!" );
		}

		SqlJetDb db;
		try
		{
			db = SqlJetDb.open( f, true );
			db.getOptions( ).setAutovacuum( true );
		}
		catch( SqlJetException e )
		{
			throw new IOException( "Failed to open database " + e.getMessage( ) );
		}

		return db;
	}

	public static void scanStorageFolderAsync( ObservableList<String> target )
	{
		TaskManager.runTask( ( ) -> {
			File f = new File( STORAGE_FOLDER );

			if( !f.exists( ) )
			{
				f.mkdir( );
			}
			else if( !f.isDirectory( ) )
			{
				GlobalInstance.getPrimaryStage( ).fireEvent(
						new UiEvent( UiEvent.CORE_EXCEPTION, new IOException( "Storage folder is a file ( " + STORAGE_FOLDER + " )" ) )
				);
				return;
			}

			try
			{
				for( File i : f.listFiles( ) )
				{
					if( i.isDirectory() )
					{
						continue;
					}

					String name = i.getName( );

					if( name.lastIndexOf( FILE_EXT ) > 0 )
					{
						Platform.runLater( ( ) -> target.add( name.replace( FILE_EXT, "" ) ) );
					}
				}
			}
			catch( NullPointerException e )
			{
				GlobalInstance.getPrimaryStage( ).fireEvent( new UiEvent( UiEvent.CORE_EXCEPTION, e ) );
			}
		} );
	}


	private static String createFilePath( String databaseName )
	{
		return STORAGE_FOLDER + File.separator + databaseName + FILE_EXT;
	}

	private static boolean isValidPath( String path )
	{
		try
		{
			Paths.get( path );
		}
		catch( InvalidPathException | NullPointerException e )
		{
			return false;
		}
		return true;
	}
}
