package edu.ntnu.idi.bidata.boardgame.core;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.type.OutputEvent;
import edu.ntnu.idi.bidata.boardgame.common.io.csv.CSVHandler;
import edu.ntnu.idi.bidata.boardgame.common.util.StringFormatter;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PlayerManager {

  private final EventBus eventBus;
  private final CSVHandler csvHandler;
  private final Pattern csvPattern;

  public PlayerManager(EventBus eventBus, CSVHandler csvHandler) {
    this.eventBus = requireNonNull(eventBus);
    this.csvHandler = requireNonNull(csvHandler);
    this.csvPattern = Pattern.compile("((\\s+)?,(\\s+)?)");
  }

  public List<MonopolyPlayer> loadCsvAsMonopolyPlayers() throws IOException {
    var lines = csvHandler.readCSV();
    var players = new ArrayList<MonopolyPlayer>();
    for (var line : lines) {
      try {
        var tokens = csvPattern.split(line);
        players.add(new MonopolyPlayer(tokens[0], Player.Figure.valueOf(tokens[1].toUpperCase())));
      } catch (IllegalArgumentException e) {
        eventBus.publishEvent(new OutputEvent("Invalid player data: " + e.getMessage()));
      }
    }
    return players;
  }

  public List<SnakeAndLadderPlayer> loadCsvAsSnakeAndLadderPlayers() throws IOException {
    var lines = csvHandler.readCSV();
    var players = new ArrayList<SnakeAndLadderPlayer>();
    for (var line : lines) {
      try {
        var tokens = csvPattern.split(line);
        players.add(
            new SnakeAndLadderPlayer(tokens[0], Player.Figure.valueOf(tokens[1].toUpperCase())));
      } catch (IllegalArgumentException e) {
        eventBus.publishEvent(new OutputEvent("Invalid player data: " + e.getMessage()));
      }
    }
    return players;
  }

  public void savePlayers(List<Player> players) throws IOException {
    var lines = players.stream().map(Player::toCsvLine).toList();
    csvHandler.writeCSV(lines, false);
  }

  private static String getAvailableFigures() {
    return Arrays.stream(Player.Figure.values())
        .map(StringFormatter::formatEnum)
        .collect(Collectors.joining(", "));
  }
}
