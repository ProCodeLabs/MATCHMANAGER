package ui.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ViewController_
{

	@FXML
	Button closeButton;

	@FXML
	public void closeWindow( )
	{
		Stage stage = ( Stage ) closeButton.getScene( ).getWindow( );
		stage.close( );
	}
}
