package edu.ntnu.idi.bidata.boardgame.core;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.type.UserInterfaceEvent;
import edu.ntnu.idi.bidata.boardgame.common.io.csv.CSVHandler;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code PlayerManager} class is responsible for managing player-related operations, including
 * loading player data from a CSV file and saving player data into a CSV file. It facilitates
 * support for multiple game types, such as Monopoly and Snake and Ladder, by leveraging the
 * provided {@code EventBus} for event-driven notifications and the {@code CSVHandler} for file I/O
 * operations.
 */
public class PlayerManager {

  private final EventBus eventBus;
  private final CSVHandler csvHandler;

  /**
   * Constructs a {@code PlayerManager} instance for managing player-related operations using the
   * provided {@code EventBus} and {@code CSVHandler}.
   *
   * @param eventBus the event bus used for publishing and subscribing to game-related events; must
   *     not be null
   * @param csvHandler the CSV handler used for reading and writing player data to/from CSV files;
   *     must not be null
   */
  public PlayerManager(EventBus eventBus, CSVHandler csvHandler) {
    this.eventBus = requireNonNull(eventBus);
    this.csvHandler = requireNonNull(csvHandler);
  }

  /**
   * Loads player data for the Monopoly game from a CSV file. Each row in the CSV file is expected
   * to contain the player's name and their chosen figure. The method attempts to create a {@link
   * MonopolyPlayer} instance for each valid row and adds it to a list. If invalid data is
   * encountered (e.g., an invalid figure name), an event is published to notify about the error,
   * and the invalid row is skipped.
   *
   * @return a list of {@link MonopolyPlayer} instances created from the valid rows in the CSV file
   * @throws IOException if an I/O error occurs while reading the CSV file
   */
  public List<MonopolyPlayer> loadCsvAsMonopolyPlayers() throws IOException {
    var rows = csvHandler.readAll().subList(1, csvHandler.readAll().size());

    var players = new ArrayList<MonopolyPlayer>();
    for (var row : rows) {
      try {
        players.add(new MonopolyPlayer(row[0], Player.Figure.valueOf(row[1].toUpperCase())));
      } catch (IllegalArgumentException e) {
        eventBus.publishEvent(
            new UserInterfaceEvent.Output("Invalid player data: " + e.getMessage()));
      }
    }
    return players;
  }

  /**
   * Loads player data for the Snake and Ladder game from a CSV file. Each row in the CSV file is
   * expected to contain the player's name and their chosen figure. The method attempts to create a
   * {@link SnakeAndLadderPlayer} instance for each valid row and adds it to a list. If invalid data
   * is encountered (e.g., an invalid figure name), an event is published to notify about the error,
   * and the invalid row is skipped.
   *
   * @return a list of {@link SnakeAndLadderPlayer} instances created from the valid rows in the CSV
   *     file
   * @throws IOException if an I/O error occurs while reading the CSV file
   */
  public List<SnakeAndLadderPlayer> loadCsvAsSnakeAndLadderPlayers() throws IOException {
    var rows = csvHandler.readAll();
    var players = new ArrayList<SnakeAndLadderPlayer>();
    for (var row : rows) {
      try {
        players.add(new SnakeAndLadderPlayer(row[0], Player.Figure.valueOf(row[1].toUpperCase())));
      } catch (IllegalArgumentException e) {
        eventBus.publishEvent(
            new UserInterfaceEvent.Output("Invalid player data: " + e.getMessage()));
      }
    }
    return players;
  }

  /**
   * Saves a list of players by converting their information into CSV format and writing it to a CSV
   * file. Each player's data is processed into a CSV-compatible format using the {@link
   * Player#toCsvLine(Player)} method, and the resulting rows are written via the associated CSV
   * handler.
   *
   * @param players the list of players to save; each player's name and figure will be included in
   *     the saved data
   * @throws IOException if an I/O error occurs while writing to the CSV file
   */
  public void savePlayers(List<Player> players) throws IOException {
    var rows = players.stream().map(Player::toCsvLine).toList();
    csvHandler.writeLines(rows);
  }
}
