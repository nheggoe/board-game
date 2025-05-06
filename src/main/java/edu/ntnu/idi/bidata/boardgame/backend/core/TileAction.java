package edu.ntnu.idi.bidata.boardgame.backend.core;


import edu.ntnu.idi.bidata.boardgame.backend.model.Player;

/**
 * {@code TileAction} defines the executable action that occurs when a player lands on a tile.
 *
 * <p>This functional interface is responsible for:
 *
 * <ul>
 *   <li>Handling purchases of ownable tiles like properties, utilities, and railroads
 *   <li>Charging rent to players when landing on owned properties
 *   <li>Applying tax penalties
 *   <li>Sending players to jail, free parking, or start
 *   <li>Allowing players to upgrade properties with houses or hotels
 * </ul>
 *
 * <p>It uses pattern matching to process different tile types in a clean, structured way.
 *
 * @author Mihailo Hranisavljevic, Nick Heggø
 * @version 2025.04.26
 */
@FunctionalInterface
public interface TileAction {

  /**
   * Executes the defined tile action for the given player.
   *
   * @param player the player who landed on the tile
   */
  void execute(Player player);
}
