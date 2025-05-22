package edu.ntnu.idi.bidata.boardgame.core.ui;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

/**
 * @author Nick Heggø
 * @version 2025.05.08
 */
public abstract class Controller {

  private final SceneSwitcher sceneSwitcher;
  private final View view;

  protected Controller(SceneSwitcher sceneSwitcher, View view) {
    this.sceneSwitcher = requireNonNull(sceneSwitcher, "SceneSwitcher cannot be null!");
    this.view = requireNonNull(view, "View cannot be null!");
    bindSizeProperty();
  }

  private void bindSizeProperty() {
    view.prefWidthProperty().bind(sceneSwitcher.getScene().widthProperty());
    view.prefHeightProperty().bind(sceneSwitcher.getScene().heightProperty());
  }

  protected SceneSwitcher getSceneSwitcher() {
    return sceneSwitcher;
  }

  public View getView() {
    return view;
  }

  public void switchTo(SceneSwitcher.SceneName name) throws IOException {
    getSceneSwitcher().switchTo(name);
  }
}
