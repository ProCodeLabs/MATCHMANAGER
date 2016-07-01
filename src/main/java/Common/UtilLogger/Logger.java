package Common.UtilLogger;


import Common.LogLevel;
import Common.ParamFunction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class Logger implements ILogger
{
	private Class targetClass;
	private ParamFunction<String> onLogCb = r -> { throw new NotImplementedException(); };



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
	public void critical( String msg )
	{
		log( LogLevel.CRITICAL, msg );
	}

	@Override
	public void log( Level level, String msg )
	{
		LogRecord record = new LogRecord( level, msg );
		{

		}

		System.out.println( record.getMessage() );
	}
}
