package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

import static edu.ntnu.idi.bidata.boardgame.backend.util.InputHandler.nextLine;
import static edu.ntnu.idi.bidata.boardgame.backend.util.OutputHandler.println;

import edu.ntnu.idi.bidata.boardgame.backend.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.InsufficientFundsException;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Ownable;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Property;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Railroad;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Utility;
import edu.ntnu.idi.bidata.boardgame.backend.model.upgrade.Upgrade;
import edu.ntnu.idi.bidata.boardgame.backend.model.upgrade.UpgradeType;
import edu.ntnu.idi.bidata.boardgame.backend.util.StringFormatter;

/**
 * {@code TileAction} defines the executable action that occurs when a player lands on a tile.
 *
 * <p>This functional interface is responsible for:
 *
 * <ul>
 *   <li>Handling purchases of ownable tiles like properties, utilities, and railroads
 *   <li>Charging rent to players when landing on owned properties
 *   <li>Applying tax penalties
 *   <li>Sending players to jail, free parking, or start
 *   <li>Allowing players to upgrade properties with houses or hotels
 * </ul>
 *
 * <p>It uses pattern matching to process different tile types in a clean, structured way.
 *
 * @author Mihailo Hranisavljevic, Nick Heggø
 * @version 2025.04.26
 */
@FunctionalInterface
public interface TileAction {

  /**
   * Executes the defined tile action for the given player.
   *
   * @param player the player who landed on the tile
   */
  void execute(Player player);

  /**
   * Factory method to create a {@code TileAction} based on the type of tile.
   *
   * @param tile the tile the player has landed on
   * @return the corresponding TileAction
   */
  static TileAction of(Tile tile) {
    return switch (tile) {
      case OwnableTile(Ownable ownable) -> ownableAction(ownable);
      case TaxTile(int percentage) ->
          player -> {
            int amountToPay = (player.getBalance() * percentage) / 100;
            player.pay(amountToPay);
            println(
                player.getName()
                    + " paid a tax of $"
                    + amountToPay
                    + " ("
                    + percentage
                    + "% of balance).");
          };
      case CornerTile cornerTile -> getCornerTileAction(cornerTile);
    };
  }

  /**
   * Returns the action associated with special corner tiles.
   *
   * @param tile the corner tile
   * @return the TileAction for the corner
   */
  private static TileAction getCornerTileAction(CornerTile tile) {
    return switch (tile) {
      case GoToJailTile unused ->
          player -> GameEngine.getInstance().getGame().orElseThrow().sendPlayerToJail(player);
      case FreeParkingTile unused -> player -> println("Free parking");
      case JailTile unused -> player -> println("Visiting Jail");
      case StartTile unused -> player -> println("On start Tile");
    };
  }

  /**
   * Returns the action associated with ownable tiles like properties, railroads, and utilities.
   *
   * @param ownable the ownable asset
   * @return the TileAction for the asset
   */
  private static TileAction ownableAction(Ownable ownable) {
    return player -> {
      var game = GameEngine.getInstance().getGame().orElseThrow();

      Player owner = game.stream().filter(p -> p.isOwnerOf(ownable)).findFirst().orElse(null);

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
  private static boolean confirmPurchase(Player player, Ownable ownable) {
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
  private static boolean processPurchase(Player player, Ownable ownable) {
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
  private static void askToUpgrade(Player player, Property property) {
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
  private static void askToBuildHouse(Player player, Property property) {
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
  private static void askToBuildHotel(Player player, Property property) {
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
}
