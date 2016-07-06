package ui.StageCore.DialogStages.Helper;

import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Data.Match;
import Core.Data.Team;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.List;

public class MatchResultCanvas
{
	private static final double RECT_PERF_SPACING_HEIGHT = 25;
	private static final double RECT_PERF_WIDTH = 100;
	private static final double RECT_PERF_HEIGHT = RECT_PERF_WIDTH / 2.0;

	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	private Canvas canvas;

	private Pair<Double, Double> renderStartPos;
	private Pair<Double, Double> rectSize;
	private double spacingHeight;


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

		calculateSizes( teamList.size( ) );

		/*if( matchList.size( ) <= 0 )
		{
			drawTeams( gc, teamList );
		}
		else
		{

		}*/


		int teamCount = teamList.size( );

		//Stream<Match> sorted = matchList.stream().filter( m -> m.getId() <= teamCount );

		double dX = getRenderStartPos( ).getKey( );
		double dY = getRenderStartPos( ).getValue( );
		double rectWidth = rectSize.getKey( );
		double rectHeight = rectSize.getValue( );

		for( int i = 0; i < teamCount; i++ )
		{
			gc.setStroke( Color.GREEN );
			gc.strokeRect( dX, dY, rectWidth, rectHeight );

			dY += rectHeight + spacingHeight;
		}

		return;
	}

	private void drawTeams( GraphicsContext gc, List<Team> teamList )
	{
		for( Team i : teamList )
		{
			drawTeamRectangle( gc, i );
			return;
		}
	}

	private void drawTeamRectangle( GraphicsContext gc, Team team )
	{
		Pair<Double, Double> rectSize = getRectSize( );


		//Pair<Double, Double> pos = renderStartPos;

		double dX = getRenderStartPos( ).getKey( );
		double dY = getRenderStartPos( ).getValue( );


		gc.setLineWidth( 1.0 );
		gc.setStroke( Color.GREEN );
		gc.strokeRect( dX, dY, rectSize.getKey( ), rectSize.getValue( ) );
	}

	/*
		width: 100
		height: width/2

		padding top + bottom -> 5%
	*/

	private void calculateSizes( int teamCount )
	{
		double canvasWidth = canvas.getWidth( );
		double canvasHeight = canvas.getHeight( );

		double dWidthNormal = ( teamCount * RECT_PERF_WIDTH ) + ( teamCount * RECT_PERF_SPACING_HEIGHT );
		double dHeightNormal = ( teamCount * RECT_PERF_HEIGHT ) + ( teamCount * RECT_PERF_SPACING_HEIGHT );

		double dScaleWidth = canvasWidth / dWidthNormal;
		double dScaleHeight = canvasHeight / dHeightNormal;

		rectSize = new Pair<>(
				RECT_PERF_WIDTH * dScaleWidth,
				RECT_PERF_HEIGHT * dScaleHeight
		);

		spacingHeight = RECT_PERF_SPACING_HEIGHT * dScaleHeight;

		double dWidthPos = ( rectSize.getKey( ) ) / 2.0;
		double dHeightPos = ( rectSize.getValue( ) ) / 2.0;

		renderStartPos = new Pair<>(
				dWidthPos,
				dHeightPos
		);


		return;
	}


	private Pair<Double, Double> getRectSize( )
	{
		return new Pair<>(
				100.0,
				50.0
		);
	}

	private Pair<Double, Double> getRenderStartPos( )
	{
		return renderStartPos;
	}

	public GraphicsContext getContext( )
	{
		return canvas.getGraphicsContext2D( );
	}

}
