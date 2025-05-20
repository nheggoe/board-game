package edu.ntnu.idi.bidata.boardgame.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.io.csv.CSVHandler;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlayerManagerTest {

  @Mock private CSVHandler csvHandler;
  @Mock private EventBus eventBus;

  @InjectMocks private PlayerManager playerManager;

  @BeforeEach
  void setup() throws IOException {
    playerManager = new PlayerManager(eventBus, csvHandler);
    when(csvHandler.readCSV()).thenReturn(List.of("Name,Figure", "John,CAR", "Jane,BATTLE_SHIP"));
  }

  @Test
  @Disabled
  void test_loadCsvAsMonopolyPlayers() throws Exception {
    List<MonopolyPlayer> players =
        List.of(
            new MonopolyPlayer("John", MonopolyPlayer.Figure.CAR),
            new MonopolyPlayer("Jane", MonopolyPlayer.Figure.BATTLE_SHIP));
    var csvPlayers = playerManager.loadCsvAsMonopolyPlayers();
    assertThat(csvPlayers).containsExactlyElementsOf(players);
  }
}
