package Common;

import javafx.concurrent.Task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by JOHNY on 03.06.2016.
 */
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


}
