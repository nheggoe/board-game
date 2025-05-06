package edu.ntnu.idi.bidata.boardgame.core;

import edu.ntnu.idi.bidata.boardgame.common.event.EventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.scene.layout.Region;

public abstract class View extends Region implements AutoCloseable {

  private final List<Component> components;

  protected View() {
    this.components = new ArrayList<>();
  }

  protected void addComponent(Component component) {
    Objects.requireNonNull(component, "Component cannot be null!");
    components.add(component);
  }

  protected void addAllComponents(Component... components) {
    for (Component component : components) {
      addComponent(component);
    }
  }

  @Override
  public void close() throws Exception {
    for (Component component : components) {
      if (component instanceof EventListener listener) {
        listener.close();
      }
    }
  }
}
