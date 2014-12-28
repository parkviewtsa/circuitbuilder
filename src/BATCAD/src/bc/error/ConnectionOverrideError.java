package bc.error;

public class ConnectionOverrideError extends Throwable {
  String message;
  
  public ConnectionOverrideError (String symbol) {
    message = "You have attempted to make a connection to a terminal that is" +
      "already connected.";
  }
}
