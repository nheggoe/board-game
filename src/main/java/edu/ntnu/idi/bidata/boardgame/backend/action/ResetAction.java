package edu.ntnu.idi.bidata.boardgame.backend.action;

import edu.ntnu.idi.bidata.boardgame.backend.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;

/**
 * The {@code ResetAction} class represents an action that moves the player to the starting tile. It
 * automatically assigns itself to the given tile during construction.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.14
 */
public class ResetAction implements TileAction {

  /** Constructs a new ResetAction. */
  public ResetAction() {
    // default constructor
  }

  /**
   * Moves the player to the starting tile.
   *
   * @param player the player landing on the tile
   */
  @Override
  public void perform(Player player, Board board) {
    player.placeOnTile(board.getTile(0));
  }
}
