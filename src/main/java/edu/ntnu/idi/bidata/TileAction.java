package edu.ntnu.idi.bidata;

/**
 * The {@code TileAction} interface represents an action that can be performed
 * when a player gets to a specific tile on the board.
 */
public interface TileAction {
  void perform(Player player);
}
