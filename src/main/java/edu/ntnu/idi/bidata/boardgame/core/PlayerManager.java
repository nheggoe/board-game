package edu.ntnu.idi.bidata.boardgame.core;

import static edu.ntnu.idi.bidata.boardgame.common.util.InputHandler.collectValidString;
import static edu.ntnu.idi.bidata.boardgame.common.util.InputHandler.nextLine;
import static edu.ntnu.idi.bidata.boardgame.common.util.OutputHandler.printInputPrompt;
import static edu.ntnu.idi.bidata.boardgame.common.util.OutputHandler.println;

import edu.ntnu.idi.bidata.boardgame.common.io.csv.CSVHandler;
import edu.ntnu.idi.bidata.boardgame.common.util.StringFormatter;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Owner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code PlayerManager} class is responsible for handling player-related operations, including
 * loading players from a CSV file, prompting the user to create new players, and managing player
 * data.
 *
 * <p>This class ensures that players are properly initialized before the game starts and provides a
 * seamless way to load saved players or create new ones.
 *
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.05.08
 */
public class PlayerManager {
  private final CSVHandler csvHandler;

  /** Constructs a {@code PlayerManager} with the required dependencies. */
  public PlayerManager() {
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
    println("Existing players found. Do you want to use them? (yes/no)");
    printInputPrompt();
    String choice = collectValidString().toLowerCase();
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
        println("Enter number of players:");
        printInputPrompt();
        String input = nextLine();

        numberOfPlayers = Integer.parseInt(input);

        if (numberOfPlayers < 1) {
          println("Number of players must be at least 1. Try again.");
        }
      } catch (NumberFormatException e) {
        println("Invalid input. Please enter a valid number.");
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
    println("Players saved successfully!");
  }

  /**
   * Prompts the user to enter details for a new player.
   *
   * @param playerNumber The player's number in sequence.
   * @return The newly created Player object.
   */
  private Player promptUserForPlayer(int playerNumber) {
    println("Enter name for player %d:".formatted(playerNumber));
    printInputPrompt();
    String name = collectValidString();

    println("Choose a figure for player %d:".formatted(playerNumber));
    while (true) {
      try {
        printInputPrompt("Available figures: " + getAvailableFigures());
        Player.Figure figure = Player.Figure.valueOf(collectValidString().strip().toUpperCase());
        return new Owner(name, figure);
      } catch (IllegalArgumentException e) {
        println("Please choose a valid figure:");
      }
    }
  }

  private static String getAvailableFigures() {
    return Arrays.stream(Player.Figure.values())
        .map(StringFormatter::formatEnum)
        .collect(Collectors.joining(", "));
  }
}
