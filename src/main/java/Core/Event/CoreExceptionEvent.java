package Core.Event;


import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Event.Helper.IEventHandlerImpl;
import Core.Event.Manager.CoreEvent;
import Core.Event.Manager.CoreEventDispatcher;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import ui.Dialog.ModalEx.UiAlert;

public class CoreExceptionEvent implements IEventHandlerImpl
{
	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	@Override
	public void onRegisterEvents(  )
	{
		CoreEventDispatcher.addEventHandler( CoreEvent.CORE_EXCEPTION, e -> {
			logger.critical( "Core error! " + e.getMessage( ) );

			Platform.runLater( ( ) -> {
				UiAlert msgBox = new UiAlert( Alert.AlertType.ERROR );
				{
					msgBox.setHeaderText( "something went wrong :( " );
					msgBox.setContentText( e.getMessage( ) );
					msgBox.addStackTraceArea( ( Exception ) e );
				}
				msgBox.showAndWait( );

				Platform.exit( );
				System.exit( 0 );
			} );
		} );
	}
}
