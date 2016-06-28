package ui.Helper;


import Common.GlobalInstance;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.logging.Logger;


public class UiBaseDialog<R> extends Dialog<R>
{
	private final Logger logger = Logger.getLogger( this.getClass( ).getName( ) );

	private UiStyleDesc desc;

	public UiBaseDialog( )
	{
		super( );
		initStyle( StageStyle.UNDECORATED );

		desc = new UiStyleDesc( getDialogPane( ), false );
		{
			desc.setOnCloseButton( ( ) -> close( ) );
		}

		getDialogPane( ).setHeader( desc.getTitleBar( ) );
	}

	public void addButtonEventHandler( ButtonType type, EventHandler<ActionEvent> handler )
	{
		assert getDialogPane( ).lookupButton( type ) != null;

		getDialogPane( ).lookupButton( type ).addEventHandler( ActionEvent.ACTION, handler );
	}

	public Node getElementById( String id )
	{
		try
		{
			return getNodeList( getDialogPane( ) )
					.stream( )
					.filter( n -> n.getId( ) != null && n.getId( ).equals( id ) )
					.findFirst( )
					.get( );
		}
		catch( NoSuchElementException e )
		{
			logger.info( "No element found with id " + id + " > " + e.getMessage( ) );
			return null;
		}
	}

	private ArrayList<Node> getNodeList( Parent parent )
	{
		ArrayList<Node> nodes = new ArrayList<>( 16 );
		{
			getElementsRecursive( parent, nodes );
		}
		return nodes;
	}

	private void getElementsRecursive( Parent parent, ArrayList<Node> nodes )
	{
		parent.getChildrenUnmodifiable( ).forEach( i -> {
			nodes.add( i );

			if( i instanceof Parent )
			{
				getElementsRecursive( ( Parent ) i, nodes );
			}
		} );
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
			GlobalInstance.getPrimaryStage( ).fireEvent(
					new UiEvent( UiEvent.CORE_EXCEPTION, "Failed to load resource! ( " + resourceId + " )" + e.getMessage( ) )
			);
		}
	}
}
