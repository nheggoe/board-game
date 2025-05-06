package edu.ntnu.idi.bidata.boardgame.core;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.controller.SceneSwitcher;
import java.util.Objects;

public abstract class Controller {

  private final SceneSwitcher sceneSwitcher;

  protected Controller(SceneSwitcher sceneSwitcher) {
    this.sceneSwitcher = Objects.requireNonNull(sceneSwitcher, "SceneSwitcher cannot be null!");
  }

  protected SceneSwitcher getSceneSwitcher() {
    return sceneSwitcher;
  }
}
