package interactions.wait;

public class ChannelUnavailableException extends RuntimeException {

  public ChannelUnavailableException(String message) {
    super("[CHANNEL_UNAVAILABLE] " + message);
  }
}