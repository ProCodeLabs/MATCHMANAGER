package Common;

import java.util.logging.*;

public class LogLevel extends Level
{
	public static LogLevel ERROR = new LogLevel( "ERROR", 950 );
	public static LogLevel SUCCESS = new LogLevel( "SUCCESS", 960 );


	private LogLevel( String name, int value )
	{
		super( name, value );
	}
}
