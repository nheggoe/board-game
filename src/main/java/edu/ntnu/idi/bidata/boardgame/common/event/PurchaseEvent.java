package edu.ntnu.idi.bidata.boardgame.common.event;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Ownable;
import java.util.Objects;

public record PurchaseEvent(MonopolyPlayer monopolyPlayer, Ownable ownable) implements Event {
  public PurchaseEvent {
    Objects.requireNonNull(monopolyPlayer, "Owner cannot be null!");
    Objects.requireNonNull(ownable, "Ownable cannot be null!");
  }
}
