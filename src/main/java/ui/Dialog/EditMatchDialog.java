package ui.Dialog;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.util.ResourceBundle;

public class EditMatchDialog implements Initializable
{
	@FXML
	DatePicker _datePicker;

	@FXML
	ChoiceBox<String> _teamSelectA;

	@FXML
	ChoiceBox<String> _teamSelectB;



	@Override
	public void initialize( URL location, ResourceBundle resources )
	{
		return;
	}


}
