package Database.CoreClasses;

public class Match
{

	private int _id;
	private String _date;
	private Team[] _teams = new Team[ 2 ];

	public Match( String _date, Team one, Team two )
	{
		this._id = Database.dbc.getNextId( "Match" );
		this._date = _date;
		this._teams[ 0 ] = one;
		this._teams[ 1 ] = two;
		Database.dbc.addMatch( this );
	}

	public Match( int _id )
	{
		this._id = _id;
	}

	//region GETTER AND SETTER
	public int getId( )
	{
		return _id;
	}

	public void setId( int _id )
	{
		this._id = _id;
	}

	public String getTeamOne( )
	{
		return _teams[ 0 ].getName( );
	}

	public String getTeamTwo( )
	{
		return _teams[ 1 ].getName( );
	}

	public String getDate( )
	{
		return _date;
	}

	public void setDate( String _date )
	{
		this._date = _date;
	}
	//endregion
}
