package Common;


public class ResourceLoader
{
	static ResourceLoader THIS_INSTANCE;

	Class _appClass;



	public ResourceLoader( Class appClass )
	{
		assert THIS_INSTANCE == null;

		THIS_INSTANCE = this;
		_appClass = appClass;
	}



	public  static Class getResourceClass()
	{
		return getInstance()._appClass;
	}

	public static ResourceLoader getInstance()
	{
		assert THIS_INSTANCE != null;

		return THIS_INSTANCE;
	}
}
