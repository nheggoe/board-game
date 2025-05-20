package edu.ntnu.idi.bidata.boardgame.common.io.json.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.LadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.NormalTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import java.lang.reflect.Type;

/**
 * @author Nick Heggø
 * @version 2025.05.20
 */
public class SnakeAndLadderTileAdapter
    implements JsonSerializer<SnakeAndLadderTile>, JsonDeserializer<SnakeAndLadderTile> {

  private static final String TYPE_KEY = "tileType";
  private static final String DATA_KEY = "data";

  @Override
  public JsonElement serialize(
      SnakeAndLadderTile tile, Type type, JsonSerializationContext context) {
    JsonObject result = new JsonObject();

    switch (tile) {
      case LadderTile ladderTile -> {
        result.addProperty(TYPE_KEY, "ladder");
        result.add(DATA_KEY, context.serialize(ladderTile, LadderTile.class));
      }
      case NormalTile normalTile -> {
        result.addProperty(TYPE_KEY, "normal");
        result.add(DATA_KEY, context.serialize(normalTile, NormalTile.class));
      }
      case SnakeTile snakeTile -> {
        result.addProperty(TYPE_KEY, "snake");
        result.add(DATA_KEY, context.serialize(snakeTile, SnakeTile.class));
      }
    }

    return result;
  }

  @Override
  public SnakeAndLadderTile deserialize(
      JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    String typeString = jsonObject.get(TYPE_KEY).getAsString();
    JsonElement data = jsonObject.get(DATA_KEY);

    return switch (typeString) {
      case "ladder" -> context.deserialize(data, LadderTile.class);
      case "normal" -> context.deserialize(data, NormalTile.class);
      case "snake" -> context.deserialize(data, SnakeTile.class);
      default -> throw new JsonParseException("Unknown SnakeAndLadderTile type: " + typeString);
    };
  }
}
