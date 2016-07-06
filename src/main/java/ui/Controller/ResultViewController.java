package ui.Controller;

import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Data.Player;
import Core.Data.Team;
import Core.MatchManager;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import ui.Container.UiBaseContainer;
import ui.StageCore.DialogStages.MatchResultView;
import ui.StageCore.DialogStages.MatchStatStage;
import ui.StageCore.Helper.UiStage;

public class ResultViewController
{
	private static final String CONTAINER_TITLE = "RESULTVIEW";
	private static final String RESOURCE_ID = "fxml/centerContent/resultView.fxml";

	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	@FXML
	private ComboBox<Team> selectTeamA;

	@FXML
	private ComboBox<Team> selectTeamB;

	@FXML
	private ListView<Player> teamMemberViewA;

	@FXML
	private ListView<Player> teamMemberViewB;

	@FXML
	private ComboBox<Team> selectResultTeam;

	@FXML
	private Button buttonLeft;

	@FXML
	private Button buttonNext;

	@FXML
	Button showTree;

	@FXML
	Button showStats;

	private ObservableList<Team> teamListA = FXCollections.observableArrayList( );
	private ObservableList<Team> teamlistB = FXCollections.observableArrayList( );


	private MatchManager manager;

	private MatchStatStage matchStatStage = new MatchStatStage( );

	private MatchResultView matchResultView;

	private UiBaseContainer container;


	public static void updateContainerStage( UiBaseContainer container, MatchManager manager )
	{
		container.setCenter( CONTAINER_TITLE, RESOURCE_ID );
		{
			ResultViewController controller = container.<ResultViewController> getController( );

			controller.manager = manager;
			controller.container = container;
			controller.initializeController( );
		}
	}


	private void initializeController( )
	{
		matchResultView = new MatchResultView( manager );

		selectTeamA.setItems( teamListA );
		selectTeamB.setItems( teamlistB );

		//
		//

		manager.getAllTeams( ).thenApply( l -> {
			l.forEach( i -> Platform.runLater( ( ) -> {
				selectTeamA.getItems( ).add( i );
				selectTeamB.getItems( ).add( i );
			} ) );
			return null;
		} );

		registerTeamSelector( selectTeamA, teamMemberViewA );
		registerTeamSelector( selectTeamB, teamMemberViewB );

		selectResultTeam.getItems( ).addListener( new ListChangeListener<Team>( )
		{
			@Override
			public void onChanged( Change<? extends Team> c )
			{
				//if( c.wasAdded( ) || c.wasRemoved( ) )
				{
					selectResultTeam.setDisable( selectResultTeam.getItems( ).size( ) < 2 );
				}
			}
		} );

		selectResultTeam.getSelectionModel( ).selectedItemProperty( ).addListener(
				( ObservableValue<? extends Team> observable, Team oldValue, Team newValue ) -> {
					if( newValue != null )
					{
						buttonNext.setDisable( false );
					}
					else
					{
						buttonNext.setDisable( true );
					}
				} );


		registerTeamChanger( selectTeamA, selectTeamB );
		registerTeamChanger( selectTeamB, selectTeamA );

		updateTitle( );
	}


	public void onShowStatsButtonClicked()
	{
		Platform.runLater( ( ) -> matchResultView.showWindow( ) );
	}
	public void onShowTreeButtonClicked()
	{
		Platform.runLater( ( ) -> matchStatStage.showWindow( ) );
	}

	private void registerTeamSelector( ComboBox<Team> select, ListView<Player> view )
	{
		select.getSelectionModel( ).selectedItemProperty( )
				.addListener( ( ObservableValue<? extends Team> observable, Team oldValue, Team newValue ) -> {

					if( newValue == null )
					{
						view.getItems( ).clear( );
						return;
					}

					if( selectResultTeam.getItems( ).contains( oldValue ) )
					{
						selectResultTeam.getItems( ).remove( oldValue );
					}

					if( newValue == null )
					{
						newValue = oldValue;
					}

					selectResultTeam.getItems( ).add( newValue );

					view.getItems( ).clear( );

					manager.getAllTeamPlayers( newValue.getTeamName( ) )
							.thenApply( r -> {
								r.forEach( i -> Platform.runLater( ( ) -> view.getItems( ).add( i ) ) );

								return null;
							} )
							.exceptionally( e -> {
								logger.error( "failed to fetch players" + e.getMessage( ) );
								return null;
							} );
				} );
	}

	private void registerTeamChanger( ComboBox<Team> selectA, ComboBox<Team> selectB )
	{
		selectA.getSelectionModel( ).selectedItemProperty( ).addListener(
				( ObservableValue<? extends Team> observable, Team oldValue, Team newValue ) -> {

					if( oldValue != null )
					{
						selectB.getItems( ).add( oldValue );
						selectB.getItems( ).remove( newValue );
					}
					else
					{
						selectB.getItems( ).remove( newValue );
					}
				} );

	}


	@FXML
	public void onCloseDatabaseButtonClick( )
	{
		manager.close( );

		hideInfoWindows( );
		SelectDatabaseController.updateContainerStage( ( UiBaseContainer ) selectTeamA.getScene( ).getRoot( ) );
	}

	@FXML
	public void onEditTeamsButtonClick( )
	{
		hideInfoWindows( );
		TeamSetupController.updateContainerStage( ( UiBaseContainer ) selectTeamA.getScene( ).getRoot( ), manager );
	}


	@FXML
	public void onButtonLeftClick( )
	{
	}

	@FXML
	public void onButtonNextClick( )
	{
		if( getSelectedResultTeam( ) == null )
		{
			return;
		}

		setNextMatch( );
	}

	private void setNextMatch( )
	{
		manager.addMatch( getSelectedTeamA( ), getSelectedTeamB( ), getSelectedResultTeam( ) ).thenApply( r -> {
			Platform.runLater( ( ) -> {
				selectTeamA.getSelectionModel( ).clearSelection( );
				selectTeamB.getSelectionModel( ).clearSelection( );
				selectResultTeam.getItems( ).clear( );

				manager.getMatchCount( ).thenAcceptBoth( manager.calculateMaxMatchNumber( ), ( count, max ) ->
						Platform.runLater( ( ) -> {
							if( count >= max - 1 )
							{
								buttonNext.setText( "FINISH" );
							}
							else if( count >= max )
							{
								setTournamentFinish( );
							}

							container.setTitle( CONTAINER_TITLE + " (" + count + "/" + max + ")" );
						} )
				);
			} );

			return null;
		} );
	}

	private void updateTitle( )
	{
	}

	private void setTournamentFinish( )
	{
		selectTeamA.setDisable( true );
		selectTeamB.setDisable( true );
		selectResultTeam.setDisable( true );
	}

	private void hideInfoWindows( )
	{
		Platform.runLater( ( ) -> {
			UiStage.closeActiveDialog( MatchStatStage.RESOURCE_ID );
			UiStage.closeActiveDialog( MatchResultView.RESOURCE_ID );
		} );
	}

	private Team getSelectedTeamA( )
	{
		return selectTeamA.getSelectionModel( ).getSelectedItem( );
	}

	private Team getSelectedTeamB( )
	{
		return selectTeamB.getSelectionModel( ).getSelectedItem( );
	}

	private Team getSelectedResultTeam( )
	{
		return selectResultTeam.getSelectionModel( ).getSelectedItem( );
	}

}
