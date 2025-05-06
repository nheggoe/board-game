package edu.ntnu.idi.bidata.boardgame.games.monopoly.model;

import static edu.ntnu.idi.bidata.boardgame.common.util.InputHandler.nextLine;

import edu.ntnu.idi.bidata.boardgame.common.event.DiceRolledEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.EventListener;
import edu.ntnu.idi.bidata.boardgame.common.event.OutputEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.PlayerMovedEvent;
import edu.ntnu.idi.bidata.boardgame.common.util.OutputHandler;
import edu.ntnu.idi.bidata.boardgame.common.util.StringFormatter;
import edu.ntnu.idi.bidata.boardgame.core.TileAction;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.dice.DiceRoll;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.InsufficientFundsException;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Ownable;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Property;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Railroad;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Utility;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.FreeParkingMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.GoToJailMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.JailMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.MonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.OwnableMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.StartMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.TaxMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.upgrade.Upgrade;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.upgrade.UpgradeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Stream;

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

  private final EventBus eventBus;

  private final UUID id;
  private final Board board;
  private final List<Player> players;
  private boolean isEnded;

  private String gameSaveName;

  public Game(EventBus eventBus, Board board, List<Player> players) {
    this.eventBus = Objects.requireNonNull(eventBus, "EventBus cannot be null!");
    this.id = UUID.randomUUID();
    this.board = Objects.requireNonNull(board, "Board cannot be null!");
    this.players = new ArrayList<>(Objects.requireNonNull(players, "Players cannot be null!"));
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
    notifyDiceRolled(player, roll);
    notifyPlayerMoved(player);
  }

  private Optional<Player> getPlayerById(UUID playerId) {
    return players.stream().filter(player -> player.getId().equals(playerId)).findFirst();
  }

  public JailMonopolyTile getJailTile() {
    return board.getJailTile();
  }

  public MonopolyTile getTile(int position) {
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

  // ------------------------  private  ------------------------

  /**
   * Factory method to create a {@code TileAction} based on the type of tile.
   *
   * @param tile the tile the player has landed on
   * @return the corresponding TileAction
   */
  private TileAction tileActionOf(MonopolyTile tile) {
    return switch (tile) {
      case OwnableMonopolyTile(Ownable ownable) -> ownableAction(ownable);
      case TaxMonopolyTile(int percentage) -> payPercentageTax(percentage);
      case GoToJailMonopolyTile unused -> this::sendPlayerToJail;
      case FreeParkingMonopolyTile unused -> player -> println("Free parking");
      case JailMonopolyTile unused -> player -> println("Visiting Jail");
      case StartMonopolyTile unused -> player -> println("On start Tile");
    };
  }

  private TileAction payPercentageTax(int percentage) {
    return player -> {
      int amountToPay = (player.getBalance() * percentage) / 100;
      player.pay(amountToPay);
      println(
          "%s paid a tax of $%d (%d%% of balance)."
              .formatted(player.getName(), amountToPay, percentage));
    };
  }

  /**
   * Returns the action associated with ownable tiles like properties, railroads, and utilities.
   *
   * @param ownable the ownable asset
   * @return the TileAction for the asset
   */
  private TileAction ownableAction(Ownable ownable) {
    return player -> {
      Player owner = players.stream().filter(p -> p.isOwnerOf(ownable)).findFirst().orElse(null);

      if (owner == null) {
        println(player.getName() + " landed on unowned " + ownable + ".");
        if (confirmPurchase(player, ownable)) {
          player.purchase(ownable);
          println(player.getName() + " purchased " + ownable + "!");
        } else {
          println(player.getName() + " declined to purchase " + ownable + ".");
        }
      } else if (owner != player) {
        println(player.getName() + " landed on " + owner.getName() + "'s " + ownable + ".");
        int rent = ownable.rent();
        player.pay(rent);
        owner.addBalance(rent);
        println(player.getName() + " paid $" + rent + " in rent to " + owner.getName() + ".");
      } else {
        println(player.getName() + " landed on their own property: " + ownable + ".");
        if (ownable instanceof Property property) {
          askToUpgrade(player, property);
        }
      }
    };
  }

  /**
   * Asks the player if they want to purchase an unowned tile.
   *
   * @param player the player
   * @param ownable the ownable tile
   * @return true if purchase successful, false otherwise
   */
  private boolean confirmPurchase(Player player, Ownable ownable) {
    if (player.hasSufficientFunds(ownable.price())) {
      String prompt =
          switch (ownable) {
            case Property property ->
                "Do you want to purchase %s of %s category for $%d?"
                    .formatted(
                        property.getName(),
                        StringFormatter.formatEnum(property.getColor()),
                        property.price());
            case Railroad railroad ->
                "Do you want to purchase a railroad for $" + railroad.price() + "?";
            case Utility(String name, int price) ->
                "Do you want to purchase %s for $%d?".formatted(name, price);
          };

      println(prompt);
      if (nextLine().equalsIgnoreCase("yes")) {
        return processPurchase(player, ownable);
      }
    }
    return false;
  }

  /**
   * Attempts to finalize a purchase, deducting the player's balance.
   *
   * @param player the player
   * @param ownable the asset being purchased
   * @return true if successful, false if insufficient funds
   */
  private boolean processPurchase(Player player, Ownable ownable) {
    try {
      player.purchase(ownable);
      return true;
    } catch (InsufficientFundsException e) {
      println(e.getMessage());
      return false;
    }
  }

  /**
   * Allows the player to upgrade a property if possible.
   *
   * @param player the property owner
   * @param property the property to upgrade
   */
  private void askToUpgrade(Player player, Property property) {
    if (property.hasHotel()) {
      println("You already have a Hotel on this property. No further upgrades possible.");
      return;
    }

    if (property.canBuildHouse()) {
      askToBuildHouse(player, property);
    } else {
      askToBuildHotel(player, property);
    }
  }

  /**
   * Asks the player if they want to build a house.
   *
   * @param player the player
   * @param property the property to upgrade
   */
  private void askToBuildHouse(Player player, Property property) {
    println("You have %d houses on %s.".formatted(property.countHouses(), property.getName()));
    println("Would you like to build a house? (yes/no)");
    if (nextLine().equalsIgnoreCase("yes")) {
      int houseCost = 50;
      if (player.hasSufficientFunds(houseCost)) {
        player.pay(houseCost);
        property.addUpgrade(new Upgrade(UpgradeType.HOUSE, 20));
        println("You built a house on " + property.getName() + "!");
      } else {
        println("You don't have enough money to build a house.");
      }
    }
  }

  /**
   * Asks the player if they want to build a hotel after building enough houses.
   *
   * @param player the player
   * @param property the property to upgrade
   */
  private void askToBuildHotel(Player player, Property property) {
    println("You have 4 houses on %s.".formatted(property.getName()));
    println("Would you like to upgrade to a Hotel? (yes/no)");
    if (nextLine().equalsIgnoreCase("yes")) {
      int hotelCost = 100;
      if (player.hasSufficientFunds(hotelCost)) {
        player.pay(hotelCost);
        property.addUpgrade(new Upgrade(UpgradeType.HOTEL, 100));
        println("You upgraded to a Hotel on " + property.getName() + "!");
      } else {
        println("You don't have enough money to build a hotel.");
      }
    }
  }

  private void notifyPlayerMoved(Player player) {
    eventBus.publishEvent(new PlayerMovedEvent(player));
  }

  private void notifyDiceRolled(Player player, DiceRoll diceRoll) {
    eventBus.publishEvent(new DiceRolledEvent(player, diceRoll));
  }

  private void println(Object o) {
    eventBus.publishEvent(new OutputEvent(o.toString()));
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

  public List<MonopolyTile> getTiles() {
    return board.tiles();
  }

  public Stream<Player> stream() {
    return players.stream();
  }
}
