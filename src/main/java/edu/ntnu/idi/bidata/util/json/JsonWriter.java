package edu.ntnu.idi.bidata.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

public class JsonWriter {

  private static final Logger LOGGER = Logger.getLogger(JsonWriter.class.getName());

  private final ObjectMapper objectMapper = JsonMapper.getInstance();
  private Class<?> targetClass;
  private final boolean isTest;

  public <T> JsonWriter(Class<T> targetClass, boolean isTest) {
    setTargetClass(targetClass);
    this.isTest = isTest;
  }

  public <T> void writeJsonFile(List<T> collection) throws IOException {
    Path jsonFilePath = JsonUrl.getJsonFilePath(targetClass, isTest);
    File file = jsonFilePath.toFile();
    File parentDir = file.getParentFile();
    if (!parentDir.exists()) {
      if (parentDir.mkdirs()) {
        LOGGER.info("Created directory: " + parentDir.getAbsolutePath());
      } else {
        LOGGER.warning("Failed to create directory: " + parentDir.getAbsolutePath());
      }
    }
    objectMapper.writeValue(file, collection);
  }

  private void setTargetClass(Class<?> targetClass) {
    if (targetClass == null) {
      throw new IllegalArgumentException("Target class must not be null");
    }
    this.targetClass = targetClass;
  }
}
