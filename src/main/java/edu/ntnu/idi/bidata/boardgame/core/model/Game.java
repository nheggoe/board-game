package edu.ntnu.idi.bidata.boardgame.core.model;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.type.DiceRolledEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.type.OutputEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.type.PlayerMovedEvent;
import edu.ntnu.idi.bidata.boardgame.core.model.dice.DiceRoll;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Nick Heggø
 * @version 2025.05.09
 */
public abstract class Game<P extends Player, T extends Tile> {

  // state
  private final UUID id;
  private String gameSaveName;
  private boolean isEnded;
  private final TurnManager<P> turnManager;

  // injected dependencies
  private final EventBus eventBus;
  private final Board<T> board;

  protected Game(EventBus eventBus, Board<T> board, List<P> players) {
    this.id = UUID.randomUUID();
    this.isEnded = false;
    this.turnManager = new TurnManager<>(players);
    this.eventBus = Objects.requireNonNull(eventBus, "EventBus cannot be null!");
    this.board = Objects.requireNonNull(board, "Board cannot be null!");
  }

  // ------------------------  API  ------------------------

  public abstract void nextTurn();

  public void printTiles() {
    StringBuilder sb = new StringBuilder();
    sb.append("Tiles on the board:\n");
    for (var tile : getTiles()) {
      sb.append(tile).append("\n");
    }
    sb.delete(sb.length() - 3, sb.length());
    println(sb);
  }

  public void endGame() {
    isEnded = true;
  }

  // ------------------------  internal  ------------------------

  protected void println(Object o) {
    eventBus.publishEvent(new OutputEvent(o.toString()));
  }

  protected void notifyPlayerMoved(Player player) {
    eventBus.publishEvent(new PlayerMovedEvent(player));
  }

  protected void notifyDiceRolled(DiceRoll diceRoll) {
    eventBus.publishEvent(new DiceRolledEvent(diceRoll));
  }

  protected void movePlayer(P player, DiceRoll roll) {
    int oldPositon = player.getPosition();
    int newPosition = (player.getPosition() + roll.getTotal()) % board.size();
    player.setPosition(newPosition);
    if (oldPositon > newPosition) {
      completeRoundAction(player);
    }
    notifyDiceRolled(roll);
    notifyPlayerMoved(player);
  }

  protected abstract void completeRoundAction(P player);

  // ------------------------  Getters and Setters  ------------------------

  public abstract Map.Entry<Integer, List<P>> getWinners();

  public P getCurrentPlayer() {
    return turnManager.getNextPlayer();
  }

  public Tile getTile(int position) {
    return board.getTileAtIndex(position);
  }

  public UUID getId() {
    return id;
  }

  public String getGameSaveName() {
    return gameSaveName;
  }

  public void setGameSaveName(String gameSaveName) {
    this.gameSaveName = gameSaveName;
  }

  public List<T> getTiles() {
    return board.tiles();
  }

  public boolean isEnded() {
    return isEnded;
  }

  protected EventBus getEventBus() {
    return eventBus;
  }

  protected P getNextPlayer() {
    return turnManager.getNextPlayer();
  }

  protected Board<T> getBoard() {
    return board;
  }

  public List<P> getPlayers() {
    return turnManager.getPlayers();
  }
}
