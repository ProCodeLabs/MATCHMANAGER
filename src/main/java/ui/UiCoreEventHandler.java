package ui;

import Common.LogLevel;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ui.Dialog.ModalEx.UiAlert;
import ui.Helper.UiEvent;

import java.util.logging.Logger;

public class UiCoreEventHandler
{
	private final Logger logger = Logger.getLogger( this.getClass( ).getName( ) );


	public void registerEvents( final Stage primaryStage )
	{
		addCriticalErrorHandler( primaryStage );
		addLogItemHandler( primaryStage );
	}


	private void addCriticalErrorHandler( final Stage stage )
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

	private void addLogItemHandler( final Stage stage )
	{
		stage.addEventFilter( UiEvent.LOG_ITEM, item -> {


		} );
	}


}
