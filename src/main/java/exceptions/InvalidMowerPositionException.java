package exceptions;

public class InvalidMowerPositionException extends RuntimeException {
  public InvalidMowerPositionException(String message) {
    super(message);
  }
}
