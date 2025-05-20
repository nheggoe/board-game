package edu.ntnu.idi.bidata.boardgame.games.snake.model;

import edu.ntnu.idi.bidata.boardgame.common.io.csv.CsvFileReader;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.LadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.NormalTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for creating Snake and Ladder boards from external files or data structures.
 *
 * @author Nick Heggø, Mihailo Hranisavljevic
 * @version 2025.05.19
 */
public class SnakeAndLadderBoardFactory {

  private SnakeAndLadderBoardFactory() {
    // Private constructor to prevent instantiation
  }

  /**
   * Creates a standard Snake and Ladder board from a default CSV file.
   *
   * @return a new Snake and Ladder board
   * @throws IllegalStateException if the board file cannot be read or is invalid
   */
  public static SnakeAndLadderBoard createBoard() {
    return createBoardFromCsv("src/main/resources/csv/snake_and_ladder_88_tiles.csv");
  }

  /**
   * Creates a Snake and Ladder board from a specified CSV file. The CSV file should have the
   * following format: Index, Type, Offset 0, Normal, 0 ...
   *
   * @param filePath path to the CSV file
   * @return a new Snake and Ladder board
   * @throws IllegalStateException if the file cannot be read or is invalid
   */
  public static SnakeAndLadderBoard createBoardFromCsv(String filePath) {
    try {
      List<String[]> lines = parseCsv(filePath);
      validateCsv(lines);

      List<SnakeAndLadderTile> tiles = new ArrayList<>();

      for (String[] line : lines) {
        tiles.add(createTile(line));
      }

      return new SnakeAndLadderBoard(tiles);

    } catch (IOException e) {
      throw new IllegalStateException("Failed to read board file at " + filePath, e);
    } catch (NumberFormatException e) {
      throw new IllegalStateException("Invalid number format in the board file at " + filePath, e);
    }
  }

  /**
   * Parses the CSV file into a list of string arrays for processing.
   *
   * @param filePath path to the CSV file
   * @return list of string arrays, each representing a row in the CSV file
   * @throws IOException if the file cannot be read
   */
  private static List<String[]> parseCsv(String filePath) throws IOException {
    return CsvFileReader.readLines(Path.of(filePath)).stream()
        .map(line -> line.split(","))
        .toList();
  }

  /**
   * Validates the structure of the CSV file before processing. It ensures that all rows have
   * exactly 3 columns.
   *
   * @param csvLines list of string arrays representing CSV data
   * @throws IllegalStateException if the CSV structure is invalid
   */
  private static void validateCsv(List<String[]> csvLines) {
    for (String[] line : csvLines) {
      if (line.length != 3) {
        throw new IllegalStateException(
            "Invalid board file format. Each row must have 3 columns (Index, Type, Offset).");
      }
    }
  }

  /**
   * Creates a specific tile (SnakeTile, LadderTile, or NormalTile) based on the input row.
   *
   * @param line the CSV row containing tile data
   * @return the corresponding SnakeAndLadderTile
   */
  private static SnakeAndLadderTile createTile(String[] line) {
    try {
      String type = line[1].trim();
      int offset = line[2].trim().isEmpty() ? 0 : Integer.parseInt(line[2].trim());

      return switch (type) {
        case "Snake" -> new SnakeTile(offset);
        case "Ladder" -> new LadderTile(offset);
        case "Normal" -> new NormalTile();
        default -> throw new IllegalStateException("Invalid tile type: " + type);
      };
    } catch (NumberFormatException e) {
      throw new IllegalStateException("Invalid offset value for tile: " + line[2]);
    }
  }
}
