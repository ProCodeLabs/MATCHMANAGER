package Common;

import javafx.concurrent.Task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TaskManager
{
	static final ExecutorService executor = Executors.newFixedThreadPool( 2 );

	public static <T> void runTask( Callable< T > callable )
	{
		executor.execute( new Task< T >(){
			@Override
			public T call() throws Exception {
				return callable.call();
			}
		});
	}


	public static void runUiTask( Runnable runnable)
	{
		executor.execute( runnable );
	}


}
