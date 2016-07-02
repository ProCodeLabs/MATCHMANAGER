package Common.LambdaExt;

@FunctionalInterface
public interface ParamFunction<T>
{
	void apply( T t );
}
