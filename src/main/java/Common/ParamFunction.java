package Common;

@FunctionalInterface
public interface ParamFunction<T>
{
	void apply( T t );
}
