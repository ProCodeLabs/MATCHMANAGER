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

public abstract class UiStage
{
	private static final ArrayList<String> activeContents = new ArrayList<>( );

	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );
	protected Stage stage;
	protected UiWindowContainer container;


	public abstract void showWindow();


	public /*virtual*/ void onSetPosition( )
	{
	}

	public /*virtual*/ void onCloseStage( )
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

			container = new UiWindowContainer( );
			{
				container.setCenter( title, resourceId );
			}
			stage.setScene( container.createScene( width, height ) );
		}

		stage.addEventFilter( WindowEvent.WINDOW_HIDING, e ->
		{
			logger.info( "Pane " + resourceId + " closed" );

			onCloseStage( );
			{
				activeContents.remove( resourceId );
			}
		} );

		showStage();

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

	public UiWindowContainer getContainer( )
	{
		return container;
	}
}
