package ui.StageCore.Helper;

import javafx.stage.Stage;
import ui.StageCore.StageEvents.CoreExceptionEvent;
import ui.StageCore.StageEvents.DebugLogEvent;

import java.util.ArrayList;

public class UiEventRegistrar
{
	ArrayList< IUiEventHandler > eventList = new ArrayList<>();

	public UiEventRegistrar( )
	{
		eventList.add( new CoreExceptionEvent() );
		eventList.add( new DebugLogEvent() );
	}

	public void registerEvents( final Stage stage )
	{
		eventList.forEach( i -> i.onRegisterEvents( stage ) );
	}
}
