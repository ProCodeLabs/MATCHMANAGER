package Core.Event.Manager;


import Core.Event.Helper.CoreEventType;

import java.util.logging.LogRecord;

public class CoreEvent
{
	//> event types
	public static final CoreEventType<Throwable> CORE_EXCEPTION = new CoreEventType<>( "COREEXCEPTION" );
	public static final CoreEventType<LogRecord> LOG_ITEM = new CoreEventType<>( "LOGITEM" );
	public static final CoreEventType<Void>	SHUTDOWN_APP = new CoreEventType<>( "SHUTDOWN_APP" );

}
