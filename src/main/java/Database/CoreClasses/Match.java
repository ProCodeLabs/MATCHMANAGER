package Database.CoreClasses;

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
	public String getTeamOne( )
	{
		return teams[ 0 ].getName();
	}

	public String getTeamTwo( )
	{
		return teams[ 1 ].getName();
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
