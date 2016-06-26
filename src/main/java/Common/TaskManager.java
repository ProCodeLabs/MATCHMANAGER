package Common;

import javafx.concurrent.Task;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;


public class TaskManager
{
	public static <T> void runTask( Callable<T> callable )
	{
		ForkJoinPool.commonPool( ).execute( new Task<T>( )
		{
			@Override
			public T call( ) throws Exception
			{
				return callable.call( );
			}
		} );
	}

	public static void runTask( Runnable runnable )
	{
		ForkJoinPool.commonPool( ).execute( runnable );
	}
}
