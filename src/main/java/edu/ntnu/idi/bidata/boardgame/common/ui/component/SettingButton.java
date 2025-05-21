package edu.ntnu.idi.bidata.boardgame.common.ui.component;

import edu.ntnu.idi.bidata.boardgame.core.ui.Component;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class SettingButton extends Component {

  public SettingButton(SceneSwitcher sceneSwitcher) {
    super();
    setAlignment(Pos.TOP_RIGHT);
    setPadding(new Insets(10));
    var settings = new Button("Settings");
    settings.setOnAction(ignored -> new SettingDialog(sceneSwitcher).showAndWait());
    getChildren().add(settings);
  }
}
