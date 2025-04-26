package edu.ntnu.idi.bidata.boardgame.frontend.view;

import edu.ntnu.idi.bidata.boardgame.backend.model.Game;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.BoardFactory;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * {@code DemoView} displays a basic UI to test the {@link UIPane}, {@link DiceView}, {@link
 * MessageLog}, and {@link DiceControlPane} with a sample game state.
 *
 * <p>It initializes a small game with two players and sets up the layout for visual testing.
 *
 * @author Mihailo
 * @version 2025.04.25
 */
public class DemoView extends Scene {

  public DemoView() {
    super(createContent(), 1000, 700);
  }

  private static BorderPane createContent() {
    // Setup sample players
    Player p1 = new Player("Alice", Player.Figure.CAT);
    Player p2 = new Player("Bob", Player.Figure.HAT);
    p1.addBalance(1500);
    p2.addBalance(1200);

    // Create game and UI components
    Game game = new Game(BoardFactory.generateBoard(BoardFactory.Layout.EASY), List.of(p1, p2));
    UIPane uiPane = new UIPane();
    MessageLog log = MessageLog.getInstance();
    DiceView diceView = new DiceView(2);

    uiPane.setCurrentPlayer(game.getCurrentPlayer());

    // Log game start
    log.log("Game started. Welcome Alice and Bob!");

    // Create dice control panel
    DiceControlPane diceControlPane = new DiceControlPane(game, diceView, log, uiPane);

    // Arrange UI
    VBox sidebar = new VBox(20, diceControlPane, uiPane);
    sidebar.setPadding(new Insets(10));
    sidebar.setAlignment(Pos.TOP_CENTER);

    BorderPane root = new BorderPane();
    root.setRight(sidebar);
    // root.setBottom(log.getRoot());
    root.setCenter(diceView);

    return root;
  }
}
