package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

import edu.ntnu.idi.bidata.boardgame.backend.io.csv.OwnableCSVHandler;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Ownable;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Property;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Utility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 2025.04.22
 * @author Nick Heggø
 */
public class TileFactory {

  private TileFactory() {}

  public static List<Tile> generateTile() {
    try {
      return generateTilesFromCSV(
          OwnableCSVHandler.readLines("src/main/resources/csv/monopoly_tiles.csv"), 4);
    } catch (IOException ignored) {
      return List.of();
    }
  }

  public static List<Tile> generateTilesFromCSV(List<String> lines, int expectedColumnCount) {
    List<Tile> tiles = new ArrayList<>();
    for (String line : lines) {
      // Name, Type, Color, Cost
      //   0,    1,    2,     3
      var tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
      if (tokens.length != expectedColumnCount) {
        throw new IllegalStateException(
            "Row count doesn't match the expected result. Expected: %d, Actual :%d."
                .formatted(expectedColumnCount, tokens.length));
      }
      Ownable ownable =
          switch (tokens[1].toUpperCase()) {
            case "PROPERTY" ->
                new Property(
                    tokens[0],
                    Property.Color.valueOf(tokens[2].toUpperCase().replace(' ', '_')),
                    Integer.parseInt(tokens[3]));
            case "UTILITY" -> new Utility(tokens[0], Integer.parseInt(tokens[3]));
            default ->
                throw new UnsupportedOperationException(
                    "Unexpected value: " + tokens[1].toUpperCase());
          };

      tiles.add(new OwnableTile(ownable));
    }

    return tiles;
  }
}
