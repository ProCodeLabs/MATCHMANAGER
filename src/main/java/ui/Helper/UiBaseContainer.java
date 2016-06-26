package ui.Helper;

import Common.ResourceLoader;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;


public class UiBaseContainer extends BorderPane
{
	final Logger log = Logger.getLogger( this.getClass( ).getName( ) );

	UiStyleDesc desc = new UiStyleDesc( this, true );


	public UiBaseContainer( )
	{
		desc.setOnCloseButton( ( ) -> {
			Alert alert = new Alert( Alert.AlertType.CONFIRMATION );
			alert.setTitle( "Close?" );
			alert.setHeaderText( "Close?" );
			alert.setContentText( "Are you sure you want to Close this Window?" );

			Optional<ButtonType> result = alert.showAndWait( );
			if( result.get( ) == ButtonType.OK )
			{
				Platform.exit( );
				System.exit( 0 );
			}
		} );

		desc.setOnMinimizeButton( ( ) ->  ( ( Stage ) getScene( ).getWindow( ) ).setIconified( true ) );

		setTop( desc.getTitleBar( ) );
	}

	public void setCenter( String title, String resourceId ) throws IOException
	{
		FXMLLoader loader = new FXMLLoader( ResourceLoader.getResourceClass( ).getResource( resourceId ) );

		try
		{
			setTitle( title );
			setCenter( loader.load() );
		}
		catch( IOException e )
		{

		}
	}

	public void setTitle( String title )
	{
		desc.setTitle( title );
	}
}
