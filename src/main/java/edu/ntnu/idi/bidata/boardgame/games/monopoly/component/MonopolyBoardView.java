package edu.ntnu.idi.bidata.boardgame.games.monopoly.component;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.EventListener;
import edu.ntnu.idi.bidata.boardgame.common.event.type.CoreEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import edu.ntnu.idi.bidata.boardgame.common.event.type.MonopolyEvent;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.core.ui.EventListeningComponent;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Ownable;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Property;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Railroad;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Utility;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.FreeParkingMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.GoToJailMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.JailMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.MonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.OwnableMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.StartMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.TaxMonopolyTile;
import java.util.List;
import java.util.function.Supplier;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author Nick Heggø
 * @version 2025.05.07
 */
public class MonopolyBoardView extends EventListeningComponent implements EventListener {

  private final GridPane board;
  private final Supplier<List<MonopolyTile>> tilesSupplier;
  private final Supplier<List<MonopolyPlayer>> playersSupplier;
  private static final Color[] PLAYER_COLORS = {
    Color.web("#fff3cd"),
    Color.web("#d1e7dd"),
    Color.web("#cfe2ff"),
    Color.web("#f8d7da"),
    Color.web("#e2e3e5")
  };

  public MonopolyBoardView(
      EventBus eventBus,
      Supplier<List<MonopolyPlayer>> playersSupplier,
      Supplier<List<MonopolyTile>> tilesSupplier) {
    super(eventBus);
    getEventBus().addListener(CoreEvent.PlayerMoved.class, this);
    getEventBus().addListener(MonopolyEvent.Purchased.class, this);

    this.playersSupplier = playersSupplier;
    this.tilesSupplier = tilesSupplier;
    board = new GridPane();
    getChildren().add(board);
    setAlignment(Pos.CENTER);
    initialize(tilesSupplier, playersSupplier);
  }

  /**
   * Updates the visual representation of a player's position on the game board. This method is
   * called when a player moves to a new position on the board.
   *
   * @param player the player who has moved
   * @param position the new position of the player on the board
   */
  public void playerMoved(Player player, int position) {
    // Clear any existing player figure from the board
    clearPlayerFigures(player);

    // Calculate the grid position for the new tile
    int size = (board.getChildren().size() + 4) / 4;
    int row = 0;
    int col = 0;

    // Position logic for clockwise movement
    if (position == 0) {
      // Bottom right corner
      row = size - 1;
      col = size - 1;
    } else if (position < size) {
      // Bottom row (going left)
      row = size - 1;
      col = size - 1 - position;
    } else if (position < size * 2 - 1) {
      // Left column (going up)
      row = size - 1 - (position - (size - 1));
      col = 0;
    } else if (position < size * 3 - 2) {
      // Top row (going right)
      row = 0;
      col = position - (size * 2 - 2);
    } else {
      // Right column (going down)
      row = position - (size * 3 - 3);
      col = size - 1;
    }

    // Get the tile at the calculated position
    StackPane tilePane = getTileAtPosition(row, col);
    if (tilePane != null) {
      // Create and add player figure to the tile
      addPlayerFigure(tilePane, player);
    }
  }

  /**
   * Removes all visual representations of the specified player from the board.
   *
   * @param player the player whose figures should be removed
   */
  private void clearPlayerFigures(Player player) {
    // Iterate through all tiles on the board
    for (javafx.scene.Node node : board.getChildren()) {
      if (node instanceof StackPane tilePane) {
        // Remove any player figure matching this player
        tilePane
            .getChildren()
            .removeIf(
                child ->
                    child instanceof ImageView imageView
                        && imageView.getUserData() != null
                        && imageView.getUserData().equals(player.getId()));
      }
    }
  }

  /**
   * Gets the tile StackPane at the specified grid position.
   *
   * @param row the row in the grid
   * @param col the column in the grid
   * @return the StackPane at the specified position, or null if not found
   */
  private StackPane getTileAtPosition(int row, int col) {
    for (javafx.scene.Node node : board.getChildren()) {
      if (node instanceof StackPane
          && GridPane.getRowIndex(node) == row
          && GridPane.getColumnIndex(node) == col) {
        return (StackPane) node;
      }
    }
    return null;
  }

  /**
   * Adds a visual representation of the player to the specified tile.
   *
   * @param tilePane the tile where the player figure should be added
   * @param player the player whose figure should be added
   */
  private void addPlayerFigure(StackPane tilePane, Player player) {
    String imagePath = getFigureImagePath(player.getFigure());
    ImageView playerFigure = new ImageView(new Image(imagePath, 30, 30, true, true));
    playerFigure.setUserData(player.getId()); // Store player ID for identification

    // Position the figure appropriately on the tile
    StackPane.setAlignment(playerFigure, Pos.CENTER);

    // If there are other players on this tile, adjust positioning
    int playerCount =
        (int)
            tilePane.getChildren().stream()
                .filter(child -> child instanceof ImageView && child.getUserData() != null)
                .count();

    // Adjust position based on number of players already on this tile
    switch (playerCount) {
      case 0 -> StackPane.setAlignment(playerFigure, Pos.TOP_LEFT);
      case 1 -> StackPane.setAlignment(playerFigure, Pos.TOP_RIGHT);
      case 2 -> StackPane.setAlignment(playerFigure, Pos.BOTTOM_LEFT);
      case 3 -> StackPane.setAlignment(playerFigure, Pos.BOTTOM_RIGHT);
      default -> StackPane.setAlignment(playerFigure, Pos.CENTER); // Fallback
    }

    // Add the player figure to the tile
    tilePane.getChildren().add(playerFigure);
  }

  /**
   * Gets the image path for a player figure.
   *
   * @param figure the player's figure type
   * @return the path to the corresponding image resource
   */
  private String getFigureImagePath(Player.Figure figure) {
    return switch (figure) {
      case BATTLE_SHIP -> "images/battleship.png";
      case CAR -> "images/car.png";
      case CAT -> "images/cat.png";
      case DUCK -> "images/duck.png";
      case HAT -> "images/hat.png";
    };
  }

  private void initialize(
      Supplier<List<MonopolyTile>> tilesSuppler, Supplier<List<MonopolyPlayer>> players) {
    board.setGridLinesVisible(false); // optional: show grid lines
    board.setAlignment(Pos.CENTER);

    var tiles = tilesSuppler.get();
    int size = (tiles.size() + 4) / 4;

    // Position tiles in clockwise order, starting with 00 at bottom right

    // Bottom right corner - position 00
    board.add(createTile(tiles.getFirst()), size - 1, size - 1);

    // Right column (going up, skip bottom right)
    int pos = 1;
    for (int row = size - 2; row >= 0; row--) {
      board.add(createTile(tiles.get(pos++)), row, size - 1);
    }

    // Top row (going left, skip top right)
    for (int col = size - 2; col >= 0; col--) {
      board.add(createTile(tiles.get(pos++)), 0, col);
    }

    // Left column (going down, skip top left)
    for (int row = 1; row < size; row++) {
      board.add(createTile(tiles.get(pos++)), row, 0);
    }

    // Bottom row (going right, skip bottom left)
    for (int col = 1; col < size - 1; col++) {
      board.add(createTile(tiles.get(pos++)), size - 1, col);
    }

    players.get().forEach(player -> playerMoved(player, 0));
  }

  public void bindSizeProperty() {
    board.prefWidthProperty().bind(this.getScene().widthProperty());
    board.prefHeightProperty().bind(this.getScene().heightProperty());
  }

  private Pane createTile(MonopolyTile tile) {
    StackPane tilePane = new StackPane();
    int tileSize = 80;
    tilePane.setPrefSize(tileSize, tileSize);

    switch (tile) {
      case FreeParkingMonopolyTile freeParkingMonopolyTile -> {
        var image = new Image("icons/free-parking-tile.png", tileSize, tileSize, true, true);
        var imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        tilePane.getChildren().add(imageView);

        tilePane.setBackground(
            new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
      }
      case GoToJailMonopolyTile goToJailMonopolyTile -> {
        var image = new Image("icons/go-to-jail-tile.png", tileSize, tileSize, true, true);
        var imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        tilePane.getChildren().add(imageView);
      }
      case JailMonopolyTile jailMonopolyTile -> {
        var image = new Image("icons/jail-tile.png", tileSize, tileSize, true, true);
        var imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        tilePane.getChildren().add(imageView);
      }
      case StartMonopolyTile startMonopolyTile -> {
        var image = new Image("icons/go-tile.png", tileSize, tileSize, true, true);
        var imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        tilePane.getChildren().add(imageView);
      }
      case OwnableMonopolyTile(Ownable ownable) -> {
        switch (ownable) {
          case Property property -> {
            tilePane.setBackground(
                new Background(
                    new BackgroundFill(
                        colorAdapter(property.getColor()), CornerRadii.EMPTY, Insets.EMPTY)));

            var image = new Image("icons/propertyTileIcon.png", tileSize, tileSize, true, true);
            var imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);

            tilePane.getChildren().add(imageView);

            // Add owner indicator and property name
            var nameLabel = new Label(property.getName());
            tilePane.getChildren().add(nameLabel);
            StackPane.setAlignment(nameLabel, Pos.BOTTOM_CENTER);

            // Property will store its data for updating owner later
            tilePane.setUserData(property);

            // Check for ownership and add owner initial if owned
            updatePropertyOwnership(tilePane, property);

            // Add upgrade indicators (houses and hotels)
            updatePropertyUpgrades(tilePane, property);
          }
          case Railroad railroad -> {
            tilePane.setBackground(
                new Background(
                    new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
            tilePane
                .getChildren()
                .add(
                    new ImageView(
                        new Image("icons/railroad.png", tileSize, tileSize, true, false)));

            // Railroad will store its data for updating owner later
            tilePane.setUserData(railroad);

            // Check for ownership and add owner initial if owned
            updatePropertyOwnership(tilePane, railroad);
          }
          case Utility utility -> {
            tilePane.setBackground(
                new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
            tilePane
                .getChildren()
                .add(
                    new ImageView(new Image("icons/utility.png", tileSize, tileSize, true, false)));

            // Utility will store its data for updating owner later
            tilePane.setUserData(utility);

            // Check for ownership and add owner initial if owned
            updatePropertyOwnership(tilePane, utility);
          }
        }
      }
      case TaxMonopolyTile taxTile -> {
        tilePane.setBackground(
            new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        tilePane
            .getChildren()
            .add(new ImageView(new Image("icons/propertyTileIcon.png", 60, 60, true, false)));
      }
    }
    tilePane.setStyle("-fx-border-color: black;");

    return tilePane;
  }

  /**
   * Updates the visual representation of property ownership by adding the owner's initial with the
   * corresponding player color from the dashboard.
   *
   * @param tilePane the tile to update
   * @param ownable the property to check for ownership
   */
  private void updatePropertyOwnership(StackPane tilePane, Ownable ownable) {
    // Remove any existing owner labels
    tilePane
        .getChildren()
        .removeIf(
            node ->
                node instanceof Label && node.getId() != null && node.getId().equals("ownerLabel"));

    // Check each player to see if they own this property
    var players = playersSupplier.get();
    for (int i = 0; i < players.size(); i++) {
      MonopolyPlayer player = players.get(i);
      if (player.isOwnerOf(ownable)) {
        // Create owner label with first capital letter
        String initial = player.getName().substring(0, 1).toUpperCase();
        Label ownerLabel = new Label(initial);
        ownerLabel.setId("ownerLabel");
        ownerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        ownerLabel.setTextFill(Color.BLACK);

        // Use the same color as in PlayerDashboard
        Color playerColor = PLAYER_COLORS[i % PLAYER_COLORS.length];
        ownerLabel.setBackground(
            new Background(new BackgroundFill(playerColor, new CornerRadii(10), Insets.EMPTY)));
        ownerLabel.setPadding(new Insets(2, 6, 2, 6));
        ownerLabel.setBorder(
            new Border(
                new BorderStroke(
                    Color.BLACK,
                    BorderStrokeStyle.SOLID,
                    new CornerRadii(10),
                    BorderWidths.DEFAULT)));

        // Add to tile and position in top-right corner
        tilePane.getChildren().add(ownerLabel);
        StackPane.setAlignment(ownerLabel, Pos.TOP_RIGHT);
        StackPane.setMargin(ownerLabel, new Insets(5, 5, 0, 0));
        break; // Found the owner, no need to check other players
      }
    }
  }

  /**
   * Updates the visual representation of property upgrades by adding green squares for houses and
   * red squares for hotels.
   *
   * @param tilePane the tile to update
   * @param property the property to check for upgrades
   */
  private void updatePropertyUpgrades(StackPane tilePane, Property property) {
    // Remove any existing upgrade indicators
    tilePane
        .getChildren()
        .removeIf(
            node ->
                node instanceof Pane
                    && (node.getId() != null
                        && (node.getId().equals("houseIndicator")
                            || node.getId().equals("hotelIndicator"))));

    // Check for hotel (red square)
    if (property.hasHotel()) {
      Pane hotelIndicator = new Pane();
      hotelIndicator.setId("hotelIndicator");
      hotelIndicator.setPrefSize(6, 6);
      hotelIndicator.setBackground(
          new Background(new BackgroundFill(Color.RED, new CornerRadii(3), Insets.EMPTY)));
      hotelIndicator.setBorder(
          new Border(
              new BorderStroke(
                  Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));

      tilePane.getChildren().add(hotelIndicator);
      StackPane.setAlignment(hotelIndicator, Pos.TOP_LEFT);
      StackPane.setMargin(hotelIndicator, new Insets(5, 0, 0, 5));
    }
    // Check for houses (green squares)
    else {
      int houseCount = property.countHouses();
      if (houseCount > 0) {
        HBox houseContainer = new HBox(2);
        houseContainer.setId("houseIndicator");

        // Add a green square for each house
        for (int i = 0; i < houseCount; i++) {
          Pane houseIndicator = new Pane();
          houseIndicator.setPrefSize(10, 10);
          houseIndicator.setBackground(
              new Background(new BackgroundFill(Color.GREEN, new CornerRadii(2), Insets.EMPTY)));
          houseIndicator.setBorder(
              new Border(
                  new BorderStroke(
                      Color.BLACK,
                      BorderStrokeStyle.SOLID,
                      new CornerRadii(2),
                      BorderWidths.DEFAULT)));

          houseContainer.getChildren().add(houseIndicator);
        }

        tilePane.getChildren().add(houseContainer);
        StackPane.setAlignment(houseContainer, Pos.TOP_LEFT);
        StackPane.setMargin(houseContainer, new Insets(5, 0, 0, 5));
      }
    }
  }

  /**
   * Updates all properties on the board to reflect current ownership and upgrades. Called after
   * purchase events or when the board is refreshed.
   */
  private void updateAllProperties() {
    for (javafx.scene.Node node : board.getChildren()) {
      if (node instanceof StackPane tilePane && tilePane.getUserData() != null) {
        if (tilePane.getUserData() instanceof Property property) {
          updatePropertyOwnership(tilePane, property);
          updatePropertyUpgrades(tilePane, property);
        } else if (tilePane.getUserData() instanceof Ownable ownable) {
          updatePropertyOwnership(tilePane, ownable);
        }
      }
    }
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

  @Override
  public void onEvent(Event event) {
    if (event instanceof CoreEvent.PlayerMoved(Player player)) {
      playerMoved(player, player.getPosition());
    } else if (event instanceof MonopolyEvent.Purchased) {
      // Update all properties to reflect new ownership
      updateAllProperties();
    }
  }

  @Override
  public void close() {
    getEventBus().removeListener(CoreEvent.PlayerMoved.class, this);
    getEventBus().removeListener(MonopolyEvent.Purchased.class, this);
  }
}
