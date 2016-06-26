package ui.Helper;


import Core.GlobalInstance;
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
	final Logger logger = Logger.getLogger( this.getClass( ).getName( ) );


	UiStyleDesc desc;

	public UiBaseDialog( )
	{
		super( );
		initStyle( StageStyle.UNDECORATED );

		desc = new UiStyleDesc( getDialogPane( ), false );
		desc.setOnCloseButton( ( ) -> getDialogPane( ).getScene( ).getWindow( ).hide( ) );

		getDialogPane( ).setHeader( desc.getTitleBar( ) );
	}


	public void addDefaultCloseButtonHandler( )
	{
		addButtonEventHandler( ButtonType.CANCEL, e -> getDialogPane( ).getScene( ).getWindow( ).hide( ) );
	}

	public void addButtonEventHandler( ButtonType type, EventHandler<ActionEvent> handler )
	{
		assert getContentPane( ).lookupButton( type ) != null;

		getContentPane( ).lookupButton( type ).addEventHandler( ActionEvent.ACTION, handler );
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
			getDialogPane( ).setContent( loader.load( ) );
		}
		catch( IOException e )
		{
			GlobalInstance.getPrimaryStage( ).fireEvent(
					new UiEvent( UiEvent.CORE_EXCEPTION, "Failed to load resource! ( " + resourceId + " )" + e.getMessage( ) )
			);
		}
	}

	public DialogPane getContentPane( )
	{
		return ( DialogPane ) getDialogPane( ).getContent( );
	}


	public Node getElementById( String id )
	{
		try
		{
			return getNodeList( getContentPane( ) ).stream( )
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


}
