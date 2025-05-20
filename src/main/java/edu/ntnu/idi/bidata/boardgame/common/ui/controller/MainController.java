package edu.ntnu.idi.bidata.boardgame.common.ui.controller;

import edu.ntnu.idi.bidata.boardgame.common.ui.view.MainView;
import edu.ntnu.idi.bidata.boardgame.core.ui.Controller;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;

/**
 * @author Nick Heggø
 * @version 2025.05.08
 */
public class MainController extends Controller {

  public MainController(SceneSwitcher sceneSwitcher) {
    super(
        sceneSwitcher,
        new MainView(
            event -> sceneSwitcher.switchTo(View.Name.MONOPOLY_GAME_VIEW),
            event -> sceneSwitcher.switchTo(View.Name.SNAKE_GAME_VIEW)));
  }
}
