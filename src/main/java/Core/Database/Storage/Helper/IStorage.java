package Core.Database.Storage.Helper;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public interface IStorage
{
	void initializeStorage( SqlJetDb db ) throws SqlJetException;

}
