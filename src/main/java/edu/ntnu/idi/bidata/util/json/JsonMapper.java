package edu.ntnu.idi.bidata.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Singleton utility class that provides a single instance of {@link ObjectMapper}. This class
 * ensures that the same instance of ObjectMapper is used throughout the application, promoting
 * efficiency and consistency in JSON operations.
 *
 * @author Nick Heggø
 * @version 2025.03.14
 */
public class JsonMapper {

  private static ObjectMapper objectMapper;

  private JsonMapper() {}

  /**
   * Provides a singleton instance of {@link ObjectMapper} for use throughout the application. If
   * the instance does not already exist, it is created and returned.
   *
   * @return a shared instance of {@link ObjectMapper}, ensuring consistent and efficient JSON
   *     operations.
   */
  public static ObjectMapper getInstance() {
    if (objectMapper == null) {
      objectMapper =
          new ObjectMapper()
              .registerModule(new JavaTimeModule())
              .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    return objectMapper;
  }
}
