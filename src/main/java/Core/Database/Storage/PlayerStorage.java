package Core.Database.Storage;

import Core.Data.Player;
import Core.Database.Storage.Helper.SingleStorage;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class PlayerStorage extends SingleStorage
{
	public PlayerStorage( SqlJetDb db )
	{
		super( db );
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

}