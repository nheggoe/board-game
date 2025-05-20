package edu.ntnu.idi.bidata.boardgame.common.io.csv;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.common.io.FileUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The {@code CSVHandler} class is responsible for reading and writing data to a CSV file. The file
 * is created if it does not exist.
 *
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.05.19
 */
public class CSVHandler {

  private final File file;

  public CSVHandler(File file) {
    this.file = requireNonNull(file);
    FileUtil.ensureFileAndDirectoryExists(this.file);
  }

  public CSVHandler(String filename) {
    this(FileUtil.generateFilePath(filename, "csv").toFile());
  }

  public List<String> readCSV() throws IOException {
    return CSVReader.readLines(file);
  }

  public void writeCSV(List<String> lines, boolean append) throws IOException {
    CSVWriter.writeLines(file, lines, append);
  }
}
