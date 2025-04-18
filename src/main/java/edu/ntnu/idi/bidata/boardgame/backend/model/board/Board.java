package edu.ntnu.idi.bidata.boardgame.backend.model.board;

import edu.ntnu.idi.bidata.boardgame.backend.model.tile.IllegalTilePositionException;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.Tile;
import java.util.List;
import org.jspecify.annotations.NullMarked;

/**
 * Represents a game board consisting of a list of tiles. Each tile has a unique position and can
 * have specific actions that are triggered when a player lands on them. Provides methods to
 * interact with and retrieve tiles on the board.
 *
 * <p>This class is immutable; once created, the tiles cannot be modified.
 *
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.04.15
 */
@NullMarked
public record Board(List<Tile> tiles) {

  public Board {
    tiles = List.copyOf(tiles);
  }

  /**
   * Calculates the tile a player lands on after moving a specified number of steps from the
   * provided starting tile.
   *
   * @param currentTile the starting {@link Tile} from which the steps are calculated
   * @param steps the number of steps to move forward from the starting tile
   * @return the {@link Tile} the player lands on after moving the specified number of steps
   */
  public Tile getTileAfterSteps(Tile currentTile, int steps) {
    if (currentTile.getTilePosition() < 0) {
      throw new IllegalTilePositionException(
          currentTile.getTilePosition(), currentTile.getTileName());
    }
    int nextIndex = (currentTile.getTilePosition() + steps) % tiles.size();
    return tiles.get(Math.max(nextIndex, 0));
  }

  /**
   * Get the starting position for the game, which is used to place players on the starting point.
   *
   * @return the staring point of the game
   */
  public Tile getStartingTile() {
    return tiles.getFirst();
  }

  /**
   * Retrieves a tile at the provided position.
   *
   * @param position the position of the tile to retrieve
   * @return the {@link Tile} object at the specified position
   * @throws IndexOutOfBoundsException if the position is out of bounds
   */
  public Tile getTile(int position) {
    return tiles.get(position);
  }

  /**
   * Returns the total number of tiles.
   *
   * @return the number of tiles
   */
  public int getNumberOfTiles() {
    return tiles.size();
  }
}
