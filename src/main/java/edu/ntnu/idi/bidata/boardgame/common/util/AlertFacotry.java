package edu.ntnu.idi.bidata.boardgame.common.util;

import javafx.scene.control.Alert;

public class AlertFacotry {

  private AlertFacotry() {}

  public static Alert createAlert(Alert.AlertType type, String content) {
    return switch (type) {
      case INFORMATION, WARNING, CONFIRMATION, ERROR -> new Alert(type, content);
      case NONE -> throw new UnsupportedOperationException();
    };
  }
}
