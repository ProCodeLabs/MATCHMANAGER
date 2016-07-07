package ui.StageCore.DialogStages.Helper.ResultCanvasHelper;


import Core.Data.Match;
import Core.Data.Player;

public class GraphNode
{
	public GraphNode left;
	public GraphNode right;

	private Match matchData;
	private Player playerData;

	public GraphNode( Match matchData )
	{
		this.matchData = matchData;
	}

	public GraphNode( Player playerData )
	{
		this.playerData = playerData;
	}
}
