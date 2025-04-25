package edu.ntnu.idi.bidata.boardgame.frontend.view;

import edu.ntnu.idi.bidata.boardgame.backend.model.Game;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.BoardFactory;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/** DemoView displays a basic UI to test the UIPane and MessageLog with a sample game state. */
public class DemoView extends Scene {

  public DemoView() {
    super(createContent(), 1000, 700);
  }

  private static BorderPane createContent() {
    Player p1 = new Player("Alice", Player.Figure.CAT);
    Player p2 = new Player("Bob", Player.Figure.HAT);
    p1.addBalance(1500);
    p2.addBalance(1200);

    Game game = new Game(BoardFactory.generateBoard(BoardFactory.Layout.EASY), List.of(p1, p2));

    UIPane uiPane = new UIPane(game);

    MessageLog log = new MessageLog();
    log.log("Game started. Welcome Alice and Bob!");

    BorderPane root = new BorderPane();
    root.setRight(uiPane);
    root.setBottom(log.getRoot());

    return root;
  }
}
