package bc.error;

public class InvalidPolarityError extends Throwable {
  String message;
  
  public InvalidPolarityError (String symbol) {
    message = "You have attempted to align component" + symbol + "in reverse.";
  }
}
