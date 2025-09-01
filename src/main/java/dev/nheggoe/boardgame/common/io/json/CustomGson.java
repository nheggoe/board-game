package dev.nheggoe.boardgame.common.io.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.nheggoe.boardgame.common.io.json.adapter.BoardAdapter;
import dev.nheggoe.boardgame.common.io.json.adapter.MonopolyTileAdapter;
import dev.nheggoe.boardgame.common.io.json.adapter.OwnableAdapter;
import dev.nheggoe.boardgame.common.io.json.adapter.SnakeAndLadderTileAdapter;
import dev.nheggoe.boardgame.core.model.Board;
import dev.nheggoe.boardgame.games.monopoly.model.ownable.Ownable;
import dev.nheggoe.boardgame.games.monopoly.model.tile.MonopolyTile;
import dev.nheggoe.boardgame.games.snake.model.tile.SnakeAndLadderTile;

/**
 * Singleton utility class that provides a single instance of {@link Gson}. This class ensures that
 * the same instance of the custom gson is used throughout the application.
 *
 * @author Nick Heggø
 * @version 2025.05.20
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
              .registerTypeAdapter(Ownable.class, new OwnableAdapter())
              .registerTypeAdapter(Board.class, new BoardAdapter())
              .registerTypeAdapter(MonopolyTile.class, new MonopolyTileAdapter())
              .registerTypeAdapter(SnakeAndLadderTile.class, new SnakeAndLadderTileAdapter())
              .setPrettyPrinting()
              .create();
    }
    return gson;
  }
}
