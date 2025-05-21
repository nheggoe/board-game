package edu.ntnu.idi.bidata.boardgame.common.ui.controller;

import edu.ntnu.idi.bidata.boardgame.common.ui.view.MainView;
import edu.ntnu.idi.bidata.boardgame.core.ui.Controller;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;

/**
 * @author Nick Heggø
 * @version 2025.05.08
 */
public class MainController extends Controller {

  public MainController(SceneSwitcher sceneSwitcher) {
    super(sceneSwitcher, createMainView(sceneSwitcher));
  }

  private static MainView createMainView(SceneSwitcher sceneSwitcher) {
    return new MainView(sceneSwitcher);
  }
}
