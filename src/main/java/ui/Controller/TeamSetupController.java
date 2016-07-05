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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ui.Container.UiBaseContainer;
import ui.Dialog.AddPlayerDialog;
import ui.Dialog.AddTeamDialog;
import ui.Dialog.ModalEx.UiAlert;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

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
										i -> Platform.runLater( ( ) -> playerList.add( i ) )
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
			dlg.setResultCallback( r -> manager.updateTeam( teamName, new Team( r ) )
					.thenApply( t -> {
						teamList.remove( teamName );
						teamList.add( r );
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

		UiAlert alert = new UiAlert( Alert.AlertType.CONFIRMATION );
		{
			alert.setTitle( "Delete" );
			alert.setHeaderText( "Delete" );
			alert.setContentText( "Are you sure you want to delete " + getSelectedTeamName( ) + "? (This cannot be undone!)" );
		}

		Optional<ButtonType> result = alert.showAndWait( );
		if( result.get( ) == ButtonType.OK )
		{
			manager.removeTeam( teamName )
					.thenApply( r -> teamList.remove( teamName ) )
					.exceptionally( e -> showDatabaseExceptionDlg( e ) );
		}
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
					.exceptionally( e -> {
						Platform.runLater( ( ) -> showDatabaseExceptionDlg( e ) );
						return null;
					} )
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
			AddPlayerDialog dlg = new AddPlayerDialog( getSelectedPlayerItem( ) );
			{
				dlg.showDialog( );
			}
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

		}
	}

	@FXML
	public void onApplyButtonClick( )
	{
		AtomicInteger count = new AtomicInteger( );

		teamList.forEach( e -> count.incrementAndGet( ) );

		if( count.get( ) > 1 )
		{
			ResultViewController.updateContainerStage( ( UiBaseContainer ) labelInfo.getScene( ).getRoot( ), manager );
		}
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

	private static boolean showDatabaseExceptionDlg( Throwable e )
	{
		UiAlert msgBox = new UiAlert( Alert.AlertType.ERROR );
		{
			msgBox.setHeaderText( "Database Exception" );
			msgBox.setContentText( e.getMessage( ) );
		}

		msgBox.showAndWait( );

		return true;
	}
}
