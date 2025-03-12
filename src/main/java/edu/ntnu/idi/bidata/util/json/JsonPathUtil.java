package edu.ntnu.idi.bidata.util.json;

import java.nio.file.Path;

/**
 * Utility class for generating file paths for JSON resources based on object instances or class
 * definitions. Provides methods to generate paths in a structured manner, targeting files stored in
 * the "src/main/resources/json" directory.
 *
 * @author Nick Heggø
 * @version 2025.03.12
 */
public class JsonPathUtil {

  private static final String JSON_DIR_TEMPLATE = "data/json/%s.json";
  private static final String TEST_JSON_DIR_TEMPLATE = "src/test/resources/json/%s.json";

  private JsonPathUtil() {}

  /**
   * Generates the file path for a JSON resource based on the class name of the provided object.
   *
   * @param obj the object whose class name will be used to generate the JSON file path; must not be
   *     null
   * @return the file path to the JSON resource corresponding to the object's class name
   * @throws IllegalArgumentException if the provided object is null
   */
  public static <T> Path getJsonFilePath(T obj, boolean isTest) {
    if (obj == null) {
      throw new IllegalArgumentException("Object cannot be null");
    }
    return isTest
        ? Path.of(TEST_JSON_DIR_TEMPLATE.formatted(obj.getClass().getSimpleName()))
        : Path.of(JSON_DIR_TEMPLATE.formatted(obj.getClass().getSimpleName()));
  }

  /**
   * Generates the file path for a JSON resource based on the simple name of the provided class.
   *
   * @param targetClass the class whose simple name will be used to generate the JSON file path;
   *     must not be null
   * @return the file path to the JSON resource corresponding to the class name
   */
  public static <T> Path getJsonFilePath(Class<T> targetClass, boolean isTest) {
    return isTest
        ? Path.of(TEST_JSON_DIR_TEMPLATE.formatted(targetClass.getSimpleName()))
        : Path.of(JSON_DIR_TEMPLATE.formatted(targetClass.getSimpleName()));
  }
}
