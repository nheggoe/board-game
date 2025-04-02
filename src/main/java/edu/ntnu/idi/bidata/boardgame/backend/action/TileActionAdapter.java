package edu.ntnu.idi.bidata.boardgame.backend.action;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/**
 * A Gson {@link TypeAdapter} for the {@link TileAction} interface. This adapter handles
 * serialization and deserialization of different implementations of the TileAction interface.
 *
 * @author Nick Heggø
 * @version 2025.04.01
 */
public class TileActionAdapter extends TypeAdapter<TileAction> {

  @Override
  public void write(JsonWriter out, TileAction value) throws IOException {
    if (value == null) {
      out.nullValue();
      return;
    }

    out.beginObject();

    String className = value.getClass().getSimpleName();
    out.name("type").value(className);

    out.endObject();
  }

  @Override
  public TileAction read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      return null;
    }

    String type = null;

    in.beginObject();
    while (in.hasNext()) {
      String name = in.nextName();
      if (name.equals("type")) {
        type = in.nextString();
      } else {
        in.skipValue();
      }
    }
    in.endObject();

    if (type == null) {
      return null;
    }

    return switch (type) {
      case "LadderAction" -> new LadderAction();
      case "SnakeAction" -> new SnakeAction();
      case "ResetAction" -> new ResetAction();
      default -> null;
    };
  }
}
