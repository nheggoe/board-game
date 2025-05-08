package edu.ntnu.idi.bidata.boardgame.games.snake.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.InvalidBoardLayoutException;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.NormalTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class SnakeAndLadderBoardTest {

  private SnakeAndLadderBoard snakeAndLadderBoard;

  @Test
  void setup() {
    var snakeAndLadderTiles = new ArrayList<SnakeAndLadderTile>();
    for (int i = 0; i < 90; i++) {
      snakeAndLadderTiles.add(new NormalTile());
    }
    snakeAndLadderBoard = new SnakeAndLadderBoard(snakeAndLadderTiles);
  }

  @Test
  void testNullTiles() {
    var tiles = new ArrayList<SnakeAndLadderTile>();
    tiles.add(null);
    assertThatThrownBy(() -> new SnakeAndLadderBoard(tiles))
        .isInstanceOf(InvalidBoardLayoutException.class);
  }
}
