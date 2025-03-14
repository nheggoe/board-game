package edu.ntnu.idi.bidata.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.ntnu.idi.bidata.core.Board;
import edu.ntnu.idi.bidata.core.Player;

/**
 * The {@code TileAction} interface represents an action that can be performed when a player gets to
 * a specific tile on the board.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.14
 */

/*
 The @JsonTypeInfo annotation is used to include the type information in the JSON output.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
@JsonSubTypes({
    @JsonSubTypes.Type(value = LadderAction.class, name = "LadderAction"),
    @JsonSubTypes.Type(value = SnakeAction.class, name = "SnakeAction"),
    @JsonSubTypes.Type(value = ResetAction.class, name = "ResetAction")
})
public interface TileAction {
  void perform(Player player, Board board);
}
