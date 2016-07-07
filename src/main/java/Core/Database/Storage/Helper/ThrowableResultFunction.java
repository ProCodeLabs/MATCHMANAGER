package Core.Database.Storage.Helper;


import org.tmatesoft.sqljet.core.SqlJetException;

public interface ThrowableResultFunction<T, R>
{
	R apply( T t ) throws SqlJetException;
}
