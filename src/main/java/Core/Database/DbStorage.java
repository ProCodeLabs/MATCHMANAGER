package Core.Database;

import Core.Helper.CoreException;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class DbStorage
{
	private SqlJetDb db;


	public DbStorage( SqlJetDb db )
	{
		this.db = db;

	}

	public void fetchData( ) throws CoreException
	{

	}

	public boolean isEmptyStorage( )
	{
		return true; //playerStorage.tableExists( ) && matchStorage.tableExists( ) && teamStorage.tableExists( );
	}




}
