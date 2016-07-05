package Core.Event.Manager;

import Core.Event.CoreExceptionEvent;
import Core.Event.DebugLogEvent;
import Core.Event.Helper.IEventHandlerImpl;
import Core.Event.LogEvent;

import java.util.ArrayList;


public class EventRegistrar
{
	private final ArrayList<IEventHandlerImpl> eventList = new ArrayList<>( );

	public EventRegistrar( )
	{
		eventList.add( new CoreExceptionEvent( ) );
		eventList.add( new DebugLogEvent( ) );
		eventList.add( new LogEvent( ) );
	}

	public void registerEvents( )
	{
		eventList.forEach( i -> i.onRegisterEvents( ) );
	}

}
