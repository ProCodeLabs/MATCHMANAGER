package Common;

import javafx.concurrent.Task;

import java.util.concurrent.Callable;


public class Util
{
	public static <T> Task<T> create_task( Callable<T> callable) {
		return new Task<T>() {
			@Override
			public T call() throws Exception {
				return callable.call();
			}
		};
	}


}
