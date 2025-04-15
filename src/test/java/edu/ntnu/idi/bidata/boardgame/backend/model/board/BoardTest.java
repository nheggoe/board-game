package edu.ntnu.idi.bidata.boardgame.backend.model.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import edu.ntnu.idi.bidata.boardgame.backend.model.tile.IllegalTilePositionException;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.Tile;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
  private Board board;

  @BeforeEach
  void setup() {
    board = new Board(generateTiles(5));
  }

  @Test
  void testGetTileAfterSteps() {
    Tile previousTile = board.getStartingTile();
    Tile nextTile = board.getTileAfterSteps(previousTile, 2);

    assertThat(previousTile.getTilePosition()).isEqualTo(0);
    assertThat(nextTile.getTilePosition()).withFailMessage("0 + 2 = 2").isEqualTo(2);

    // we've created a list with 5 tiles, with indexes: 0, 1, 2, 3, 4
    // since our board game will be looping around the tiles, it should go to the first tile from
    // the end
    // e.g., 0 -> 1 -> 2 -> 3 -> 4 -> 0 -> 1 -> ...
    previousTile = nextTile;
    nextTile = board.getTileAfterSteps(previousTile, 9);
    // (2 + 9) % 5 = 1
    assertThat(nextTile.getTilePosition()).isEqualTo(1);
  }

  @Test
  void testBoard() {
    assertThat(board.getStartingTile().getTileName()).isEqualTo("Start");
    assertThat(board.getTileAfterSteps(board.getStartingTile(), 4).getTileName()).isEqualTo("End");
    assertThat(board.getNumberOfTiles()).isEqualTo(board.tiles().size());
  }

  @Test
  void testGetInvalidPosition() {
    Tile errorTile = new Tile(-1, "ERROR", unused -> {}) {};
    assertThatThrownBy(() -> board.getTileAfterSteps(errorTile, 2))
        .isInstanceOf(IllegalTilePositionException.class);
  }

  @Test
  void testGenerateMethod() {
    List<Tile> tiles = generateTiles(5);
    assertThat(tiles.size()).isEqualTo(5);

    for (int i = 0; i < tiles.size(); i++) {
      assertThat(tiles.get(i).getTilePosition()).isEqualTo(i);
    }
  }

  private List<Tile> generateTiles(int numberOfTiles) {
    List<Tile> tmp = new ArrayList<>();
    for (int i = 1; i < numberOfTiles - 1; i++) {
      final int finalI = i;
      tmp.add(
          new Tile(
              i,
              "Tile",
              player -> System.out.println(player.getName() + " is safe on tile " + finalI)) {});
    }
    tmp.addFirst(
        new Tile(
            0,
            "Start",
            player -> System.out.println(player.getName() + " is on the first tile")) {});
    tmp.addLast(
        new Tile(
            tmp.size(),
            "End",
            player -> System.out.println(player.getName() + " is on the last tile")) {});
    return tmp;
  }
}
