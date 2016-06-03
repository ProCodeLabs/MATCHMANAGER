package ui.Helper;


import java.util.function.Consumer;

public class UiHelper
{
	public static <T> T buildComponent( T node, Consumer<T> initializer )
	{
		{
			initializer.accept( node );
		}
		return node;
	}


}
