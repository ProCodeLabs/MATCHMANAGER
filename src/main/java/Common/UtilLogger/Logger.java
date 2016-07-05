package Common.UtilLogger;


import Common.LogLevel;
import Core.Event.Manager.CoreEvent;
import Core.Event.Manager.CoreEventDispatcher;

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
		final LogRecord record = new LogRecord( level, msg );
		{
			record.setSourceClassName( targetClass.getName( ) );
		}
		CoreEventDispatcher.fireEvent( CoreEvent.LOG_ITEM, record );
	}
}
