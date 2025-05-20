package edu.ntnu.idi.bidata.boardgame.common.ui.view;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.io.csv.CSVHandler;
import edu.ntnu.idi.bidata.boardgame.core.PlayerManager;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class PlayerSetupView extends View {

  public PlayerSetupView() {
    super();
    var root = new BorderPane();
    setRoot(root);
  }

  private VBox createCenterPane(EventBus eventBus) {
    var center = new VBox();
    center.setAlignment(Pos.CENTER);
    center.setSpacing(10);

    var tableView = createPlayerTableView();
    center.getChildren().add(tableView);
    var lines = new PlayerManager(eventBus, new CSVHandler("players"));

    return center;
  }

  // FIXME
  private static TableView<Player> createPlayerTableView() {
    var tableView = new TableView<Player>();
    tableView.setPadding(new Insets(10));
    tableView.setPrefWidth(300);
    tableView.setPrefHeight(200);
    tableView.setEditable(true);
    tableView.getColumns().add(new TableColumn<>("Name"));
    tableView.getColumns().add(new TableColumn<>("Color"));
    tableView.setRowFactory(
        tv -> {
          var row = new TableRow<Player>();

          row.setOnContextMenuRequested(
              event -> {
                ContextMenu contextMenu = new ContextMenu();
                MenuItem editItem = new MenuItem("Edit");
                MenuItem deleteItem = new MenuItem("Delete");

                editItem.setOnAction(
                    e -> {
                      row.getItem();
                    });
              });
          return row;
        });
    return tableView;
  }
}
