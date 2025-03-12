package edu.ntnu.idi.bidata.util.json;

import java.io.IOException;
import java.util.List;

public class JsonService {

  private final JsonReader jsonReader;
  private final JsonWriter jsonWriter;

  public <T> JsonService(Class<T> tClass, boolean isTest) {
    jsonReader = new JsonReader(tClass, isTest);
    jsonWriter = new JsonWriter(tClass, isTest);
  }

  public <T> List<T> loadCollection() throws IOException {
    return jsonReader.parseJsonFile();
  }

  public <T> void writeCollection(List<T> collection) throws IOException {
    jsonWriter.writeJsonFile(collection);
  }
}
