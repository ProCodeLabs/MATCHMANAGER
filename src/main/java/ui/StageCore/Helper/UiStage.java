package ui.StageCore.Helper;

import Common.GlobalInstance;
import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import ui.Container.UiWindowContainer;

import java.util.ArrayList;

public abstract class UiStage implements IUiStage
{
	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );
	private static final ArrayList<String> activeContents = new ArrayList<>( );
	protected Stage stage;


	public /*virtual*/ void onSetPosition( )
	{
	}


	public boolean createStage( String title, String resourceId, long width, long height )
	{
		if( checkActiveResource( resourceId ) )
		{
			return false;
		}

		stage = new Stage( );
		{
			stage.initStyle( StageStyle.UNDECORATED );

			UiWindowContainer container = new UiWindowContainer( );
			{
				container.setCenter( title, resourceId );
			}
			stage.setScene( container.createScene( width, height ) );
		}

		stage.addEventFilter( WindowEvent.WINDOW_HIDING, e ->
		{
			logger.info( "Pane " + resourceId + " closed" );
			activeContents.remove( resourceId );
		} );

		return true;
	}

	public void showStage( )
	{
		stage.show( );
		onSetPosition( );
	}

	private boolean checkActiveResource( String resourceId )
	{
		if( activeContents.contains( resourceId ) )
		{
			logger.warning( "Pane " + resourceId + " is already active!" );
			return true;
		}

		logger.info( "Open pane" + resourceId );

		activeContents.add( resourceId );

		return false;
	}

	public Pair<Double, Double> getPrimaryWindowPos( )
	{
		return new Pair<>(
				GlobalInstance.getPrimaryStage( ).getX( ),
				GlobalInstance.getPrimaryStage( ).getY( )
		);
	}

	public Stage getStage( )
	{
		return stage;
	}
}
