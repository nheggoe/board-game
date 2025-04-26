package edu.ntnu.idi.bidata.boardgame.backend.core;

import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.DiceRoll;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.TileAction;
import java.util.UUID;

/**
 * The {@code GameObserver} interface defines a contract for observing and reacting to key events
 * within a board game. Implementers of this interface can monitor game-related updates and take
 * appropriate actions based on events such as player movements, property purchases, dice rolls, and
 * balance changes.
 *
 * @author Nick Heggø
 * @version 2025.04.25
 */
public interface GameObserver {

  void onPlayerMoved(Player player, int oldPosition, int newPosition);

  void onTileAction(Player player, TileAction action);

  void onPropertyPurchased(int playerId, int propertyId);

  void onDiceRolled(Player player, DiceRoll diceRoll);

  void onPlayerBalanceChanged(UUID playerId, int oldBalance, int newBalance);
}
