package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Ownable;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Property;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Utility;
import java.util.ArrayList;
import java.util.List;

public class TileFactory {

  private TileFactory() {}

  public static List<Tile> generateTilesFromCSV(List<String> lines, int exptectedRows) {
    List<Tile> tiles = new ArrayList<>();
    for (String line : lines) {
      // Name,Type,Color,Cost
      //  0,   1,    2,   3
      var tokens = line.split("(\\s*)(,)?(\\s*)");
      if (tokens.length != exptectedRows) {
        throw new IllegalStateException("Row count doesn't match the expected result.");
      }
      Ownable ownable =
          switch (tokens[1].toUpperCase()) {
            case "PROPERTY" ->
                new Property(
                    tokens[0],
                    Property.Color.valueOf(tokens[2].toUpperCase()),
                    Integer.parseInt(tokens[3]));
            case "UTILITY" -> new Utility(tokens[0], Integer.parseInt(tokens[3]));
            default ->
                throw new IllegalStateException("Unexpected value: " + tokens[1].toUpperCase());
          };

      tiles.add(new OwnableTile(ownable));
    }

    return tiles;
  }
}
