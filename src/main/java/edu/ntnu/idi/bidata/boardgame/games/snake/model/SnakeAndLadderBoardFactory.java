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

  private static final Pattern CSV_PATTERN = Pattern.compile("\\s*,\\s*");

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
   * Reads a CSV file and constructs a {@link SnakeAndLadderBoard} from its rows.
   *
   * <p>Automatically detects and skips a header row if it begins with "Index,". Blank lines are
   * ignored. Each data row must have exactly three comma-separated fields: index, tile type
   * ("SNAKE", "LADDER" or "NORMAL"), and offset.
   *
   * @param file the CSV file to read; may include a header line and blank lines
   * @return a newly created {@code SnakeAndLadderBoard} containing one tile per row
   * @throws IllegalStateException if the file is empty, cannot be read, or any data row does not
   *     have exactly three columns or contains invalid values
   */
  public static SnakeAndLadderBoard createBoardFromCsv(File file) {
    try {
      List<String> lines = CSVReader.readLines(file);
      if (lines.isEmpty()) {
        throw new IllegalStateException("Board file is empty: " + file.getAbsolutePath());
      }

      String first =
          lines.stream().map(String::trim).filter(l -> !l.isEmpty()).findFirst().orElseThrow();
      boolean hasHeader = first.toLowerCase().startsWith("index,");

      List<String> dataLines =
          lines.stream()
              .map(String::trim)
              .filter(l -> !l.isEmpty())
              .skip(hasHeader ? 1 : 0)
              .toList();

      validateCsv(dataLines);

      List<SnakeAndLadderTile> tiles =
          dataLines.stream().map(SnakeAndLadderBoardFactory::createTile).toList();

      return new SnakeAndLadderBoard(tiles);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read board file at " + file.getAbsolutePath(), e);
    }
  }

  /**
   * Ensures each CSV data row has exactly 3 columns.
   *
   * @param lines raw CSV input including header
   * @throws IllegalStateException if the column count is invalid
   */
  private static void validateCsv(List<String> lines) {
    for (int i = 1; i < lines.size(); i++) {
      String line = lines.get(i).trim();
      if (line.isEmpty()) {
        continue;
      }
      String[] tokens = CSV_PATTERN.split(line);
      if (tokens.length != 3) {
        throw new IllegalStateException(
            "Invalid board file format at line "
                + (i + 1)
                + ": each row must have exactly 3 columns (Index,Type,Offset).");
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
