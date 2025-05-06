package edu.ntnu.idi.bidata.boardgame.common.event;

public sealed interface Event
    permits DiceRolledEvent, OutputEvent, PlayerMovedEvent, PlayerRemovedEvent, PurchaseEvent {
  Object payload();
}
