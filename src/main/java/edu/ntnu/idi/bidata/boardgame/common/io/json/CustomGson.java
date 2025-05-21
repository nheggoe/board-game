package edu.ntnu.idi.bidata.boardgame.common.io.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.ntnu.idi.bidata.boardgame.common.io.json.adapter.BoardAdapter;
import edu.ntnu.idi.bidata.boardgame.common.io.json.adapter.MonopolyTileAdapter;
import edu.ntnu.idi.bidata.boardgame.common.io.json.adapter.OwnableAdapter;
import edu.ntnu.idi.bidata.boardgame.common.io.json.adapter.SnakeAndLadderTileAdapter;
import edu.ntnu.idi.bidata.boardgame.core.model.Board;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Ownable;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.MonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;

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
