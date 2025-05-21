package edu.ntnu.idi.bidata.boardgame.common.io.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

  private CSVReader() {}

  public static List<String> readLines(File file) throws IOException {
    List<String> lines = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
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
