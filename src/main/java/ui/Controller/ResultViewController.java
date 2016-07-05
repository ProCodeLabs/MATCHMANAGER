package ui.Controller;

import Core.MatchManager;
import ui.Container.UiBaseContainer;

public class ResultViewController
{
	public static final String CONTAINER_TITLE = "RESULTVIEW";
	public static final String RESOURCE_ID = "fxml/centerContent/resultView.fxml";

	private MatchManager manager;



	public static void updateContainerStage( UiBaseContainer container, MatchManager manager )
	{
		container.setCenter( CONTAINER_TITLE, RESOURCE_ID );
		{
			ResultViewController controller = container.<ResultViewController> getController( );

			controller.setMatchManager( manager );
		}
	}



	public void setMatchManager( MatchManager manager )
	{
		this.manager = manager;
	}

}
