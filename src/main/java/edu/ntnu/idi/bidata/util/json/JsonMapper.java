package edu.ntnu.idi.bidata.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Singleton utility class that provides a single instance of {@link ObjectMapper}. This class
 * ensures that the same instance of ObjectMapper is used throughout the application, promoting
 * efficiency and consistency in JSON operations.
 *
 * @author Nick Heggø
 * @version 2025.03.12
 */
public class JsonMapper {

  private static ObjectMapper objectMapper;

  private JsonMapper() {}

  public static ObjectMapper getInstance() {
    if (objectMapper == null) {
      objectMapper = new ObjectMapper();
    }
    return objectMapper;
  }
}
