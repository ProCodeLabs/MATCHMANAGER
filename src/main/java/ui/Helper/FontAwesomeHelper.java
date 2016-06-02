package ui.Helper;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;


public class FontAwesomeHelper
{
	public static Button createIconButton( String iconName )
	{
		return createIconButton( iconName, "", 1 );
	}

	public static Button createIconButton( String iconName, String text )
	{
		return createIconButton( iconName, text, 1 );
	}

	public static Button createIconButton( String iconName, int iconSize )
	{
		return createIconButton( iconName, "", iconSize );
	}

	public static Button createIconButton( String iconName, String text, int iconSize )
	{
		Label icon = createIconLabel( iconName );
		{
			icon.setStyle( "-fx-font-size: " + iconSize + "em;" );
			icon.getStyleClass( ).add( "fa" );
		}

		return UIHelper.buildComponent( new Button( ), button -> {
			button.setText( text );
			button.setGraphic( icon );

			button.getStyleClass().add( "button-icon" );
		} );
	}

	public static Label createIconLabel( String icon )
	{
		return createIconLabel( icon, 16 );
	}

	public static Label createIconLabel( String iconName, int iconSize )
	{
		return LabelBuilder.create()
				.text(iconName)
				.styleClass("icons")
				.style("-fx-font-size: " + iconSize + "px;")
				.build();

	}
}

