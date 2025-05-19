package edu.ntnu.idi.bidata.boardgame.games.monopoly.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.MonopolyGame;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.MonopolyBoard;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class JsonMonopolyGameRepositoryTest {

  private MonopolyGame monopolyGame;

  @BeforeEach
  void setup() {
    EventBus eventBus = mock(EventBus.class);
    MonopolyBoard monopolyBoard = mock(MonopolyBoard.class);
    List<MonopolyPlayer> players = List.of();
    monopolyGame = new MonopolyGame(eventBus, monopolyBoard, players);
  }

  @Test
  void testToJson() {
    // var repo = new JsonMonopolyGameRepository();
    // repo.add(monopolyGame);
  }
}
