package edu.ntnu.idi.bidata.boardgame.games.snake.model;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.LadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.NormalTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SnakeAndLadderGameTest {
  private SnakeAndLadderGame game;

  @BeforeEach
  void setup() {
    var eventBus = new EventBus();
    var tiles =
        List.<SnakeAndLadderTile>of(
            new NormalTile(), new SnakeTile(1), new LadderTile(1), new NormalTile());
    var board = new SnakeAndLadderBoard(tiles);
    var players =
        List.of(
            new SnakeAndLadderPlayer("John", Player.Figure.CAR),
            new SnakeAndLadderPlayer("Jane", Player.Figure.HAT));
    game =
        new SnakeAndLadderGame(eventBus, board, players) {

          @Override
          protected void completeRoundAction(SnakeAndLadderPlayer player) {
            // do nothing
          }

          @Override
          public Map.Entry<Integer, List<SnakeAndLadderPlayer>> getWinners() {
            var treeMap = new TreeMap<Integer, List<SnakeAndLadderPlayer>>();
            for (var player : getPlayers()) {
              treeMap
                  .computeIfAbsent(player.getPosition(), unused -> new ArrayList<>())
                  .add(player);
            }
            return treeMap.reversed().firstEntry();
          }
        };
  }

  @Test
  void testBasic() {
    assertNotNull(game.getPlayers());
    assertNotNull(game.getId());
    assertNotNull(game.getWinners());
    assertNotNull(game.getPlayerIds());
  }
}
