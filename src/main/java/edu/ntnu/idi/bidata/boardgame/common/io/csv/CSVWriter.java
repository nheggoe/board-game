package edu.ntnu.idi.bidata.boardgame.common.io.csv;

import static java.util.Objects.requireNonNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class CSVWriter {

  private static final Logger LOGGER = Logger.getLogger(CSVWriter.class.getName());

  private CSVWriter() {}

  public static void writeLines(File file, List<String> lines, boolean append) throws IOException {
    if (!file.exists()) {
      throw new FileNotFoundException("File does not exist: " + file.getAbsolutePath());
    }
    var dataCells = convertLinesToCells(lines);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
      for (List<String> row : dataCells) {
        writer.write(String.join(",", row));
        writer.newLine();
      }
    }
    LOGGER.info(
        () ->
            "Wrote %d rows to %s (append=%s)"
                .formatted(lines.size(), file.getParentFile(), append));
  }

  private static List<List<String>> convertLinesToCells(List<String> lines) {
    requireNonNull(lines, "Values cannot be null!");
    var dataCells =
        lines.stream().map(line -> Arrays.stream(line.split("(\\s+)?,(\\s+)?")).toList()).toList();
    assertDataCells(lines, dataCells);
    return dataCells;
  }

  private static void assertDataCells(List<String> lines, List<List<String>> dataCells) {
    if (lines.stream().anyMatch(list -> list == null || list.isEmpty())) {
      throw new IllegalArgumentException("Values cannot contain null or empty lists!");
    }
    if (dataCells.stream().anyMatch(list -> list.size() != dataCells.getFirst().size())) {
      throw new IllegalArgumentException("Values cannot contain lists of different sizes!");
    }
  }
}
