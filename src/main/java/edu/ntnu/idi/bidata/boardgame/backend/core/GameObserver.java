package edu.ntnu.idi.bidata.boardgame.backend.core;

import edu.ntnu.idi.bidata.boardgame.backend.model.dice.DiceRoll;
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
  /**
   * Triggered when a player moves from one position on the board to another. This method can be
   * used to notify observers about the player's movement, update game state, or trigger actions
   * related to the movement.
   *
   * @param playerId The unique identifier of the player who moved.
   * @param oldPosition The position on the board where the player was before moving.
   * @param newPosition The position on the board where the player has moved to.
   */
  void onPlayerMoved(UUID playerId, int oldPosition, int newPosition);

  /**
   * Triggered when a player purchases a property during the game. This method can be used by
   * observers to update the game state, notify players, or record transaction details.
   *
   * @param playerId The unique identifier of the player who purchased the property.
   * @param propertyId The unique identifier of the property that was purchased.
   */
  void onPropertyPurchased(int playerId, int propertyId);

  /**
   * Triggered when a dice roll occurs in the game. This method allows observers to react to the
   * outcome of a dice roll, such as updating the game state, notifying players, or handling
   * specific actions tied to the dice results.
   *
   * @param diceRoll An instance of {@code DiceRoll} representing the outcome of the dice roll,
   *     which contains the face values of each die rolled.
   */
  void onDiceRolled(DiceRoll diceRoll);

  /**
   * Triggered when a player's balance changes during the game. This method can be used by observers
   * to react to balance adjustments, update game state, or notify players about financial events
   * such as transactions, penalties, or rewards.
   *
   * @param playerId The unique identifier of the player whose balance has changed.
   * @param oldBalance The previous balance of the player before the change.
   * @param newBalance The updated balance of the player after the change.
   */
  void onPlayerBalanceChanged(UUID playerId, int oldBalance, int newBalance);
}
