package Database.CoreClasses;

public class Player
{
	private int _id=0;
	private String _surname;
	private String _lastname;
	private String _nickname;
	private String _image;

	public Player( String _surname, String _lastname, String _nickname, String _image )
	{
		this._id = Database.dbc.getNextId( "Player" );
		this._surname = _surname;
		this._lastname = _lastname;
		this._nickname = _nickname;
		this._image = _image;
		Database.dbc.addPlayer( this );
	}

	public Player( int _id )
	{
		this._id = _id;
	}

	//region GETTER AND SETTER
	public boolean hasID( )
	{
		if( this._id != 0 )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getId( )
	{
		return _id;
	}

	public void setId( int _id )
	{
		this._id = _id;
	}

	public String getLastname( )
	{
		return _lastname;
	}

	public void setLastname( String _lastname )
	{
		this._lastname = _lastname;
	}

	public String getNickname( )
	{
		return _nickname;
	}

	public void setNickname( String _nickname )
	{
		this._nickname = _nickname;
	}

	public String getImage( )
	{
		return _image;
	}

	public void setImage( String _image )
	{
		this._image = _image;
	}

	public String getSurname( )
	{
		return _surname;
	}

	public void setSurname( String _surname )
	{
		this._surname = _surname;
	}
	//endregion
}
