package ui.StageCore.DialogStages.Helper.ResultCanvasHelper;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.geom.Point2D;

public class GraphConnection
{
	private Point2D.Double nodeA;
	private Point2D.Double nodeB;

	private Point2D.Double targetNode;

	public GraphConnection( double x1, double y1, double x2, double y2, double targetX, double targetY )
	{
		this.nodeA = new Point2D.Double( x1, y1 );
		this.nodeB = new Point2D.Double( x2, y2 );
		this.targetNode = new Point2D.Double( targetX, targetY );
	}


	/*
		-------|
			   |-------
		-------|
	 */
	public void drawConnection( GraphicsContext gc )
	{
		double dXMinA = targetNode.getX() - nodeA.getX();
		double dXMinB = targetNode.getX() - nodeB.getX();

		if( dXMinA > dXMinB )
		{
			gc.strokeLine( nodeA.getX( ), nodeA.getY( ), nodeA.getX( ) + dXMinA, nodeA.getY( ) );

		}
		else
		{
			gc.strokeLine( nodeA.getX( ), nodeA.getY( ), nodeA.getX( ) + dXMinB, nodeA.getY( ) );

		}
/*
		double dHalfMin = Math.min( dXMinA, dXMinB );

		double dHalfX = ( targetNode.getX( ) - dHalfMin ) / 2.0;


		gc.strokeLine( nodeA.getX( ), nodeA.getY( ), nodeA.getX( ) + dHalfX, nodeA.getY( ) );
		gc.strokeLine( nodeB.getX( ), nodeB.getY( ), nodeB.getX( ) + dHalfX, nodeB.getY( ) );
		gc.strokeLine( nodeA.getX( ) + dHalfX, nodeA.getY( ), nodeB.getX( ) + dHalfX, nodeB.getY( ) );

		gc.strokeLine( targetNode.getX( ) - dHalfX, targetNode.getY( ), targetNode.getX( ), targetNode.getY( ) );
*/


		gc.setStroke( Color.AQUA );
		gc.strokeLine( nodeA.getX( ), nodeA.getY( ), nodeA.getX( ) + 1, nodeA.getY( ) + 1 );
		gc.strokeLine( nodeB.getX( ), nodeB.getY( ), nodeB.getX( ) + 1, nodeB.getY( ) + 1 );
		gc.strokeLine( targetNode.getX( ), targetNode.getY( ), targetNode.getX( ) + 1, targetNode.getY( ) + 1 );
		//gc.strokeLine( nodeB.getX( ) + dHalfX, targetNode.getY( ), nodeB.getX( ) +  dHalfX + 1, targetNode.getY( ) );


	}

}
