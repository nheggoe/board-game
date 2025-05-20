package edu.ntnu.idi.bidata.boardgame.common.event.type;

public sealed interface SnakeAndLadderEvent extends Event {
  record SnakeEncountered(int position) implements SnakeAndLadderEvent {}

  record LadderEncountered(int position) implements SnakeAndLadderEvent {}
}
