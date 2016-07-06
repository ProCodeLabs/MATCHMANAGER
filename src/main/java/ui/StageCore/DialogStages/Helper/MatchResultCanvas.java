package ui.StageCore.DialogStages.Helper;

import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Data.Match;
import Core.Data.Team;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import ui.StageCore.DialogStages.Helper.ResultCanvasHelper.GraphConnection;

import java.awt.geom.Point2D;
import java.util.List;

public class MatchResultCanvas
{
	private static final double RECT_PERF_SPACING_WIDTH = 50;
	private static final double RECT_PERF_SPACING_HEIGHT = 25;
	private static final double RECT_PERF_WIDTH = 100;
	private static final double RECT_PERF_HEIGHT = RECT_PERF_WIDTH / 2.0;

	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	private Canvas canvas;

	private Pair<Double, Double> renderStartPos;
	private Pair<Double, Double> rectSize;
	private double spacingWidth;
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


		{
			matchList.clear( );
			matchList.add( new Match( 1, 1, 2, 1 ) );
			matchList.add( new Match( 2, 3, 4, 3 ) );
			matchList.add( new Match( 3, 1, 3, 1 ) );
		}

		drawConnections( gc, teamList, matchList );

		drawTeams( gc, teamList );
		drawMatches( gc, matchList );

		if( teamList.size( ) == ( matchList.size( ) - 1 ) )
		{
			drawWinningTeam( gc, teamList.get( teamList.size( ) - 1 ), matchList.get( matchList.size( ) - 1 ) );
		}
	}

	private void drawTeams( GraphicsContext gc, List<Team> teamList )
	{
		for( int i = 0; i < teamList.size( ); i++ )
		{
			drawTeamRectangle( gc, i, teamList.get( i ) );
		}
	}

	private void drawMatches( GraphicsContext gc, List<Match> matchList )
	{

	}

	private void drawConnections( GraphicsContext gc, List<Team> teamList, List<Match> matchList )
	{
		GraphConnection con = new GraphConnection( 50.0, 50.0,  0.0, 200.0, 150.0, 140.0   );
		{
			con.drawConnection( gc );
		}

		GraphConnection con2 = new GraphConnection( 200 + 200.0, 50.0, 200 + 400, 200.0, 200 + 150.0, 140.0   );
		{
			con2.drawConnection( gc );
		}


		for( Team i : teamList )
		{

		}
	}

	private void drawWinningTeam( GraphicsContext gc, Team team, Match match )
	{

	}


	private void drawTeamRectangle( GraphicsContext gc, int it, Team team )
	{
		Pair<Double, Double> rectSize = getRectSize( );


		double dX = getRenderStartPos( ).getKey( );
		double dY = getRenderStartPos( ).getValue( ) + ( rectSize.getValue( ) * it ) + ( spacingHeight * it );


		gc.setLineWidth( 1.0 );
		gc.setStroke( Color.GREEN );
		gc.strokeRect( dX, dY, rectSize.getKey( ), rectSize.getValue( ) );
	}

	/*
		pos = position from left to right
		it = iterator index ->
	 */
	Point2D.Double getRectangleCoords( double pos, double it )
	{

		/*Double itPosH = ((pos * rectSize.getValue() )+ ((pos/2)*spacingWidth))/2;

		return new Point2D.Double(
				getRenderStartPos().getKey() + (pos * spacingWidth) + (pos)
		);*/

		return null;
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

		double dWidthNormal = ( calculateNodeCount( teamCount ) * RECT_PERF_WIDTH ) + ( calculateNodeCount( teamCount ) * RECT_PERF_SPACING_WIDTH );
		double dHeightNormal = ( teamCount * RECT_PERF_HEIGHT ) + ( teamCount * RECT_PERF_SPACING_HEIGHT );

		double dScaleWidth = canvasWidth / dWidthNormal;
		double dScaleHeight = canvasHeight / dHeightNormal;

		rectSize = new Pair<>(
				RECT_PERF_WIDTH * dScaleWidth,
				RECT_PERF_HEIGHT * dScaleHeight
		);

		spacingWidth = RECT_PERF_SPACING_WIDTH * dScaleWidth;
		spacingHeight = RECT_PERF_SPACING_HEIGHT * dScaleHeight;


		double dWidthPos = ( canvasWidth - dWidthNormal ) / 2.0;
		double dHeightPos = ( canvasHeight - dHeightNormal ) / 2.0;

		if( dWidthNormal > canvasWidth )
		{
			dWidthPos = 0;
		}
		if( dHeightNormal > canvasHeight )
		{
			dHeightPos = 0;
		}


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


	/*
		> info usage
	 */
	private int calculateNodeCount( int teamCount )
	{
		return teamCount;
	}
}

	/*int teamCount = teamList.size( );

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
		}*/