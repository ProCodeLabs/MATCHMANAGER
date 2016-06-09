package Core;

import Core.Data.Team;
import Core.Database.DbStorage;

import java.io.IOException;
import java.util.Vector;

public class MatchOverview
{
	private DbStorage _storage = new DbStorage( );
	private Vector<Team> _teams = new Vector<>( );

	public MatchOverview( )
	{
	}

	public void loadDatabase( String path ) throws IOException
	{

	}

	public void createDatabase( String name ) throws IOException
	{

	}


}
