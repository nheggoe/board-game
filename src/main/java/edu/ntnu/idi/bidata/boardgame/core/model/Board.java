package edu.ntnu.idi.bidata.boardgame.core.model;

import java.util.List;

/**
 * Represents a game board which consists of multiple tiles. This interface provides methods to
 * access and interact with the tiles on the board.
 *
 * @param <T> the type of tile used in this board, which must implement the {@link Tile} interface
 * @author Nick Heggø
 * @version 2025.05.08
 */
public interface Board<T extends Tile> {

  List<T> tiles();

  T getTileAtIndex(int index);

  int size();
}
