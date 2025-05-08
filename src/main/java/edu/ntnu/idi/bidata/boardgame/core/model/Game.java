package edu.ntnu.idi.bidata.boardgame.core.model;

import edu.ntnu.idi.bidata.boardgame.common.event.DiceRolledEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.OutputEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.PlayerMovedEvent;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.dice.DiceRoll;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author Nick Heggø
 * @version 2025.05.08
 */
public abstract class Game<T extends Tile, P extends Player> {

  // state
  private final UUID id;
  private String gameSaveName;
  private boolean isEnded;

  // dependencies
  private final EventBus eventBus;
  private final Board<T> board;
  private final List<P> players;

  protected Game(EventBus eventBus, Board<T> board, List<P> players) {
    this.id = UUID.randomUUID();
    this.isEnded = false;
    this.eventBus = Objects.requireNonNull(eventBus, "EventBus cannot be null!");
    this.board = Objects.requireNonNull(board, "Board cannot be null!");
    this.players = new ArrayList<>(Objects.requireNonNull(players, "Players cannot be null!"));
  }

  public void printTiles() {
    StringBuilder sb = new StringBuilder();
  }

  public void movePlayer(UUID playerId, DiceRoll roll) {
    var player =
        getPlayerById(playerId)
            .orElseThrow(() -> new IllegalArgumentException("Player not found!"));
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

  private Optional<P> getPlayerById(UUID playerId) {
    return players.stream().filter(player -> player.getId().equals(playerId)).findFirst();
  }

  public Tile getTile(int position) {
    return board.getTileAtIndex(position);
  }

  public void endGame() {
    isEnded = true;
  }

  public abstract Map.Entry<Integer, List<P>> getWinners();

  protected void notifyPlayerMoved(Player player) {
    eventBus.publishEvent(new PlayerMovedEvent(player));
  }

  protected void notifyDiceRolled(DiceRoll diceRoll) {
    eventBus.publishEvent(new DiceRolledEvent(diceRoll));
  }

  protected void println(Object o) {
    eventBus.publishEvent(new OutputEvent(o.toString()));
  }

  public UUID getId() {
    return id;
  }

  public boolean isEnded() {
    return isEnded;
  }

  public void setGameSaveName(String gameSaveName) {
    this.gameSaveName = gameSaveName;
  }

  public String getGameSaveName() {
    return gameSaveName;
  }

  public List<UUID> getPlayerIds() {
    return players.stream().map(Player::getId).toList();
  }

  public List<P> getPlayers() {
    return players;
  }

  protected Board<T> getBoard() {
    return board;
  }

  protected EventBus getEventBus() {
    return eventBus;
  }

  public List<T> getTiles() {
    return board.tiles();
  }

  public Stream<P> stream() {
    return players.stream();
  }
}
