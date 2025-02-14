package edu.ntnu.idi.bidata.action;

import edu.ntnu.idi.bidata.core.Board;
import edu.ntnu.idi.bidata.core.Player;

/**
 * The {@code SnakeAction} class represents an action that moves the player 10 tiles backwards.
 * It automatically assigns itself to the given tile during construction.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.14
 */
public class SnakeAction implements TileAction {

  /**
   * Constructs a new SnakeAction.
   */
  public SnakeAction() {
    // default constructor
  }

  /**
   * Moves the player 10 tiles backwards.
   *
   * @param player the player landing on the tile
   */
  @Override
  public void perform(Player player, Board board) {
    player.move(-10, board);
  }
}
