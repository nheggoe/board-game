package edu.ntnu.idi.bidata.boardgame.common.io.csv;

import edu.ntnu.idi.bidata.boardgame.backend.io.FileUtil;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.util.OutputHandler;
import java.io.*;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * The {@code CSVHandler} class is responsible for reading and writing data to a CSV file. The file
 * is created if it does not exist. The file is expected to have a header row with the columns
 * "Name" and "Figure".
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.03.12
 */
public class CSVHandler {
  private static final Logger LOGGER = Logger.getLogger(CSVHandler.class.getName());
  private final Path filePath;

  public CSVHandler(String filename) {
    this.filePath = FileUtil.generateFilePath(filename, "csv");
    initializeFile();
  }

  public CSVHandler(Path filePath) {
    this.filePath = filePath;
    initializeFile();
  }

  /**
   * Creates a file with the given filename if it does not exist. The file is expected to have a
   * header row with the columns "Name" and "Figure". If the file already exists, nothing is done.
   */
  private void initializeFile() {
    File file = filePath.toFile();
    FileUtil.ensureFileAndDirectoryExists(file);
    if (!file.exists()) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        writer.write("Name,Figure");
        writer.newLine();
      } catch (IOException e) {
        LOGGER.severe(() -> "Error creating CSV file: " + filePath);
      }
    }
  }

  /**
   * Saves players to the CSV file using a Stream.
   *
   * @param playersStream A Stream of Player objects to save.
   */
  public void savePlayers(Stream<Player> playersStream) {
    try (BufferedWriter writer =
        new BufferedWriter(new FileWriter(filePath.toFile(), false))) { // Overwrites the file
      writer.write("Name,Figure");
      writer.newLine();

      playersStream.forEach(
          player -> {
            try {
              String line = player.getName() + "," + player.getFigure();
              writer.write(line);
              writer.newLine();
              OutputHandler.println("Saved: " + line);
            } catch (IOException e) {
              LOGGER.severe(() -> "Error writing to CSV file: " + filePath);
            }
          });

    } catch (IOException e) {
      LOGGER.severe(() -> "Error writing to CSV file: " + filePath);
    }
  }

  /**
   * Loads players from the CSV file and returns them as a Stream.
   *
   * @return A Stream of Player objects loaded from the CSV file.
   */
  public Stream<Player> loadPlayers() {
    Stream.Builder<Player> playerStreamBuilder = Stream.builder();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
      String line;
      boolean firstLine = true;

      while ((line = reader.readLine()) != null) {
        if (firstLine) {
          firstLine = false;
          continue;
        }

        String[] data = line.split(",");
        if (data.length == 2) {
          Player player = new Player(data[0], Player.Figure.valueOf(data[1]));
          playerStreamBuilder.add(player);
          OutputHandler.println("Loaded player: " + data[0] + " - " + data[1]);
        }
      }
    } catch (IOException e) {
      LOGGER.severe(() -> "Error reading from CSV file: " + filePath);
    }
    return playerStreamBuilder.build();
  }
}
