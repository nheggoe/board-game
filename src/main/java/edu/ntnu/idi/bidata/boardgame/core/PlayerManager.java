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
import java.util.stream.Collectors;

public class PlayerManager<P extends Player> {

  private final EventBus eventBus;
  private final CSVHandler csvHandler;
  private final Class<P> genericType;

  public PlayerManager(EventBus eventBus, CSVHandler csvHandler, Class<P> genericType) {
    this.eventBus = requireNonNull(eventBus);
    this.csvHandler = requireNonNull(csvHandler);
    this.genericType = requireNonNull(genericType);
  }

  public List<P> loadPlayers() throws IOException {
    var lines = csvHandler.readCSV();
    var players = new ArrayList<P>();
    for (var line : lines) {
      try {
        var tokens = line.split("((\\s+)?,(\\s+)?)");
        P player =
            (P)
                switch (genericType) {
                  case Class<?> generic when generic == MonopolyPlayer.class ->
                      new MonopolyPlayer(tokens[0], Player.Figure.valueOf(tokens[1]));
                  case Class<?> generic when generic == SnakeAndLadderPlayer.class ->
                      new SnakeAndLadderPlayer(tokens[0], Player.Figure.valueOf(tokens[1]));
                  default ->
                      throw new IllegalArgumentException("Unknown player type: " + genericType);
                };
        players.add(player);
      } catch (IllegalArgumentException e) {
        eventBus.publishEvent(new OutputEvent("Invalid player data: " + e.getMessage() + "\n"));
      }
    }
    return players;
  }

  public void savePlayers() throws IOException {}

  private static String getAvailableFigures() {
    return Arrays.stream(Player.Figure.values())
        .map(StringFormatter::formatEnum)
        .collect(Collectors.joining(", "));
  }

  public List<P> getPlayers() {
    return null;
  }
}
