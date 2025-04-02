package edu.ntnu.idi.bidata.event;

import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;

public class GameEvent {
  private final String eventType;
  private Player player;
  private Object data;

  public GameEvent(String eventType) {
    this.eventType = eventType;
  }

  public String getEventType() {
    return eventType;
  }

  public Player getPlayer() {
    return player;
  }

  public Object getData() {
    return data;
  }
}
