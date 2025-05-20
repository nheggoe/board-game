package edu.ntnu.idi.bidata.boardgame.games.snake.model;

import edu.ntnu.idi.bidata.boardgame.core.model.Board;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.InvalidBoardLayoutException;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.LadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.NormalTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import java.util.List;

/**
 * Represents the board layout for a Snake and Ladder game.
 *
 * @author Nick Heggø, Mihailo Hranisavljevic
 * @version 2025.05.20
 */
public record SnakeAndLadderBoard(List<SnakeAndLadderTile> tiles)
    implements Board<SnakeAndLadderTile> {

  /**
   * Constructs a new SnakeAndLadderBoard with the provided tiles. Performs validation on all tiles
   * to ensure game integrity.
   *
   * @throws InvalidBoardLayoutException if any tile is invalid
   */
  public SnakeAndLadderBoard {
    validateTiles(tiles);
    tiles = List.copyOf(tiles);
  }

  /**
   * Returns the tile at the specified index.
   *
   * @param index the index of the tile (0-based)
   * @return the tile at the specified index
   */
  @Override
  public SnakeAndLadderTile getTileAtIndex(int index) {
    return tiles.get(index);
  }

  /**
   * Returns the total number of tiles on the board.
   *
   * @return the number of tiles
   */
  @Override
  public int size() {
    return tiles.size();
  }

  /**
   * Validates all tiles on the board. Ensures no null tiles exist and checks that all SnakeTile and
   * LadderTile instances have valid offset positions.
   *
   * @param tiles the list of tiles to validate
   * @throws InvalidBoardLayoutException if any tile is invalid
   */
  private void validateTiles(List<SnakeAndLadderTile> tiles) {
    for (int index = 0; index < tiles.size(); index++) {
      var tile = tiles.get(index);
      switch (tile) {
        case null ->
            throw new InvalidBoardLayoutException(
                "Board cannot contain null tiles. Index: %d".formatted(index));
        case SnakeTile snakeTile -> assertSnakeTile(index, snakeTile);
        case LadderTile ladderTile -> assertLadderTile(index, tiles.size(), ladderTile);
        case NormalTile unused -> {
          /*Empty case, no action needed*/
        }
      }
    }
  }

  /**
   * Validates a SnakeTile to ensure it does not move the player off the board.
   *
   * @param index the index of the tile
   * @param tile the SnakeTile instance
   * @throws InvalidBoardLayoutException if the snake effect is invalid
   */
  private void assertSnakeTile(int index, SnakeTile tile) {
    if (index - tile.tilesToSlideBack() < 0) {
      throw new InvalidBoardLayoutException(
          "Snake tile at index %d slide back more than the the index of the tile."
              .formatted(index));
    }
  }

  /**
   * Validates a LadderTile to ensure it does not move the player beyond the final tile.
   *
   * @param index the index of the tile
   * @param totalTile the total number of tiles on the board
   * @param tile the LadderTile instance
   * @throws InvalidBoardLayoutException if the ladder effect is invalid
   */
  private void assertLadderTile(int index, int totalTile, LadderTile tile) {
    if (index + tile.tilesToSkip() >= totalTile) {
      throw new InvalidBoardLayoutException(
          "Ladder tile at index %d skips more tiles than the number of rest of the tiles."
              .formatted(index));
    }
  }
}
