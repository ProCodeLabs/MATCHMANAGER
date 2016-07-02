package Core.Data;


public class Player
{
	private long id;

	private String forename;
	private String surname;

	public Player( long id, Player player )
	{
		this.id = id;
		this.forename = player.getForename( );
		this.surname = player.getSurname( );
	}

	public Player( long id, String forename, String surname )
	{
		this.id = id;
		this.forename = forename;
		this.surname = surname;
	}

	public Player( String forename, String surname )
	{
		this.forename = forename;
		this.surname = surname;
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
}
