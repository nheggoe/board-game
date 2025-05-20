package edu.ntnu.idi.bidata.boardgame.common.io.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.io.FileUtil;
import edu.ntnu.idi.bidata.boardgame.common.util.GameFactory;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.MonopolyBoard;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class JsonServiceTest {

  @Test
  void test_read_write_SnakeAndLadderBoard() {
    var jsonService = new JsonService<>(SnakeAndLadderBoard.class, true);
    var board =
        GameFactory.createSnakeGame(
                new EventBus(), List.of(new SnakeAndLadderPlayer("John", Player.Figure.HAT)))
            .getBoard();

    jsonService.serializeToSource(Set.of(board));

    var boards = jsonService.deserializeFromSource().toList();

    assertThat(boards).containsExactly(board);

    // cleanup
    var file = FileUtil.generateFilePath("SnakeAndLadderBoard", "json", true).toFile();
    file.delete();
    file.getParentFile().delete();
  }

  @Test
  void test_read_write_MonopolyBoard() {
    var jsonService = new JsonService<>(MonopolyBoard.class, true);

    var eventBus = new EventBus();
    var players = List.of(new MonopolyPlayer("John", Player.Figure.HAT));

    var board = GameFactory.createMonopolyGame(eventBus, players).getBoard();

    jsonService.serializeToSource(Set.of(board));
    var file = FileUtil.generateFilePath("MonopolyBoard", "json", true).toFile();

    assertThat(contentOf(file))
        .isNotEmpty()
        .contains("start")
        .contains("property")
        .contains("position");

    // cleanup
    assertThat(file.delete()).isTrue();
    assertThat(file.getParentFile().delete()).isTrue();
  }
}
