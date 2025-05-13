package edu.ntnu.idi.bidata.boardgame.common.io.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReader {

  private CsvFileReader() {}

  public static List<String> readLines(Path filePath) throws IOException {
    List<String> lines = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
      String line;
      br.readLine(); // skip the first line
      while ((line = br.readLine()) != null) {
        if (!line.startsWith("#")) {
          lines.add(line);
        }
      }
    }
    return lines;
  }
}
