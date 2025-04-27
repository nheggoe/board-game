package edu.ntnu.idi.bidata.boardgame.frontend.component;

import edu.ntnu.idi.bidata.boardgame.backend.model.dice.DiceRoll;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * {@code DiceView} is a visual component for displaying and animating dice rolls. It dynamically
 * loads dice face images and provides an animated rolling effect before displaying the final
 * result.
 *
 * <p>This class uses JavaFX's {@link Timeline} for simple roll animation, and can be used to
 * visually represent dice outcomes in the board game. Expected dice face images should be placed
 * under the {@code /images} directory with filenames matching the pattern {@code dice1.png}, {@code
 * dice2.png}, etc.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * DiceView diceView = new DiceView(2);
 * diceView.rollDiceAnimated(diceRoll, () -> System.out.println("Roll complete!"));
 * }</pre>
 *
 * @version 2025.04.25
 */
public class DiceView extends HBox {

  private static final String BASE_PATH = "/images/dice";
  private static final int FACE_COUNT = 6;
  private static final int STEPS = 30; // More frames for smoother animation
  private static final int TOTAL_DURATION_MS = 1500; // 1.5 seconds
  private static final int FRAME_DURATION_MS = TOTAL_DURATION_MS / STEPS;

  private ImageView[] diceImages;
  private final Random random = new Random();

  /**
   * Constructs a {@code DiceView} with the specified number of dice.
   *
   * @param diceCount the number of dice to display
   */
  public DiceView() {
    setAlignment(Pos.CENTER);
    setSpacing(20);
  }

  /**
   * Animates the dice roll, showing random faces during the animation, then settling on the final
   * result provided by the {@link DiceRoll}.
   *
   * @param result the final dice roll result to display
   * @param onFinish a {@link Runnable} to execute after the animation finishes
   */
  public void rollDiceAnimated(DiceRoll result, Runnable onFinish) {
    int diceCount = result.rolls().length;
    this.diceImages = new ImageView[diceCount];
    for (int i = 0; i < diceCount; i++) {
      ImageView imageView = new ImageView();
      imageView.setFitWidth(64);
      imageView.setFitHeight(64);
      diceImages[i] = imageView;
      getChildren().add(imageView);
    }

    Timeline timeline = new Timeline();
    timeline.setCycleCount(STEPS);

    timeline
        .getKeyFrames()
        .add(
            new KeyFrame(
                Duration.millis(FRAME_DURATION_MS),
                event -> {
                  for (ImageView imageView : diceImages) {
                    int randomFace = random.nextInt(FACE_COUNT) + 1;
                    imageView.setImage(loadFace(randomFace));
                  }
                }));

    timeline.setOnFinished(
        event -> {
          for (int i = 0; i < diceImages.length; i++) {
            int face = result.rolls()[i];
            diceImages[i].setImage(loadFace(face));
          }
          if (onFinish != null) onFinish.run();
        });

    timeline.play();
  }

  /**
   * Loads the image corresponding to a given dice face value.
   *
   * @param face the face value (1–6) of the die
   * @return the {@link Image} representing the face
   */
  private Image loadFace(int face) {
    return new Image(BASE_PATH + face + ".png");
  }
}
