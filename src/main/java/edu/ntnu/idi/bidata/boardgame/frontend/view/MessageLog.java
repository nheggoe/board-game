package edu.ntnu.idi.bidata.boardgame.frontend.view;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * The {@code MessageLog} class represents a stylized dialogue box component. It displays game
 * messages with a typewriter effect and supports skipping to the full message instantly.
 *
 * <p>This class is intended to be placed at the bottom of the game scene as a feedback area for
 * players, showing narration or action logs in a visually engaging format.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * MessageLog log = new MessageLog();
 * log.log("Kaia rolled a 6 and landed on Tile 10!");
 * }</pre>
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.04.25
 */
public class MessageLog extends StackPane {

  private static final MessageLog instance = new MessageLog();

  private final Label textLabel;
  private final StringBuilder currentMessage;
  private Timeline typewriter;

  /** Constructs a new {@code MessageLog} with visuals and animation. */
  private MessageLog() {
    setPrefHeight(140);
    setPadding(new Insets(10));

    Rectangle background = new Rectangle(1000, 140);
    background.setArcWidth(20);
    background.setArcHeight(20);
    background.setFill(Color.BLACK);
    background.setStroke(Color.WHITE);
    background.setStrokeWidth(3);

    textLabel = new Label();
    textLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
    textLabel.setTextFill(Color.WHITE);
    textLabel.setWrapText(true);
    textLabel.setMaxWidth(900);
    textLabel.setAlignment(Pos.TOP_LEFT);

    currentMessage = new StringBuilder();

    getChildren().addAll(background, textLabel);
    StackPane.setAlignment(textLabel, Pos.TOP_LEFT);
    StackPane.setMargin(textLabel, new Insets(10));
  }

  public static MessageLog getInstance() {
    return instance;
  }

  /**
   * Displays a message in the log with a typewriter effect. If a previous message is still typing,
   * it is interrupted.
   *
   * @param message the message to display
   */
  public void log(String message) {
    AtomicInteger charIndex = new AtomicInteger();
    if (typewriter != null) {
      typewriter.stop();
    }

    charIndex.set(0);
    currentMessage.setLength(0);
    currentMessage.append(message);
    textLabel.setText("");

    typewriter =
        new Timeline(
            new KeyFrame(
                Duration.millis(30),
                e -> {
                  if (charIndex.get() < currentMessage.length()) {
                    textLabel.setText(
                        textLabel.getText() + currentMessage.charAt(charIndex.getAndIncrement()));
                  } else {
                    typewriter.stop();
                  }
                }));
    typewriter.setCycleCount(currentMessage.length());
    typewriter.play();
  }

  /**
   * Instantly completes the current message animation and shows the full message. Useful when the
   * player presses a key or clicks to skip.
   */
  public void skipToFullText() {
    if (typewriter != null && typewriter.getStatus() == Animation.Status.RUNNING) {
      typewriter.stop();
      textLabel.setText(currentMessage.toString());
    }
  }
}
