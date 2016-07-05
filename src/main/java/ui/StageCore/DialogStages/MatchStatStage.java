package ui.StageCore.DialogStages;

import javafx.util.Pair;
import ui.StageCore.Helper.UiStage;

public class MatchStatStage extends UiStage
{
	private static final String RESOURCE_ID = "fxml/centerContent/statView.fxml";
	private static final double WINDOW_POS = 50;


	public MatchStatStage( )
	{
	}

	@Override
	public void showWindow( )
	{
		if( createStage( "STATVIEW", RESOURCE_ID, 600, 400 ) )
		{

		}
	}

	@Override
	public void onSetPosition( )
	{
		Pair<Double, Double> pos = getPrimaryWindowPos( );


	}
}