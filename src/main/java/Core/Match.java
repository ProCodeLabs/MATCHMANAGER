package Core;

public class Match
{
	private String _date;

	private Team[] teams = new Team[ 2 ];

	public Match( String _date, Team one, Team two )
	{
		this._date = _date;
		teams[ 0 ] = one;
		teams[ 1 ] = two;
	}

	//region GETTER AND SETTER
	public Team getTeamOne( )
	{
		return teams[ 0 ];
	}

	public Team getTeamTwo( )
	{
		return teams[ 1 ];
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
