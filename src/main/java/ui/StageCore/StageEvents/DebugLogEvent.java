package ui.StageCore.StageEvents;

import Common.UtilLogger.LogListEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ui.Helper.UiEvent;
import ui.StageCore.DebugLogViewStage;
import ui.StageCore.Helper.IUiEventHandler;

import java.util.logging.LogRecord;

public class DebugLogEvent implements IUiEventHandler
{
	public static ObservableList<LogListEntry> logList = FXCollections.observableArrayList( );

	@Override
	public void onRegisterEvents( Stage stage )
	{
		stage.addEventFilter( KeyEvent.KEY_RELEASED, e -> {
			if( e.getCode( ) == KeyCode.F2 )
			{
				DebugLogViewStage s = new DebugLogViewStage( );
				{
					s.showWindow( );
				}

				e.consume( );
			}
		} );

		stage.addEventFilter( UiEvent.LOG_ITEM, item -> {
			LogRecord log = ( LogRecord ) item.getEventData( );

			logList.add( new LogListEntry(
					String.valueOf( log.getMillis( ) ),
					log.getLevel( ).toString( ),
					log.getMessage( )
			) );
		} );
	}


}
