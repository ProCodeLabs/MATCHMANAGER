package ui.Controller;

import Core.MatchManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import ui.Dialog.EditMatchDialog;
import ui.Dialog.EditPlayerDialog;
import ui.Dialog.EditTeamDialog;
import ui.Helper.UiBaseContainer;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable
{
	public static final String RESOURCE_ID = "fxml/centerContent/mainView.fxml";

	@FXML
	public TreeView<String> matchList;

	@FXML
	public Button closeDbButton;

	@FXML
	public Button editMatchButton;

	@FXML
	public Button editTeamButton;

	@FXML
	public Button editPlayerButton;

	@FXML
	public Label teamALabel;

	@FXML
	public Label teamBLabel;


	private MatchManager manager;


	@Override
	public void initialize( URL location, ResourceBundle resources )
	{
	/*	TreeItem<String> rootItem = new TreeItem<String>( "MATCHES" );
		rootItem.setExpanded( true );
		for( int i = 1; i < 6; i++ )
		{
			TreeItem<String> item = new TreeItem<String>( "MATCH - " + i );
			rootItem.getChildren( ).add( item );
		}

		treeView = new TreeView<>( rootItem );*/
	}


	@FXML
	public void buttonCloseDbClicked()
	{
		manager.Close();

		UiBaseContainer container = ( UiBaseContainer ) closeDbButton.getScene( ).getRoot( );
		{
			container.setCenter( "Select Database", SelectDatabaseController.RESOURCE_ID );
		}
	}

	@FXML
	public void buttonEditMatchClicked()
	{
		EditMatchDialog dlg = new EditMatchDialog();

		dlg.showDialog();
	}

	@FXML
	public void buttonEditTeamClicked()
	{
		EditTeamDialog dlg= new EditTeamDialog();

		dlg.showDialog();

	}

	@FXML
	public void buttonEditPlayerClicked()
	{
		EditPlayerDialog dlg = new EditPlayerDialog();
		{

		}
		dlg.showDialog();
	}


	public void setMatchManager( MatchManager manager )
	{
		this.manager = manager;
	}



}