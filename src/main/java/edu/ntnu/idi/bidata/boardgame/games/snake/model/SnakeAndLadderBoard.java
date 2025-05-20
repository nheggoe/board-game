package edu.ntnu.idi.bidata.boardgame.games.snake.model;

import edu.ntnu.idi.bidata.boardgame.core.model.Board;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.InvalidBoardLayoutException;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.LadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.NormalTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import java.util.List;

/**
 * @param tiles the tiles this board will contain
 * @author Nick Heggø
 * @version 2025.05.20
 */
public record SnakeAndLadderBoard(List<SnakeAndLadderTile> tiles)
    implements Board<SnakeAndLadderTile> {

  public SnakeAndLadderBoard {
    validateTiles(tiles);
    tiles = List.copyOf(tiles);
  }

  @Override
  public SnakeAndLadderTile getTileAtIndex(int index) {
    return tiles.get(index);
  }

  @Override
  public int size() {
    return tiles.size();
  }

  private void validateTiles(List<SnakeAndLadderTile> tiles) {
    for (int index = 0; index < tiles.size(); index++) {
      var tile = tiles.get(index);
      switch (tile) {
        case null ->
            throw new InvalidBoardLayoutException(
                "Board cannot contain null tiles. Index: %d".formatted(index));
        case SnakeTile snakeTile -> assertSnakeTile(index, snakeTile);
        case LadderTile ladderTile -> assertLadderTile(index, tiles.size(), ladderTile);
        case NormalTile unused -> {} // Do nothing
      }
    }
  }

  private void assertSnakeTile(int index, SnakeTile tile) {
    if (index - tile.tilesToSlideBack() < 0) {
      throw new InvalidBoardLayoutException(
          "Snake tile at index %d slide back more than the the index of the tile."
              .formatted(index));
    }
  }

  private void assertLadderTile(int index, int totalTile, LadderTile tile) {
    if (index + tile.tilesToSkip() >= totalTile) {
      throw new InvalidBoardLayoutException(
          "Ladder tile at index %d skips more tiles than the number of rest of the tiles."
              .formatted(index));
    }
  }
}
