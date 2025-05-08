package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board;

import edu.ntnu.idi.bidata.boardgame.core.model.Board;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.CornerMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.JailMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.MonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.StartMonopolyTile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a game board consisting of a list of tiles. Each tile has a unique position and can
 * have specific actions that are triggered when a player lands on them. Provides methods to
 * interact with and retrieve tiles on the board.
 *
 * <p>This class is immutable; once created, the tiles cannot be modified.
 *
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.04.22
 */
public record MonopolyBoard(List<MonopolyTile> tiles) implements Board<MonopolyTile> {

  public MonopolyBoard {
    assertValidLayout(tiles);
    tiles = List.copyOf(shuffleTiles(tiles));
  }

  /**
   * Calculates the tile a player lands on after moving a specified number of steps from the
   * provided starting tile.
   *
   * @param currentPosition the starting {@link MonopolyTile} from which the steps are calculated
   * @param steps the number of steps to move forward from the starting tile
   * @return the {@link MonopolyTile} the player lands on after moving the specified number of steps
   */
  public MonopolyTile getTileAfterSteps(int currentPosition, int steps) {
    if (currentPosition < 0) {
      throw new IllegalTilePositionException();
    }
    int nextIndex = (currentPosition + steps) % tiles.size();
    return tiles.get(nextIndex);
  }

  /**
   * Get the starting position for the game, which is used to place players on the starting point.
   *
   * @return the staring point of the game
   */
  public StartMonopolyTile getStartingTile() {
    return tiles.stream()
        .filter(StartMonopolyTile.class::isInstance)
        .map(StartMonopolyTile.class::cast)
        .findAny()
        .orElseThrow(InvalidBoardLayoutException::new);
  }

  public JailMonopolyTile getJailTile() {
    return tiles.stream()
        .filter(JailMonopolyTile.class::isInstance)
        .map(JailMonopolyTile.class::cast)
        .findAny()
        .orElseThrow(InvalidBoardLayoutException::new);
  }

  public int size() {
    return tiles.size();
  }

  public int getTilePosition(MonopolyTile tile) {
    return tiles.indexOf(tile);
  }

  /**
   * Retrieves a tile at the provided position.
   *
   * @param position the position of the tile to retrieve
   * @return the {@link MonopolyTile} object at the specified position
   * @throws IndexOutOfBoundsException if the position is out of bounds
   */
  public MonopolyTile getTileAtIndex(int position) {
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

  private List<MonopolyTile> shuffleTiles(List<MonopolyTile> tiles) {
    Collections.shuffle(tiles);

    List<CornerMonopolyTile> cornerTiles =
        tiles.stream()
            .filter(CornerMonopolyTile.class::isInstance)
            .map(CornerMonopolyTile.class::cast)
            .toList();

    var topLeft = findCornerTileByPosition(cornerTiles, CornerMonopolyTile.Position.TOP_LEFT);
    var topRight = findCornerTileByPosition(cornerTiles, CornerMonopolyTile.Position.TOP_RIGHT);
    var bottomLeft = findCornerTileByPosition(cornerTiles, CornerMonopolyTile.Position.BOTTOM_LEFT);
    var bottomRight =
        findCornerTileByPosition(cornerTiles, CornerMonopolyTile.Position.BOTTOM_RIGHT);

    var orderedTiles = List.of(bottomRight, bottomLeft, topLeft, topRight);

    int tilesPerSide = tiles.size() / 4;

    int i = 0;
    for (var tile : orderedTiles) {
      Collections.swap(tiles, i, tiles.indexOf(tile));
      i += tilesPerSide;
    }

    return tiles;
  }

  private CornerMonopolyTile findCornerTileByPosition(
      List<CornerMonopolyTile> tiles, CornerMonopolyTile.Position position) {
    return tiles.stream().filter(tile -> tile.getPosition() == position).findAny().orElseThrow();
  }

  private void assertValidLayout(List<MonopolyTile> tiles) {
    var cornerTiles =
        tiles.stream()
            .filter(CornerMonopolyTile.class::isInstance)
            .map(CornerMonopolyTile.class::cast)
            .toList();
    assertCornerTiles(cornerTiles);
    assertLayoutShape(tiles);
  }

  private void assertLayoutShape(List<MonopolyTile> tiles) {
    int tilesPerSide = tiles.size() - 4;
    if ((tilesPerSide % 4) != 0) {
      throw new InvalidBoardLayoutException(
          "The board must be a square shape! There are currently %.2f tiles per side."
              .formatted(tilesPerSide / 4.0));
    }
  }

  private void assertCornerTiles(List<CornerMonopolyTile> tiles) {
    // assert there are four corners
    if (tiles.size() != 4) {
      throw new InvalidBoardLayoutException("A valid board layout will need 4 corner tiles!");
    }

    // assert there are one tile of each type
    var sortByType = new HashMap<Class<?>, Integer>();
    for (var tile : tiles) {
      sortByType.merge(tile.getClass(), 1, Integer::sum);
    }
    if (sortByType.size() != 4) {
      StringBuilder sb = new StringBuilder();
      sortByType.forEach(
          (key, value) -> {
            if (value > 1) {
              sb.append("\n").append(key.getSimpleName()).append("=").append(value);
            }
          });
      throw new InvalidBoardLayoutException("Duplicated corner tile types!" + sb);
    }

    // assert there are one tile per corner
    var sortByPositon =
        new EnumMap<CornerMonopolyTile.Position, List<CornerMonopolyTile>>(
            CornerMonopolyTile.Position.class);
    for (var tile : tiles) {
      sortByPositon.computeIfAbsent(tile.getPosition(), k -> new ArrayList<>()).add(tile);
    }
    if (sortByPositon.size() != 4) {
      String errorMessage = constructErrorMessage(sortByPositon);
      throw new InvalidBoardLayoutException("Missing tiles on all four corners!" + errorMessage);
    }
  }

  private static <K, V> String constructErrorMessage(Map<K, List<V>> map) {
    StringBuilder sb = new StringBuilder();
    for (var mapEntry : map.entrySet()) {
      if (mapEntry.getValue().size() > 1) {
        sb.append("\n");
        if (mapEntry.getKey() instanceof Class<?> c) {
          sb.append(c.getSimpleName());
        } else {
          sb.append(mapEntry.getKey());
        }
        sb.append("=[");
        mapEntry.getValue().forEach(tile -> sb.append(tile.getClass().getSimpleName()).append(","));
        sb.delete(sb.length() - 1, sb.length()).append("]");
      }
    }
    return sb.toString();
  }
}
