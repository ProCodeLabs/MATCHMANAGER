package ui.Controller;

import Core.MatchManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import ui.Dialog.AddPlayerDialog;
import ui.Dialog.EditMatchDialog;
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

	@FXML
	public VBox matchInfoDisplay;


	private MatchManager manager;


	@Override
	public void initialize( URL location, ResourceBundle resources )
	{
		matchList.setRoot( createTreeList( ) );
		matchInfoDisplay.setVisible( false );

		matchList.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
			matchInfoDisplay.setVisible( true );
		});

	}


	private TreeItem<String> createTreeList()
	{
		TreeItem<String> root = new TreeItem<>( "MATCHES" );
		{
			TreeItem<String> due = new TreeItem<>( "PENDING" );
			TreeItem<String> done = new TreeItem<>( "DONE" );


			due.getChildren().add( new TreeItem<>( "MATCH 1" ) );
			due.getChildren().add( new TreeItem<>( "MATCH 2" ) );
			due.getChildren().add( new TreeItem<>( "MATCH 3" ) );

			done.getChildren().add( new TreeItem<>( "MATCH 1" ) );
			done.getChildren().add( new TreeItem<>( "MATCH 2" ) );
			done.getChildren().add( new TreeItem<>( "MATCH 3" ) );



			root.getChildren().addAll( due, done );
		}
		return root;
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
		AddPlayerDialog dlg = new AddPlayerDialog();
		{

		}
		dlg.showDialog();
	}


	public void setMatchManager( MatchManager manager )
	{
		this.manager = manager;
	}



}