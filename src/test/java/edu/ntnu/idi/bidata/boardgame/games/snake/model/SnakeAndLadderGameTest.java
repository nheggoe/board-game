package edu.ntnu.idi.bidata.boardgame.games.snake.model;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.LadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.NormalTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import java.util.List;
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
    game = new SnakeAndLadderGame(eventBus, board, players);
  }

  @Test
  void testBasic() {
    assertNotNull(game.getPlayers());
    assertNotNull(game.getId());
    assertNotNull(game.getWinners());
  }
}
