package edu.ntnu.idi.bidata.boardgame.common.event.type;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

public sealed interface UserInterfaceEvent extends Event {

  record Output(String message) implements UserInterfaceEvent {
    public Output {
      requireNonNull(message, "Output message cannot be null!");
    }
  }

  record UserInputRequest<T>(String message, Consumer<T> callback) implements UserInterfaceEvent {
    public UserInputRequest {
      requireNonNull(message, "Message cannot be null!");
      requireNonNull(callback, "Callback cannot be null!");
    }

    public void accept(T input) {
      callback.accept(input);
    }
  }
}
