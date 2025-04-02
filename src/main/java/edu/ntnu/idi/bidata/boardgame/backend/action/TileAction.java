package edu.ntnu.idi.bidata.boardgame.backend.action;

import edu.ntnu.idi.bidata.boardgame.backend.model.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;

/**
 * The {@code TileAction} interface represents an action that can be performed when a player gets to
 * a specific tile on the board.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.14
 */
public interface TileAction {
  void perform(Player player, Board board);
}
