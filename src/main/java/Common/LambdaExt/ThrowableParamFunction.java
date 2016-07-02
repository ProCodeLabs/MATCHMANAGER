package Common.LambdaExt;

import org.tmatesoft.sqljet.core.SqlJetException;

@FunctionalInterface
public interface ThrowableParamFunction<T>
{
	void apply( T t ) throws SqlJetException;
}
