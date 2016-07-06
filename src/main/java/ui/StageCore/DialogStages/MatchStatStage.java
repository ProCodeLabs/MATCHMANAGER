package ui.StageCore.DialogStages;

import Common.GlobalInstance;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.util.Pair;
import ui.StageCore.Helper.UiStage;

public class MatchStatStage extends UiStage
{
	public static final String RESOURCE_ID = "fxml/centerContent/statView.fxml";
	private static final double WINDOW_POS = 20;


	public MatchStatStage( )
	{
	}

	@Override
	public void showWindow( )
	{
		if( createStage( "STATVIEW", 600, 400 ) )
		{

		}
	}

	@Override
	public String getResourceId( )
	{
		return RESOURCE_ID;
	}

	@Override
	public void onSetPosition( )
	{
		Pair<Double, Double> pos = getPrimaryWindowPos( );

		Rectangle2D primaryScreenBounds = Screen.getPrimary( ).getVisualBounds( );

		double x = pos.getKey( ) + GlobalInstance.getPrimaryStage( ).getWidth( ) + WINDOW_POS;

		if( x <= primaryScreenBounds.getMaxX( ) )
		{
			getStage( ).setX( x );
		}
		getStage( ).setY( pos.getValue( ) );
	}
}