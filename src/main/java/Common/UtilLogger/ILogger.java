package Common.UtilLogger;

import java.util.logging.Level;

public interface ILogger
{
	void info( String msg );

	void warning( String msg );

	void success( String msg );

	void error( String msg );

	void critical( String msg );

	void log( Level l, String msg );
}
