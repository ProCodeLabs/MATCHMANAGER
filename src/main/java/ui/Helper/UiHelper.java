package ui.Helper;

import javafx.scene.control.TextField;

import static Common.Constants.CSS_ERROR_CLS;

public class UiHelper
{
	public static void attachInvalidTextHandler( TextField textField )
	{
		textField.textProperty( ).addListener( e -> {
			if( textField.getStyleClass( ).contains( CSS_ERROR_CLS ) )
			{
				textField.getStyleClass( ).remove( CSS_ERROR_CLS );
			}
		} );
	}

	public static void addInvalidTextClass( TextField textField )
	{
		textField.getStyleClass( ).add( CSS_ERROR_CLS );
	}

}
