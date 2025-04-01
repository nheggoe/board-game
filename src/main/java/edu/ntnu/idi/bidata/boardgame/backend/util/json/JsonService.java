package edu.ntnu.idi.bidata.boardgame.backend.util.json;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service class for reading from and writing to JSON files. This class utilizes {@link JsonReader}
 * for deserializing JSON content into objects and {@link JsonWriter} for serializing objects into
 * JSON files. It offers functionality to handle collections of specified types.
 *
 * <p>The behavior of the JSON operations is determined by the associated class type and an optional
 * flag for distinguishing between test and production environments.
 *
 * @author Nick Heggø
 * @version 2025.03.28
 */
public class JsonService<T> {

  private final JsonReader<T> jsonReader;
  private final JsonWriter<T> jsonWriter;

  /**
   * Constructs a new instance of the JsonService class for reading from and writing to JSON files.
   * This lightweight constructor only requires the target class type and defaults the environment
   * to production.
   *
   * @throws UnsupportedOperationException if the {@link JsonType} does not contain the necessary
   *     type for serialization
   * @throws IllegalArgumentException if the targetClass parameter is null
   */
  public JsonService(Class<T> targetClass) {
    this(targetClass, false);
  }

  /**
   * Constructs a new instance of the JsonService class for reading from and writing to JSON files.
   * This service is designed to handle collections of specified types. It utilizes {@link
   * JsonReader} to deserialize JSON content into objects and {@link JsonWriter} to serialize
   * objects into JSON files.
   *
   * @param isTest a boolean flag indicating whether the operations should be performed in the test
   *     environment (true) or the production environment (false)
   * @throws UnsupportedOperationException if the {@link JsonType} does not contain the necessary
   *     type for serialization
   * @throws IllegalArgumentException if the targetClass parameter is null
   */
  public JsonService(Class<T> targetClas, boolean isTest) {
    if (JsonType.getType(targetClas) == null) {
      throw new UnsupportedOperationException("Unsupported target class type: " + targetClas);
    }
    jsonReader = new JsonReader<>(targetClas, isTest);
    jsonWriter = new JsonWriter<>(targetClas, isTest);
  }

  /**
   * Loads a collection of objects from a JSON file. The JSON file is dynamically located based on
   * the target class type and whether the operation is performed in a test or production
   * environment. If the file does not exist, it is created and an empty list is returned. If the
   * file exists, the data in the file is deserialized into a list.
   *
   * @return a stream of objects deserialized from the JSON file, or an empty stream if the file is
   *     newly created
   */
  public Stream<T> loadJsonAsStream() {
    return jsonReader.parseJsonStream();
  }

  /**
   * Writes a set of objects to a JSON file. The file is created at a location dynamically
   * determined based on the target class type and whether the operation is performed in a test or
   * production environment. The method ensures that the directory structure exists before writing.
   * It will update existing data if present
   *
   * @param set the set of objects to be written into the JSON file
   */
  public void writeCollection(Set<T> set) {
    Set<T> existingData = loadJsonAsStream().collect(Collectors.toSet());
    existingData.removeAll(set);
    existingData.addAll(set);
    jsonWriter.writeJsonFile(existingData);
  }

  /**
   * Adds an item to the JSON collection by writing it to the JSON file. The provided item is
   * wrapped in a stream and passed to the {@code writeCollection} method for writing. The location
   * of the JSON file is determined dynamically based on the class type and the environment (test or
   * production).
   *
   * @param item the object of the target type to be added to the JSON collection; must not be null
   */
  public void addItem(T item) {
    writeCollection(Set.of(item));
  }
}
