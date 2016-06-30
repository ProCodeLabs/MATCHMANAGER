package Core.Data;


import java.util.Date;

public class Match
{
	private int id;
	private Date date;
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
