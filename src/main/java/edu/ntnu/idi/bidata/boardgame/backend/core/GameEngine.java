package edu.ntnu.idi.bidata.boardgame.backend.core;

import edu.ntnu.idi.bidata.boardgame.backend.model.Game;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.Dice;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.JailTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.TileAction;
import edu.ntnu.idi.bidata.boardgame.backend.util.InputHandler;
import edu.ntnu.idi.bidata.boardgame.backend.util.OutputHandler;

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

  private final OutputHandler outputHandler;
  private final Dice dice;
  private boolean running = true;
  private int roundNumber = 1;

  private Game game;
  private JailTile jailTile;

  /**
   * Constructs a {@code GameEngine} with the required dependencies.
   *
   * @param outputHandler Handles output to the user.
   * @param dice The dice object used for rolling moves.
   */
  private GameEngine(OutputHandler outputHandler, Dice dice) {
    this.outputHandler = outputHandler;
    this.dice = dice;
  }

  public static synchronized GameEngine getInstance() {
    if (instance == null) {
      instance = new GameEngine(OutputHandler.getInstance(), Dice.getInstance());
    }
    return instance;
  }

  public void goToJail(Player player) {
    jailTile.jailForNmberOfRounds(player, 2);
  }

  public void setup(Game game) {
    setGame(game);
    setJailTile(game.getJailTile());
  }

  /** Starts and manages the game loop, allowing players to take turns until the game ends. */
  public void run() {
    outputHandler.println("Game has started! Initial player positions:");
    printPlayerLocations();

    while (running) {
      outputHandler.println("Press enter to play round or 'exit' to quit:");
      String input = InputHandler.getInstance().nextLine();
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
  private void playRound() {
    try {
      outputHandler.println("Round %d:".formatted(roundNumber++));
      game.forEach(player -> game.movePlayer(player, dice.roll(2).getTotal()));
      game.forEach(this::executeTileAction);
      roundNumber += 1;
      if (roundNumber >= 20) {

        checkWinningStatus();
      }
    } catch (Exception e) {
      outputHandler.println("An error occurred during the round: " + e.getMessage());
    }
  }

  /**
   * Checks if a player lands on a tile that has an action and executes it.
   *
   * @param player The player whose current tile is checked for an action.
   */
  private void executeTileAction(Player player) {
    TileAction.of(game.getTile(player.getPosition())).execute(player);
  }

  /** Prints the current locations of all players. */
  private void printPlayerLocations() {
    game.forEach(
        player ->
            outputHandler.println(
                "Player %s is on tile %d".formatted(player.getName(), player.getPosition())));
  }

  /** Checks if any player has reached the winning tile and ends the game if a winner is found. */
  private void checkWinningStatus() {
    var winners = game.getWinners().getValue();
    if (winners.isEmpty()) {
      throw new IllegalArgumentException("Failed to retrieve winners.");
    } else if (winners.size() == 1) {
      Player winner = winners.getFirst();
      outputHandler.println(
          "The winner is %s with net worth of $%d!"
              .formatted(winner.getName(), winner.getNetWorth()));
    } else {
      outputHandler.println("We have multiple winners!");
      for (int i = 0; i < winners.size(); i++) {
        outputHandler.println("%d. %s".formatted((i + 1), winners.get(i).getName()));
      }
      outputHandler.println(
          "All finished the game with net worth of $%d"
              .formatted(winners.getFirst().getNetWorth()));
    }
  }

  // ------------------------  getters and setters  ------------------------

  public Game getGame() {
    return game;
  }

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
