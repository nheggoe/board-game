package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.core.Board;
import edu.ntnu.idi.bidata.core.Player;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * The {@code CSVHandler} class is responsible for reading and writing data to a CSV file.
 * The file is created if it does not exist.
 * The file is expected to have a header row with the columns "Name" and "Figure".
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.03.12
 */
public class CSVHandler {
  private static final Logger logger = Logger.getLogger(CSVHandler.class.getName());
  private final String filename;

  public CSVHandler(String filename) {
    this.filename = filename;
    initializeFile();
  }

  /**
   * Creates a file with the given filename if it does not exist.
   * The file is expected to have a header row with the columns "Name" and "Figure".
   * If the file already exists, nothing is done.
   */
  private void initializeFile() {
    File file = new File(filename);
    if (!file.exists()) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        writer.write("Name,Figure");
        writer.newLine();
      } catch (IOException e) {
        logger.log(Level.SEVERE, () -> "Error creating CSV file: " + filename);
      }
    }
  }

  /**
   * Saves players to the CSV file using a Stream.
   *
   * @param playersStream A Stream of Player objects to save.
   */
  public void savePlayers(Stream<Player> playersStream) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
      writer.write("Name,Figure");
      writer.newLine();
      playersStream.forEach(player -> {
        try {
          writer.write(player.getName() + "," + player.getFigure());
          writer.newLine();
        } catch (IOException e) {
          logger.log(Level.SEVERE, () -> "Error writing player data to CSV file: " + filename);
        }
      });
    } catch (IOException e) {
      logger.log(Level.SEVERE, () -> "Error opening CSV file for writing: " + filename);
    }
  }

  /**
   * Loads players from the CSV file and returns them as a Stream.
   *
   * @return A Stream of Player objects loaded from the CSV file.
   */
  public Stream<Player> loadPlayers(Board board) {
    Stream.Builder<Player> playerStreamBuilder = Stream.builder();
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");
        if (data.length == 2) {
          playerStreamBuilder.add(new Player(data[0], board, data[1]));
        }
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, () -> "Error reading CSV file: " + filename);
    }
    return playerStreamBuilder.build();
  }
}
