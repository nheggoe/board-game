package edu.ntnu.idi.bidata.boardgame.common.ui.view;

import edu.ntnu.idi.bidata.boardgame.common.ui.component.SettingButton;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class PlayerSetupView extends View {

  public PlayerSetupView(SceneSwitcher sceneSwitcher) {
    super();
    var root = new BorderPane();
    setRoot(root);

    root.setCenter(createCenterPane(sceneSwitcher));
    root.setRight(new SettingButton(sceneSwitcher));
  }

  private VBox createCenterPane(SceneSwitcher sceneSwitcher) {
    var center = new VBox();
    center.setAlignment(Pos.CENTER);
    center.setSpacing(10);
    center.getChildren().add(new SettingButton(sceneSwitcher));

    var tableView = createPlayerTableView();
    center.getChildren().add(tableView);
    var players =
        List.of(
            new SnakeAndLadderPlayer("Player 1", Player.Figure.HAT),
            new SnakeAndLadderPlayer("Player 2", Player.Figure.BATTLE_SHIP));
    tableView.setItems(FXCollections.observableArrayList(players));

    return center;
  }

  private TableView<Player> createPlayerTableView() {
    var tableView = new TableView<Player>();
    tableView.setPadding(new Insets(10));
    tableView.setPrefWidth(300);
    tableView.setPrefHeight(200);
    tableView.setEditable(true);
    tableView.getColumns().addAll(createTableColumn());
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

  private List<TableColumn<Player, String>> createTableColumn() {
    var nameColumn = new TableColumn<Player, String>("Name");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    nameColumn.setPrefWidth(100);
    var figureColumn = new TableColumn<Player, String>("Figure");
    figureColumn.setCellValueFactory(new PropertyValueFactory<>("figure"));
    figureColumn.setPrefWidth(100);
    return List.of(nameColumn, figureColumn);
  }
}
