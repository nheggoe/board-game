package edu.ntnu.idi.bidata.boardgame.backend.model;

import static edu.ntnu.idi.bidata.boardgame.backend.util.InputHandler.nextLine;
import static edu.ntnu.idi.bidata.boardgame.backend.util.OutputHandler.println;

import edu.ntnu.idi.bidata.boardgame.backend.event.Event;
import edu.ntnu.idi.bidata.boardgame.backend.event.EventListener;
import edu.ntnu.idi.bidata.boardgame.backend.event.EventManager;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.DiceRoll;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.InsufficientFundsException;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Ownable;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Property;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Railroad;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Utility;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.CornerTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.FreeParkingTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.GoToJailTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.JailTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.OwnableTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.StartTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.TaxTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.Tile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.TileAction;
import edu.ntnu.idi.bidata.boardgame.backend.util.OutputHandler;
import edu.ntnu.idi.bidata.boardgame.backend.util.StringFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
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
public class Game implements EventManager {

  private final UUID id;
  private final Board board;
  private final List<Player> players;
  private final List<EventListener> eventListeners;
  private boolean isEnded;

  private String gameSaveName;

  public Game(Board board, List<Player> players) {
    this.id = UUID.randomUUID();
    this.board = Objects.requireNonNull(board, "Board cannot be null!");
    this.players = new ArrayList<>(Objects.requireNonNull(players, "Players cannot be null!"));
    this.eventListeners = new ArrayList<>();
    this.isEnded = false;
    players.forEach(player -> player.addBalance(200));
  }

  // ------------------------  APIs  ------------------------

  public void printTiles() {
    board.tiles().forEach(OutputHandler::println);
  }

  public void movePlayer(UUID playerId, DiceRoll roll) {
    var player =
        getPlayerById(playerId)
            .orElseThrow(() -> new IllegalArgumentException("Player not found!"));
    int oldPositon = player.getPosition();
    int newPosition = (player.getPosition() + roll.getTotal()) % board.size();
    player.setPosition(newPosition);
    if (oldPositon > newPosition) {
      player.addBalance(200);
    }
    notifyPlayerMoved(player, newPosition);
  }

  private Optional<Player> getPlayerById(UUID playerId) {
    return players.stream().filter(player -> player.getId().equals(playerId)).findFirst();
  }

  public JailTile getJailTile() {
    return board.getJailTile();
  }

  public Tile getTile(int position) {
    return board.getTile(position);
  }

  public void endGame() {
    isEnded = true;
  }

  public void attach(EventListener observer) {}

  public void detach(EventListener observer) {}

  /**
   * Sends a player to jail by teleporting them to the jail tile and marking them as jailed.
   *
   * @param player the player to send to jail
   */
  public void sendPlayerToJail(Player player) {
    player.setPosition(board.getTilePosition(getJailTile()));
    getJailTile().jailForNumberOfRounds(player, 2);
  }

  public Map.Entry<Integer, List<Player>> getWinners() {
    var treeMap = new TreeMap<Integer, List<Player>>();
    players.forEach(
        player ->
            treeMap.computeIfAbsent(player.getNetWorth(), unused -> new ArrayList<>()).add(player));
    return treeMap.reversed().firstEntry();
  }

  @Override
  public void addListener(EventListener listener) {
    Objects.requireNonNull(listener, "Observer cannot be null!");
    eventListeners.add(listener);
  }

  @Override
  public void removeListener(EventListener listener) {
    Objects.requireNonNull(listener, "Observer cannot be null!");
    eventListeners.remove(listener);
  }

  @Override
  public void update(Event event) {
    eventListeners.forEach(listener -> listener.update(event));
  }

  // ------------------------  private  ------------------------

  private TileAction tileActionOf(Tile tile) {
    return switch (tile) {
      case OwnableTile(Ownable ownable) -> ownableAction(ownable);
      case TaxTile(int amount) -> player -> player.pay(amount);
      case CornerTile cornerTile -> getCornerTileAction(cornerTile);
    };
  }

  private TileAction getCornerTileAction(CornerTile tile) {
    return switch (tile) {
      case GoToJailTile unused -> player -> sendPlayerToJail(player);
      case FreeParkingTile unused -> player -> println("Free parking");
      case JailTile unused -> player -> println("Visiting Jail");
      case StartTile unused -> player -> println("On start Tile");
    };
  }

  private TileAction ownableAction(Ownable ownable) {
    return player -> {
      if (!player.isOwnerOf(ownable) && !confirmPurchase(player, ownable)) {
        player.pay(ownable.rent());
      } else {
        println("Welcome owner");
      }
    };
  }

  private boolean confirmPurchase(Player player, Ownable ownable) {
    if (player.hasSufficientFunds(ownable.price())) {
      String prompt =
          switch (ownable) {
            case Property(String name, var color, int price) ->
                "Do you want to purchase %s of %s category for $%d?"
                    .formatted(name, StringFormatter.formatEnum(color), price);
            case Railroad(int price) -> "Do you want to purchase railroad for $" + price + " ?";
            case Utility(String name, int price) ->
                "Do you want to purchase %s for $%d?".formatted(name, price);
          };
      println(prompt);
      if (nextLine().equals("yes")) {
        return processPurchase(player, ownable);
      }
    }
    return false;
  }

  private boolean processPurchase(Player player, Ownable ownable) {
    try {
      player.purchase(ownable);
      return true;
    } catch (InsufficientFundsException e) {
      println(e.getMessage());
      return false;
    }
  }

  private void notifyPlayerMoved(Player player, int newPosition) {
    update(new Event(Event.Type.PLAYER_MOVED, player.getId(), newPosition));
  }

  private void notifyDiceRolled(Player player, DiceRoll diceRoll) {
    update(new Event(Event.Type.DICE_ROLLED, player.getId(), diceRoll));
  }

  // ------------------------  getters and setters  ------------------------

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

  public List<Player> getPlayers() {
    return players;
  }

  public List<Tile> getTiles() {
    return board.tiles();
  }
}
