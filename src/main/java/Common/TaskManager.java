package Common;

import javafx.concurrent.Task;

import java.util.concurrent.*;


public class TaskManager
{
	private static final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor( );

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

	public static void runScheduledTask( long delay, Runnable runnable )
	{
		scheduledExecutor.schedule( runnable, delay, TimeUnit.SECONDS );
	}
}
