import java.io.*;

public class SWErrorHandler {

  SWErrorHandler( String status, String message )
      throws IOException {

    if( status.equals("CLI_ERROR") ) {
      Notify n = new Notify( message );
      try {
        n.send();
      } catch (IOException e ) {
        e.printStackTrace();
      }
    }

    if( status.equals("SECURITY_EMERGENCY") ) {
      Notify n = new Notify( message );
      try {
        n.send();
        System.out.println("SECURITY_EMERGENCY: " + status);
      } catch (IOException e ) {
        e.printStackTrace();
      }

      // Getting out for now
      System.exit(0);
    }
  }
}
