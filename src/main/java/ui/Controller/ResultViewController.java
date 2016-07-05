package ui.Controller;

import Core.MatchManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import ui.Container.UiBaseContainer;
import ui.StageCore.DialogStages.MatchResultView;
import ui.StageCore.DialogStages.MatchStatStage;
import ui.StageCore.Helper.UiStage;

public class ResultViewController
{
	private static final String CONTAINER_TITLE = "RESULTVIEW";
	private static final String RESOURCE_ID = "fxml/centerContent/resultView.fxml";

	@FXML
	private ChoiceBox<String> selectTeamA;

	@FXML
	private ChoiceBox<String> selectTeamB;

	@FXML
	private ChoiceBox<String> selectResultTeam;

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
		MatchStatStage s = new MatchStatStage( );
		{
			s.showWindow( );
		}

		MatchResultView b = new MatchResultView( manager );
		{
			b.showWindow( );
		}
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
