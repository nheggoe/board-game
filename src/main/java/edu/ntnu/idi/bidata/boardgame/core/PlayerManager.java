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

public class PlayerManager {

  private final EventBus eventBus;
  private final CSVHandler csvHandler;

  public PlayerManager(EventBus eventBus, CSVHandler csvHandler) {
    this.eventBus = requireNonNull(eventBus);
    this.csvHandler = requireNonNull(csvHandler);
  }

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

  public void savePlayers(List<Player> players) throws IOException {
    var rows = players.stream().map(Player::toCsvLine).toList();
    csvHandler.writeLines(rows);
  }
}
