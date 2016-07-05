package ui.Container;


import Common.GlobalInstance;
import Core.Event.Manager.CoreEventDispatcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import Core.Event.Manager.CoreEvent;
import ui.Helper.UiStyleDesc;

import java.io.IOException;
import java.util.logging.Logger;


public class UiBaseDialog<R> extends Dialog<R>
{
	private final Logger logger = Logger.getLogger( this.getClass( ).getName( ) );

	private UiContainerHelper uiHelper;
	private UiStyleDesc desc;



	public UiBaseDialog( )
	{
		super( );
		initStyle( StageStyle.UNDECORATED );
		initModality( Modality.WINDOW_MODAL );

		desc = new UiStyleDesc( getDialogPane( ), false );
		{
			desc.setOnCloseButton( ( ) -> close( ) );
		}
		uiHelper = new UiContainerHelper( getDialogPane() );

		getDialogPane( ).setHeader( desc.getTitleBar( ) );
	}


	protected /*virtual*/ void onLoad( FXMLLoader loader )
	{
	}

	public void addButtonEventHandler( ButtonType type, EventHandler<ActionEvent> handler )
	{
		assert getDialogPane( ).lookupButton( type ) != null;
		getDialogPane( ).lookupButton( type ).addEventFilter( ActionEvent.ACTION, handler );
	}

	public void setDialogTitle( String text )
	{
		desc.setTitle( text );
		setHeaderText( text );
	}

	public void setContent( String resourceId )
	{
		FXMLLoader loader = new FXMLLoader( GlobalInstance.getResource( resourceId ) );

		try
		{
			onLoad( loader );

			Object pane = loader.load( );

			if( !( pane instanceof DialogPane ) )
			{
				throw new IOException( "resource is not a dialog pane! " + resourceId );
			}

			DialogPane dialogPane = ( DialogPane ) pane;
			{
				setDialogTitle( dialogPane.getHeaderText( ) );
				getDialogPane( ).setContent( dialogPane.getContent( ) );

				dialogPane.getButtonTypes( ).forEach(
						b -> getDialogPane( ).getButtonTypes( ).add( b )
				);
			}
		}
		catch( IOException e )
		{
			CoreEventDispatcher.fireEvent( CoreEvent.CORE_EXCEPTION, e );
		}
	}


	public UiContainerHelper getHelper()
	{
		return uiHelper;
	}


}
