package Core.Event.Manager;


import Common.LambdaExt.ParamFunction;
import Common.TaskManager;
import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Event.Helper.CoreEventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoreEventDispatcher
{
	private static final ILogger logger = LoggerFactory.createLogger( CoreEventDispatcher.class );
	private static final HashMap<String, List<Object>> eventMap = new HashMap<>( );


	public static <T> void addEventHandler(
			final CoreEventType<T> eventType,
			final ParamFunction<? super T> eventHandler
	)
	{
		String eventId = eventType.toString( );

		TaskManager.runTask( ( ) -> {
			synchronized( eventMap )
			{
				if( eventMap.containsKey( eventId ) )
				{
					eventMap.get( eventId ).add( eventHandler );
				}
				else
				{
					List<Object> eventList = new ArrayList<>( );
					{
						eventList.add( eventHandler );
					}
					eventMap.put( eventId, eventList );
				}
			}
		} );
	}

	public static <T> void fireEvent(
			final CoreEventType<? super T> eventType,
			final T eventData
	)
	{
		String eventId = eventType.toString( );

		TaskManager.runTask( ( ) -> {
			synchronized( eventMap )
			{
				if( eventMap.containsKey( eventId ) )
				{
					//> unsafe cast
					eventMap.get( eventId ).forEach( i -> ( ( ParamFunction<T> ) i ).apply( eventData ) );
				}
				else
				{
					logger.warning( "fired unregistered event " + eventId );
				}
			}
		} );
	}
}
