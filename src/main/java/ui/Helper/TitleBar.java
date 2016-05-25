package ui.Helper;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

public class TitleBar extends HBox
{
	public TitleBar( String title, boolean hasMaximize, boolean hasMinimize, boolean hasClose )
	{

		if( hasClose )
		{
			Button btnClose = new Button( "X" );

			btnClose.setOnAction( e -> {
				System.out.println( "Hello World Lambda" );
			} );

		}

	}

	static void AddTitleBar( BorderPane parent, String title, boolean hasMaximize, boolean hasMinimize, boolean hasClose )
	{
		parent.setTop(
				new TitleBar( title, hasMaximize, hasMinimize, hasClose )
		);
	}

}
