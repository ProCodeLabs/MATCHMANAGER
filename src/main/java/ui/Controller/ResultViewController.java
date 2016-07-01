package ui.Controller;

import Core.MatchManager;

public class ResultViewController
{
	public static final String RESOURCE_ID = "fxml/centerContent/resultView.fxml";

	private MatchManager manager;



	public void setMatchManager( MatchManager manager )
	{
		this.manager = manager;
	}

}
