import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class ViewController
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
