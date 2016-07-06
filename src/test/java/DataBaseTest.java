import Core.Data.Player;
import Core.Data.Team;
import Core.Database.StorageManager;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

public class DataBaseTest extends TestCase
{
	@Test
	public void testWriteToDataBaseTeam( )
	{
		String s = "dbtest";

		try
		{
			StorageManager.createDatabase( s ).get( );
			StorageManager.loadDatabase( s ).thenApply( m -> {
				m.addTeam( "neues team" ).thenApply( t -> {
					assertEquals( "neues team", t.getTeamName( ) );

					try
					{
						StorageManager.deleteDatabase( s ).get( );
					}
					catch( Exception e )
					{
						throw new CompletionException( e );
					}

					return null;
				} ).exceptionally( e -> {
					assertTrue( false );
					return null;
				} );

				return null;
			} );
		}
		catch( CompletionException | InterruptedException | ExecutionException e )
		{
			assertTrue( false );
		}

	}

	@Test
	public void testWriteToDataBasePlayer( )
	{
		String s = "dbtest";

		Player ply = new Player( "Test", "Player" );

		try
		{
			StorageManager.createDatabase( s ).get( );
			StorageManager.loadDatabase( s ).thenApply( m -> {
				m.addPlayer( "neues Team", ply ).thenApply( p -> {
					assertEquals( "Test", p.getForename( ) );

					try
					{
						StorageManager.deleteDatabase( s ).get( );
					}
					catch( Exception e )
					{
						throw new CompletionException( e );
					}

					return null;
				} ).exceptionally( e -> {
					assertTrue( false );
					return null;
				} );

				return null;
			} );
		}
		catch( CompletionException | InterruptedException | ExecutionException e )
		{
			assertTrue( false );
		}

	}

	@Test
	public void testWriteToDataBaseMatch( )
	{
		String s = "dbtest";

		Team t1 = new Team( "T1" );
		Team t2 = new Team( "T2" );

		try
		{
			StorageManager.createDatabase( s ).get( );
			StorageManager.loadDatabase( s ).thenApply( m -> {
				m.addMatch( t1,t2,t1 ).thenApply( t -> {
					//TODO: ASSERT TEST MIT ID aber kp wie
					try
					{
						StorageManager.deleteDatabase( s ).get( );
					}
					catch( Exception e )
					{
						throw new CompletionException( e );
					}

					return null;
				} ).exceptionally( e -> {
					assertTrue( false );
					return null;
				} );

				return null;
			} );
		}
		catch( CompletionException | InterruptedException | ExecutionException e )
		{
			assertTrue( false );
		}

	}

}
