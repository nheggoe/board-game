package edu.ntnu.idi.bidata.core;

import edu.ntnu.idi.bidata.action.TileAction;
import edu.ntnu.idi.bidata.io.InputHandler;
import edu.ntnu.idi.bidata.io.OutputHandler;
import java.util.ArrayList;
import java.util.List;
import edu.ntnu.idi.bidata.util.CSVHandler;

/**
 * The {@code Game} class represents a simple text-based interactive game.
 * The game continuously runs in a loop until the user decides to exit by providing
 * specific input ("yes" or "y").
 *
 * @author Nick Heggø
 * @version 2025.03.12
 */
public class Game {

  private final InputHandler inputHandler;
  private final OutputHandler outputHandler;
  private final List<Player> players;
  private final Board board;
  private final Dice dice;
  private final CSVHandler csvHandler;

  private boolean running = true;
  private int roundNumber = 1;

  /**
   * Constructs a new game with an input handler, output handler, and a list of players.
   */
  public Game() {
    inputHandler = new InputHandler();
    outputHandler = new OutputHandler();
    players = new ArrayList<>();
    board = new Board();
    dice = Dice.getInstance();
    csvHandler = new CSVHandler("players.csv");
  }


  /**
  * The public interface of the program, main entry.
  */
  public void run() {
    gameStartSetup();
    engine();
  }

  /**
   * Sets up the game by either loading existing players from a CSV file or
   * allowing the user to create new players.
   * <p>
   * If saved players exist in the CSV file, the user is prompted to choose
   * whether they want to use the existing players or create new ones.
   * If no saved players are found, the user is required to enter new player details.
   * </p>
   *
   * @throws NumberFormatException if the user inputs a non-integer value for the number of players.
   */
  private void gameStartSetup() {
    if (loadExistingPlayers()) {
      return;
    }
    createNewPlayers();
  }

  /**
   * Loads existing players from the CSV file and asks the user if they want to use them.
   * If the user chooses to use them, they are added to the game.
   *
   * @return {@code true} if players were loaded and chosen; {@code false} otherwise.
   */
  private boolean loadExistingPlayers() {
    List<Player> loadedPlayers = csvHandler.loadPlayers(board).toList();

    if (!loadedPlayers.isEmpty()) {
      outputHandler.println("Existing players found. Do you want to use them? (yes/no)");
      outputHandler.printInputPrompt();
      String choice = inputHandler.collectValidString().toLowerCase();

      if (choice.equals("yes") || choice.equals("y")) {
        players.addAll(loadedPlayers);
        outputHandler.println("Loaded existing players:");
        printPlayerLocation();
        return true;
      }
    }
    return false;
  }

  /**
   * Creates new players by asking for their details and then saving them to the CSV file.
   */
  private void createNewPlayers() {
    outputHandler.println("Please enter the number of players:");
    outputHandler.printInputPrompt();
    int numberOfPlayers = Integer.parseInt(inputHandler.nextLine());

    for (int i = 0; i < numberOfPlayers; i++) {
      players.add(promptUserForPlayers(i + 1));
    }

    saveNewPlayers();
  }

  /**
   * Prompts the user to enter details for a new player.
   *
   * @param playerNumber The player's number in sequence.
   * @return The newly created Player object.
   */
  private Player promptUserForPlayers(int playerNumber) {
    outputHandler.println("Please enter the name for player %d:".formatted(playerNumber));
    outputHandler.printInputPrompt();
    String name = inputHandler.collectValidString();

    outputHandler.println("Please choose a figure for player %d:".formatted(playerNumber));
    outputHandler.printInputPrompt();
    String figure = inputHandler.collectValidString();

    return new Player(name, board, figure);
  }

  /**
   * Saves newly created players to the CSV file.
   */
  private void saveNewPlayers() {
    csvHandler.savePlayers(players.stream());
    outputHandler.println("Players saved successfully!");
    outputHandler.println("The following players are playing the game:");
    printPlayerLocation();
  }




  private boolean validateExitString(String s) {
    return s.equalsIgnoreCase("exit");
  }

  /**
   * Terminates the current game loop by setting the {@code running} flag to {@code false}.
   * This effectively signals the game engine to stop processing further iterations.
   * Before terminating saves the current players to the CSV file.
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
          updatePlayerPosition();
          checkWinningStatus();
          printPlayerLocation();
        } else {
          this.terminate();
        }
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
  * Check if the player has reached the winning tile,
  * if yes, announce the winner and end the game.
  */
  private void checkWinningStatus() {
    players.forEach(player -> {
      if (player.getCurrentTile().equals(board.getWinningTile())) {
        outputHandler.println("%s has won the game!".formatted(player.getName()));
        running = false;
      }
    });
  }

  /**
  * Updates the player position, and ran the tile actions.
  */
  private void updatePlayerPosition() {
    players.forEach(player -> player.move(dice.roll(), board));
    players.forEach(player -> {
      TileAction action = player.getCurrentTile().getLandAction();
      if (action != null) {
        action.perform(player, board);
      }
    });
  }

  /**
  * Print the current player position using the {@link OutputHandler}
  */
  private void printPlayerLocation() {
    players.stream()
        .map(player -> "Player %s on tile %d"
            .formatted(
                player.getName(),
                player.getCurrentTile().getPosition() + 1
            )
        ).forEach(outputHandler::println);
  }
}
