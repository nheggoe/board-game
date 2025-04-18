package edu.ntnu.idi.bidata.boardgame.backend.core;

import edu.ntnu.idi.bidata.boardgame.backend.io.csv.CSVHandler;
import edu.ntnu.idi.bidata.boardgame.backend.model.player.Figure;
import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;
import edu.ntnu.idi.bidata.boardgame.backend.util.InputHandler;
import edu.ntnu.idi.bidata.boardgame.backend.util.OutputHandler;
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
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.04.18
 */
public class PlayerManager {
  private final InputHandler inputHandler;
  private final OutputHandler outputHandler;
  private final CSVHandler csvHandler;

  /** Constructs a {@code PlayerManager} with the required dependencies. */
  public PlayerManager() {
    this.inputHandler = InputHandler.getInstance();
    this.outputHandler = OutputHandler.getInstance();
    this.csvHandler = new CSVHandler("players");
  }

  /**
   * Initializes the players for the game by loading existing players from the CSV file or prompting
   * the user to create new ones.
   *
   * @return A list of initialized players.
   */
  public List<Player> initializePlayers() {
    List<Player> players = csvHandler.loadPlayers().toList();
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
    while (true) {
      try {
        outputHandler.printInputPrompt("Available figures: " + Figure.getAvailableFigures());
        Figure figure = Figure.valueOf(inputHandler.collectValidString().strip().toUpperCase());
        return new Player(name, figure);
      } catch (IllegalArgumentException e) {
        outputHandler.println("Please choose a valid figure:");
      }
    }
  }
}
