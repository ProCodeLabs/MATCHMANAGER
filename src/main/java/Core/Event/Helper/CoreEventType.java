package Core.Event.Helper;


public class CoreEventType<T>
{
	private String name;

	public CoreEventType( final String name )
	{
		this.name = name;
	}

	@Override
	public String toString( )
	{
		return this.name;
	}
}
