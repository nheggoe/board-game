package edu.ntnu.idi.bidata.boardgame.common.io.json;

import com.google.gson.reflect.TypeToken;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.MonopolyGame;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.MonopolyBoard;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderGame;
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
 * @version 2025.05.20
 */
public class JsonType {
  private static final Map<Class<?>, Type> TYPE_MAP =
      Map.of(
          MonopolyGame.class, new TypeToken<Set<MonopolyBoard>>() {}.getType(),
          Player.class, new TypeToken<Set<Player>>() {}.getType(),
          SnakeAndLadderGame.class, new TypeToken<Set<SnakeAndLadderGame>>() {}.getType(),
          SnakeAndLadderBoard.class, new TypeToken<Set<SnakeAndLadderBoard>>() {}.getType(),
          MonopolyBoard.class, new TypeToken<Set<MonopolyBoard>>() {}.getType());

  private JsonType() {}

  public static Type getType(Class<?> targetClass) {
    return TYPE_MAP.get(targetClass);
  }
}
