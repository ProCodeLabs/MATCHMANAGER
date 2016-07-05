package ui.Controller;

import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Data.Player;
import Core.Data.Team;
import Core.MatchManager;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
	private ChoiceBox<Team> selectTeamA;

	@FXML
	private ChoiceBox<Team> selectTeamB;

	@FXML
	private ListView<Player> teamMemberViewA;

	@FXML
	private ListView<Player> teamMemberViewB;

	@FXML
	private ChoiceBox<Team> selectResultTeam;

	@FXML
	private Button buttonLeft;

	@FXML
	private Button buttonNext;


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

		Platform.runLater( ( ) -> matchStatStage.showWindow( ) );
		Platform.runLater( ( ) -> matchResultView.showWindow( ) );

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
				buttonNext.setDisable( selectResultTeam.getItems( ).size( ) < 2 );
			}
		} );

		updateTitle();
	}

	private void registerTeamSelector( ChoiceBox<Team> select, ListView<Player> view )
	{
		select.getSelectionModel( ).selectedItemProperty( )
				.addListener( ( ObservableValue<? extends Team> observable, Team oldValue, Team newValue ) -> {

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

		/*
				selectTeamA.getSelectionModel( ).selectedItemProperty( )
				.addListener( ( ObservableValue<? extends Team> observable, Team oldValue, Team newValue ) -> {
					selectTeamA.getItems().clear();

					manager.getAllTeamPlayers( newValue.getTeamName( ) ).thenApply( r -> {
						r.forEach( i -> Platform.runLater( ( ) -> teamMemberViewA.getItems( ).add( i ) ) );

						return null;
					} );
				} );
		 */
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
	public void onButtonNextClick()
	{

	}

	private void updateTitle()
	{
		manager.calculateMaxMatchNumber( )
				.thenApply( r -> {
					Platform.runLater( ( ) -> container.setTitle( CONTAINER_TITLE + " (0/" + r + ")" ) );
					return null;
				} );
	}

	private void hideInfoWindows( )
	{
		Platform.runLater( ( ) -> {
			UiStage.closeActiveDialog( MatchStatStage.RESOURCE_ID );
			UiStage.closeActiveDialog( MatchResultView.RESOURCE_ID );
		} );
	}

}
