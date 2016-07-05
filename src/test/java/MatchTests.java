import Core.MatchManager;
import junit.framework.TestCase;

public class MatchTests extends TestCase
{
	public void testMatchCount()
	{
		assertEquals( MatchManager.calculateMatchCount( 0 ), 0 );
		assertEquals( MatchManager.calculateMatchCount( 2 ), 1 );
		assertEquals( MatchManager.calculateMatchCount( 3 ), 2 );
		assertEquals( MatchManager.calculateMatchCount( 4 ), 3 );
		assertEquals( MatchManager.calculateMatchCount( 5 ), 4 );
	}


}
