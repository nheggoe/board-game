package edu.ntnu.idi.bidata.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class JsonReader {

  private final ObjectMapper objectMapper = JsonMapper.getInstance();
  private Class<?> targetClass;
  private final boolean isTest;

  public <T> JsonReader(Class<T> targetClass) {
    setTargetClass(targetClass);
    this.isTest = false;
  }

  public <T> JsonReader(Class<T> targetClass, boolean isTest) {
    setTargetClass(targetClass);
    this.isTest = isTest;
  }

  public <T> List<T> parseJsonFile() throws IOException {
    Path jsonFilePath = JsonUrl.getJsonFilePath(targetClass, isTest);
    File file = jsonFilePath.toFile();
    File parentDir = file.getParentFile();
    if (!parentDir.exists() && !parentDir.mkdirs()) {
      throw new IOException("Failed to create directory: " + parentDir.getAbsolutePath());
    }

    boolean isNewFileCreated = file.createNewFile();
    return isNewFileCreated
        ? List.of()
        : objectMapper.readValue(
            file, objectMapper.getTypeFactory().constructCollectionType(List.class, targetClass));
  }

  private void setTargetClass(Class<?> targetClass) {
    if (targetClass == null) {
      throw new IllegalArgumentException("Target class must not be null");
    }
    this.targetClass = targetClass;
  }
}
