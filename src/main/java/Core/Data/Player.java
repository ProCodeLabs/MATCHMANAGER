package Core.Data;


public class Player
{
	private int id;

	private String forename;
	private String surname;

	public Player( int id, String forename, String surname )
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

	public String getForename()
	{
		return forename;
	}

	public String getSurname()
	{
		return surname;
	}
}
