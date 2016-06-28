package Core.Database;

import Core.Database.Storage.MatchStorage;
import Core.Database.Storage.PlayerStorage;
import Core.Database.Storage.TeamStorage;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class DbStorage
{
	private SqlJetDb db;
	private PlayerStorage playerStorage = new PlayerStorage( );
	private MatchStorage matchStorage = new MatchStorage( );
	private TeamStorage teamStorage = new TeamStorage( );



}
