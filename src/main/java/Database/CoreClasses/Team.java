package Database.CoreClasses;

public class Team
{
	private int _id=0;
	private String _name;

	public Team( String _name )
	{
		this._id = Database.dbc.getNextId( "Team" );
		this._name = _name;
		Database.dbc.addTeam( this );
	}

	public Team( int _id, String _name )
	{
		this._id = _id;
		this._name = _name;
	}

	public Team( int _id )
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
