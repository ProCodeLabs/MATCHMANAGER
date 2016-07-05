package Common.UtilLogger;


public class LoggerFactory
{
	public static ILogger createLogger( Class targetClass )
	{
		return new Logger( targetClass );
	}
}
