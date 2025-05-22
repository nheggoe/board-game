package edu.ntnu.idi.bidata.boardgame.common.ui.component;

import edu.ntnu.idi.bidata.boardgame.common.ui.view.PlayerSetupView;
import edu.ntnu.idi.bidata.boardgame.core.ui.Controller;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;

public class PlayerSetupController extends Controller {
  public PlayerSetupController(SceneSwitcher sceneSwitcher) {
    super(sceneSwitcher, createPlayerSetupView(sceneSwitcher));
  }

  private static View createPlayerSetupView(SceneSwitcher sceneSwitcher) {
    return new PlayerSetupView(sceneSwitcher);
  }
}
