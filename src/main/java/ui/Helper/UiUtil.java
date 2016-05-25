package ui.Helper;


import java.util.function.*;

public class UiUtil
{
	public static <T> T buildComponent(T node, Consumer<T> initializer) {
		{
			initializer.accept( node );
		}
		return node;
	}



}
