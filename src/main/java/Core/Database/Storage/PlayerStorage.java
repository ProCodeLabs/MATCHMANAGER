package Core.Database.Storage;

import Core.Data.Player;
import Core.Database.Storage.Helper.DbStorage;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class PlayerStorage extends DbStorage
{
	public PlayerStorage( SqlJetDb db )
	{
		super( db );
	}

	public Player addPlayer( String teamName, Player player ) throws SqlJetException
	{
		getDb( ).beginTransaction( SqlJetTransactionMode.WRITE );

		try
		{
			getTable( ).insert( "", "" );
		}
		finally
		{
			getDb( ).commit( );
		}

		return null;
	}


	@Override
	protected String getTableName( )
	{
		return "user_storage";
	}

	@Override
	protected String getResourceName( )
	{
		return "sql/playerStorage.sql";
	}


}