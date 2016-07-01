package ui.Container;

import Common.Files;
import Common.GlobalInstance;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.Helper.UiEvent;
import ui.Helper.UiStyleDesc;

import java.io.IOException;


public class UiBaseContainer extends BorderPane
{
	protected UiStyleDesc desc = new UiStyleDesc( this, true );
	protected Object controller;

	protected UiBaseContainer( )
	{
		desc.setOnMinimizeButton( ( ) -> ( ( Stage ) getScene( ).getWindow( ) ).setIconified( true ) );

		setTop( desc.getTitleBar( ) );
	}

	public void setCenter( String title, String resourceId )
	{
		FXMLLoader loader = new FXMLLoader( GlobalInstance.getResource( resourceId ) );
		{
			setTitle( title.toUpperCase( ) );
		}

		try
		{
			setCenter( loader.load( ) );
			controller = loader.getController( );
		}
		catch( IOException e )
		{
			GlobalInstance.fireGlobalEvent( new UiEvent( UiEvent.CORE_EXCEPTION, e ) );
		}
	}

	public Scene createScene( double width, double height )
	{
		Scene scene = new Scene( this, width, height );
		{
			scene.getStylesheets().clear();
			scene.getStylesheets().add( GlobalInstance.getResourceUrl( Files.PATH_STYLE_RESOURCE ) );
		}
		return scene;
	}

	public final <T> T getController( )
	{
		assert controller != null;

		return ( T ) controller;
	}


	public void setTitle( String title )
	{
		desc.setTitle( title );
	}
}
