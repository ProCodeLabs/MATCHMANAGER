package Core;

import Core.Data.Match;
import Core.Database.DbStorage;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.util.List;
import java.util.logging.Logger;


public class MatchManager extends DbStorage
{
	final Logger logger = Logger.getLogger( this.getClass( ).getName( ) );

	public MatchManager( SqlJetDb db )
	{
		super( db );
	}



	public void Close()
	{

	}


	public List<Match> getMatches()
	{
		return null;

	}



}
