package Core.Data;

public class Team
{
	private int id;
	private String teamName;

	public Team( int id, String teamName )
	{
		this.id = id;
		this.teamName = teamName;
	}

	public Team( String teamName )
	{
		this.teamName = teamName;
	}


	public String getTeamName( )
	{
		return teamName;
	}


}
