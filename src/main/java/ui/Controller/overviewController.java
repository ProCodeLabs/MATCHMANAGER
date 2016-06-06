package ui.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;

public class overviewController
{
	@FXML
	TreeView<String> treeView;

	@FXML
	ListView<String> teamOneListView;

	@FXML
	ListView<String> teamTwoListView;

	@FXML
	Label teamOne;

	@FXML
	Label teamTwo;

	@FXML
	Button editMatchButton;

	@FXML
	Button editTeamButton;

	@FXML
	Button editPlayerButton;

	@FXML
	public void editMatchButtonClicked() {

	}

	@FXML
	public void editTeamButtonClicked() {

	}

	@FXML
	public void editPlayerButtonClicked() {

	}
}
