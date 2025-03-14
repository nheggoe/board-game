package edu.ntnu.idi.bidata.util.json;

import java.nio.file.Path;

/**
 * Utility class for generating file paths for JSON resources based on class names. This class
 * supports determining file paths for both test and production resources dynamically.
 *
 * <p>Note: This class is not meant to be instantiated as it only contains static utility methods.
 *
 * @author Nick Heggø
 * @version 2025.03.14
 */
public class JsonPathUtil {

  private static final String JSON_DIR_TEMPLATE = "data/json/%s.json";
  private static final String TEST_JSON_DIR_TEMPLATE = "src/test/resources/json/%s.json";

  private JsonPathUtil() {}

  /**
   * Generates a file path for a JSON resource based on the simple name of the provided object's
   * class. The file path is determined by whether the operation targets test resources or
   * production resources.
   *
   * @param <T> the type of the object used to determine the file path
   * @param obj the object whose class is used for generating the file path; must not be null
   * @param isTest a boolean indicating whether the file path should target test resources (true) or
   *     production resources (false)
   * @return the file path to the JSON resource associated with the object's class name
   * @throws IllegalArgumentException if the provided object is null
   */
  public static <T> Path generateJsonPath(T obj, boolean isTest) {
    if (obj == null) {
      throw new IllegalArgumentException("Object cannot be null");
    }
    return isTest
        ? Path.of(TEST_JSON_DIR_TEMPLATE.formatted(obj.getClass().getSimpleName()))
        : Path.of(JSON_DIR_TEMPLATE.formatted(obj.getClass().getSimpleName()));
  }

  /**
   * Generates a file path for a JSON resource based on the simple name of the specified class. The
   * file path is determined by whether the operation targets test resources or production
   * resources.
   *
   * @param <T> the type of the class used for generating the file path
   * @param targetClass the class whose simple name is used to generate the file path; must not be
   *     null
   * @param isTest a boolean indicating whether the file path should target test resources (true) or
   *     production resources (false)
   * @return the file path to the JSON resource associated with the specified class name
   */
  public static <T> Path generateJsonPath(Class<T> targetClass, boolean isTest) {
    return isTest
        ? Path.of(TEST_JSON_DIR_TEMPLATE.formatted(targetClass.getSimpleName()))
        : Path.of(JSON_DIR_TEMPLATE.formatted(targetClass.getSimpleName()));
  }
}
