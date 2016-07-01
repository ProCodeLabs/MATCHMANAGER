package ui.StageCore;

import javafx.util.Pair;
import ui.StageCore.Helper.UiStage;

public class DebugLogViewStage extends UiStage
{
	public static final String RESOURCE_ID = "fxml/centerContent/debugLogView.fxml";
	public static final double WINDOW_POS = 50;

	@Override
	public void showWindow( )
	{
		if( createStage( "LOG", RESOURCE_ID, 600, 300 ) )
		{
			showStage();
		}
	}

	@Override
	public void onSetPosition( )
	{
		Pair<Double, Double> pos = getPrimaryWindowPos( );

		double windowWidth = pos.getKey( ) -  ( getStage().getWidth() + WINDOW_POS );
		if( windowWidth > 0 )
		{
			getStage( ).setX( windowWidth );
		}
		else
		{
			getStage( ).setX( pos.getKey( ) );
		}

		getStage( ).setY( pos.getValue( ) );
	}


}
