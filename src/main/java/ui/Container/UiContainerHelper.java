package ui.Container;

import Common.UtilLogger.ILogger;
import Common.UtilLogger.LoggerFactory;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class UiContainerHelper
{
	private final ILogger logger = LoggerFactory.createLogger( getClass() );
	private Parent parent;


	public UiContainerHelper( Parent parent )
	{
		this.parent = parent;
	}

	public Node getElementById( String id )
	{
		try
		{
			return getNodeList( parent )
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


}
