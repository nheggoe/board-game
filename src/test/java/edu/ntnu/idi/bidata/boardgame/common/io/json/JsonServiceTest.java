package edu.ntnu.idi.bidata.boardgame.common.io.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.io.FileUtil;
import edu.ntnu.idi.bidata.boardgame.common.util.GameFactory;
import edu.ntnu.idi.bidata.boardgame.core.PlayerManager;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.MonopolyBoard;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class JsonServiceTest {

  @Test
  void test_read_write_SnakeAndLadderBoard() throws IOException {

    var jsonService = new JsonService<>(SnakeAndLadderBoard.class, true);

    var mockPlayerManager = mock(PlayerManager.class);
    when(mockPlayerManager.loadCsvAsSnakeAndLadderPlayers())
        .thenReturn(List.of(new SnakeAndLadderPlayer("John", Player.Figure.BATTLE_SHIP)));
    var board = GameFactory.createSnakeGame(new EventBus(), mockPlayerManager).getBoard();

    jsonService.serializeToSource(Set.of(board));

    var boards = jsonService.deserializeFromSource().toList();

    assertThat(boards).containsExactly(board);

    // cleanup
    var file = FileUtil.generateFilePath("SnakeAndLadderBoard", "json", true).toFile();
    file.delete();
    file.getParentFile().delete();
  }

  @Test
  void test_read_write_MonopolyBoard() throws IOException {
    var mockEventbus = mock(EventBus.class);
    var mockPlayerManager = mock(PlayerManager.class);
    when(mockPlayerManager.loadCsvAsMonopolyPlayers())
        .thenReturn(List.of(new MonopolyPlayer("John", Player.Figure.HAT)));
    var board = GameFactory.createMonopolyGame(mockEventbus, mockPlayerManager).getBoard();

    var jsonService = new JsonService<>(MonopolyBoard.class, true);
    jsonService.serializeToSource(Set.of(board));

    var file = FileUtil.generateFilePath("MonopolyBoard", "json", true).toFile();

    assertThat(contentOf(file))
        .isNotEmpty()
        .contains("start")
        .contains("property")
        .contains("position");

    // cleanup
    assertThat(file.delete()).isTrue();
    file.getParentFile().delete();
  }
}
