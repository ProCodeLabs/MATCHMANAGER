package ui.Helper;

import javafx.event.Event;
import javafx.event.EventType;

public class UiEvent
{
	public static final EventType<Event> BEGIN_RESIZE = new EventType<>( Event.ANY, "BEGIN_RESIZE" );
	public static final EventType<Event> FINISH_RESIZE = new EventType<>( Event.ANY, "FINISH_RESIZE" );

	public static final EventType<Event> SHUTDOWN_CORE_WINDOW = new EventType<>( Event.ANY, "SHUTDOWN_CORE_WINDOW"  );

	public static final EventType<Event> MINIMIZE_WINDOW = new EventType<>( Event.ANY, "MINIMIZE_WINDOW" );
	public static final EventType<Event> CLOSE_WINDOW = new EventType<>( Event.ANY, "CLOSE_WINDOW" );

}
