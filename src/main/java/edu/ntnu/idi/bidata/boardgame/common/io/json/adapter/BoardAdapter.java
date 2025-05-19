package edu.ntnu.idi.bidata.boardgame.common.io.json.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import edu.ntnu.idi.bidata.boardgame.core.model.Board;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.MonopolyBoard;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import java.lang.reflect.Type;

/**
 * Specialized Gson adapter for the Board interface. Handles serialization/deserialization of
 * MonopolyBoard and SnakeAndLadderBoard implementations.
 *
 * @author Nick Heggø
 * @version 2025.05.18
 */
public class BoardAdapter implements JsonSerializer<Board<?>>, JsonDeserializer<Board<?>> {

  private static final String TYPE_KEY = "boardType";
  private static final String DATA_KEY = "data";

  @Override
  public JsonElement serialize(Board<?> board, Type type, JsonSerializationContext context) {
    JsonObject result = new JsonObject();

    switch (board) {
      case MonopolyBoard monopolyBoard -> {
        result.addProperty(TYPE_KEY, "monopoly");
        result.add(DATA_KEY, context.serialize(monopolyBoard, MonopolyBoard.class));
      }
      case SnakeAndLadderBoard snakeBoard -> {
        result.addProperty(TYPE_KEY, "snakeAndLadder");
        result.add(DATA_KEY, context.serialize(snakeBoard, SnakeAndLadderBoard.class));
      }
      default -> {
        // Use generic interface adapter for unknown Board implementations
        result.addProperty(TYPE_KEY, "generic");
        result.addProperty("className", board.getClass().getName());
        result.add(DATA_KEY, context.serialize(board));
      }
    }

    return result;
  }

  @Override
  public Board<?> deserialize(JsonElement json, Type type, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    String typeString = jsonObject.get(TYPE_KEY).getAsString();
    JsonElement data = jsonObject.get(DATA_KEY);

    return switch (typeString) {
      case "monopoly" -> context.deserialize(data, MonopolyBoard.class);
      case "snakeAndLadder" -> context.deserialize(data, SnakeAndLadderBoard.class);
      default -> throw new JsonParseException("Unknown Board type: " + typeString);
    };
  }
}
