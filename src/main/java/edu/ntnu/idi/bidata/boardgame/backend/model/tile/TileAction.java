package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;

/**
 * The {@code TileAction} interface represents an action that can be performed when a player gets to
 * a specific tile on the board.
 *
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.04.15
 */
@FunctionalInterface
public interface TileAction {
  void performAction(Player player);
}
