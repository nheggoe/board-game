package edu.ntnu.idi.bidata.boardgame.common.event;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.dice.DiceRoll;
import java.util.Objects;

public record DiceRolledEvent(Player player, DiceRoll payload) implements Event {
  public DiceRolledEvent {
    Objects.requireNonNull(payload, "DiceRoll cannot be null!");
  }
}
