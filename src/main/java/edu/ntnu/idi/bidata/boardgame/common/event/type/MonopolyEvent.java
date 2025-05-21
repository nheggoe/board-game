package edu.ntnu.idi.bidata.boardgame.common.event.type;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Ownable;

public sealed interface MonopolyEvent extends Event {

  record Purchased(MonopolyPlayer monopolyPlayer, Ownable ownable) implements MonopolyEvent {
    public Purchased {
      requireNonNull(monopolyPlayer, "Owner cannot be null!");
      requireNonNull(ownable, "Ownable cannot be null!");
    }
  }

  record UpgradePurchased(MonopolyPlayer monopolyPlayer, Ownable ownable) implements MonopolyEvent {
    public UpgradePurchased {
      requireNonNull(monopolyPlayer, "Owner cannot be null!");
      requireNonNull(ownable, "Ownable cannot be null!");
    }
  }
}
