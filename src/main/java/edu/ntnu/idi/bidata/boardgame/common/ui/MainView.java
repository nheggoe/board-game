package edu.ntnu.idi.bidata.boardgame.common.ui;

import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainView extends View {

  @SafeVarargs
  public MainView(EventHandler<ActionEvent>... eventHandlers) {
    int actionCount = 2;
    if (eventHandlers.length != actionCount) {
      throw new IllegalArgumentException("Must provide %d actions!".formatted(actionCount));
    }

    var root = new BorderPane();
    root.prefWidthProperty().bind(widthProperty());
    root.prefHeightProperty().bind(heightProperty());
    getChildren().add(root);

    root.setCenter(createCenterPane(eventHandlers));
  }

  private static VBox createCenterPane(EventHandler<ActionEvent>[] eventHandlers) {
    var center = new VBox();
    center.setAlignment(Pos.CENTER);
    center.setSpacing(10);
    var monopolyButton = new Button("Monopoly");
    var snakeAndeLadderButton = new Button("Snake and Ladder");
    monopolyButton.setOnAction(eventHandlers[0]);
    snakeAndeLadderButton.setOnAction(eventHandlers[1]);
    center.getChildren().addAll(monopolyButton, snakeAndeLadderButton);
    return center;
  }
}
