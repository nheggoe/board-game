package edu.ntnu.idi.bidata.core;

import edu.ntnu.idi.bidata.action.LadderAction;
import edu.ntnu.idi.bidata.action.ResetAction;
import edu.ntnu.idi.bidata.action.SnakeAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code Board} class represents a game board consisting of a list of tiles. Each tile has a
 * unique position and a name. The board is initialized with a predefined set of tiles.
 *
 * @author Mihailo Hranisavljevic, Nick Heggø
 * @version 2025.02.14
 */
public class Board {
  /** The list of tiles that make up the board. */
  private final List<Tile> tiles;

  /** Constructs a new board with predefined tiles. */
  public Board() {
    tiles = new ArrayList<>();
    initializeBoard();
  }

  /**
   * # IntelliJ generated Used to compare if the current matches the winning tile.
   *
   * @param o the object that will be passed in for comparison
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Board board = (Board) o;
    return Objects.equals(tiles, board.tiles);
  }

  /** IntelliJ generated Must change {@code hashCode()} when {@code equals()} is changed. */
  @Override
  public int hashCode() {
    return Objects.hashCode(tiles);
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
   * Get the winning tile, it is used to see if the player has reached the finished line.
   *
   * @return the winning tile of the game
   */
  public Tile getWinningTile() {
    return tiles.getLast();
  }

  /**
   * Initializes the board by adding 90 tiles. The first tile is named "Start", and the rest go
   * sequentially.
   */
  private void initializeBoard() {
    for (int i = 0; i < 90; i++) {
      tiles.add(new Tile(i, i == 0 ? "Start" : "Tile " + (i + 1)));
    }
    // set LadderAction
    tiles.get(3).setLandAction(new LadderAction());
    tiles.get(36).setLandAction(new LadderAction());
    tiles.get(72).setLandAction(new LadderAction());
    // set SnakeAction
    tiles.get(11).setLandAction(new SnakeAction());
    tiles.get(48).setLandAction(new SnakeAction());
    tiles.get(60).setLandAction(new SnakeAction());
    // set ResetAction
    tiles.get(88).setLandAction(new ResetAction());
  }

  /**
   * Retrieves a tile at the provided position.
   *
   * @param position the position of the tile to retrieve
   * @return the {@code Tile} object at the specified position
   * @throws IllegalArgumentException if the position is out of bounds
   */
  public Tile getTile(int position) {
    if (position < 0 || position >= tiles.size()) {
      throw new IllegalArgumentException();
    }
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
