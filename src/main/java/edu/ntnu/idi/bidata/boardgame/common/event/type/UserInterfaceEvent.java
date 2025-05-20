package edu.ntnu.idi.bidata.boardgame.common.event.type;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

public sealed interface UserInterfaceEvent extends Event {

  record Output(String message) implements UserInterfaceEvent {
    public Output {
      requireNonNull(message, "Output message cannot be null!");
    }
  }

  record Exception(java.lang.Exception exception) implements UserInterfaceEvent {
    public Exception {
      requireNonNull(exception, "Exception cannot be null!");
    }
  }

  record UserInputRequest(String message, Consumer<String> callback) {
    public UserInputRequest {
      requireNonNull(message, "Message cannot be null!");
      requireNonNull(callback, "Callback cannot be null!");
    }
  }
}
