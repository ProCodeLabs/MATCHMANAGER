package Core.Event;


import Common.Constants;
import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Event.Helper.IEventHandlerImpl;
import Core.Event.Manager.CoreEvent;
import Core.Event.Manager.CoreEventDispatcher;

import java.io.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.logging.LogRecord;

public class LogEvent implements IEventHandlerImpl
{

	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	@Override
	public void onRegisterEvents( )
	{
		CoreEventDispatcher.addEventHandler( CoreEvent.LOG_ITEM, l -> System.out.println( formatLogs( l ) ) );

		openLogFile( )
				.thenApply( stream -> {
					CoreEventDispatcher.addEventHandler( CoreEvent.LOG_ITEM, l -> {
						try
						{
							stream.write( formatLogs( l ) + Constants.LINE_SPEREATOR );
							stream.flush( );
						}
						catch( IOException e )
						{
							logger.error( "Failed to write log to file " + e.getMessage( ) );
						}
					} );

					CoreEventDispatcher.addEventHandler( CoreEvent.SHUTDOWN_APP, r -> {
						try
						{
							stream.close( );
						}
						catch( IOException e )
						{
							logger.error( "failed to close writer stream " + e.getMessage( ) );
						}
					} );

					return null;
				} )
				.exceptionally( e -> {
					logger.warning( "failed to add logfile " + e.getMessage( ) );

					return null;
				} );
	}

	private CompletableFuture<Writer> openLogFile( )
	{
		return CompletableFuture.supplyAsync( ( ) -> {
			Writer writer;
			try
			{
				writer = new BufferedWriter(
						new OutputStreamWriter( new FileOutputStream( Constants.LOG_FILE_PATH ), "utf-8" )
				);
			}
			catch( IOException e )
			{
				throw new CompletionException( e );
			}

			return writer;
		} );
	}

	private static String formatLogs( LogRecord record )
	{
		return record.getMillis( ) + " : " + record.getSourceClassName( ) + " " + record.getMessage( );
	}
}