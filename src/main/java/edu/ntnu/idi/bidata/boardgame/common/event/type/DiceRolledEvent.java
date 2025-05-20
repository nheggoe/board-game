package edu.ntnu.idi.bidata.boardgame.common.event.type;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.core.model.dice.DiceRoll;

/**
 * @param diceRoll the dice roll that was rolled, must not be null
 * @author Nick Heggø
 * @version 2025.05.08
 */
public record DiceRolledEvent(DiceRoll diceRoll) implements Event {
  public DiceRolledEvent {
    requireNonNull(diceRoll, "DiceRoll cannot be null!");
  }
}
