package ui.Helper;


import javafx.event.Event;
import javafx.event.EventType;

public class UiEvent extends Event
{
	public static final EventType<UiEvent> ANY = new EventType<UiEvent>( Event.ANY, "UIEVENT" );

	public static final EventType<UiEvent> CORE_EXCEPTION = new EventType<>( ANY, "COREEXCEPTION" );
	public static final EventType<UiEvent> LOG_ITEM = new EventType<>( ANY, "LOGITEM" );


	public UiEvent( final EventType<? extends UiEvent> eventType )
	{
		super( eventType );
	}

	public UiEvent( final EventType<? extends UiEvent> eventType, Object eventData )
	{
		super( eventType );
		_eventData = eventData;
	}

	@Override
	public EventType<? extends UiEvent> getEventType( )
	{
		return ( EventType<? extends UiEvent> ) super.getEventType( );
	}

	private Object _eventData = null;


	public Object getEventData( )
	{
		return _eventData;
	}


}
