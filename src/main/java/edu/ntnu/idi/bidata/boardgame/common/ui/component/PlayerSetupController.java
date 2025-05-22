package edu.ntnu.idi.bidata.boardgame.common.ui.component;

import edu.ntnu.idi.bidata.boardgame.common.ui.view.PlayerSetupView;
import edu.ntnu.idi.bidata.boardgame.core.ui.Controller;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;

public class PlayerSetupController extends Controller {

  /**
   * Constructs a PlayerSetupController instance that initializes the player setup view to manage
   * the player's configuration setup within the application.
   *
   * @param sceneSwitcher the SceneSwitcher responsible for managing scene transitions; must not be
   *     null
   */
  public PlayerSetupController(SceneSwitcher sceneSwitcher) {
    super(sceneSwitcher, createPlayerSetupView(sceneSwitcher));
  }

  private static View createPlayerSetupView(SceneSwitcher sceneSwitcher) {
    return new PlayerSetupView(sceneSwitcher);
  }
}
