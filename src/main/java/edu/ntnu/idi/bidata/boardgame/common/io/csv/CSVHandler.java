package edu.ntnu.idi.bidata.boardgame.common.io.csv;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.common.io.FileUtil;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * The {@code CSVHandler} class is responsible for reading and writing data to a CSV file. The file
 * is created if it does not exist.
 *
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.05.19
 */
public class CSVHandler {

  private final Path csvFile;

  public CSVHandler(Path csvFile) {
    this.csvFile = requireNonNull(csvFile);
    try {
      FileUtil.ensureFileAndDirectoryExists(this.csvFile);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to create CSV file: " + csvFile, e);
    }
  }

  public List<String[]> readAll() throws IOException {
    return CSVReader.readAll(csvFile);
  }

  public void writeLines(List<String[]> rows) throws IOException {
    CSVWriter.writeLines(csvFile, rows);
  }
}
