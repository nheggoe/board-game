package edu.ntnu.idi.bidata.util;

/**
 * Utility class for generating file paths for JSON resources based on object instances or class
 * definitions. Provides methods to generate paths in a structured manner, targeting files stored in
 * the "src/main/resources/json" directory.
 *
 * @author Nick Heggø
 * @version 2025.03.12
 */
public class JsonUrlUtil {

  private JsonUrlUtil() {}

  /**
   * Generates the file path for a JSON resource based on the class name of the provided object.
   *
   * @param obj the object whose class name will be used to generate the JSON file path; must not be
   *     null
   * @return the file path to the JSON resource corresponding to the object's class name
   * @throws IllegalArgumentException if the provided object is null
   */
  public static <T> String getJsonFilePath(T obj, boolean isTest) {
    if (obj == null) {
      throw new IllegalArgumentException("Object cannot be null");
    }
    return isTest
        ? "src/test/resources/json/%s.json".formatted(obj.getClass().getSimpleName())
        : "src/main/resources/json/%s.json".formatted(obj.getClass().getSimpleName());
  }

  /**
   * Generates the file path for a JSON resource based on the simple name of the provided class.
   *
   * @param clazz the class whose simple name will be used to generate the JSON file path; must not
   *     be null
   * @return the file path to the JSON resource corresponding to the class name
   */
  public static <T> String getJsonFilePath(Class<T> clazz, boolean isTest) {
    return isTest
        ? "src/test/resources/json/%s.json".formatted(clazz.getSimpleName())
        : "src/main/resources/json/%s.json".formatted(clazz.getSimpleName());
  }
}
