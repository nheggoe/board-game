package dev.nheggoe.boardgame.common.util;

import javafx.scene.control.Alert;

/**
 * The AlertFactory class is a utility class responsible for creating instances of Alert dialogs
 * with specified types and content messages. This class provides a static method to return
 * pre-configured Alert objects based on the Alert type.
 *
 * @author Nick Heggø
 * @version 2025.04.23
 */
public class AlertFactory {

  private AlertFactory() {}

  /**
   * Creates an alert dialog of the specified type with the provided content message. The alert type
   * determines the nature of the dialog (e.g., informational, warning, etc.). Throws an exception
   * if the alert type is unsupported.
   *
   * @param type the type of alert to be created, e.g., INFORMATION, WARNING, CONFIRMATION, or ERROR
   * @param content the message content to display in the alert dialog
   * @return an Alert object of the specified type with the provided content
   * @throws UnsupportedOperationException if the alert type is NONE
   */
  public static Alert createAlert(Alert.AlertType type, String content) {
    return switch (type) {
      case INFORMATION, WARNING, CONFIRMATION, ERROR -> new Alert(type, content);
      case NONE -> throw new UnsupportedOperationException();
    };
  }
}
