package ui.StageCore.StageEvents;


import Common.LogLevel;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ui.Dialog.ModalEx.UiAlert;
import ui.Helper.UiEvent;
import ui.StageCore.Helper.IUiEventHandler;

import java.util.logging.Logger;

public class CoreExceptionEvent implements IUiEventHandler
{
	private final Logger logger = Logger.getLogger( this.getClass( ).getName( ) );

	@Override
	public void onRegisterEvents( Stage stage )
	{
		stage.addEventHandler( UiEvent.CORE_EXCEPTION, event -> {
			assert event.getEventData( ) instanceof Exception;

			Exception e = ( Exception ) event.getEventData( );

			logger.log( LogLevel.CRITICAL, "Core error! " + e.getMessage( ) );

			UiAlert msgBox = new UiAlert( Alert.AlertType.ERROR );
			{
				msgBox.setHeaderText( "something went wrong :( " );
				msgBox.setContentText( e.getMessage( ) );
				msgBox.addStackTraceArea( e );
			}
			msgBox.showAndWait( );

			Platform.exit( );
			System.exit( 0 );
		} );

	}
}
