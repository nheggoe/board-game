package edu.ntnu.idi.bidata.core;

import edu.ntnu.idi.bidata.Board;
import edu.ntnu.idi.bidata.Dice;
import edu.ntnu.idi.bidata.io.InputHandler;
import edu.ntnu.idi.bidata.io.OutputHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Game} class represents a simple text-based interactive game.
 * The game continuously runs in a loop until the user decides to exit by providing
 * specific input ("yes" or "y").
 *
 * @author Nick Heggø
 * @version 2025.02.07
 */
public class Game {

  private final InputHandler inputHandler;
  private final OutputHandler outputHandler;
  private final List<String> players;
  private final Board board;
  private final Dice dice;

  private boolean running = true;
  private int roundNumber = 1;

  public Game() {
    inputHandler = new InputHandler();
    outputHandler = new OutputHandler();
    players = new ArrayList<>();
    board = new Board();
    dice = Dice.getInstance();
  }

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
      players.add(inputHandler.collectValidString());
    }
    outputHandler.println("The following players are playing the game");
    for (String name : players) {
      outputHandler.println("Player: %s".formatted(name));
    }
  }

  private boolean validateExitString(String s) {
    return s.equalsIgnoreCase("exit");
  }

  /**
   * Terminates the current game loop by setting the {@code running} flag to {@code false}.
   * This effectively signals the game engine to stop processing further iterations.
   */
  private void terminate() {
    running = false;
  }

  /**
   * Represents the core loop of the game engine that continuously processes user input
   * until a termination condition is met. The loop reads input from the user, validates it,
   * and terminates the game if the input matches the exit condition.
   * <p>
   * If an invalid input is detected during the validation process, an appropriate error
   * message is displayed to the user.
   * <p>
   * The method relies on:
   * - {@code validateExitString(String)} to determine if the termination condition is met.
   * - {@code terminate()} to stop the game loop by setting the {@code running} flag to false.
   */
  private void engine() {
    while (running) {
      try {
        outputHandler.println("Press enter to go to the next round or exit to exit");
        String input = inputHandler.nextLine();
        if (!validateExitString(input)) {
          outputHandler.println("Round %d:".formatted(roundNumber++));
          // FIXME waiting for player class
          dice.roll();
        } else {
          this.terminate();
        }
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
