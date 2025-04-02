package edu.ntnu.idi.bidata.boardgame.backend.core;

import edu.ntnu.idi.bidata.boardgame.backend.action.TileAction;
import edu.ntnu.idi.bidata.boardgame.backend.io.OutputHandler;
import edu.ntnu.idi.bidata.boardgame.backend.model.Dice;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;
import java.util.List;

/**
 * The {@code GameEngine} class is responsible for managing the core game loop and logic. It
 * processes each game round, moves players, checks for tile actions, and determines if a player has
 * won.
 *
 * <p>This class interacts with the {@link Board}, {@link Dice}, and {@link Player} objects to
 * facilitate game progression.
 *
 * @author Nick Heggø
 * @version 2025.03.14
 */
public class GameEngine {
  private final OutputHandler outputHandler;
  private final Board board;
  private final Dice dice;
  private boolean running = true;
  private int roundNumber = 1;

  /**
   * Constructs a {@code GameEngine} with the required dependencies.
   *
   * @param outputHandler Handles output to the user.
   * @param board The game board containing tiles and game elements.
   * @param dice The dice object used for rolling moves.
   */
  public GameEngine(OutputHandler outputHandler, Board board, Dice dice) {
    this.outputHandler = outputHandler;
    this.board = board;
    this.dice = dice;
  }

  /**
   * Starts and manages the game loop, allowing players to take turns until the game ends.
   *
   * @param players The list of players participating in the game.
   */
  public void run(List<Player> players) {
    outputHandler.println("Game has started! Initial player positions:");
    printPlayerLocations(players);

    while (running) {
      outputHandler.println("Press enter to play round or 'exit' to quit:");
      String input = new java.util.Scanner(System.in).nextLine();
      if (input.equalsIgnoreCase("exit")) {
        running = false;
      } else {
        playRound(players);
      }
    }
  }

  /**
   * Executes a game round by rolling the dice, moving players, checking tile actions, and
   * determining if any player has won.
   *
   * @param players The list of players taking turns in the game.
   */
  private void playRound(List<Player> players) {
    try {
      outputHandler.println("Round %d:".formatted(roundNumber++));
      players.forEach(player -> player.move(dice.roll(), board));
      players.forEach(this::checkTileAction);
      printPlayerLocations(players);
      checkWinningStatus(players);
    } catch (Exception e) {
      outputHandler.println("An error occurred during the round: " + e.getMessage());
    }
  }

  /**
   * Checks if a player lands on a tile that has an action and executes it.
   *
   * @param player The player whose current tile is checked for an action.
   */
  private void checkTileAction(Player player) {
    TileAction action = player.getCurrentTile().getLandAction();
    if (action != null) {
      action.perform(player, board);
    }
  }

  /**
   * Prints the current locations of all players.
   *
   * @param players The list of players whose positions are displayed.
   */
  private void printPlayerLocations(List<Player> players) {
    players.forEach(
        player ->
            outputHandler.println(
                "Player %s is on tile %d"
                    .formatted(player.getName(), player.getCurrentTile().getPosition() + 1)));
  }

  /**
   * Checks if any player has reached the winning tile and ends the game if a winner is found.
   *
   * @param players The list of players in the game.
   */
  private void checkWinningStatus(List<Player> players) {
    for (Player player : players) {
      if (player.getCurrentTile().equals(board.getWinningTile())) {
        outputHandler.println(player.getName() + " has won the game!");
        running = false;
      }
    }
  }
}
