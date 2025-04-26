package edu.ntnu.idi.bidata.boardgame.backend.io.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OwnableCSVHandler {

  private OwnableCSVHandler() {}

  public static List<String> readLines(String filename) throws IOException {
    List<String> lines = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
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
