package edu.ntnu.idi.bidata.boardgame.common.event;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.dice.DiceRoll;
import java.util.Objects;

public record DiceRolledEvent(DiceRoll diceRoll) implements Event {
  public DiceRolledEvent {
    Objects.requireNonNull(diceRoll, "DiceRoll cannot be null!");
  }
}
