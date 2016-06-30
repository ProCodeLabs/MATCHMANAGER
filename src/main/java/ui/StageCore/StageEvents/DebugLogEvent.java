package ui.StageCore.StageEvents;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ui.Helper.UiEvent;
import ui.StageCore.Helper.IUiEventHandler;

public class DebugLogEvent implements IUiEventHandler
{
	//private boolean isLog

	@Override
	public void onRegisterEvents( Stage stage )
	{
		stage.addEventFilter( KeyEvent.KEY_RELEASED, e -> {
			if( e.getCode() == KeyCode.F2 )
			{

				e.consume();
			}
		} );

		stage.addEventFilter( UiEvent.LOG_ITEM, item -> {


		} );
	}


}
