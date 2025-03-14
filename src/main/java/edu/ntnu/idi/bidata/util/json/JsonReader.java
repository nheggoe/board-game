package edu.ntnu.idi.bidata.util.json;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idi.bidata.util.FileUtil;
import java.io.File;
import java.io.IOException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Utility class for reading JSON files and deserializing their content into objects of a specified
 * target type. The JSON file is located dynamically based on the provided class type, with separate
 * support for test and production environments.
 *
 * <p>The class ensures that the directory structure for the JSON file is created if it does not
 * already exist. If the specified JSON file does not exist, an empty list is returned.
 *
 * <p>This class leverages a shared instance of {@link ObjectMapper} provided by the {@link
 * JsonMapper} class for efficient JSON operations.
 *
 * @author Nick Heggø
 * @version 2025.03.14
 */
public class JsonReader {

  private final ObjectMapper objectMapper = JsonMapper.getInstance();
  private Class<?> targetClass;
  private final boolean isTest;

  /**
   * Constructs a new JsonReader instance for reading JSON files and deserializing their content
   * into objects of the specified target class type. The JSON file location is determined
   * dynamically based on the provided target class and is designed for use in a production
   * environment by default.
   *
   * @param <T> the type of the objects that will be deserialized
   * @param targetClass the class type of the objects to be deserialized; must not be null
   * @throws IllegalArgumentException if the targetClass parameter is null
   */
  public <T> JsonReader(Class<T> targetClass) {
    setTargetClass(targetClass);
    this.isTest = false;
  }

  /**
   * Constructs a new instance of the JsonReader class for reading JSON files and deserializing
   * their content into objects of the specified target class type. The JSON file location is
   * determined dynamically based on the provided target class and whether the operation is
   * performed in a test or production environment.
   *
   * @param <T> the type of the objects that will be deserialized
   * @param targetClass the class type of the objects to be deserialized; must not be null
   * @param isTest a boolean flag indicating whether the JSON file should be located in the test
   *     environment (true) or the production environment (false)
   * @throws IllegalArgumentException if the targetClass parameter is null
   */
  public <T> JsonReader(Class<T> targetClass, boolean isTest) {
    setTargetClass(targetClass);
    this.isTest = isTest;
  }

  /**
   * Parses a JSON file into a stream of objects of the specified target class type. The JSON file
   * is located at a dynamically determined path based on the class name and an environment flag
   * (test or production). If the directory structure for the file does not exist, it will be
   * created. If the file does not exist, it will be created as an empty file and an empty stream is
   * returned. Otherwise, the JSON content will be deserialized into objects of the target class.
   *
   * @param <T> the type of the objects to be deserialized
   * @return a stream of objects deserialized from the JSON file; an empty stream if the file is
   *     newly created
   * @throws IOException if an I/O error occurs during directory creation, file creation, or reading
   *     from the file
   */
  public <T> Stream<T> parseJsonStream() throws IOException {
    File file = JsonPathUtil.generateJsonPath(targetClass, isTest).toFile();
    FileUtil.ensureFileAndDirectoryExists(file);

    MappingIterator<T> iterator = objectMapper.readerFor(targetClass).readValues(file);

    return StreamSupport.stream(
        Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
  }

  private void setTargetClass(Class<?> targetClass) {
    if (targetClass == null) {
      throw new IllegalArgumentException("Target class must not be null");
    }
    this.targetClass = targetClass;
  }
}
