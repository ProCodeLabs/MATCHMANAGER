package Core.Data;


public class Match
{
	private long id;
	private long teamAId;
	private long teamBId;
	private long resultTeam;


	public Match( long id, Team teamA, Team teamB, Team resultTeam )
	{
		this.id = id;
		this.teamAId = teamA.getId( );
		this.teamBId = teamB.getId( );
		this.resultTeam = resultTeam.getId( );
	}

	public Match( long id, long teamAId, long teamBId, long resultTeam )
	{
		this.id = id;
		this.teamAId = teamAId;
		this.teamBId = teamBId;
		this.resultTeam = resultTeam;
	}

	public long getTeamAId( )
	{
		return teamAId;
	}

	public long getTeamBId( )
	{
		return teamBId;
	}

	public long getResultTeamId( )
	{
		return resultTeam;
	}

}
