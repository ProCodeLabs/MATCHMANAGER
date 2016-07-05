package ui.StageCore.DialogStages;

import Core.MatchManager;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import ui.Helper.UiEvent;
import ui.StageCore.DialogStages.Helper.MatchResultCanvas;
import ui.StageCore.Helper.UiStage;

public class MatchResultView extends UiStage
{
	private static final String RESOURCE_ID = "fxml/centerContent/matchResultView.fxml";

	private MatchManager manager;
	private MatchResultCanvas canvas;

	public MatchResultView( MatchManager manager )
	{
		this.manager = manager;
	}

	@Override
	public void showWindow( )
	{
		if( createStage( "RESULTS", RESOURCE_ID, 400, 300 ) )
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
}
