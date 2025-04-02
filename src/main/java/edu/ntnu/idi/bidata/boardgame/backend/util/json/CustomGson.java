package edu.ntnu.idi.bidata.boardgame.backend.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.ntnu.idi.bidata.boardgame.backend.action.TileAction;
import edu.ntnu.idi.bidata.boardgame.backend.action.TileActionAdapter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Singleton utility class that provides a single instance of {@link Gson}. This class ensures that
 * the same instance of the custom gson is used throughout the application.
 *
 * @author Nick Heggø
 * @version 2025.03.28
 */
public class CustomGson {

  private static Gson gson;

  private CustomGson() {}

  /**
   * Provides a singleton instance of {@link Gson} for use throughout the application. If the
   * instance does not already exist, it is created and returned.
   *
   * @return a shared instance of {@link Gson} with config applied
   */
  public static Gson getInstance() {
    if (gson == null) {
      gson =
          new GsonBuilder()
              .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
              .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
              .registerTypeAdapter(TileAction.class, new TileActionAdapter())
              .setPrettyPrinting()
              .create();
    }
    return gson;
  }

  /**
   * Adapter for {@link LocalDateTime} type, which is used as the time stamp for all instances that
   * will be stored in the JSON file in this application
   *
   * <p>It must be registered with the GSON instance, or else the serialization of objects
   * containing {@link LocalDateTime} will fail
   *
   * <p>Modern Java time package does not allow to be accessed via reflection
   */
  private static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
      if (value == null) {
        out.nullValue();
      } else {
        out.value(formatter.format(value));
      }
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
      if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
        in.nextNull();
        return null;
      }
      return LocalDateTime.parse(in.nextString(), formatter);
    }
  }

  /**
   * Adapter for {@link LocalDate} type used as the {@code deadLine} in the {@link
   * edu.ntnu.idi.bidata.tiedy.backend.task.Task} class
   *
   * <p>It must be registered with the GSON instance, or else the serialization of objects
   * containing {@link LocalDate} will fail
   *
   * <p>Modern Java time package does not allow to be accessed via reflection
   */
  private static class LocalDateAdapter
      implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    private final DateTimeFormatter formatter =
        DateTimeFormatter.ISO_LOCAL_DATE; // Uses format like "2023-04-15"

    @Override
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(date.format(formatter));
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      return LocalDate.parse(json.getAsString(), formatter);
    }
  }
}
