package edu.ntnu.idi.bidata.boardgame.games.snake.model;

import edu.ntnu.idi.bidata.boardgame.common.io.csv.CSVReader;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.LadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.NormalTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Factory for creating Snake and Ladder boards from external files or data structures.
 *
 * @author Nick Heggø, Mihailo Hranisavljevic
 * @version 2025.05.19
 */
public class SnakeAndLadderBoardFactory {

  private static final Pattern CSV_PATTERN = Pattern.compile("((\\s+)?,(\\s+)?)");

  private SnakeAndLadderBoardFactory() {}

  /**
   * Returns a standard board from the default file.
   *
   * @return a new SnakeAndLadderBoard
   * @throws IllegalStateException if the file fails to load or parse
   */
  public static SnakeAndLadderBoard createBoard() {
    return createBoardFromCsv(new File("src/main/resources/csv/snake_and_ladder_88_tiles.csv"));
  }

  /**
   * Loads a board from a given CSV file.
   *
   * <p>CSV format must be: Index, Type, Offset
   *
   * @param file the file to read from
   * @return a new SnakeAndLadderBoard
   * @throws IllegalStateException on load or parse failure
   */
  public static SnakeAndLadderBoard createBoardFromCsv(File file) {
    try {
      var lines = CSVReader.readLines(file);
      validateCsv(lines);
      var tiles = lines.stream().map(SnakeAndLadderBoardFactory::createTile).toList();
      return new SnakeAndLadderBoard(tiles);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read board file at " + file.getAbsolutePath(), e);
    } catch (NumberFormatException e) {
      throw new IllegalStateException(
          "Invalid number format in the board file at " + file.getAbsolutePath(), e);
    }
  }

  /**
   * Ensures each CSV row has 3 columns.
   *
   * @param lines raw CSV input
   * @throws IllegalStateException if the column count is invalid
   */
  private static void validateCsv(List<String> lines) {
    for (var line : lines) {
      var tokens = CSV_PATTERN.split(line);
      if (tokens.length != 3) {
        throw new IllegalStateException(
            "Invalid board file format. Each row must have 3 columns (Index, Type, Offset).");
      }
    }
  }

  /**
   * Instantiates a tile based on its row data.
   *
   * @param line the CSV line
   * @return a tile instance
   * @throws IllegalStateException on malformed input
   */
  private static SnakeAndLadderTile createTile(String line) {
    try {
      var tokens = CSV_PATTERN.split(line.strip());
      String type = tokens[1];
      int offset = tokens[2].isEmpty() ? 0 : Integer.parseInt(tokens[2]);
      return switch (type.toUpperCase()) {
        case "SNAKE" -> new SnakeTile(offset);
        case "LADDER" -> new LadderTile(offset);
        case "NORMAL" -> new NormalTile();
        default -> throw new IllegalStateException("Invalid tile type: " + type);
      };
    } catch (NumberFormatException ignored) {
      throw new IllegalStateException("Invalid CSV value: " + line);
    }
  }
}
