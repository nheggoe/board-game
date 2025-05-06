package edu.ntnu.idi.bidata.boardgame.backend.core;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.TurnManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TurnManagerTest {

  private List<UUID> ids;
  private TurnManager turnManager;

  @BeforeEach
  void setup() {
    ids =
        new ArrayList<>(
            List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()));
    turnManager = new TurnManager(new EventBus(), ids);
  }

  @Test
  void testRemove() {
    var id1 = ids.get(0);
    var id2 = ids.get(1);
    var id3 = ids.get(2);
    var id4 = ids.get(3);

    assertThat(turnManager.getCurrentPlayerId()).isEqualTo(id1);
    turnManager.removePlayer(id1);
    assertThat(turnManager.getCurrentPlayerId()).isEqualTo(id2);
  }
}
