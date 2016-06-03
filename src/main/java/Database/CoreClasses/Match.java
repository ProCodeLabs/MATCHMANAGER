package Database.CoreClasses;

public class Match
{

	private int _id;
	private String _date;
	private Team _teamOne;
	private Team _teamTwo;

	public Match( String _date, Team one, Team two )
	{
		this._id = Database.dbc.getNextId( "Match" );
		this._date = _date;
		this._teamOne = one;
		this._teamTwo = two;
		Database.dbc.addMatch( this );
	}

	public Match( int _id, String _date, String teamOne, String teamTwo )
	{
		this._id = _id;
		this._date = _date;
		this._teamOne.setName( teamOne );
		this._teamTwo.setName( teamTwo );
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
		return _teamOne.getName( );
	}

	public String getTeamTwo( )
	{
		return _teamTwo.getName( );
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
