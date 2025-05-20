package edu.ntnu.idi.bidata.boardgame.core.model;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.type.CoreEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.type.UserInterfaceEvent;
import edu.ntnu.idi.bidata.boardgame.core.model.dice.DiceRoll;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nick Heggø
 * @version 2025.05.09
 */
public abstract class Game<T extends Tile, P extends Player> {

  // state
  private final UUID id;
  private String gameSaveName;
  private boolean isEnded;

  @SuppressWarnings("java:S2065")
  private final transient TurnManager<P> turnManager;

  // injected dependencies

  @SuppressWarnings("java:S2065")
  private final transient EventBus eventBus;

  private final Board<T> board;

  protected Game(EventBus eventBus, Board<T> board, List<P> players) {
    this.id = UUID.randomUUID();
    this.isEnded = false;
    this.turnManager = new TurnManager<>(players);
    this.eventBus = requireNonNull(eventBus, "EventBus cannot be null!");
    this.board = requireNonNull(board, "Board cannot be null!");
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
    eventBus.publishEvent(new UserInterfaceEvent.Output(o.toString()));
  }

  protected void notifyPlayerMoved(Player player) {
    eventBus.publishEvent(new CoreEvent.PlayerMoved(player));
  }

  protected void notifyDiceRolled(DiceRoll diceRoll) {
    eventBus.publishEvent(new CoreEvent.DiceRolled(diceRoll));
  }

  public void movePlayer(P player, int numberOfTiles) {
    int oldPositon = player.getPosition();
    int newPosition = (player.getPosition() + numberOfTiles) % board.size();
    player.setPosition(newPosition);
    if (oldPositon > newPosition) {
      completeRoundAction(player);
    }
    notifyPlayerMoved(player);
  }

  protected void removePlayer(P player) {
    turnManager.removePlayer(player);
    eventBus.publishEvent(new CoreEvent.PlayerRemoved(player));
  }

  protected abstract void completeRoundAction(P player);

  // ------------------------  Getters and Setters  ------------------------

  public abstract Map.Entry<Integer, List<P>> getWinners();

  public P getCurrentPlayer() {
    return turnManager.getNextPlayer();
  }

  public T getTile(int position) {
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

  public P getNextPlayer() {
    return turnManager.getNextPlayer();
  }

  public Board<T> getBoard() {
    return board;
  }

  public List<P> getPlayers() {
    return turnManager.getPlayers();
  }
}
