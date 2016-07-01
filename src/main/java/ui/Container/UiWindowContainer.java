package ui.Container;

public class UiWindowContainer extends UiBaseContainer
{
	public UiWindowContainer( )
	{
		super( );

		desc.setOnCloseButton( ( ) -> getScene( ).getWindow( ).hide( ) );
	}

}
