import java.io.*;
import java.util.*;

public class Exceptions {

	public String[] loadArray( String FileName ) {

    File fi = null;
    FileReader in = null;
    String dstring = null;
    try {
      fi = new File( FileName );
      in = new FileReader(fi);
      int size = (int)fi.length();
      char[] data = new char[size];
      int chars_read = 0;

      // Read in the record
      while(chars_read < size) {
        chars_read += in.read(data, chars_read, size - chars_read);
      }
      dstring = new String( data );
    } catch (IOException e ) {
			System.out.println("Could not open input file");
			System.exit(0);
    }

		StringTokenizer sst =
          new StringTokenizer( dstring, "\n", false);

		String[] sa = new String[ sst.countTokens() ];

		int i = 0;

		while( sst.hasMoreTokens() ) {
			sa[ i ] = sst.nextToken();
			i++;
		}

		return sa;

	}

	public void go() {

		String[] exlist = loadArray( "./exceptions.dat" );
		String[] checklist = loadArray( "./checkout" );
		String[] endlist = new String[ 60000 ];

		int i = 0;
		int j = 0;
		int k = 0;
		int excount = exlist.length;
		int checkcount = checklist.length;
		int endcount = 0;
		int bookmark = 0;
		boolean foundflag = false;

		while( i < 60000 ) {
			endlist[ i ] = null;
			i++;
		}

		i = 0;

		while( i < checkcount ) {
			while( j < excount ) {
				if( exlist[j].equals( checklist[i] )) {
					foundflag = true;
					break;
				} 
				j++;
			}

			if( !foundflag ) {
				endlist[ k++ ] = checklist[i] + "\n";
			} else {
				System.out.println("Found: " + checklist[i]);
			}
			foundflag = false;
			j = 0;
			i++;
		}

		File f = new File( "./finalout" );
    FileOutputStream fos = null;

    try {
      fos = new FileOutputStream( f );
    } catch (IOException e) {
      e.printStackTrace();
    }

    j = 0;

    while( j < endlist.length && endlist[ j ] != null ) {
 
      byte[] buffer = endlist[ j ].getBytes();

      try {
        fos.write( buffer );
        j++;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

	}

	public static void main( String args[] ) {
		Exceptions sl = new Exceptions();
		sl.go();
	}

}
