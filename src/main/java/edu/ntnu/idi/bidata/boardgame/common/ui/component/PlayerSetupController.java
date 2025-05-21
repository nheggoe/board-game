package edu.ntnu.idi.bidata.boardgame.common.ui.component;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.ui.view.PlayerSetupView;
import edu.ntnu.idi.bidata.boardgame.core.ui.Controller;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;

public class PlayerSetupController extends Controller {
  public PlayerSetupController(SceneSwitcher sceneSwitcher, EventBus eventBus) {
    super(sceneSwitcher, createPlayerSetupView(sceneSwitcher, eventBus));
  }

  private static View createPlayerSetupView(SceneSwitcher sceneSwitcher, EventBus eventBus) {
    return new PlayerSetupView(sceneSwitcher);
  }
}
