package ui.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable
{
	public static final String RESOURCE_ID = "fxml/centerContent/mainView.fxml";

	@FXML
	TreeView<String> treeView;

	@Override
	public void initialize( URL location, ResourceBundle resources )
	{
		TreeItem<String> rootItem = new TreeItem<String> ( "MATCHES");
		rootItem.setExpanded(true);
		for (int i = 1; i < 6; i++) {
			TreeItem<String> item = new TreeItem<String> ("MATCH - " + i);
			rootItem.getChildren().add(item);
		}

		treeView = new TreeView<>(rootItem);
	}
}