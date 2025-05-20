package edu.ntnu.idi.bidata.boardgame.common.event.type;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Ownable;

/**
 * @param monopolyPlayer the player that purchased the owned object
 * @param ownable the object that was purchased
 * @author Nick Heggø
 * @version 2025.05.08
 */
public record PurchaseEvent(MonopolyPlayer monopolyPlayer, Ownable ownable) implements Event {
  public PurchaseEvent {
    requireNonNull(monopolyPlayer, "Owner cannot be null!");
    requireNonNull(ownable, "Ownable cannot be null!");
  }
}
