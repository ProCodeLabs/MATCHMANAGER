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
	private static final ArrayList<UiStage> activeContents = new ArrayList<>( );

	private final ILogger logger = LoggerFactory.createLogger( getClass( ) );
	protected Stage stage;
	protected UiWindowContainer container;


	public abstract void showWindow( );

	public abstract String getResourceId( );

	public /*virtual*/ void onSetPosition( )
	{
	}

	public /*virtual*/ void onCloseStage( )
	{
	}


	public boolean createStage( String title, double width, double height )
	{
		if( !checkActiveResource( ) )
		{
			return false;
		}

		stage = new Stage( );
		{
			stage.initStyle( StageStyle.UNDECORATED );

			container = new UiWindowContainer( );
			{
				container.setCenter( title, getResourceId( ) );
			}
			stage.setScene( container.createScene( width, height ) );
		}

		stage.addEventFilter( WindowEvent.WINDOW_HIDING, e ->
		{
			logger.info( "Pane " + getResourceId( ) + " closed" );

			onCloseStage( );
			{
				activeContents.remove( this );
			}
		} );

		showStage( );

		return true;
	}

	public void showStage( )
	{
		stage.show( );
		onSetPosition( );
	}

	private boolean checkActiveResource( )
	{
		if( getUiStage( getResourceId( ) ) != null )
		{
			logger.warning( "Pane " + getResourceId( ) + " is already active!" );
			return false;
		}

		logger.info( "Open pane" + getResourceId( ) );

		return activeContents.add( this );
	}


	public static UiStage getUiStage( String resourceName )
	{
		for( UiStage i : activeContents )
		{
			if( i.getResourceId( ).equals( resourceName ) )
			{
				return i;
			}
		}
		return null;
	}


	protected Pair<Double, Double> getPrimaryWindowPos( )
	{
		return new Pair<>(
				GlobalInstance.getPrimaryStage( ).getX( ),
				GlobalInstance.getPrimaryStage( ).getY( )
		);
	}

	protected Pair<Double, Double> getPrimaryWindowSize( )
	{
		return new Pair<>(
				GlobalInstance.getPrimaryStage( ).getWidth( ),
				GlobalInstance.getPrimaryStage( ).getHeight( )
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


	public static void closeActiveDialog( String resourceId )
	{
		UiStage stage = getUiStage( resourceId );
		if( stage != null )
		{
			stage.getStage( ).close( );
		}
	}
}
