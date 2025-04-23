package edu.ntnu.idi.bidata.boardgame.frontend.view;

import edu.ntnu.idi.bidata.boardgame.backend.model.Game;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.BoardFactory;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Property;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.FreeParkingTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.GoToJailTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.JailTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.OwnableTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.StartTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.TaxTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.Tile;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameView extends Scene {

  public GameView(double width, double height) {
    super(new Pane(), width, height);
    setRoot(createContent());
  }

  private Parent createContent() {
    var root = new BorderPane();
    var center = new FlowPane();
    var game =
        new Game(
            BoardFactory.generateBoard(BoardFactory.Layout.NORMAL),
            List.of(
                new Player("Nick", Player.Figure.HAT),
                new Player("Misha", Player.Figure.BATTLE_SHIP)));
    game.getBoard().tiles().stream()
        .map(GameView::renderTile)
        .forEach(center.getChildren()::addAll);

    root.setCenter(center);
    return root;
  }

  private static Node renderTile(Tile tile) {
    return switch (tile) {
      case JailTile jailTile -> createBackground(Color.DARKGREY);
      case OwnableTile(Property(var name, var color, var price)) ->
          createBackground(colorAdapter(color));
      case OwnableTile ownableTile -> createBackground(Color.ORANGE);
      case TaxTile taxTile -> null;
      case FreeParkingTile freeParkingTile -> createBackground(Color.ORANGE);
      case GoToJailTile goToJailTile -> createBackground(Color.DARKGREY);
      case StartTile startTile -> createBackground(Color.BLUE);
    };
  }

  private static Color colorAdapter(Property.Color color) {
    return switch (color) {
      case BROWN -> Color.BROWN;
      case DARK_BLUE -> Color.DARKBLUE;
      case GREEN -> Color.GREEN;
      case LIGHT_BLUE -> Color.LIGHTBLUE;
      case ORANGE -> Color.ORANGE;
      case PINK -> Color.PINK;
      case RED -> Color.RED;
      case YELLOW -> Color.YELLOW;
    };
  }

  private static Node createBackground(Color background) {
    Rectangle rectangle = new Rectangle(20, 20);
    rectangle.setFill(background);
    return rectangle;
  }
}
