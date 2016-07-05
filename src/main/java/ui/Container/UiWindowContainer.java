package ui.Container;

import ui.Helper.UiEvent;

public class UiWindowContainer extends UiBaseContainer
{
	public UiWindowContainer( )
	{
		super( );

		addEventHandler( UiEvent.CLOSE_WINDOW, e -> getScene( ).getWindow( ).hide( ) );
	}

}
