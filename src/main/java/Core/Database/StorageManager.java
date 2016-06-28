package Core.Database;

import Core.Database.Storage.PlayerStorage;
import Core.Helper.CoreException;
import Core.MatchManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static java.util.concurrent.CompletableFuture.supplyAsync;


public class StorageManager
{
	private static final String STORAGE_FOLDER = System.getProperty( "user.home" ) + File.separator + "Matchmanager";
	private static final String FILE_EXT = ".sqlite";


	public static CompletableFuture<Void> createDatabase( String name )
	{
		return supplyAsync( ( ) -> {
			String path = StorageManager.createFilePath( name );

			if( name.length( ) <= 0 )
			{
				throw new CompletionException( new IOException( "Filename cannot be empty" ) );
			}

			if( !StorageManager.isValidPath( name ) )
			{
				throw new CompletionException( new IOException( "Filename is invalid: " + path ) );
			}

			File f = new File( path );

			if( f.exists( ) )
			{
				throw new CompletionException( new IOException( "File already exists" ) );
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

				PlayerStorage s = new PlayerStorage();
				s.initializeStorage( db );

				db.close( );
			}
			catch( SqlJetException e )
			{
				try
				{
					deleteDatabase( name ).get( );
				}
				catch( Exception ex )
				{

				}

				throw new CompletionException(
						new IOException( "Failed to open db file! File corrupted? " + e.getMessage( ) )
				);
			}

			return null;
		} );

	}


	public static CompletableFuture<MatchManager> loadDatabase( String name )
	{
		return supplyAsync( ( ) -> {

			File file;
			try
			{
				file = openFile( name );
			}
			catch( IOException e )
			{
				throw new CompletionException( e );
			}

			SqlJetDb db;
			try
			{
				db = SqlJetDb.open( file, true );
				db.getOptions( ).setAutovacuum( true );
			}
			catch( SqlJetException e )
			{
				throw new CompletionException(
						new IOException( "Failed to open database " + e.getMessage( ) )
				);
			}

			return new MatchManager( db );
		} ).thenApply( r -> {

			try
			{
				r.fetchData( );
			}
			catch( CoreException e )
			{
				throw new CompletionException( e );
			}

			return r;
		} );
	}

	public static CompletableFuture<Void> deleteDatabase( String name )
	{
		return supplyAsync( ( ) -> {
			Path path = Paths.get( STORAGE_FOLDER, name + FILE_EXT );

			try
			{
				Files.delete( path );
			}
			catch( NoSuchFileException x )
			{
				throw new CompletionException( x );
			}
			catch( DirectoryNotEmptyException x )
			{
				throw new CompletionException( x );
			}
			catch( IOException x )
			{
				throw new CompletionException( x );
			}

			return null;
		} );
	}


	public static CompletableFuture<Void> scanStorageFolderAsync( ObservableList<String> target )
	{
		return supplyAsync( ( ) -> {
			File f = new File( STORAGE_FOLDER );

			if( !f.exists( ) )
			{
				f.mkdir( );
			}
			else if( !f.isDirectory( ) )
			{
				throw new CompletionException( new IOException( "Storage folder is a file ( " + STORAGE_FOLDER + " )" ) );
			}

			try
			{
				for( File i : f.listFiles( ) )
				{
					if( i.isDirectory( ) )
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
				throw new CompletionException( e );
			}

			return null;
		} );
	}

	private static File openFile( String name ) throws IOException
	{
		String path = createFilePath( name );

		if( !isValidPath( path ) )
		{
			throw new IOException( "Database name is invalid!" );
		}

		File file = new File( path );

		if( !file.isFile( ) )
		{
			throw new IOException( "Target file isn't a valid file!" );
		}

		return file;
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
