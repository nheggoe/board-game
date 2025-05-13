package edu.ntnu.idi.bidata.boardgame.common.event.type;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Ownable;
import java.util.Objects;

/**
 * @param monopolyPlayer the player that purchased the owned object
 * @param ownable the object that was purchased
 * @author Nick Heggø
 * @version 2025.05.08
 */
public record PurchaseEvent(MonopolyPlayer monopolyPlayer, Ownable ownable) implements Event {
  public PurchaseEvent {
    Objects.requireNonNull(monopolyPlayer, "Owner cannot be null!");
    Objects.requireNonNull(ownable, "Ownable cannot be null!");
  }
}
