package edu.ntnu.idi.bidata.boardgame.games.monopoly.model;

import static edu.ntnu.idi.bidata.boardgame.common.util.InputHandler.nextLine;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.util.StringFormatter;
import edu.ntnu.idi.bidata.boardgame.core.TileAction;
import edu.ntnu.idi.bidata.boardgame.core.model.Game;
import edu.ntnu.idi.bidata.boardgame.core.model.dice.Dice;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.MonopolyBoard;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.InsufficientFundsException;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
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
import java.util.TreeMap;

/**
 * @author Nick Heggø
 * @version 2025.05.14
 */
public class MonopolyGame extends Game<MonopolyTile, MonopolyPlayer> {

  public MonopolyGame(
      EventBus eventBus, MonopolyBoard monopolyBoard, List<MonopolyPlayer> players) {
    super(eventBus, monopolyBoard, players);
    players.forEach(player -> player.addBalance(200));
  }

  @Override
  public void nextTurn() {
    playTurn(getNextPlayer());
  }

  /**
   * In Monopoly, a player gets to roll again immediately if:
   * <li>They roll doubles (same number on both dice) But, if a player rolls doubles three times in
   *     a row, they go to jail immediately instead of taking a third extra turn.
   *
   * @param player
   */
  private void playTurn(MonopolyPlayer player) {
    var diceRoll = Dice.roll(2);
    notifyDiceRolled(diceRoll);
    movePlayer(player, diceRoll.getTotal());
    var action = tileActionOf(getTile(player.getPosition()));
    action.execute(player);
    if (diceRoll.areDiceEqual()) {
      println(
          "Player %s rolled a double! They are forced to move again.".formatted(player.getName()));
      playTurn(player); // recursive
    }
  }

  @Override
  protected void completeRoundAction(MonopolyPlayer player) {
    player.addBalance(200);
  }

  @Override
  public Map.Entry<Integer, List<MonopolyPlayer>> getWinners() {
    var treeMap = new TreeMap<Integer, List<MonopolyPlayer>>();
    for (var player : getPlayers()) {
      treeMap.computeIfAbsent(player.getNetWorth(), unused -> new ArrayList<>()).add(player);
    }
    return treeMap.reversed().firstEntry();
  }

  @Override
  public MonopolyBoard getBoard() {
    return (MonopolyBoard) super.getBoard();
  }

  // ------------------------  APIs  ------------------------

  public JailMonopolyTile getJailTile() {
    return getBoard().getJailTile();
  }

  /**
   * Sends a player to jail by teleporting them to the jail tile and marking them as jailed.
   *
   * @param player the player to send to jail
   */
  public void sendPlayerToJail(MonopolyPlayer player) {
    player.setPosition(getBoard().getTilePosition(getJailTile()));
    getJailTile().jailForNumberOfRounds(player, 2);
  }

  // ------------------------  private  ------------------------

  /**
   * Factory method to create a {@code TileAction} based on the type of tile.
   *
   * @param tile the tile the player has landed on
   * @return the corresponding TileAction
   */
  private TileAction<MonopolyPlayer> tileActionOf(MonopolyTile tile) {
    return switch (tile) {
      case OwnableMonopolyTile(Ownable ownable) -> ownableAction(ownable);
      case TaxMonopolyTile(int percentage) -> payPercentageTax(percentage);
      case GoToJailMonopolyTile unused -> this::sendPlayerToJail;
      case FreeParkingMonopolyTile unused -> player -> println("Free parking");
      case JailMonopolyTile unused -> player -> println("Visiting Jail");
      case StartMonopolyTile unused -> player -> println("On start Tile");
    };
  }

  private TileAction<MonopolyPlayer> payPercentageTax(int percentage) {
    return owner -> {
      int amountToPay = (owner.getBalance() * percentage) / 100;
      owner.pay(amountToPay);
      println(
          "%s paid a tax of $%d (%d%% of balance)."
              .formatted(owner.getName(), amountToPay, percentage));
    };
  }

  /**
   * Returns the action associated with ownable tiles like properties, railroads, and utilities.
   *
   * @param ownable the ownable asset
   * @return the TileAction for the asset
   */
  private TileAction<MonopolyPlayer> ownableAction(Ownable ownable) {
    return player -> {
      MonopolyPlayer monopolyPlayer =
          getPlayers().stream().filter(p -> p.isOwnerOf(ownable)).findFirst().orElse(null);

      if (monopolyPlayer == null) {
        println(player.getName() + " landed on unowned " + ownable + ".");
        if (confirmPurchase(player, ownable)) {
          player.purchase(ownable);
          println(player.getName() + " purchased " + ownable + "!");
        } else {
          println(player.getName() + " declined to purchase " + ownable + ".");
        }
      } else if (monopolyPlayer != player) {
        println(
            player.getName() + " landed on " + monopolyPlayer.getName() + "'s " + ownable + ".");
        int rent = ownable.rent();
        player.pay(rent);
        monopolyPlayer.addBalance(rent);
        println(
            player.getName() + " paid $" + rent + " in rent to " + monopolyPlayer.getName() + ".");
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
  private boolean confirmPurchase(MonopolyPlayer player, Ownable ownable) {
    if (player.hasSufficientFunds(ownable.price())) {
      String prompt =
          switch (ownable) {
            case Property property ->
                "Do you want to purchase %s of %s category for $%d?"
                    .formatted(
                        property.getName(),
                        StringFormatter.formatEnum(property.getColor()),
                        property.price());
            case Railroad(int price) -> "Do you want to purchase a railroad for $" + price + "?";
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
  private boolean processPurchase(MonopolyPlayer player, Ownable ownable) {
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
  private void askToUpgrade(MonopolyPlayer player, Property property) {
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
  private void askToBuildHouse(MonopolyPlayer player, Property property) {
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
  private void askToBuildHotel(MonopolyPlayer player, Property property) {
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

  // ------------------------  getters and setters  ------------------------

}
