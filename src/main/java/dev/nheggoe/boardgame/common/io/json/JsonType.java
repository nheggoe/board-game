package dev.nheggoe.boardgame.common.io.json;

import com.google.gson.reflect.TypeToken;
import dev.nheggoe.boardgame.core.model.Player;
import dev.nheggoe.boardgame.games.monopoly.model.MonopolyGame;
import dev.nheggoe.boardgame.games.monopoly.model.board.MonopolyBoard;
import dev.nheggoe.boardgame.games.snake.model.SnakeAndLadderBoard;
import dev.nheggoe.boardgame.games.snake.model.SnakeAndLadderGame;
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

  /**
   * Retrieves the {@link Type} associated with the specified target class.
   *
   * @param targetClass the class for which the corresponding type is to be retrieved; must not be
   *     null
   * @return the {@link Type} associated with the given target class, or null if no mapping is found
   */
  public static Type getType(Class<?> targetClass) {
    return TYPE_MAP.get(targetClass);
  }
}
