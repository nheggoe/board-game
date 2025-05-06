package edu.ntnu.idi.bidata.boardgame.games.monopoly.controller;

import java.util.Objects;

public abstract class Controller implements AutoCloseable {

  private final SceneSwitcher sceneSwitcher;

  protected Controller(SceneSwitcher sceneSwitcher) {
    this.sceneSwitcher = Objects.requireNonNull(sceneSwitcher, "SceneSwitcher cannot be null!");
  }

  protected SceneSwitcher getSceneSwitcher() {
    return sceneSwitcher;
  }
}
