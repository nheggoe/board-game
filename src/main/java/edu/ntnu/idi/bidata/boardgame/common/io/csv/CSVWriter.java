package edu.ntnu.idi.bidata.boardgame.common.io.csv;

import edu.ntnu.idi.bidata.boardgame.common.io.FileUtil;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

public class CSVWriter {

  private static final Logger LOGGER = Logger.getLogger(CSVWriter.class.getName());

  private CSVWriter() {}

  public static void writeLines(Path csvFile, List<String[]> rows) throws IOException {
    FileUtil.ensureFileAndDirectoryExists(csvFile);
    try (BufferedWriter writer = Files.newBufferedWriter(csvFile)) {
      for (var row : rows) {
        writer.write(String.join(",", row));
        writer.newLine();
      }
    }
    LOGGER.fine(() -> "Wrote %d rows to %s".formatted(rows.size(), csvFile));
  }
}
