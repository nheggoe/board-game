package edu.ntnu.idi.bidata.boardgame.frontend.controller;

import edu.ntnu.idi.bidata.boardgame.backend.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.backend.model.Game;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.BoardFactory;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Property;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Railroad;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Utility;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {

  private final Game game;

  public GameController() {
    game =
        GameEngine.getInstance()
            .getGame()
            .orElseThrow(() -> new IllegalArgumentException("Game engine is not correct setup!"));
  }

  /**
   * @return the number of tiles the game have
   */
  public int getBoardSize() {
    return game.getBoard().getNumberOfTiles();
  }

  private Parent createContent(GridPane gridPane) {
    var root = new BorderPane();
    var center = new FlowPane();
    var game =
        new Game(
            BoardFactory.generateBoard(BoardFactory.Layout.NORMAL),
            List.of(
                new Player("Nick", Player.Figure.HAT),
                new Player("Misha", Player.Figure.BATTLE_SHIP)));
    game.getBoard().tiles().stream().map(this::renderTile).forEach(center.getChildren()::addAll);

    root.setCenter(center);
    return root;
  }

  private Node renderTile(Tile tile) {
    return switch (tile) {
      case JailTile jailTile -> createBackground(Color.DARKGREY);
      case OwnableTile ownableTile -> createOwnableTilePane(ownableTile);
      case TaxTile taxTile -> null;
      case FreeParkingTile freeParkingTile -> createBackground(Color.ORANGE);
      case GoToJailTile goToJailTile -> createBackground(Color.DARKGREY);
      case StartTile startTile -> createBackground(Color.BLUE);
    };
  }

  private Node createOwnableTilePane(OwnableTile ownableTile) {
    return switch (ownableTile) {
      case OwnableTile(Property(String name, Property.Color color, int price)) -> {
        var pane = new Pane(createBackground(colorAdapter(color)));
        // pane.setOnMouseClicked(event -> AlertFacotry.createAlert(Alert.AlertType.INFORMATION,
        // property.toString()));
        yield pane;
      }
      case OwnableTile(Railroad railroad) -> createBackground(Color.BROWN);
      case OwnableTile(Utility utility) -> createBackground(Color.ORANGE);
    };
  }

  private Color colorAdapter(Property.Color color) {
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

  private Rectangle createBackground(Color background) {
    Rectangle rectangle = new Rectangle(20, 20);
    rectangle.setFill(background);
    return rectangle;
  }
}
