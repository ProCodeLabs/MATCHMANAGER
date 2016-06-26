import Core.MatchManager;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

public class IoTests extends TestCase
{

	public void testTest( ) throws Exception
	{
		assertTrue( true );
	}

	@Test(expected = IOException.class)
	public void testMatchManagerInvalidPath() throws Exception
	{
		MatchManager m = new MatchManager();
		{
			m.loadDatabase( "asdasdasd" );
		}
		assertTrue( true );
	}








}
