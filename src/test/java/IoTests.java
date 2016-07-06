import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

public class IoTests extends TestCase
{

	public void testTest( ) throws Exception
	{
		assertTrue( true );
	}

	@Test( expected = IOException.class )
	public void testMatchManagerInvalidPath( ) throws Exception
	{
		assertTrue( true );
	}



}
