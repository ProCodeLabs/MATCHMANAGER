package ui.Controller;

import Core.Data.Player;
import Core.Data.Team;
import Core.MatchManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
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

	private MatchManager manager;


	public static void updateContainerStage( UiBaseContainer container, MatchManager manager )
	{
		container.setCenter( CONTAINER_TITLE, RESOURCE_ID );
		{
			ResultViewController controller = container.<ResultViewController> getController( );

			controller.setMatchManager( manager );
			controller.initializeController( );
		}
	}


	private void initializeController( )
	{

		Platform.runLater( ( ) -> {
			MatchStatStage s = new MatchStatStage( );
			{
				s.showWindow( );
			}
		} );

		Platform.runLater( ( ) -> {
			MatchResultView b = new MatchResultView( manager );
			{
				b.showWindow( );
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
	public void onLeftButtonClick( )
	{

	}


	private void hideInfoWindows( )
	{
		Platform.runLater( ( ) -> {
			UiStage.closeActiveDialog( MatchStatStage.RESOURCE_ID );
			UiStage.closeActiveDialog( MatchResultView.RESOURCE_ID );
		} );
	}

	private void setMatchManager( MatchManager manager )
	{
		this.manager = manager;
	}

}
