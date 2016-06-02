package ui.Helper;


import java.util.function.Consumer;

public class UIHelper
{
	public static <T> T buildComponent( T node, Consumer<T> initializer )
	{
		{
			initializer.accept( node );
		}
		return node;
	}


}
