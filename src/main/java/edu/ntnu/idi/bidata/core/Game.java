package edu.ntnu.idi.bidata.core;

import edu.ntnu.idi.bidata.action.TileAction;
import edu.ntnu.idi.bidata.io.InputHandler;
import edu.ntnu.idi.bidata.io.OutputHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link Game} class represents a simple text-based interactive game. The game continuously
 * runs in a loop until the user decides to exit by providing specific input ("yes" or "y").
 *
 * @author Nick Heggø
 * @version 2025.02.14
 */
public class Game {

  private final InputHandler inputHandler;
  private final OutputHandler outputHandler;
  private final List<Player> players;
  private final Board board;
  private final Dice dice;

  private boolean running = true;
  private int roundNumber = 1;

  /** Constructs a new game with an input handler, output handler, and a list of players. */
  public Game() {
    inputHandler = new InputHandler();
    outputHandler = new OutputHandler();
    players = new ArrayList<>();
    board = new Board();
    dice = Dice.getInstance();
  }

  /** The public interface of the program, main entry. */
  public void run() {
    gameStartSetup();
    engine();
  }

  private void gameStartSetup() {
    outputHandler.println("Please enter the amount of player that are playing the game:");
    outputHandler.printInputPrompt();
    int numberOfPlayers = Integer.parseInt(inputHandler.nextLine());
    for (int i = 0; i < numberOfPlayers; i++) {
      outputHandler.println("Please enter the name for player %d:".formatted(i + 1));
      outputHandler.printInputPrompt();
      players.add(new Player(inputHandler.collectValidString(), board));
    }
    outputHandler.println("The following players are playing the game");
    printPlayerLocation();
  }

  private boolean validateExitString(String s) {
    return s.equalsIgnoreCase("exit");
  }

  /**
   * Terminates the current game loop by setting the {@code running} flag to {@code false}. This
   * effectively signals the game engine to stop processing further iterations.
   */
  private void terminate() {
    running = false;
  }

  /**
   * Represents the core loop of the game engine that continuously processes user input until a
   * termination condition is met. The loop reads input from the user, validates it, and terminates
   * the game if the input matches the exit condition.
   *
   * <p>If an invalid input is detected during the validation process, an appropriate error message
   * is displayed to the user.
   *
   * <p>The method relies on: - {@code validateExitString(String)} to determine if the termination
   * condition is met. - {@code terminate()} to stop the game loop by setting the {@code running}
   * flag to false.
   */
  private void engine() {
    while (running) {
      try {
        outputHandler.println("Press enter to go to the next round or exit to exit");
        String input = inputHandler.nextLine();
        if (!validateExitString(input)) {
          outputHandler.println("Round %d:".formatted(roundNumber++));
          updatePlayerPosition();
          checkWinningStatus();
          printPlayerLocation();
        } else {
          this.terminate();
        }
      } catch (IllegalArgumentException e) {
        outputHandler.println(e.getMessage());
      }
    }
  }

  /**
   * Check if the player has reached the winning tile, if yes, announce the winner and end the game.
   */
  private void checkWinningStatus() {
    players.forEach(
        player -> {
          if (player.getCurrentTile().equals(board.getWinningTile())) {
            outputHandler.println("%s has won the game!".formatted(player.getName()));
            running = false;
          }
        });
  }

  /** Updates the player position and ran the tile actions. */
  private void updatePlayerPosition() {
    players.forEach(player -> player.move(dice.roll(), board));
    players.forEach(
        player -> {
          TileAction action = player.getCurrentTile().getLandAction();
          if (action != null) {
            action.perform(player, board);
          }
        });
  }

  /**
   * Prints the current location of all players in the game. Gets the player's name and its current
   * position on the board.
   */
  private void printPlayerLocation() {
    players.stream()
        .map(
            player ->
                "Player %s on tile %d"
                    .formatted(
                        player.getName(),
                        player.getCurrentTile().getPosition() + 1)) // human index start at 1
        .forEach(outputHandler::println);
  }
}
