package edu.ntnu.idi.bidata.boardgame.common.event;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Ownable;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Owner;
import java.util.Objects;

public record PurchaseEvent(Owner owner, Ownable ownable) implements Event {
  public PurchaseEvent {
    Objects.requireNonNull(owner, "Owner cannot be null!");
    Objects.requireNonNull(ownable, "Ownable cannot be null!");
  }
}
