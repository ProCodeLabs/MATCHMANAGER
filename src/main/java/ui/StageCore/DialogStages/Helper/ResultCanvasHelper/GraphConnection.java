package ui.StageCore.DialogStages.Helper.ResultCanvasHelper;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.geom.Point2D;

public class GraphConnection
{
	private Point2D.Double nodeA;
	private Point2D.Double nodeB;

	private Point2D.Double targetNode;

	public GraphConnection( Point2D.Double nodeA, Point2D.Double nodeB, Point2D.Double targetNode )
	{
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.targetNode = targetNode;
	}

	public GraphConnection( Point2D.Double nodeB, Point2D.Double nodeA )
	{
		this.nodeB = nodeB;
		this.nodeA = nodeA;
	}

	/*
			-------|
				   |------- 3math5me
			-------|
	*/
	public void drawConnection( GraphicsContext gc )
	{
		double x1Min = targetNode.getX( ) - nodeA.getX( );
		double x2Min = targetNode.getX( ) - nodeB.getX( );

		double dMinMid = Math.min( x1Min, x2Min ) / 2;

		Point2D.Double ptMid;
		if( nodeA.getX( ) < nodeB.getX( ) )
		{
			ptMid = new Point2D.Double( nodeB.getX( ) + dMinMid, targetNode.getY( ) );
		}
		else
		{
			ptMid = new Point2D.Double( nodeA.getX( ) + dMinMid, targetNode.getY( ) );
		}

		gc.strokeLine( nodeA.getX( ), nodeA.getY( ), ptMid.getX( ), nodeA.getY( ) );
		gc.strokeLine( nodeB.getX( ), nodeB.getY( ), ptMid.getX( ), nodeB.getY( ) );
		gc.strokeLine( ptMid.getX( ), nodeA.getY( ), ptMid.getX( ), nodeB.getY( ) );
		gc.strokeLine( ptMid.getX( ), ptMid.getY( ), targetNode.getX( ), ptMid.getY( ) );


		Paint c = gc.getStroke( );
		gc.setStroke( Color.ORANGE );
		drawDebugPoint( gc, nodeA );
		drawDebugPoint( gc, nodeB );
		drawDebugPoint( gc, targetNode );
		drawDebugPoint( gc, ptMid );
		gc.setStroke( c );
	}

	public void drawResultConnection( GraphicsContext gc )
	{
		gc.strokeLine( nodeA.getX( ), nodeA.getY( ), nodeB.getX( ), nodeB.getY( ) );
	}

	private void drawDebugPoint( GraphicsContext gc, Point2D.Double pt )
	{
		gc.strokeLine( pt.getX( ), pt.getY( ), pt.getX( ) + 1, pt.getY( ) );
	}
}