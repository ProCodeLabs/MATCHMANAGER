package Database.CoreClasses;

public class Team
{
	private String _name;

	public Team( String _name )
	{
		this._name = _name;
	}

	//region GETTER AND SETTER
	public String getName( )
	{
		return _name;
	}

	public void setName( String _name )
	{
		this._name = _name;
	}
	//endregion
}
