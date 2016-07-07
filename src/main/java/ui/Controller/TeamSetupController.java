package ui.Controller;

import Core.Data.Player;
import Core.Data.Team;
import Core.MatchManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ui.Container.UiBaseContainer;
import ui.Dialog.AddPlayerDialog;
import ui.Dialog.AddTeamDialog;
import ui.Dialog.DeletePlayerDialog;
import ui.Dialog.DeleteTeamDialog;
import ui.Dialog.ModalEx.UiAlert;

import java.net.URL;
import java.util.ResourceBundle;

public class TeamSetupController implements Initializable
{
	public static final String CONTAINER_TITLE = "TEAMSETUP";
	public static final String RESOURCE_ID = "fxml/centerContent/setupTeams.fxml";


	private ObservableList<String> teamList = FXCollections.observableArrayList( );
	private ObservableList<Player> playerList = FXCollections.observableArrayList( );


	@FXML
	public ListView<String> teamListView;

	@FXML
	public ListView<Player> playerListView;

	@FXML
	public Label labelInfo;

	private MatchManager manager;


	public static void updateContainerStage( UiBaseContainer container, MatchManager manager )
	{
		container.setCenter( CONTAINER_TITLE, RESOURCE_ID );
		{
			TeamSetupController controller = container.<TeamSetupController> getController( );

			controller.setMatchManager( manager );
			controller.initializeController( );
		}
	}

	@Override
	public void initialize( URL location, ResourceBundle resources )
	{
		FXCollections.sort( teamList );

		teamListView.setItems( teamList );
		playerListView.setItems( playerList );

		teamListView.getSelectionModel( )
				.getSelectedItems( )
				.addListener( ( ListChangeListener.Change<? extends String> c ) -> {
					playerList.clear( );

					String teamName = getSelectedTeamName( );

					if( teamName != null )
					{
						manager.getAllTeamPlayers( teamName )
								.thenApply( r -> {
									r.forEach(
											i -> Platform.runLater( ( ) -> playerList.add( i ) )
									);
									return null;
								} )
								.exceptionally( TeamSetupController::showDatabaseExceptionDlg );

					}
				} );
	}

	private void initializeController( )
	{
		manager.getAllTeams( ).thenApply( r -> {
			r.forEach( i -> Platform.runLater( ( ) -> teamList.add( i.getTeamName( ) ) ) );
			return null;
		} );
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
		if( !checkSelectedTeam( ) )
		{
			return;
		}

		String teamName = getSelectedTeamName( );

		AddTeamDialog dlg = new AddTeamDialog( teamName );
		{
			Platform.runLater( ( ) -> teamListView.getSelectionModel( ).clearSelection( ) );
			dlg.setResultCallback( r -> manager.updateTeam( teamName, new Team( r ) )
					.thenApply( t -> {
						Platform.runLater( ( ) -> teamList.remove( teamName ) );
						Platform.runLater( ( ) -> teamList.add( r ) );
						return null;
					} ) );
		}
		dlg.showDialog( );
	}

	@FXML
	public void onRemoveTeamButtonClicked( )
	{
		if( !checkSelectedTeam( ) )
		{
			return;
		}

		String teamName = getSelectedTeamName( );

		manager.getMatchCount( ).thenApply( r -> {
			if( r > 0 )
			{
				Platform.runLater( ( ) -> setInfoText( "You cannot remove a team while a match is running!" ) );
			}
			else
			{
				Platform.runLater( ( ) -> {
					DeleteTeamDialog dlg = new DeleteTeamDialog( teamName );
					{
						Platform.runLater( ( ) -> teamListView.getSelectionModel( ).clearSelection( ) );

						dlg.setResultCallback( result -> manager.removeTeam( teamName )
								.thenApply( res -> {
									Platform.runLater( ( ) -> teamList.remove( teamName ) );
									return null;
								} )
								.exceptionally( TeamSetupController::showDatabaseExceptionDlg )
						);
					}
					dlg.showDialog( );
				} );
			}
			return null;
		} );
	}

	@FXML
	public void onAddPlayerButtonClicked( )
	{
		String teamName = getSelectedTeamName( );

		if( teamName == null )
		{
			setInfoText( "You need to select your target team first!" );
			return;
		}

		AddPlayerDialog dlg = new AddPlayerDialog( teamName );
		{
			dlg.setResultCallback( result -> manager.addPlayer( teamName, result )
					.thenApply( r -> {
						Platform.runLater( ( ) -> playerList.add( r ) );
						return null;
					} )
					.exceptionally( TeamSetupController::showDatabaseExceptionDlg )
			);
		}
		dlg.showDialog( );
	}

	@FXML
	public void onEditPlayerButtonClicked( )
	{
		if( getSelectedPlayerItem( ) == null || getSelectedTeamName( ) == null )
		{
			setInfoText( "You need to select your target team and player first!" );
		}
		else
		{
			Player selectedPlayer = getSelectedPlayerItem( );

			AddPlayerDialog dlg = new AddPlayerDialog( selectedPlayer );
			{
				dlg.setResultCallback( player -> manager.updatePlayer( selectedPlayer.getId( ), player )
						.thenApply( r -> {
							Platform.runLater( ( ) -> {
								playerList.remove( selectedPlayer );
								playerList.add( player );
							} );
							return null;
						} )
						.exceptionally( TeamSetupController::showDatabaseExceptionDlg )
				);
			}
			dlg.showDialog( );
		}
	}

	@FXML
	public void onRemovePlayerClicked( )
	{
		if( getSelectedPlayerItem( ) == null || getSelectedTeamName( ) == null )
		{
			setInfoText( "You need to select your target team and player first!" );
		}
		else
		{
			long currentPlayerID = getSelectedPlayerItem( ).getId( );
			Player pl = getSelectedPlayerItem( );

			DeletePlayerDialog dlg = new DeletePlayerDialog( getSelectedPlayerItem( ).getFullName( ) );
			{
				dlg.setResultCallback( result -> manager.removePlayer( currentPlayerID )
						.thenApply( r -> {
							Platform.runLater( ( ) -> playerList.remove( pl ) );
							return null;
						} )
						.exceptionally( TeamSetupController::showDatabaseExceptionDlg )
				);
			}
			dlg.showDialog( );
		}
	}

	@FXML
	public void onApplyButtonClick( )
	{
		manager.getAllTeams( ).thenApply( r -> {
			if( r.size( ) >= 2 )
			{
				Platform.runLater( ( ) -> ResultViewController.updateContainerStage( ( UiBaseContainer ) labelInfo.getScene( )
						.getRoot( ), manager )
				);
			}
			else
			{
				Platform.runLater( ( ) -> setInfoText( "You have to add at least TWO teams!" ) );
			}

			return null;
		} );
	}


	private String getSelectedTeamName( )
	{
		return teamListView.getSelectionModel( ).getSelectedItem( );
	}

	private Player getSelectedPlayerItem( )
	{
		return playerListView.getSelectionModel( ).getSelectedItem( );
	}

	private void setInfoText( String text )
	{
		labelInfo.setText( "Info: " + text );
	}


	private void setMatchManager( MatchManager manager )
	{
		this.manager = manager;
	}

	private boolean checkSelectedTeam( )
	{
		if( getSelectedTeamName( ) == null )
		{
			setInfoText( "You need to select your target team first!" );
			return false;
		}

		return true;
	}


	private static boolean showTeamAlreadyExistsDlg( Throwable e )
	{
		UiAlert msgBox = new UiAlert( Alert.AlertType.ERROR );
		{
			msgBox.setHeaderText( "Team already exists!" );
			msgBox.setContentText( e.getMessage( ) );
		}

		msgBox.showAndWait( );

		return true;
	}

	private static Void showDatabaseExceptionDlg( Throwable e )
	{
		Platform.runLater( ( ) -> {
			UiAlert msgBox = new UiAlert( Alert.AlertType.ERROR );
			{
				msgBox.setHeaderText( "Database Exception" );
				msgBox.setContentText( e.getMessage( ) );
			}
			msgBox.showAndWait( );
		} );

		return null;
	}
}
