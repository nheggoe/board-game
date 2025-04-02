package edu.ntnu.idi.bidata.boardgame.backend.io.json;

/**
 * Custom exception used to indicate errors related to JSON handling operations. This exception is
 * typically thrown to encapsulate issues encountered during JSON parsing, serialization, or
 * deserialization processes.
 *
 * @author Nick Heggø
 * @version 2025.03.25
 */
public class JsonException extends RuntimeException {
  public JsonException(String message) {
    super(message);
  }
}
