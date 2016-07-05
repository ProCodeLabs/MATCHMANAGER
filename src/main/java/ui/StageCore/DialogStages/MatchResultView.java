package ui.StageCore.DialogStages;

import Common.GlobalInstance;
import Core.MatchManager;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
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

	private MatchManager manager;
	private MatchResultCanvas canvas;

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
			VBox vboxCanvas = ( VBox ) getContainer( ).getUiHelper( ).getElementById( "vboxCanvas" );
			{
				canvas = new MatchResultCanvas( vboxCanvas.getWidth( ), vboxCanvas.getHeight( ) );
			}
			vboxCanvas.getChildren( ).add( canvas );

			getContainer( ).addEventHandler( UiEvent.BEGIN_RESIZE, e -> canvas.fillInvalidate( ) );
			getContainer( ).addEventHandler( UiEvent.FINISH_RESIZE, e -> {
				canvas.setWidth( vboxCanvas.getWidth( ) );
				canvas.setHeight( vboxCanvas.getHeight( ) );
				canvas.redraw( );
			} );

			//canvas.widthProperty( ).bind( vboxCanvas.widthProperty( ) );
			//canvas.heightProperty( ).bind( vboxCanvas.heightProperty( ) );


			manager.getAllTeams( )
					.thenApply( t -> {
						Platform.runLater( ( ) -> canvas.addTeams( t ) );
						return null;
					} );

			Platform.runLater( ( ) -> canvas.redraw( ) );
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
		Pair<Double, Double> size = getPrimaryWindowSize( );

		Rectangle2D primaryScreenBounds = Screen.getPrimary( ).getVisualBounds( );

		double y = pos.getValue( ) + size.getValue( ) + HEIGHT_THRESHOLD;

		if( y <= primaryScreenBounds.getMaxY( ) )
		{
			getStage( ).setY( y );
		}

		getStage( ).setX( pos.getKey( ) );
	}

}
