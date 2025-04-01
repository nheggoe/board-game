package edu.ntnu.idi.bidata.boardgame.backend.util.json;

import com.google.gson.Gson;
import edu.ntnu.idi.bidata.boardgame.backend.util.FileUtil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Utility class for writing collections of objects to JSON files. The JSON files are created
 * dynamically based on the provided target class type, and their location is determined by whether
 * the class is in a test or production environment.
 *
 * <p>The class relies on a shared instance of {@link Gson} from the {@link CustomGson} class for
 * efficient serialization operations. It creates the necessary directory structure before writing
 * the data if it does not already exist.
 *
 * <p>This class is designed for use with collections of objects and does not support writing
 * individual objects directly.
 *
 * @author Nick Heggø
 * @version 2025.03.28
 */
public class JsonWriter<T> {

  private final Gson gson = CustomGson.getInstance();
  private final Class<T> targetClass;
  private final boolean isTest;

  /**
   * Constructs a new instance of the JsonWriter class for serializing collections of objects to a
   * JSON file. The file location is determined dynamically based on the provided target class type
   * and whether the operation is in a test or production environment.
   *
   * @param targetClass the class type of the objects to be serialized; must not be null
   * @param isTest a boolean flag indicating whether the JSON file should be created in the test
   *     environment (true) or the production environment (false)
   * @throws IllegalArgumentException if the targetClass parameter is null
   */
  public JsonWriter(Class<T> targetClass, boolean isTest) {
    if (targetClass == null) {
      throw new IllegalArgumentException("Target class must not be null");
    }
    this.targetClass = targetClass;
    this.isTest = isTest;
  }

  /**
   * Serializes the provided set of objects into a JSON file. The JSON file is created at a location
   * determined based on the target class type and whether the operation is performed in a test or
   * production environment. The method ensures that the necessary directory structure exists before
   * writing the file.
   *
   * @param set the set of objects to serialize and write into the JSON file
   */
  public void writeJsonFile(Set<T> set) {
    File file = FileUtil.generateFilePath(targetClass.getSimpleName(), "json", isTest).toFile();
    FileUtil.ensureFileAndDirectoryExists(file);

    String json = gson.toJson(set, JsonType.getType(targetClass));

    try (FileWriter writer = new FileWriter(file)) {
      writer.write(json);
    } catch (IOException e) {
      throw new JsonException("Could not write JSON file: " + file + "\n" + e.getMessage());
    }
  }
}
