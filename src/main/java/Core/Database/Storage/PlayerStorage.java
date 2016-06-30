package Core.Database.Storage;


import Core.Database.Storage.Helper.StorageHelper;
import org.tmatesoft.sqljet.core.SqlJetException;

public class PlayerStorage extends StorageHelper
{
	static final String TABLE_NAME = "player_tbl";
	static final String RESOURCE_ID = "sql/playerStorage.sql";

	@Override
	protected void onInitializeStorage( ) throws SqlJetException
	{
		createTableFromResource( RESOURCE_ID );
	}

	@Override
	public boolean tableExists( )
	{
		return checkTableExists( TABLE_NAME );
	}
}

