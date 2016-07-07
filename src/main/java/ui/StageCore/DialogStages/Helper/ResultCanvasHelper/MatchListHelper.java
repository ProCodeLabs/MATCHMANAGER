package ui.StageCore.DialogStages.Helper.ResultCanvasHelper;


import Common.LambdaExt.ParamFunction;
import Core.Data.Match;

import java.util.List;

public class MatchListHelper
{
	private List<Match> matchList;

	public MatchListHelper( List<Match> matchList )
	{
		this.matchList = matchList;
	}

	public void forReverse( ParamFunction<Match> function )
	{
		for( int i = matchList.size( ) - 1; i >= 0; i-- )
		{
			function.apply( matchList.get( i ) );
		}
	}

	public GraphNode createBinaryTree( )
	{
		return sortTree( 0, matchList.size( ) - 1 );
	}

	private GraphNode sortTree( int start, int end )
	{
		if( start > end )
		{
			return null;
		}

		int mid = ( start + end ) / 2;
		GraphNode node = new GraphNode( matchList.get( mid ) );
		{
			node.left = sortTree( start, mid - 1 );
			node.right = sortTree( mid + 1, end );
		}
		return node;
	}
}
