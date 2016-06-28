package Common;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util
{
	public static String drainInputStream( InputStream stream ) throws IOException
	{
		StringBuilder sb = new StringBuilder( );
		BufferedReader br = new BufferedReader( new InputStreamReader( stream ) );
		String read;

		while( ( read = br.readLine( ) ) != null )
		{
			sb.append( read );
		}

		br.close( );
		return sb.toString( );
	}

}
