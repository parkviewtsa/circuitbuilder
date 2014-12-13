package bc.error;

public class InvalidPolarityError {
  public InvalidPolarityError (String symbol) {
    message = "You have attempted to align component" + symbol + "in reverse.";
  }
}
