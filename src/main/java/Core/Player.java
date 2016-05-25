package Core;

/**
 * Created by JOHNY on 25.05.2016.
 */
public class Player
{
	private String _surname;
	private String _lastname;
	private String _nickname;
	private String _image;

	public Player( String _surname, String _lastname, String _nickname, String _image )
	{
		this._surname = _surname;
		this._lastname = _lastname;
		this._nickname = _nickname;
		this._image = _image;
	}

	//region GETTER UND SETTER
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
