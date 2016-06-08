package ui.Helper;


import javafx.scene.control.Dialog;
import javafx.stage.StageStyle;

public class UiBaseDialog<R> extends Dialog<R>
{
	UiStyleDesc desc;

	public UiBaseDialog( )
	{
		super( );
		initStyle( StageStyle.UNDECORATED );

		desc = new UiStyleDesc( getDialogPane( ), false );
		desc.setOnCloseButton( ( ) -> getDialogPane( ).getScene( ).getWindow( ).hide( ) );

		getDialogPane( ).setHeader( desc.getTitleBar( ) );
	}

	public void setDialogTitle( String text )
	{
		desc.setTitle( text );

		setHeaderText( text );
	}
}
