package Core.Data;


public class Player
{
	private long id;
	private long teamId;
	private String forename;
	private String surname;

	public Player( long id, Player player )
	{
		this.id = id;
		this.teamId = player.getTeamId( );
		this.forename = player.getForename( );
		this.surname = player.getSurname( );
	}

	public Player( long id, long teamId, String forename, String surname )
	{
		this.id = id;
		this.teamId = teamId;
		this.forename = forename;
		this.surname = surname;
	}

	public Player( String forename, String surname )
	{
		this.forename = forename;
		this.surname = surname;
	}


	public long getId( )
	{
		return id;
	}

	public long getTeamId( )
	{
		return teamId;
	}

	public String getForename( )
	{
		return forename;
	}

	public String getSurname( )
	{
		return surname;
	}

	public String getFullName( )
	{
		return getForename( ) + " " + getSurname( );
	}

	@Override
	public String toString( )
	{
		return getFullName( );
	}
}
