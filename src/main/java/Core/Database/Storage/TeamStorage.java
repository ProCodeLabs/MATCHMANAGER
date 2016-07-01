package Core.Database.Storage;


import Core.Data.Team;
import Core.Database.Storage.Helper.DbStorage;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class TeamStorage extends DbStorage
{
	public TeamStorage( SqlJetDb db )
	{
		super( db );
	}

	public Team createTeam( String teamName )
	{
		//return getTable().lookup( teamName );
	}

	public Team getTeam( String teamName )
	{
		return null;
	}


	@Override
	protected String getTableName( )
	{
		return "team_storage";
	}

	@Override
	protected String getResourceName( )
	{
		return "sql/teamStorage.sql";
	}
}
