package Common.LambdaExt;

import org.tmatesoft.sqljet.core.SqlJetException;

@FunctionalInterface
public interface ReflectFunction<T>
{
	T apply( ) throws SqlJetException;
}
