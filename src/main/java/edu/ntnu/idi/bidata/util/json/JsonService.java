package edu.ntnu.idi.bidata.util.json;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Service class for reading from and writing to JSON files. This class utilizes {@link JsonReader}
 * for deserializing JSON content into objects and {@link JsonWriter} for serializing objects into
 * JSON files. It offers functionality to handle collections of a specified type.
 *
 * <p>The behavior of the JSON operations is determined by the associated class type and an optional
 * flag for distinguishing between test and production environments.
 *
 * @author Nick Heggø
 * @version 2025.03.14
 */
public class JsonService {

  private final JsonReader jsonReader;
  private final JsonWriter jsonWriter;

  /**
   * Constructs a new instance of the JsonService class for reading from and writing to JSON files.
   * This lightweight constructor only requires the target class type and defaults the environment
   * to production.
   *
   * @param <T> the type of the objects that will be read from or written to JSON files
   * @param targetClass the class type of the objects to be handled by this service; must not be
   *     null
   * @throws IllegalArgumentException if the targetClass parameter is null
   */
  public <T> JsonService(Class<T> targetClass) {
    this(targetClass, false);
  }

  /**
   * Constructs a new instance of the JsonService class for reading from and writing to JSON files.
   * This service is designed to handle collections of a specified type. It utilizes {@link
   * JsonReader} to deserialize JSON content into objects and {@link JsonWriter} to serialize
   * objects into JSON files.
   *
   * @param <T> the type of the objects that will be read from or written to JSON files
   * @param targetClass the class type of the objects to be handled by this service; must not be
   *     null
   * @param isTest a boolean flag indicating whether the operations should be performed in the test
   *     environment (true) or the production environment (false)
   * @throws IllegalArgumentException if the targetClass parameter is null
   */
  public <T> JsonService(Class<T> targetClass, boolean isTest) {
    jsonReader = new JsonReader(targetClass, isTest);
    jsonWriter = new JsonWriter(targetClass, isTest);
  }

  /**
   * Loads a collection of objects from a JSON file. The JSON file is dynamically located based on
   * the target class type and whether the operation is performed in a test or production
   * environment. If the file does not exist, it is created and an empty list is returned. If the
   * file exists, the data in the file is deserialized into a list of objects of the specified type.
   *
   * @param <T> the type of objects to be loaded from the JSON file
   * @return a list of objects deserialized from the JSON file, or an empty list if the file is
   *     newly created
   * @throws IOException if an error occurs during the file creation or reading process
   */
  public <T> Stream<T> loadJsonAsStream() throws IOException {
    return jsonReader.parseJsonStream();
  }

  /**
   * Writes a stream of objects to a JSON file. The file is created at a location dynamically
   * determined based on the target class type and whether the operation is performed in a test or
   * production environment. The method ensures that the directory structure exists before writing.
   *
   * @param <T> the type of the elements in the stream to be serialized
   * @param stream the list of objects to be written into the JSON file; must not be null
   * @throws IOException if an I/O error occurs during file creation or writing
   */
  public <T> void writeCollection(Stream<T> stream) throws IOException {
    jsonWriter.writeJsonFile(stream);
  }
}
