package Core.Database.Storage;


import Core.Database.Storage.Helper.IStorage;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class PlayerStorage implements IStorage
{
	static final String RESOURCE_ID = "sql/userStorage.sql";

	@Override
	public void initializeStorage( SqlJetDb db ) throws SqlJetException
	{
		db.createTable( "" );
	}
}

