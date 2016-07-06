package ui.StageCore.DialogStages.Helper;

import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Data.Match;
import Core.Data.Team;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import Common.CustomColor;

import java.util.List;

public class MatchResultCanvas
{


	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	private Canvas canvas;

	public MatchResultCanvas( Canvas canvas )
	{
		this.canvas = canvas;
	}

	public void fillInvalidate( )
	{
		GraphicsContext gc = canvas.getGraphicsContext2D( );

		gc.setFill( Color.ORANGE );
		gc.setGlobalAlpha( 0.8 );
		gc.fillRect( 0, 0, canvas.getWidth( ), canvas.getHeight( ) );
		gc.setGlobalAlpha( 1.0 );
	}

	public void redraw( List<Team> teamList, List<Match> matchList )
	{
		GraphicsContext gc = canvas.getGraphicsContext2D( );
		gc.clearRect( 0, 0, canvas.getWidth( ), canvas.getHeight( ) );


		int teamCount = teamList.size( );

		//Stream<Match> sorted = matchList.stream().filter( m -> m.getId() <= teamCount );

		double dX = 20;
		double dY = 10;
		double rectWidth = 100;
		double rectHeight = 50;

		for( int i = 0; i < teamCount; i++ )
		{
			gc.setStroke( CustomColor.PRIMARY);
			gc.strokeRect( dX, dY, rectWidth, rectHeight );

			gc.setStroke( CustomColor.SECONDARY );
			gc.strokeLine( dX + rectWidth, dY + ( rectHeight / 2 ), dX + rectWidth + 50, dY + ( rectHeight / 2 ) );

			dY += rectHeight + 20;
		}
	}


	public GraphicsContext getContext( )
	{
		return canvas.getGraphicsContext2D( );
	}

}
