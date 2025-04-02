package edu.ntnu.idi.bidata.boardgame.backend.core;

import edu.ntnu.idi.bidata.boardgame.backend.io.InputHandler;
import edu.ntnu.idi.bidata.boardgame.backend.io.OutputHandler;
import edu.ntnu.idi.bidata.boardgame.backend.io.csv.CSVHandler;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code PlayerManager} class is responsible for handling player-related operations, including
 * loading players from a CSV file, prompting the user to create new players, and managing player
 * data.
 *
 * <p>This class ensures that players are properly initialized before the game starts and provides a
 * seamless way to load saved players or create new ones.
 *
 * @author Nick Heggø
 * @version 2025.03.14
 */
public class PlayerManager {
  private final InputHandler inputHandler;
  private final OutputHandler outputHandler;
  private final CSVHandler csvHandler;
  private final Board board;

  /**
   * Constructs a {@code PlayerManager} with the required dependencies.
   *
   * @param inputHandler Handles user input.
   * @param outputHandler Handles user output.
   * @param board The game board used for player positioning.
   */
  public PlayerManager(InputHandler inputHandler, OutputHandler outputHandler, Board board) {
    this.inputHandler = inputHandler;
    this.outputHandler = outputHandler;
    this.board = board;
    this.csvHandler = new CSVHandler("/data/csv/players.csv");
  }

  /**
   * Initializes the players for the game by loading existing players from the CSV file or prompting
   * the user to create new ones.
   *
   * @return A list of initialized players.
   */
  public List<Player> initializePlayers() {
    List<Player> players = csvHandler.loadPlayers(board).toList();
    if (!players.isEmpty() && confirmUseExistingPlayers()) {
      return players;
    }
    return createNewPlayers();
  }

  /**
   * Asks the user if they want to use existing players loaded from the CSV file.
   *
   * @return {@code true} if the user confirms; {@code false} otherwise.
   */
  private boolean confirmUseExistingPlayers() {
    outputHandler.println("Existing players found. Do you want to use them? (yes/no)");
    outputHandler.printInputPrompt();
    String choice = inputHandler.collectValidString().toLowerCase();
    return choice.equals("yes") || choice.equals("y");
  }

  /**
   * Creates new players by asking for their details and then saving them to the CSV file.
   *
   * @return A list of newly created players.
   */
  private List<Player> createNewPlayers() {
    int numberOfPlayers = -1;

    while (numberOfPlayers < 1) {
      try {
        outputHandler.println("Enter number of players:");
        outputHandler.printInputPrompt();
        String input = inputHandler.nextLine();

        numberOfPlayers = Integer.parseInt(input);

        if (numberOfPlayers < 1) {
          outputHandler.println("Number of players must be at least 1. Try again.");
        }
      } catch (NumberFormatException e) {
        outputHandler.println("Invalid input. Please enter a valid number.");
      }
    }

    List<Player> players = new ArrayList<>();
    for (int i = 0; i < numberOfPlayers; i++) {
      players.add(promptUserForPlayer(i + 1));
    }
    saveNewPlayers(players);
    return players;
  }

  /**
   * Saves the list of players to the CSV file and prints a confirmation message.
   *
   * @param players The list of players to save.
   */
  private void saveNewPlayers(List<Player> players) {
    csvHandler.savePlayers(players.stream());
    outputHandler.println("Players saved successfully!");
  }

  /**
   * Prompts the user to enter details for a new player.
   *
   * @param playerNumber The player's number in sequence.
   * @return The newly created Player object.
   */
  private Player promptUserForPlayer(int playerNumber) {
    outputHandler.println("Enter name for player %d:".formatted(playerNumber));
    outputHandler.printInputPrompt();
    String name = inputHandler.collectValidString();

    outputHandler.println("Choose a figure for player %d:".formatted(playerNumber));
    outputHandler.printInputPrompt();
    String figure = inputHandler.collectValidString();

    return new Player(name, board, figure);
  }
}
