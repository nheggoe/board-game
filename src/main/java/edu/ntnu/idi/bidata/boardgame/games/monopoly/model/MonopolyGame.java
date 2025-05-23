package edu.ntnu.idi.bidata.boardgame.games.monopoly.model;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.type.MonopolyEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.type.UserInterfaceEvent;
import edu.ntnu.idi.bidata.boardgame.common.util.AlertFactory;
import edu.ntnu.idi.bidata.boardgame.common.util.StringFormatter;
import edu.ntnu.idi.bidata.boardgame.core.model.Game;
import edu.ntnu.idi.bidata.boardgame.core.model.TileAction;
import edu.ntnu.idi.bidata.boardgame.core.model.dice.Dice;
import edu.ntnu.idi.bidata.boardgame.core.model.dice.DiceRoll;
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
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Represents a Monopoly game. It is an extension of the generic {@code Game} class tailored to
 * Monopoly-specific tiles, players, and rules. Manages the gameplay logic, player turns, and
 * interactions with Monopoly tiles and actions.
 */
public class MonopolyGame extends Game<MonopolyTile, MonopolyPlayer> {

  /**
   * Constructs a new instance of the MonopolyGame class.
   *
   * <p>This constructor initializes the game with the provided event bus, game board, and list of
   * players. Additionally, each player is given an initial balance of 200 units.
   *
   * @param eventBus the event bus used for managing game-related events
   * @param monopolyBoard the game board used in the Monopoly game
   * @param players the list of players participating in the game
   */
  public MonopolyGame(
      EventBus eventBus, MonopolyBoard monopolyBoard, List<MonopolyPlayer> players) {
    super(eventBus, monopolyBoard, players);
    players.forEach(player -> player.addBalance(200));
  }

  @Override
  public void nextTurn() {
    if (isEnded()) {
      return;
    }

    int doubleCount = 0;
    var player = getNextPlayer();
    var jail = getJailTile();
    if (jail.isPlayerInJail(player)) {
      jail.releaseIfPossible(player);
      boolean isOutOfJail = jail.isPlayerInJail(player);
      if (isOutOfJail) {
        println("%s is out of jail.".formatted(player.getName()));
      } else {
        println(
            "%s got %d rounds left in jail."
                .formatted(player.getName(), jail.getNumberOfRoundsLeft(player)));
      }
      return;
    }

    var diceRoll = playTurn(player);

    while (diceRoll.areDiceEqual()) {
      if (doubleCount >= 3) {
        AlertFactory.createAlert(
                Alert.AlertType.INFORMATION,
                "Player has rolled doubles 3 times in a row. They are forced to go to jail.")
            .showAndWait();
        sendPlayerToJail(player);
        break;
      }
      AlertFactory.createAlert(
              Alert.AlertType.INFORMATION,
              "Player %s rolled a double! They need to move again.".formatted(player.getName()))
          .showAndWait();
      diceRoll = playTurn(player);
      doubleCount++;
    }
  }

  /**
   * In Monopoly, a player gets to roll again immediately if:
   * <li>They roll doubles (the same number on both dice) But, if a player rolls doubles three times
   *     in a row, they go to jail immediately instead of taking a third extra turn.
   *
   * @param player the player to play a turn (roll -> move -> action)
   */
  private DiceRoll playTurn(MonopolyPlayer player) {
    var diceRoll = Dice.roll(2);
    notifyDiceRolled(diceRoll);
    movePlayer(player, diceRoll.getTotal());
    var action = tileActionOf(getTile(player.getPosition()));
    try {
      action.execute(player);
    } catch (InsufficientFundsException e) {
      removePlayer(player);
    }
    return diceRoll;
  }

  @Override
  protected void removePlayer(MonopolyPlayer player) {
    println("%s has gone bankrupt and is removed from the game.".formatted(player.getName()));
    super.removePlayer(player);

    if (getPlayers().size() == 1) {
      MonopolyPlayer winner = getPlayers().getFirst();
      println("%s has won the game!".formatted(winner.getName()));

      Platform.runLater(
          () -> {
            AlertFactory.createAlert(
                    Alert.AlertType.INFORMATION, "%s has won the game!".formatted(winner.getName()))
                .showAndWait();
            endGame();
          });
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

  private JailMonopolyTile getJailTile() {
    return getBoard().getJailTile();
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
      case TaxMonopolyTile(int percentage) -> payTaxAction(percentage);
      case GoToJailMonopolyTile unused -> goToJailAction();
      case FreeParkingMonopolyTile unused -> freeParkingAction();
      case JailMonopolyTile unused -> visitJailAction();
      case StartMonopolyTile unused -> startTileAction();
    };
  }

  private TileAction<MonopolyPlayer> goToJailAction() {
    return this::sendPlayerToJail;
  }

  private TileAction<MonopolyPlayer> startTileAction() {
    return player -> println("%s landed on start Tile (+$200)".formatted(player.getName()));
  }

  private TileAction<MonopolyPlayer> visitJailAction() {
    return player -> println("Visiting Jail");
  }

  private TileAction<MonopolyPlayer> freeParkingAction() {
    return player -> println("Free parking");
  }

  private TileAction<MonopolyPlayer> ownableAction(Ownable ownable) {
    return player -> {
      var optionalOwner = getOwner(ownable);

      if (optionalOwner.isEmpty()) {
        handlePurchase(ownable, player);
      } else {
        var owner = optionalOwner.orElseThrow();

        if (Objects.equals(owner, player) && ownable instanceof Property property) {
          handleUpgrade(player, property);
        } else {
          handleRent(owner, player, ownable);
        }
      }
    };
  }

  private TileAction<MonopolyPlayer> payTaxAction(int percentage) {
    return owner -> {
      int amountToPay = (owner.getBalance() * percentage) / 100;
      owner.pay(amountToPay);
      println(
          "%s paid a tax of $%d (%d%% of balance)."
              .formatted(owner.getName(), amountToPay, percentage));
    };
  }

  private void handlePurchase(Ownable ownable, MonopolyPlayer player) {
    var sb = new StringBuilder();
    sb.append(player.getName()).append(" landed on unowned ").append(ownable).append(".");
    println(sb);

    var output =
        switch (processTransaction(player, ownable)) {
          case TRANSACTION_COMPLETED -> {
            getEventBus().publishEvent(new MonopolyEvent.Purchased(player, ownable));
            yield player.getName() + " purchased " + ownable + "!";
          }
          case DENY -> player.getName() + " declined to purchase " + ownable + ".";
          case INEFFICIENT_FUNDS ->
              player.getName() + " doesn't have enough money to purchase " + ownable + ".";
        };
    println(output);
  }

  private void handleRent(MonopolyPlayer owner, MonopolyPlayer player, Ownable ownable) {
    if (Objects.equals(owner, player)) {
      println("%s landed on his own property.".formatted(player.getName()));
      return;
    }
    var sb = new StringBuilder();
    sb.append(player.getName())
        .append(" landed on ")
        .append(owner.getName())
        .append("'s ")
        .append(ownable)
        .append(".")
        .append(System.lineSeparator())
        .append(System.lineSeparator());

    int rent = ownable.rent();
    try {
      player.pay(rent);
      owner.addBalance(rent);
    } catch (InsufficientFundsException e) {
      println(
          "%s couldn't afford $%d in rent to %s."
              .formatted(player.getName(), rent, owner.getName()));
      removePlayer(player);
      return;
    }

    sb.append(player.getName())
        .append(" paid $")
        .append(rent)
        .append(" in rent to ")
        .append(owner.getName())
        .append(".");

    println(sb);
  }

  private void sendPlayerToJail(MonopolyPlayer player) {
    player.setPosition(getBoard().getTilePosition(getJailTile()));
    getJailTile().jailForNumberOfRounds(player, 2);
    getEventBus().publishEvent(new MonopolyEvent.PlayerSentToJail(player));
    getEventBus()
        .publishEvent(
            new UserInterfaceEvent.Output(
                "%s sent to jail for 2 rounds".formatted(player.getName())));
  }

  private PurchaseOption processTransaction(MonopolyPlayer player, Ownable ownable) {
    if (!player.hasSufficientFunds(ownable.price())) {
      return PurchaseOption.INEFFICIENT_FUNDS;
    }

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

    var result = AlertFactory.createAlert(Alert.AlertType.CONFIRMATION, prompt).showAndWait();

    if (result.isEmpty() || result.get() == ButtonType.CANCEL) {
      return PurchaseOption.DENY;
    }
    return processPurchase(player, ownable)
        ? PurchaseOption.TRANSACTION_COMPLETED
        : PurchaseOption.INEFFICIENT_FUNDS;
  }

  /**
   * Attempts to finalise a purchase, deducting the player's balance.
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
      return false;
    }
  }

  /**
   * Allows the player to upgrade a property if possible.
   *
   * @param owner the property owner
   * @param property the property to upgrade
   */
  private void handleUpgrade(MonopolyPlayer owner, Property property) {
    if (property.hasHotel()) {
      println(
          "%s already have a Hotel on this property. No further upgrades possible."
              .formatted(owner.getName()));
      return;
    }

    if (property.canBuildHouse()) {
      askToBuildHouse(owner, property);
    } else {
      askToBuildHotel(owner, property);
    }
  }

  /**
   * Asks the player if they want to build a house.
   *
   * @param player the player
   * @param property the property to upgrade
   */
  private void askToBuildHouse(MonopolyPlayer player, Property property) {
    println(
        "%s has %d houses on %s."
            .formatted(player.getName(), property.countHouses(), property.getName()));
    var alert =
        AlertFactory.createAlert(
            Alert.AlertType.CONFIRMATION,
            "Would you like to build a house on %s for $50?".formatted(property.getName()));
    var result = alert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
      int houseCost = 50;
      if (player.hasSufficientFunds(houseCost)) {
        player.pay(houseCost);
        property.addUpgrade(new Upgrade(UpgradeType.HOUSE, 20));
        println("%s built a house on %s!".formatted(player.getName(), property.getName()));
      } else {
        println("%s don't have enough money to build a house.".formatted(player.getName()));
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
    println("%s has 4 houses on %s.".formatted(player.getName(), property.getName()));
    var alert =
        AlertFactory.createAlert(
            Alert.AlertType.CONFIRMATION,
            "Would you like to upgrade to a Hotel on %s for $100?".formatted(property.getName()));
    var result = alert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
      int hotelCost = 100;
      if (player.hasSufficientFunds(hotelCost)) {
        player.pay(hotelCost);
        property.addUpgrade(new Upgrade(UpgradeType.HOTEL, 100));
        println("%s upgraded to a Hotel on %s!".formatted(player.getName(), property.getName()));
      } else {
        println("%s doesn't have enough money to build a hotel.".formatted(player.getName()));
      }
    }
  }

  private Optional<MonopolyPlayer> getOwner(Ownable ownable) {
    return getPlayers().stream().filter(player -> player.isOwnerOf(ownable)).findFirst();
  }

  private enum PurchaseOption {
    TRANSACTION_COMPLETED,
    DENY,
    INEFFICIENT_FUNDS,
  }
}
