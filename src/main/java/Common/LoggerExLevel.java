package Common;

import java.util.logging.*;


public class LoggerExLevel extends Level
{
	public static LoggerExLevel ERROR = new LoggerExLevel( "ERROR", 950 );
	public static LoggerExLevel SUCCESS = new LoggerExLevel( "SUCCESS", 960 );


	private LoggerExLevel( String name, int value )
	{
		super( name, value );
	}
}
