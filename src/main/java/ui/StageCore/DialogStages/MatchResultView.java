package ui.StageCore.DialogStages;

import Common.GlobalInstance;
import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.MatchManager;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;
import ui.Helper.UiEvent;
import ui.StageCore.DialogStages.Helper.MatchResultCanvas;
import ui.StageCore.Helper.UiStage;

public class MatchResultView extends UiStage
{
	public static final String RESOURCE_ID = "fxml/centerContent/matchResultView.fxml";
	private static final double HEIGHT_THRESHOLD = 20;
	private static final double BORDER_PADDING = 18;

	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );
	private MatchManager manager;
	private MatchResultCanvas canvas;
	private Canvas viewCanvas;

	public MatchResultView( MatchManager manager )
	{
		this.manager = manager;
	}

	@Override
	public void showWindow( )
	{
		Stage primaryStage = GlobalInstance.getPrimaryStage( );

		if( createStage( "RESULTS", primaryStage.getWidth( ), 400 ) )
		{
			viewCanvas = ( Canvas ) getContainer( ).getUiHelper( ).getElementById( "matchViewCanvas" );
			{
				canvas = new MatchResultCanvas( viewCanvas );
			}

			getContainer( ).addEventHandler( UiEvent.BEGIN_RESIZE, e -> canvas.fillInvalidate( ) );
			getContainer( ).addEventHandler( UiEvent.FINISH_RESIZE, e -> {
				double dWidth = getStage( ).getWidth( ) - BORDER_PADDING;
				double dHeight = getStage( ).getHeight( ) - 55 - BORDER_PADDING;

				viewCanvas.setWidth( dWidth < 0.0 ? 0 : dWidth );
				viewCanvas.setHeight( dHeight < 0.0 ? 0 : dHeight );
				{
					redrawCanvas( );
				}
			} );

			Platform.runLater( ( ) -> getContainer( ).fireEvent( new Event( UiEvent.FINISH_RESIZE ) ) );
		}
	}


	private void redrawCanvas( )
	{
		manager.getAllTeams( )
				.thenAcceptBoth( manager.getAllMatches( ), ( teams, matches ) ->
						Platform.runLater( ( ) -> canvas.redraw( teams, matches ) )
				)
				.exceptionally( e -> {
					logger.error( "render failed to fetch data " + e.getMessage( ) );
					return null;
				} );
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
		Pair<Double, Double> size = getPrimaryWindowSize( );

		Rectangle2D primaryScreenBounds = Screen.getPrimary( ).getVisualBounds( );

		double y = pos.getValue( ) + size.getValue( ) + HEIGHT_THRESHOLD;

		if( y <= primaryScreenBounds.getMaxY( ) )
		{
			getStage( ).setY( y );
		}

		getStage( ).setX( pos.getKey( ) );
	}

	private VBox getParent( )
	{
		return ( VBox ) viewCanvas.getParent( );
	}

}
