package edu.ntnu.idi.bidata.boardgame.common.event.type;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import java.util.List;
import java.util.function.Consumer;

public record PlayersRequiredEvent<P extends Player>(
    Class<P> playerType, Consumer<List<P>> playerConsumer) implements Event {

  public PlayersRequiredEvent {
    requireNonNull(playerType, "Player type cannot be null!");
    requireNonNull(playerConsumer, "Player consumer cannot be null!");
  }
}
