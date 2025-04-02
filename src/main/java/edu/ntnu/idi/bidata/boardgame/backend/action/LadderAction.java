package edu.ntnu.idi.bidata.boardgame.backend.action;

import edu.ntnu.idi.bidata.boardgame.backend.model.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;

/**
 * The {@code LadderAction} moves a player 10 tiles forward. It automatically assigns itself to the
 * given tile during construction.
 *
 * @author Mihailo
 * @version 2025.02.14
 */
public class LadderAction implements TileAction {

  /** Constructs a new ladder action. */
  public LadderAction() {
    // default constructor
  }

  /**
   * Moves the player 10 tiles forward.
   *
   * @param player the player landing on the tile
   */
  @Override
  public void perform(Player player, Board board) {
    player.move(10, board);
  }
}
