package ui.StageCore.DialogStages.Helper;

import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import Core.Data.Team;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class MatchResultCanvas extends Canvas
{
	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );

	public MatchResultCanvas( double width, double height )
	{
		super( width, height );
	}

	public void addTeams( List<Team> teams )
	{
		redraw( );
	}

	public void fillInvalidate( )
	{
		GraphicsContext gc = getGraphicsContext2D( );

		gc.setFill( Color.ORANGE );
		gc.setGlobalAlpha( 0.8 );
		gc.fillRect( 0, 0, getWidth( ), getHeight( ) );
		gc.setGlobalAlpha( 1.0 );
	}

	public void redraw( )
	{
		logger.info( "Redrawing: size ( w: " + getWidth( ) + " h: " + getHeight( ) + " )" );


		GraphicsContext gc = getGraphicsContext2D( );
		gc.clearRect( 0, 0, getWidth( ), getHeight( ) );


		gc.setFill( Color.BLUE );
		gc.fillRect( 75, 75, 100, 100 );


	}


	@Override
	public boolean isResizable( )
	{
		return true;
	}

	@Override
	public double prefWidth( double height )
	{
		return getWidth( );
	}

	@Override
	public double prefHeight( double width )
	{
		return getHeight( );
	}


}
