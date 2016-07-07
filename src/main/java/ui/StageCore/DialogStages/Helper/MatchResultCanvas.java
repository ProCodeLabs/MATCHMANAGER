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
import ui.StageCore.DialogStages.Helper.ResultCanvasHelper.GraphNode;
import ui.StageCore.DialogStages.Helper.ResultCanvasHelper.MatchListHelper;

import java.awt.geom.Point2D;
import java.util.List;

public class MatchResultCanvas
{
	private static final double RECT_PREF_SPACING_WIDTH = 50;
	private static final double RECT_PREF_SPACING_HEIGHT = 25;
	private static final double RECT_PREF_WIDTH = 100;
	private static final double RECT_PREF_HEIGHT = RECT_PREF_WIDTH / 2.0;

	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	private Canvas canvas;

	private Pair<Double, Double> renderStartPos;
	private Pair<Double, Double> rectSize;
	private double spacingWidth;
	private double spacingHeight;
	//private List<GraphNode> nodes = new ArrayList<GraphNode>( );
	private MatchListHelper helper;


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


		helper = new MatchListHelper( matchList );

		GraphNode root = helper.createBinaryTree( );

		drawTree( gc, root, 0, canvas.getWidth( ), canvas.getHeight( ) / 2 );


		//drawConnections( gc, teamList, matchList );

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

	private void drawTree( GraphicsContext gc, GraphNode root, int it, double dWidth, double dHeight )
	{
		double dX = dWidth + ( it * spacingWidth ) - rectSize.getKey( );

		System.out.println( dWidth + " " + dHeight );

		gc.setLineWidth( 1.0 );
		gc.setStroke( Color.GREEN );
		gc.strokeRect( dX, dHeight, rectSize.getKey( ), rectSize.getValue( ) );

		if( root.left != null )
		{
			drawTree( gc, root.left, it + 1, dWidth - dX, dHeight + rectSize.getValue( ) + spacingHeight );
		}

		if( root.right != null )
		{
			drawTree( gc, root.right, it + 1, dWidth - dX, dHeight - rectSize.getValue( ) - spacingHeight );
		}
	}

	private void drawConnections( GraphicsContext gc, List<Team> teamList, List<Match> matchList )
	{
		GraphConnection con = new GraphConnection( new Point2D.Double( 50.0, 50.0 ), new Point2D.Double( 0.0, 200.0 ), new Point2D.Double( 150.0, 75.0 ) );
		{
			con.drawConnection( gc );
		}

		GraphConnection con2 = new GraphConnection( new Point2D.Double( 300, 100.0 ), new Point2D.Double( 350, 200 ), new Point2D.Double( 400, 150 ) );
		{
			con2.drawConnection( gc );
		}

		GraphConnection con3 = new GraphConnection( new Point2D.Double( 300, 300 ), new Point2D.Double( 300, 350 ), new Point2D.Double( 400, 325 ) );
		{
			con3.drawConnection( gc );
		}


		for( Team i : teamList )
		{

		}
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
		width: 100
		height: width/2

		padding top + bottom -> 5%
	*/

	private void calculateSizes( int teamCount )
	{
		double canvasWidth = canvas.getWidth( );
		double canvasHeight = canvas.getHeight( );

		double dWidthNormal = ( calculateNodeCount( teamCount ) * RECT_PREF_WIDTH ) + ( calculateNodeCount( teamCount ) * RECT_PREF_SPACING_WIDTH );
		double dHeightNormal = ( teamCount * RECT_PREF_HEIGHT ) + ( teamCount * RECT_PREF_SPACING_HEIGHT );

		double dScaleWidth = canvasWidth / dWidthNormal;
		double dScaleHeight = canvasHeight / dHeightNormal;

		rectSize = new Pair<>(
				RECT_PREF_WIDTH * dScaleWidth,
				RECT_PREF_HEIGHT * dScaleHeight
		);

		spacingWidth = RECT_PREF_SPACING_WIDTH * dScaleWidth;
		spacingHeight = RECT_PREF_SPACING_HEIGHT * dScaleHeight;


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
