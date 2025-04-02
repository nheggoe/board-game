package edu.ntnu.idi.bidata.boardgame.backend.io.json;

import com.google.gson.reflect.TypeToken;
import edu.ntnu.idi.bidata.boardgame.backend.model.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * Utility class for managing and retrieving type information used in JSON serialization and
 * deserialization.
 *
 * @see <a href="https://github.com/google/gson/blob/main/UserGuide.md#collections-examples">GSON
 *     collection examples</a>
 * @author Nick Heggø
 * @version 2025.03.28
 */
public class JsonType {
  private static final Map<Class<?>, Type> TYPE_MAP =
      Map.of(
          Board.class, new TypeToken<Set<Board>>() {}.getType(),
          Player.class, new TypeToken<Set<Player>>() {}.getType());

  private JsonType() {}

  public static Type getType(Class<?> targetClass) {
    return TYPE_MAP.get(targetClass);
  }
}
