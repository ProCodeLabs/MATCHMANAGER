package ui.Helper;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;


public class FontAwesomeHelper
{
	public static Button createIconButton(String iconName)
	{
		return createIconButton(iconName, "", 16);
	}

	public static Button createIconButton(String iconName, String text)
	{
		return createIconButton(iconName, text, 16);
	}

	public static Button createIconButton(String iconName, int iconSize)
	{
		return createIconButton(iconName, "", iconSize);
	}

	public static Button createIconButton(String iconName, String text, int iconSize)
	{
		Label icon = createIconLabel(iconName);
		icon.setStyle("-fx-font-size: " + iconSize + "px;");

		return UiHelper.buildComponent( new Button(), button -> {
			button.setText( text );
			button.setGraphic( icon );
		} );
	}

	public static Label createIconLabel(String iconName, String style)
	{
		return UiHelper.buildComponent( new Label(), label ->{
			label.setText( iconName );
			label.setStyle( style );
		} );
	}

	public static Label createIconLabel(String iconName)
	{
		return createIconLabel(iconName, 16);
	}

	public static Label createIconLabel(String iconName, int iconSize)
	{
		return LabelBuilder.create()
				.text(iconName)
				.styleClass("icons")
				.style("-fx-font-size: " + iconSize + "px;")
				.build();
	}
}

