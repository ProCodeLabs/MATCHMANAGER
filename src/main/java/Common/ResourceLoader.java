package Common;


public class ResourceLoader
{
	private static ResourceLoader _instance;

	private Class _appClass;


	public ResourceLoader( Class appClass )
	{
		assert _instance == null;

		_instance = this;
		_appClass = appClass;
	}


	public static Class getResourceClass( )
	{
		return getInstance( )._appClass;
	}

	public static ResourceLoader getInstance( )
	{
		assert _instance != null;

		return _instance;
	}
}
