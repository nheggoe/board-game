package edu.ntnu.idi.bidata.boardgame.backend.core;

import static edu.ntnu.idi.bidata.boardgame.backend.util.InputHandler.*;
import static edu.ntnu.idi.bidata.boardgame.backend.util.OutputHandler.*;

import edu.ntnu.idi.bidata.boardgame.backend.model.Game;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.Dice;
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
import edu.ntnu.idi.bidata.boardgame.backend.util.StringFormatter;
import java.util.Objects;

/**
 * The {@code GameEngine} class is responsible for managing the core game loop and logic. It
 * processes each game round, moves players, checks for tile actions, and determines if a player has
 * won.
 *
 * <p>This class interacts with the {@link Board}, {@link Dice}, and {@link Player} objects to
 * facilitate game progression.
 *
 * @author Nick Heggø
 * @version 2025.04.15
 */
public class GameEngine {

  private boolean running = true;
  private final int roundNumber = 1;

  private final Game game;
  private final TurnManager turnManager;

  public GameEngine(Game game, TurnManager turnManager) {
    this.game = Objects.requireNonNull(game, "Game cannot be null!");
    this.turnManager = Objects.requireNonNull(turnManager, "TurnManager cannot be null!");
  }

  /** Starts and manages the game loop, allowing players to take turns until the game ends. */
  public void run() {
    println("Game has started! Initial player positions:");
    printPlayerLocations();

    while (running) {
      println("Press enter to play round or 'exit' to quit:");
      String input = nextLine();
      if (input.equalsIgnoreCase("exit")) {
        running = false;
      } else {
        playRound();
      }
    }
  }

  /**
   * Executes a game round by rolling the dice, moving players, checking tile actions, and
   * determining if any player has won.
   */
  private void playRound() {}

  /**
   * Checks if a player lands on a tile that has an action and executes it.
   *
   * @param player The player whose current tile is checked for an action.
   */
  private void executeTileAction(Player player) {
    try {
      tileActionOf(game.getTile(player.getPosition())).execute(player);
    } catch (InsufficientFundsException e) {
      println("");
    }
  }

  /** Prints the current locations of all players. */
  private void printPlayerLocations() {}

  /** Checks if any player has reached the winning tile and ends the game if a winner is found. */
  private void checkWinningStatus() {
    var winners = game.getWinners().getValue();
    if (winners.isEmpty()) {
      throw new IllegalArgumentException("Failed to retrieve winners.");
    } else if (winners.size() == 1) {
      Player winner = winners.getFirst();
      println(
          "The winner is %s with net worth of $%d!"
              .formatted(winner.getName(), winner.getNetWorth()));
    } else {
      println("We have multiple winners!");
      for (int i = 0; i < winners.size(); i++) {
        println("%d. %s".formatted((i + 1), winners.get(i).getName()));
      }
      println(
          "All finished the game with net worth of $%d"
              .formatted(winners.getFirst().getNetWorth()));
    }
  }

  // ------------------------  tile action  ------------------------

  private TileAction tileActionOf(Tile tile) {
    return switch (tile) {
      case OwnableTile(Ownable ownable) -> ownableAction(ownable);
      case TaxTile(int amount) -> player -> player.pay(amount);
      case CornerTile cornerTile -> getCornerTileAction(cornerTile);
    };
  }

  private TileAction getCornerTileAction(CornerTile tile) {
    return switch (tile) {
      case GoToJailTile unused -> player -> game.sendPlayerToJail(player);
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
}
