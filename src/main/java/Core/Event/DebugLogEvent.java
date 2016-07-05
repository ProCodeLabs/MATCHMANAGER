package Core.Event;

import Common.GlobalInstance;
import Core.Event.Helper.IEventHandlerImpl;
import Core.Event.Manager.CoreEvent;
import Core.Event.Manager.CoreEventDispatcher;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ui.StageCore.DialogStages.DebugLogViewStage;
import ui.StageCore.DialogStages.Helper.LogListEntry;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DebugLogEvent implements IEventHandlerImpl
{
	@Override
	public void onRegisterEvents( )
	{
		GlobalInstance.getPrimaryStage( ).addEventFilter( KeyEvent.KEY_RELEASED, e -> {
			if( e.getCode( ) == KeyCode.F2 )
			{
				DebugLogViewStage s = new DebugLogViewStage( );
				{
					s.showWindow( );
				}

				e.consume( );
			}
		} );

		CoreEventDispatcher.addEventHandler( CoreEvent.LOG_ITEM, log -> {
			Instant instant = Instant.ofEpochMilli( log.getMillis( ) );
			ZoneId zid = ZoneId.systemDefault( );
			ZonedDateTime zdt = ZonedDateTime.ofInstant( instant, zid );

			LogListEntry entry = new LogListEntry(
					zdt.toString( ),
					log.getSourceClassName( ),
					log.getLevel( ).toString( ),
					log.getMessage( )
			) ;

			Platform.runLater( ( ) -> DebugLogViewStage.addLogListEntry( entry ) );
		} );
	}


}
