package Common.UtilLogger;


public class LoggerFactory
{

	public static ILogger createLogger( Class targetClass )
	{
		Logger l = new Logger( targetClass );

		return l;
	}

}
