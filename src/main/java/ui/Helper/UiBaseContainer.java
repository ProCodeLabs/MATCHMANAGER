package ui.Helper;

import Common.ResourceLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.logging.Logger;



public class UiBaseContainer extends BorderPane
{
	final Logger log = Logger.getLogger( this.getClass( ).getName( ) );

	UiStyleDesc desc = new UiStyleDesc( this );

	public UiBaseContainer( )
	{
		setTop( desc.getTitleBar( ) );
	}

	public void setCenter( String title, String resourceId ) throws IOException
	{
		FXMLLoader loader = new FXMLLoader( ResourceLoader.getResourceClass().getResource( resourceId ) );

		setTitle( title );
		setCenter( loader.load() );
	}

	public void setTitle( String title )
	{
		desc.setTitle( title );
		//_titleLabel.setText( title );
	}


}
