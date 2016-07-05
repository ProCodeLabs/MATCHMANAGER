package ui.StageCore.DialogStages;

import Core.MatchManager;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import ui.StageCore.DialogStages.Helper.MatchResultCanvas;
import ui.StageCore.Helper.UiStage;

public class MatchResultView extends UiStage
{
	private static final String RESOURCE_ID = "fxml/centerContent/matchResultView.fxml";

	private MatchManager manager;
	private MatchResultCanvas canvas = new MatchResultCanvas( );

	public MatchResultView( MatchManager manager )
	{
		this.manager = manager;
	}

	@Override
	public void showWindow( )
	{
		if( createStage( "RESULTS", RESOURCE_ID, 300, 200 ) )
		{
			VBox vboxCanvas = ( VBox ) getContainer( ).getUiHelper( ).getElementById( "vboxCanvas" );
			vboxCanvas.getChildren( ).add( canvas );

			//System.out.println( vboxCanvas.getWidth() );

			canvas.widthProperty( ).bind( vboxCanvas.widthProperty( ) );
			canvas.heightProperty( ).bind( vboxCanvas.heightProperty( ) );


			manager.getAllTeams( )
					.thenApply( t -> {
						Platform.runLater( ( ) -> canvas.addTeams( t ) );
						return null;
					} );

		}
	}
}
