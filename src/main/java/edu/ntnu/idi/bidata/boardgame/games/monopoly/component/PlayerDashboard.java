package edu.ntnu.idi.bidata.boardgame.games.monopoly.component;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.type.DiceRolledEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import edu.ntnu.idi.bidata.boardgame.common.event.type.OutputEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.type.PlayerMovedEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.type.PlayerRemovedEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.type.PurchaseEvent;
import edu.ntnu.idi.bidata.boardgame.core.EventListeningComponent;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Ownable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * {@code UIPane} is a dynamic sidebar for the Monopoly GUI. It displays each player as a visually
 * styled card including: name, balance, position, figure image, and owned properties.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.05.08
 */
public class PlayerDashboard extends EventListeningComponent {

  private final HashMap<Player, PlayerInfoBox> playerBoxes = new HashMap<>();
  private Player currentPlayer;

  private static final Color[] CARD_COLORS = {
    Color.web("#fff3cd"),
    Color.web("#d1e7dd"),
    Color.web("#cfe2ff"),
    Color.web("#f8d7da"),
    Color.web("#e2e3e5")
  };

  public PlayerDashboard(EventBus eventBus, List<Player> players) {
    super(eventBus);
    getEventBus().addListener(PlayerMovedEvent.class, this);
    getEventBus().addListener(PlayerRemovedEvent.class, this);

    setPrefWidth(320);
    setStyle("-fx-background-color: linear-gradient(to bottom, #1e293b, #0f172a);");
    setPadding(new Insets(10));

    VBox content = new VBox(16);
    content.setPadding(new Insets(10));

    int i = 0;
    for (Player player : players) {
      PlayerInfoBox box = new PlayerInfoBox(player, CARD_COLORS[i % CARD_COLORS.length]);
      playerBoxes.put(player, box);
      content.getChildren().add(box);
      i++;
    }

    ScrollPane scrollPane = new ScrollPane(content);
    scrollPane.setFitToWidth(true);
    scrollPane.setStyle("-fx-background: transparent;");
    getChildren().add(scrollPane);
    VBox.setVgrow(scrollPane, Priority.ALWAYS);
  }

  /** Refreshes all player displays in the sidebar. */
  public void refresh() {
    playerBoxes.values().forEach(PlayerInfoBox::refresh);
    highlightCurrentPlayer();
  }

  /** Sets the player whose turn it is, and updates the glow. */
  public void setCurrentPlayer(Player player) {
    this.currentPlayer = player;
    highlightCurrentPlayer();
  }

  /** Highlights the active player and removes glow from others. */
  private void highlightCurrentPlayer() {
    for (var entry : playerBoxes.entrySet()) {
      PlayerInfoBox box = entry.getValue();
      box.setGlow(entry.getKey().equals(currentPlayer));
    }
  }

  @Override
  public void onEvent(Event event) {
    switch (event) {
      case PlayerMovedEvent(Player player) -> {
        refresh();
        highlightCurrentPlayer();
      }
      case PlayerRemovedEvent(Player player) -> {
        playerBoxes.remove(player);
        refresh();
      }
      case DiceRolledEvent diceRolledEvent -> {}
      case OutputEvent outputEvent -> {}
      case PurchaseEvent purchaseEvent -> {}
    }
  }

  @Override
  public void close() throws Exception {
    getEventBus().removeListener(PlayerMovedEvent.class, this);
    getEventBus().removeListener(PlayerRemovedEvent.class, this);
  }

  /** {@code PlayerInfoBox} represents a stylized card for one player. */
  private static class PlayerInfoBox extends VBox {
    private static final String TITLE_FONT = "Georgia";
    private static final String BODY_FONT = "Verdana";
    private static final String PROPERTY_FONT = "Arial";

    private final Player player;
    private final Label nameLabel;
    private final Label balanceLabel;
    private final Label positionLabel;
    private final VBox propertiesList;
    private final ImageView figureImage;

    public PlayerInfoBox(Player player, Color backgroundColor) {
      this.player = player;

      setSpacing(10);
      setPadding(new Insets(14));
      setAlignment(Pos.TOP_CENTER);
      setPrefWidth(240);
      setBackground(
          new Background(new BackgroundFill(backgroundColor, new CornerRadii(12), Insets.EMPTY)));
      setBorder(
          new Border(
              new BorderStroke(
                  Color.web("#b0b0b0"),
                  BorderStrokeStyle.SOLID,
                  new CornerRadii(12),
                  BorderWidths.DEFAULT)));
      setEffect(new DropShadow(6, Color.gray(0.4)));

      nameLabel = label(Font.font(TITLE_FONT, FontWeight.BOLD, 20), "#1a1a1a");
      balanceLabel = label(Font.font(BODY_FONT, FontWeight.BOLD, 14), "#166534");
      positionLabel = label(Font.font(BODY_FONT, FontWeight.NORMAL, 13), "#1e40af");

      figureImage = new ImageView();
      figureImage.setFitHeight(90);
      figureImage.setFitWidth(90);
      figureImage.setPreserveRatio(true);
      loadFigureImage(player.getFigure());

      Label propsHeader = label(Font.font(BODY_FONT, FontWeight.BOLD, 13), "#333");
      propsHeader.setText("Properties:");

      propertiesList = new VBox(4);
      propertiesList.setAlignment(Pos.TOP_LEFT);
      propertiesList.setPadding(new Insets(4, 0, 0, 12));

      refresh();

      getChildren()
          .addAll(nameLabel, balanceLabel, positionLabel, figureImage, propsHeader, propertiesList);
    }

    private Label label(Font font, String hexColor) {
      Label l = new Label();
      l.setFont(font);
      l.setTextFill(Color.web(hexColor));
      l.setStyle("-fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.2), 1, 0.0, 1, 1);");
      return l;
    }

    private void loadFigureImage(Player.Figure figure) {
      String resourcePath =
          switch (figure) {
            case CAT -> "/images/cat.png";
            case CAR -> "/images/car.png";
            case HAT -> "/images/hat.png";
            case BATTLE_SHIP -> "/images/battleship.png";
            case DUCK -> "/images/duck.png";
          };
      figureImage.setImage(
          new Image(Objects.requireNonNull(getClass().getResourceAsStream(resourcePath))));
    }

    public void refresh() {
      nameLabel.setText(player.getName());
      positionLabel.setText("Tile: " + player.getPosition());

      // monopoly
      if (player instanceof MonopolyPlayer monopolyPlayer) {
        balanceLabel.setText("Balance: $" + monopolyPlayer.getBalance());
        propertiesList.getChildren().clear();
        for (Ownable ownable : monopolyPlayer.getOwnedAssets()) {
          Label propLabel = new Label("• " + ownable.toString());
          propLabel.setFont(Font.font(PROPERTY_FONT, FontWeight.SEMI_BOLD, 13));
          propLabel.setTextFill(Color.web("#212529"));
          propLabel.setBackground(
              new Background(
                  new BackgroundFill(Color.web("#ffffffcc"), new CornerRadii(4), Insets.EMPTY)));
          propLabel.setBorder(
              new Border(
                  new BorderStroke(
                      Color.web("#ced4da"),
                      BorderStrokeStyle.SOLID,
                      new CornerRadii(4),
                      BorderWidths.DEFAULT)));
          propLabel.setPadding(new Insets(3, 6, 3, 6));
          propertiesList.getChildren().add(propLabel);
        }
      }

      if (propertiesList.getChildren().isEmpty()) {
        Label none = new Label("(none)");
        none.setFont(Font.font(PROPERTY_FONT, FontPosture.ITALIC, 12));
        none.setTextFill(Color.web("#6c757d"));
        propertiesList.getChildren().add(none);
      }
    }

    /** Toggles a bright animated blue glow effect if it's the player's turn. */
    public void setGlow(boolean active) {
      if (active) {
        DropShadow glow = new DropShadow();
        glow.setColor(Color.web("#3b82f6"));
        glow.setRadius(26);
        glow.setSpread(0.6);
        glow.setOffsetX(0);
        glow.setOffsetY(0);
        setEffect(glow);
      } else {
        setEffect(new DropShadow(6, Color.gray(0.4)));
      }
    }
  }
}
