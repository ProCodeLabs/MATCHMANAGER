package Core.Data;

public class Team
{
	private long id;
	private String teamName;

	public Team( long id, String teamName )
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

	public long getId( )
	{
		return id;
	}

}
