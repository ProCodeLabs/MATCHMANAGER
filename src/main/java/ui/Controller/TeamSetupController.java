package ui.Controller;

import Core.Data.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ui.Dialog.AddPlayerDialog;
import ui.Dialog.AddTeamDialog;

import java.net.URL;
import java.util.ResourceBundle;

public class TeamSetupController implements Initializable
{
	public static final String RESOURCE_ID = "fxml/centerContent/setupTeams.fxml";


	private ObservableList<String> teamList = FXCollections.observableArrayList( );
	private ObservableList<String> playerList = FXCollections.observableArrayList( );


	@FXML
	public Button applyButton;

	@FXML
	public ListView<String> teamListView;

	@FXML
	public ListView<String> playerListView;

	@FXML
	public Label labelInfo;


	@Override
	public void initialize( URL location, ResourceBundle resources )
	{
		teamListView.setItems( teamList );
		playerListView.setItems( playerList );

		//teamListView.setDisable( true );
		//playerListView.setDisable( true );
	}

	@FXML
	public void onAddTeamButtonClicked( )
	{
		AddTeamDialog dlg = new AddTeamDialog( );
		{
			dlg.setResultCallback( r -> teamList.add( r ) );
		}
		dlg.showDialog( );
	}

	@FXML
	public void onEditTeamButtonClicked( )
	{

	}

	@FXML
	public void onRemoveTeamButtonClicked( )
	{

	}

	@FXML
	public void onAddPlayerButtonClicked( )
	{
		if( getSelectedTeamName( ) == null )
		{
			setInfoText( "You need to select your target team first!" );
		}
		else
		{
			AddPlayerDialog dlg = new AddPlayerDialog( );
			{
				dlg.setResultCallback( r -> playerList.add( r.getForename( ) + " " + r.getSurname( ) ) );
			}
			dlg.showDialog( );
		}
	}

	@FXML
	public void onEditPlayerButtonClicked( )
	{
		if( getSelectedPlayerName( ) == null || getSelectedTeamName( ) == null )
		{
			setInfoText( "You need to select your target team and player first!" );
		}
		else
		{
			AddPlayerDialog dlg = new AddPlayerDialog( new Player( 0, "asdf", "aasdasd22") );
			{
				dlg.showDialog( );
			}
		}
	}

	@FXML
	public void onRemovePlayerClicked( )
	{
		if( getSelectedPlayerName( ) == null || getSelectedTeamName( ) == null )
		{
			setInfoText( "You need to select your target team and player first!" );
		}
		else
		{

		}
	}


	private String getSelectedTeamName( )
	{
		return teamListView.getSelectionModel( ).getSelectedItem( );
	}

	private String getSelectedPlayerName( )
	{
		return playerListView.getSelectionModel( ).getSelectedItem( );
	}

	private void setInfoText( String text )
	{
		labelInfo.setText( "Info: " + text );
	}

}
