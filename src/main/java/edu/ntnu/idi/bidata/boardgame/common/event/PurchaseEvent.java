package edu.ntnu.idi.bidata.boardgame.common.event;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Owner;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Property;
import java.util.Objects;

public record PurchaseEvent(Owner owner, Property payload) implements Event {
  public PurchaseEvent {
    Objects.requireNonNull(owner, "Owner cannot be null!");
    Objects.requireNonNull(payload, "Property cannot be null!");
  }
}
