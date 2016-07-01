package Core.Data;


public class Match
{
	private int id;
	private Team[] teams = new Team[ 2 ];
	private Team result;


	public Match( )
	{

	}


	public boolean isDone()
	{
		return true;
	}


}
