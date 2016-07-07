package Core.Event;


import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Event.Helper.IEventHandlerImpl;
import Core.Event.Manager.CoreEvent;
import Core.Event.Manager.CoreEventDispatcher;
import ui.Dialog.ErrorDialog;

public class CoreExceptionEvent implements IEventHandlerImpl
{
	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	@Override
	public void onRegisterEvents( )
	{
		CoreEventDispatcher.addEventHandler( CoreEvent.CORE_EXCEPTION, e -> {
			logger.critical( "Core error! " + e.getMessage( ) );

			ErrorDialog err = new ErrorDialog( e.getMessage( ) );
			{
				err.setResultCallback( result -> {
					return;
				} );
			}
			err.showDialog();

			//Platform.runLater( ( ) -> {
			//	Platform.exit( );
			//	System.exit( 0 );
			//} );
		} );
	}
}
