package edu.ntnu.idi.bidata.boardgame.games.snake.model;

import edu.ntnu.idi.bidata.boardgame.common.io.csv.CsvFileReader;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.LadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.NormalTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class SnakeAndLadderBoardFactory {

  private SnakeAndLadderBoardFactory() {}

  public static SnakeAndLadderBoard createBoard() {
    try {

      var lines =
          CsvFileReader.readLines(Path.of("src/main/resources/csv/snake_and_ladder_88_tiles.csv"))
              .stream()
              .map(line -> line.split(","))
              .toList();

      // assertion
      lines.forEach(
          tokenString -> {
            if (tokenString.length != 3) {
              throw new IllegalStateException("Invalid board file");
            }
          });

      // Index,Type,Offset
      //   0,   1,   2
      var tiles = new ArrayList<SnakeAndLadderTile>();
      for (var line : lines) {
        var tile =
            switch (line[1]) {
              case "Snake" -> new SnakeTile(Integer.parseInt(line[2]));
              case "Ladder" -> new LadderTile(Integer.parseInt(line[2]));
              case "Normal" -> new NormalTile();
              default -> throw new IllegalStateException("Invalid tile type: " + line[1]);
            };
        tiles.add(tile);
      }

      return new SnakeAndLadderBoard(tiles);

    } catch (IOException ignored) {
      throw new IllegalStateException("Failed to read board file");
    }
  }
}
