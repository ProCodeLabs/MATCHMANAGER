package ui.Helper;

import Common.GlobalInstance;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.Dialog.ModalEx.UiAlert;

import java.io.IOException;
import java.util.Optional;


public class UiBaseContainer extends BorderPane
{
	private UiStyleDesc desc = new UiStyleDesc( this, true );
	private Object controller;

	public UiBaseContainer( )
	{
		desc.setOnCloseButton( ( ) -> {
			UiAlert alert = new UiAlert( Alert.AlertType.CONFIRMATION );
			{
				alert.setTitle( "Close?" );
				alert.setHeaderText( "Close?" );
				alert.setContentText( "Are you sure you want to Close this Window?" );
			}

			Optional<ButtonType> result = alert.showAndWait( );
			if( result.get( ) == ButtonType.OK )
			{
				Platform.exit( );
				System.exit( 0 );
			}
		} );

		desc.setOnMinimizeButton( ( ) -> ( ( Stage ) getScene( ).getWindow( ) ).setIconified( true ) );

		setTop( desc.getTitleBar( ) );
	}

	public void setCenter( String title, String resourceId )
	{
		FXMLLoader loader = new FXMLLoader( GlobalInstance.getResource( resourceId ) );
		{
			setTitle( title );
		}

		try
		{
			setCenter( loader.load( ) );
			controller = loader.getController( );
		}
		catch( IOException e )
		{
			GlobalInstance.getPrimaryStage( ).fireEvent(
					new UiEvent( UiEvent.CORE_EXCEPTION, "Failed to load resource! ( " + resourceId + " )" + e.getMessage( ) )
			);
		}
	}



	public final <T> T getController( ) {
		assert controller != null;

		return (T)controller;
	}


	public void setTitle( String title )
	{
		desc.setTitle( title );
	}
}
