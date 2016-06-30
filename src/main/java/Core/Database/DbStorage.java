package Core.Database;

import Core.Database.Storage.MatchStorage;
import Core.Database.Storage.PlayerStorage;
import Core.Database.Storage.TeamStorage;
import Core.Helper.CoreException;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class DbStorage
{
	private SqlJetDb db;

	protected PlayerStorage playerStorage = new PlayerStorage( );
	protected MatchStorage matchStorage = new MatchStorage( );
	protected TeamStorage teamStorage = new TeamStorage( );


	public DbStorage( SqlJetDb db )
	{
		this.db = db;
	}

	public void fetchData( ) throws CoreException
	{

	}

	protected boolean isEmptyStorage( )
	{
		return playerStorage.tableExists( ) && matchStorage.tableExists( ) && teamStorage.tableExists( );
	}

	protected void checkDatabaseIntegrity( ) throws CoreException
	{
		if( playerStorage.tableExists( ) )
		{
			if( matchStorage.tableExists( ) )
			{
				if( teamStorage.tableExists( ) )
				{
					return;
				}
				else
				{
					throw new CoreException( "team storage does not exist!" );
				}
			}
			else
			{
				throw new CoreException( "match storage does not exist!" );
			}
		}
		else
		{
			throw new CoreException( "player storage does not exist!" );
		}
	}


}
