package Common.UtilLogger;


import Common.GlobalInstance;
import Common.LogLevel;
import ui.Helper.UiEvent;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class Logger implements ILogger
{
	private Class targetClass;

	public Logger( Class targetClass )
	{
		this.targetClass = targetClass;
	}

	@Override
	public void info( String msg )
	{
		log( LogLevel.INFO, msg );
	}

	@Override
	public void warning( String msg )
	{
		log( LogLevel.WARNING, msg );
	}

	@Override
	public void success( String msg )
	{
		log( LogLevel.SUCCESS, msg );
	}

	@Override
	public void error( String msg )
	{
		log( LogLevel.ERROR, msg );
	}

	@Override
	public void critical( String msg )
	{
		log( LogLevel.CRITICAL, msg );
	}

	@Override
	public void log( Level level, String msg )
	{
		LogRecord record = new LogRecord( level, msg );
		{
			record.setSourceClassName( targetClass.getName( ) );

			GlobalInstance.fireGlobalEvent( new UiEvent( UiEvent.LOG_ITEM, record ) );
		}
		System.out.println( record.getMessage( ) );
	}
}
