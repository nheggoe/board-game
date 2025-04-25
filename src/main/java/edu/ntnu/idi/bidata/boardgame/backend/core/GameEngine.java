package edu.ntnu.idi.bidata.boardgame.backend.core;

import static edu.ntnu.idi.bidata.boardgame.backend.util.InputHandler.*;
import static edu.ntnu.idi.bidata.boardgame.backend.util.OutputHandler.*;

import edu.ntnu.idi.bidata.boardgame.backend.model.Game;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.Dice;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.InsufficientFundsException;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.JailTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.TileAction;
import java.util.Optional;

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

  private static GameEngine instance;

  private boolean running = true;
  private int roundNumber = 1;

  private Game game;
  private JailTile jailTile;

  /** Constructs a {@code GameEngine} with the required dependencies. */
  private GameEngine() {}

  public static synchronized GameEngine getInstance() {
    if (instance == null) {
      instance = new GameEngine();
    }
    return instance;
  }

  public void goToJail(Player player) {
    jailTile.jailForNmberOfRounds(player, 2);
  }

  public void setup(Game game) {
    setGame(game);
    setJailTile(game.getJailTile());
    game.forEach(player -> player.addBalance(200));
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

  public Optional<Game> getGame() {
    return Optional.ofNullable(game);
  }

  /**
   * Executes a game round by rolling the dice, moving players, checking tile actions, and
   * determining if any player has won.
   */
  private void playRound() {
    println("Round %d:".formatted(roundNumber++));

    for (var player : game) {
      var diceRoll = Dice.getInstance().roll(2);
      println(player + " has rolled " + diceRoll);
      game.movePlayer(player, diceRoll);
      executeTileAction(player);
    }

    if (roundNumber >= 20) {
      checkWinningStatus();
    }
  }

  /**
   * Checks if a player lands on a tile that has an action and executes it.
   *
   * @param player The player whose current tile is checked for an action.
   */
  private void executeTileAction(Player player) {
    try {
      TileAction.of(game.getTile(player.getPosition())).execute(player);
    } catch (InsufficientFundsException e) {
      println("");
    }
  }

  /** Prints the current locations of all players. */
  private void printPlayerLocations() {
    for (var player : game) {
      println("Player %s is on tile %d".formatted(player.getName(), player.getPosition()));
    }
  }

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

  // ------------------------  getters and setters  ------------------------

  private void setGame(Game game) {
    if (game == null) {
      throw new IllegalArgumentException("Game cannot be null!");
    }
    this.game = game;
  }

  private void setJailTile(JailTile jailTile) {
    if (jailTile == null) {
      throw new IllegalArgumentException("Jail tile cannot be null!");
    }
    this.jailTile = jailTile;
  }
}
