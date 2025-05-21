package edu.ntnu.idi.bidata.boardgame.common.ui.component;

import edu.ntnu.idi.bidata.boardgame.core.ui.Component;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class SettingButton extends Component {

  private final Button settings;
  private boolean inGame;

  public SettingButton(SceneSwitcher sceneSwitcher) {
    super();
    setAlignment(Pos.TOP_RIGHT);
    setPadding(new Insets(10));
    settings = new Button("Settings");
    settings.setOnAction(ignored -> new SettingDialog(sceneSwitcher, inGame).showAndWait());
    getChildren().add(settings);
  }

  public void isInGame(boolean inGame) {
    this.inGame = inGame;
  }
}
