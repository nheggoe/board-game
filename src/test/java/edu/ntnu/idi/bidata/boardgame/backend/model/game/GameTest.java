package edu.ntnu.idi.bidata.boardgame.backend.model.game;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.backend.model.board.BoardGameFactory;
import edu.ntnu.idi.bidata.boardgame.backend.model.player.Figure;
import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
  private Game game;

  @BeforeEach
  void setup() {
    var players = List.of(new Player("Duke", Figure.CAT), new Player("John", Figure.DUCK));
    game = new Game(BoardGameFactory.generateBoard(BoardGameFactory.Layout.NORMAL), players);
  }

  @Test
  void testPlayerConfiguration() {
    game.forEach(
        player -> {
          assertThat(player.getGameId()).isEqualTo(game.getGameId());
          assertThat(player.getCurrentTile()).isEqualTo(game.getBoard().getStartingTile());
        });
  }
}
