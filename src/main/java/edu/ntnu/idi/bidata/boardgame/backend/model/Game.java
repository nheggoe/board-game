package edu.ntnu.idi.bidata.boardgame.backend.model;

import edu.ntnu.idi.bidata.boardgame.backend.core.GameObserver;
import edu.ntnu.idi.bidata.boardgame.backend.core.TurnManager;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.DiceRoll;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.JailTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.Tile;
import edu.ntnu.idi.bidata.boardgame.backend.util.OutputHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * The {@link Game} class represents a board game instance. It manages players, their states, and
 * the game board. The game maintains a unique identifier and keeps track of players and their
 * interactions with the board.
 *
 * <p>This class implements {@link Iterable}, allowing iteration over the collection of players
 * participating in the game.
 *
 * @author Nick Heggø
 * @version 2025.04.22
 */
public class Game {

  private final UUID id;
  private final TurnManager players;
  private final List<GameObserver> observers;

  private String saveName;
  private boolean isEnded;
  private Board board;

  private Game(List<Player> players) {
    this.id = UUID.randomUUID();
    this.observers = new ArrayList<>();
    this.players = new TurnManager(players);
  }

  public Game(Board board, List<Player> players) {
    this(players);
    setBoard(board);
    players.forEach(player -> player.addBalance(200));
    isEnded = false;
  }

  // ------------------------  APIs  ------------------------

  public void printTiles() {
    board.tiles().forEach(OutputHandler::println);
  }

  public Map.Entry<Integer, List<Player>> getWinners() {
    return players.getWinners();
  }

  public void movePlayer(Player player, DiceRoll roll) {
    int oldPositon = player.getPosition();
    int newPosition = (player.getPosition() + roll.getTotal()) % board.size();
    player.setPosition(newPosition);
    if (oldPositon > newPosition) {
      player.addBalance(200);
    }
    notifyPlayerMoved(player, oldPositon, newPosition);
  }

  public JailTile getJailTile() {
    return board.getJailTile();
  }

  public Tile getTile(int position) {
    return board.getTile(position);
  }

  public boolean isEnded() {
    return isEnded;
  }

  public void endGame() {
    isEnded = true;
  }

  public Player getCurrentPlayer() {
    return players.getCurrentPlayer();
  }

  public Player nextPlayer() {
    return players.next();
  }

  public void attach(GameObserver observer) {
    Objects.requireNonNull(observer, "Observer cannot be null!");
    observers.add(observer);
  }

  public void detach(GameObserver observer) {
    Objects.requireNonNull(observer, "Observer cannot be null!");
    observers.remove(observer);
  }

  /**
   * Sends a player to jail by teleporting them to the jail tile and marking them as jailed.
   *
   * @param player the player to send to jail
   */
  public void sendPlayerToJail(Player player) {
    player.setPosition(board.getTilePosition(getJailTile()));
    getJailTile().jailForNumberOfRounds(player, 2);
  }

  // ------------------------  getters and setters  ------------------------

  private void setBoard(Board board) {
    if (board == null) {
      throw new IllegalArgumentException("Board cannot be null!");
    }
    this.board = board;
  }

  public UUID getId() {
    return id;
  }

  public void setSaveName(String saveName) {
    this.saveName = saveName;
  }

  public String getSaveName() {
    return saveName;
  }

  public Board getBoard() {
    return board;
  }

  public TurnManager getPlayers() {
    return players;
  }

  // ------------------------  private  ------------------------

  private void notifyPlayerMoved(Player player, int oldPositon, int newPosition) {
    for (var observer : observers) {
      observer.onPlayerMoved(player, oldPositon, newPosition);
    }
  }

  private void notifyDiceRolled(Player player, DiceRoll diceRoll) {
    for (var observer : observers) {
      observer.onDiceRolled(player, diceRoll);
    }
  }
}
