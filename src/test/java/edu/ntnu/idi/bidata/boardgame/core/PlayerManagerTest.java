package edu.ntnu.idi.bidata.boardgame.core;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import edu.ntnu.idi.bidata.boardgame.common.event.type.UserInterfaceEvent;
import edu.ntnu.idi.bidata.boardgame.common.io.csv.CSVHandler;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlayerManagerTest {

  @Mock private CSVHandler csvHandler;
  @Mock private EventBus eventBus;

  @Captor ArgumentCaptor<Event> eventCaptor;

  @InjectMocks private PlayerManager playerManager;

  @BeforeEach
  void setUp() throws IOException {
    playerManager = new PlayerManager(eventBus, csvHandler);
    when(csvHandler.readAll())
        .thenReturn(
            List.of(
                new String[] {"Name", "Figure"},
                new String[] {"John", "CAR"},
                new String[] {"Jane", "BATTLE_SHIP"}));
  }

  @Test
  void test_loadPlayersFromCSV() throws Exception {
    var monopolyPlayers = playerManager.loadCsvAsMonopolyPlayers();
    assertThat(monopolyPlayers)
        .anyMatch(
            player -> player.getName().equals("John") && player.getFigure() == Player.Figure.CAR)
        .anyMatch(
            player ->
                player.getName().equals("Jane") && player.getFigure() == Player.Figure.BATTLE_SHIP)
        .allMatch(MonopolyPlayer.class::isInstance);

    var snakeAndLadderPlayers = playerManager.loadCsvAsSnakeAndLadderPlayers();
    assertThat(snakeAndLadderPlayers)
        .anyMatch(
            player -> player.getName().equals("John") && player.getFigure() == Player.Figure.CAR)
        .anyMatch(
            player ->
                player.getName().equals("Jane") && player.getFigure() == Player.Figure.BATTLE_SHIP)
        .allMatch(SnakeAndLadderPlayer.class::isInstance);
  }

  @Test
  void test_loadPlayersFromCSV_invalid() throws Exception {
    when(csvHandler.readAll())
        .thenReturn(List.of(new String[] {"John", "CAR"}, new String[] {"Jane", "INVALID"}));

    assertThatCode(() -> playerManager.loadCsvAsMonopolyPlayers()).doesNotThrowAnyException();

    verify(eventBus).publishEvent(eventCaptor.capture());
    assertThat(eventCaptor.getValue()).isInstanceOf(UserInterfaceEvent.Output.class);
    assertThat(((UserInterfaceEvent.Output) eventCaptor.getValue()).message())
        .contains("Invalid player data: ");
  }
}
