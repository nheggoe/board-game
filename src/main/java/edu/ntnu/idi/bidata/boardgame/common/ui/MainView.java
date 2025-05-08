package edu.ntnu.idi.bidata.boardgame.common.ui;

import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainView extends View {

  public MainView(EventHandler<ActionEvent>... eventHandlers) {

    // int actionCount = 2;
    //
    // if (eventHandlers.length != actionCount) {
    //   throw new IllegalArgumentException("Must provide %d actions!".formatted(actionCount));
    // }

    // root
    var root = new BorderPane();
    getChildren().add(root);
    root.prefWidthProperty().bind(widthProperty());
    root.prefHeightProperty().bind(heightProperty());

    // Center
    var center = new VBox();
    root.setCenter(center);
    var monopolyButton = new Button();
    monopolyButton.setOnAction(eventHandlers[0]);
    center.getChildren().add(monopolyButton);
  }
}
