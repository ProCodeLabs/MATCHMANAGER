package ui.Controller;

import Core.Data.Player;
import Core.MatchManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ui.Container.UiBaseContainer;
import ui.Dialog.AddPlayerDialog;
import ui.Dialog.AddTeamDialog;
import ui.Dialog.ModalEx.UiAlert;

import java.net.URL;
import java.util.ResourceBundle;

public class TeamSetupController implements Initializable
{
	public static final String CONTAINER_TITLE = "TEAMSETUP";
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

	private MatchManager manager;

	public static void updateContainerStage( UiBaseContainer container, MatchManager manager )
	{
		container.setCenter( CONTAINER_TITLE, RESOURCE_ID );
		{
			container.<TeamSetupController> getController( ).setMatchManager( manager );
		}
	}


	@Override
	public void initialize( URL location, ResourceBundle resources )
	{
		teamListView.setItems( teamList );
		playerListView.setItems( playerList );

		teamListView.getSelectionModel( ).getSelectedItems( ).addListener( new ListChangeListener<String>( )
		{
			@Override
			public void onChanged( Change<? extends String> c )
			{
				playerList.clear( );

				String teamName = getSelectedTeamName( );

				if( teamName != null )
				{
					manager.getAllTeamPlayers( teamName )
							.thenApply( r -> {
								r.forEach(
										i -> Platform.runLater( ( ) -> playerList.add( i.getFullName( ) ) )
								);
								return null;
							} )
							.exceptionally( e -> {
								showDatabaseExceptionDlg( e );
								return null;
							} );

				}
			}
		} );


		//teamListView.setDisable( true );
		//playerListView.setDisable( true );
	}

	@FXML
	public void onAddTeamButtonClicked( )
	{
		AddTeamDialog dlg = new AddTeamDialog( );
		{
			dlg.setResultCallback( r -> manager.addTeam( r )
					.thenApply( result -> {
						Platform.runLater( ( ) -> teamList.add( result.getTeamName( ) ) );
						return null;
					} ).exceptionally( e -> {
						Platform.runLater( ( ) -> showTeamAlreadyExistsDlg( e ) );
						return null;
					} ) );
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
		String teamName = getSelectedTeamName( );

		if( teamName == null )
		{
			setInfoText( "You need to select your target team first!" );
		}
		else
		{
			AddPlayerDialog dlg = new AddPlayerDialog( teamName );
			{
				dlg.setResultCallback( result -> manager.addPlayer( teamName, result )
						.thenApply( r -> {
							Platform.runLater( ( ) -> playerList.add( r.getFullName( ) ) );
							return null;
						} )
						.exceptionally( e -> {
							Platform.runLater( ( ) -> showDatabaseExceptionDlg( e ) );
							return null;
						} )
				);
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
			AddPlayerDialog dlg = new AddPlayerDialog( new Player( "asdf", "aasdasd22" ) );
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


	public void setMatchManager( MatchManager manager )
	{
		this.manager = manager;
	}

	private void showTeamAlreadyExistsDlg( Throwable e )
	{
		UiAlert msgBox = new UiAlert( Alert.AlertType.ERROR );
		{
			msgBox.setHeaderText( "Team already exists!" );
			msgBox.setContentText( e.getMessage( ) );
		}

		msgBox.showAndWait( );
	}

	private void showDatabaseExceptionDlg( Throwable e )
	{
		UiAlert msgBox = new UiAlert( Alert.AlertType.ERROR );
		{
			msgBox.setHeaderText( "Database Exception" );
			msgBox.setContentText( e.getMessage( ) );
		}

		msgBox.showAndWait( );
	}
}
