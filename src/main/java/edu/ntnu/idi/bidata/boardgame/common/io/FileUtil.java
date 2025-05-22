package edu.ntnu.idi.bidata.boardgame.common.io;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

/**
 * Utility class for handling file and directory operations. Provides methods to ensure that a
 * specified file and its parent directories exist, creating them if necessary. Supports generating
 * paths for standard and test environments with validated extensions.
 *
 * @author Nick Heggø
 * @version 2025.05.22
 */
public class FileUtil {

  private static final Logger LOGGER = Logger.getLogger(FileUtil.class.getName());

  private static final String FILE_PATH_TEMPLATE = "data/%s/%s.%s";
  private static final List<String> SUPPORTED_FILE_EXTENSIONS = List.of("json", "csv");

  private FileUtil() {}

  public static Path generateFilePath(String fileName, String fileExtension) {
    requireNonNull(fileName, "File name cannot be null");
    requireNonNull(fileExtension, "File extension cannot be null");
    String ext = fileExtension.toLowerCase().strip();
    if (!SUPPORTED_FILE_EXTENSIONS.contains(ext)) {
      throw new UnsupportedOperationException("Unsupported file extension: " + fileExtension);
    }
    String name = fileName.strip();
    return Path.of(FILE_PATH_TEMPLATE.formatted(ext, name, ext));
  }

  /**
   * Ensures that the specified file and its parent directories exist. If the directories do not
   * exist, they will be created. If the file does not exist, it will be created.
   *
   * @param path the path to the file to ensure exists; must not be null
   */
  public static void ensureFileAndDirectoryExists(Path path) {
    requireNonNull(path, "The path cannot be null");
    try {
      Files.createDirectories(path.getParent());

      if (Files.notExists(path)) {
        Files.createFile(path);
        LOGGER.fine(() -> "Created file: " + path.toAbsolutePath());
      }
    } catch (IOException e) {
      LOGGER.severe(() -> "Failed to create file or directories: " + path + " — " + e.getMessage());
    }
  }
}
