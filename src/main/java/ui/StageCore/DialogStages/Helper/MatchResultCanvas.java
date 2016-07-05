package ui.StageCore.DialogStages.Helper;

import Core.Data.Team;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class MatchResultCanvas extends Canvas
{
	public MatchResultCanvas(  )
	{
		super( 300, 200 );
		widthProperty( ).addListener( observable -> redraw( ) );
		heightProperty( ).addListener( observable -> redraw( ) );
	}

	public void addTeams( List<Team> teams )
	{
		redraw( );
	}


	public void redraw( )
	{
		//System.out.println( "redraw" );
		GraphicsContext context = getGraphicsContext2D( );
		context.setFill( Color.BLUE );
		context.clearRect( 0, 0, getWidth( ), getHeight( ) );


		//context.moveTo( 50, 50 );
		//context.rect( 25, 25, 150, 150 );

		context.fillText( "Text centered on your Canvas",
						  Math.round( getWidth( ) / 2 ),
						  Math.round( getHeight( ) / 2 )
		);
	}


	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public double prefWidth(double height) {
		return getWidth();
	}

	@Override
	public double prefHeight(double width) {
		return getHeight();
	}


}
