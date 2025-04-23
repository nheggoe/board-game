package edu.ntnu.idi.bidata.boardgame.backend.model.game;


import edu.ntnu.idi.bidata.boardgame.backend.model.Game;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.BoardFactory;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

class GameTest {
  private Game game;

  @BeforeEach
  void setup() {
    var players =
        List.of(new Player("Duke", Player.Figure.CAT), new Player("John", Player.Figure.DUCK));
    game = new Game(BoardFactory.generateBoard(BoardFactory.Layout.NORMAL), players);
  }
}
