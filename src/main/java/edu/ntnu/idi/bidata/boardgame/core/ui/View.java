package edu.ntnu.idi.bidata.boardgame.core.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.layout.Region;

/**
 * @author Nick Heggø
 * @version 2025.05.06
 */
public abstract class View extends Region implements AutoCloseable {

  private final List<Component> components;

  protected View() {
    this.components = new ArrayList<>();
  }

  protected void addComponents(Component... components) {
    this.components.addAll(Arrays.asList(components));
  }

  @Override
  public void close() throws Exception {
    for (var component : components) {
      if (component instanceof EventListeningComponent listener) {
        listener.close();
      }
    }
  }

  public enum Name {
    MAIN_VIEW,
    GAME_VIEW
  }
}
