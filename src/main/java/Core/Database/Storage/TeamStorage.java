package Core.Database.Storage;


import Core.Database.Storage.Helper.StorageHelper;
import org.tmatesoft.sqljet.core.SqlJetException;

public class TeamStorage extends StorageHelper
{
	public static final String TABLE_NAME = "team_tbl";

	@Override
	protected void onInitializeStorage( ) throws SqlJetException
	{

	}


	@Override
	public boolean tableExists( )
	{
		return checkTableExists( TABLE_NAME );
	}
}
