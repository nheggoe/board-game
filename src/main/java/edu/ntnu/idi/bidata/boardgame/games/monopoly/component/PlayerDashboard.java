package edu.ntnu.idi.bidata.boardgame.games.monopoly.component;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import edu.ntnu.idi.bidata.boardgame.common.event.type.PlayerMovedEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.type.PlayerRemovedEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.type.PurchaseEvent;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.core.ui.EventListeningComponent;
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
import javafx.scene.text.FontWeight;

/**
 * {@code UIPane} is a dynamic sidebar for the Monopoly GUI. It displays each player as a visually
 * styled card including: name, balance, position, figure image, and owned properties.
 *
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.05.14
 */
public class PlayerDashboard extends EventListeningComponent {

  private final HashMap<Player, PlayerInfoBox> playerRegistry = new HashMap<>();

  private static final Color[] CARD_COLORS = {
    Color.web("#fff3cd"),
    Color.web("#d1e7dd"),
    Color.web("#cfe2ff"),
    Color.web("#f8d7da"),
    Color.web("#e2e3e5")
  };

  public PlayerDashboard(EventBus eventBus, List<? extends Player> players) {
    super(eventBus);
    getEventBus().addListener(PlayerMovedEvent.class, this);
    getEventBus().addListener(PlayerRemovedEvent.class, this);
    getEventBus().addListener(PurchaseEvent.class, this);

    setPrefWidth(320);
    setStyle("-fx-background-color: linear-gradient(to bottom, #1e293b, #0f172a);");
    setPadding(new Insets(10));

    VBox content = new VBox(16);
    content.setPadding(new Insets(10));

    int i = 0;
    for (Player player : players) {
      PlayerInfoBox box = new PlayerInfoBox(player, CARD_COLORS[i % CARD_COLORS.length]);
      playerRegistry.put(player, box);
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
    playerRegistry.values().forEach(PlayerInfoBox::refresh);
  }

  private void highlightPlayer(Player player) {
    for (var entry : playerRegistry.entrySet()) {
      PlayerInfoBox box = entry.getValue();
      box.setGlow(entry.getKey().equals(player));
    }
  }

  @Override
  public void onEvent(Event event) {
    switch (event) {
      case PlayerMovedEvent(Player player) -> highlightPlayer(player);
      case PlayerRemovedEvent(Player player) -> playerRegistry.remove(player);
      case PurchaseEvent(MonopolyPlayer monopolyPlayer, Ownable ownable) -> {
        var infoBox = playerRegistry.remove(monopolyPlayer);
        playerRegistry.put(monopolyPlayer, infoBox);
      }
      default -> throw new IllegalStateException("Unexpected value: " + event);
    }
    refresh();
  }

  @Override
  public void close() {
    getEventBus().removeListener(PlayerMovedEvent.class, this);
    getEventBus().removeListener(PlayerRemovedEvent.class, this);
    getEventBus().removeListener(PurchaseEvent.class, this);
  }

  // ------------------------  inner class  ------------------------

  /** {@code PlayerInfoBox} represents a stylized card for one player. */
  private static class PlayerInfoBox extends VBox {
    private static final String TITLE_FONT = "Georgia";
    private static final String BODY_FONT = "Verdana";

    private final Player player;
    private final Label nameLabel;
    private final Label balanceLabel;
    private final Label positionLabel;
    private final ImageView figureImage;

    private PlayerInfoBox(Player player, Color backgroundColor) {
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

      refresh();

      getChildren().addAll(nameLabel, balanceLabel, positionLabel, figureImage);
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
    }

    /** Toggles a bright animated blue glow effect if it's the player's turn. */
    private void setGlow(boolean active) {
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
