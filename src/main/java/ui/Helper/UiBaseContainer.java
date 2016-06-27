package ui.Helper;

import Common.GlobalInstance;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.Dialog.CloseAppDialog;

import java.io.IOException;


public class UiBaseContainer extends BorderPane
{
	private UiStyleDesc desc = new UiStyleDesc( this, true );
	private Object controller;

	public UiBaseContainer( )
	{
		desc.setOnCloseButton( ( ) -> {
			CloseAppDialog dlg = new CloseAppDialog();
			{
				dlg.showDialog( );
			}
		} );

		desc.setOnMinimizeButton( ( ) -> ( ( Stage ) getScene( ).getWindow( ) ).setIconified( true ) );

		setTop( desc.getTitleBar( ) );
	}

	public void setCenter( String title, String resourceId )
	{
		FXMLLoader loader = new FXMLLoader( GlobalInstance.getResource( resourceId ) );
		{
			setTitle( title.toUpperCase() );
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
