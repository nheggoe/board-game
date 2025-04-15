package edu.ntnu.idi.bidata.boardgame.backend.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * Utility class for handling file and directory operations. Provides methods to ensure that a
 * specified file and its parent directories exist, creating them if necessary.
 *
 * @author Nick Heggø
 * @version 2025.04.15
 */
public class FileUtil {

  private static final Logger LOGGER = Logger.getLogger(FileUtil.class.getName());

  private static final String FILE_PATH_TEMPLATE = "data/%s/%s.%s";
  private static final String TEST_FILE_PATH_TEMPLATE = "src/test/resources/%s/%s.%s";

  private FileUtil() {}

  public static Path generateFilePath(String fileName, String fileExtension) {
    return generateFilePath(fileName, fileExtension, false);
  }

  /**
   * Generates a file path based on the provided file name, file extension, and whether the file is
   * intended for testing purposes.
   *
   * @param fileName the name of the file to be generated; must not be null
   * @param fileExtension the desired file extension; must not be null
   * @param isTest a boolean flag indicating whether the file is intended for testing purposes
   * @return the generated file path as a {@link Path} object based on the provided parameters
   */
  public static Path generateFilePath(String fileName, String fileExtension, boolean isTest) {
    if (fileName == null || fileExtension == null) {
      throw new IllegalArgumentException("File name and file extension must not be null");
    }
    String fileNameFormatted = fileName.strip();
    String fileExtensionFormatted = fileExtension.toLowerCase().strip();
    return Path.of(
        (isTest ? TEST_FILE_PATH_TEMPLATE : FILE_PATH_TEMPLATE)
            .formatted(fileExtensionFormatted, fileNameFormatted, fileExtensionFormatted));
  }

  /**
   * Ensures that the specified file and its parent directories exist. If the directories do not
   * exist, they will be created. If the file does not exist, it will be created.
   *
   * @param file the file whose existence (and its parent directories) should be ensured; must not
   *     be null
   */
  public static void ensureFileAndDirectoryExists(File file) {
    if (file == null) {
      throw new IllegalStateException("The file cannot be null");
    }
    createDirectory(file);
    createFile(file);
  }

  private static void createDirectory(File file) {
    File parentDir = file.getParentFile();
    if (parentDir != null && parentDir.mkdirs()) {
      LOGGER.info(() -> "Created directory: " + parentDir.getAbsolutePath());
    }
  }

  private static void createFile(File file) {
    try {
      if (file.createNewFile()) {
        LOGGER.info(() -> "Created file: " + file.getAbsolutePath());
      }
    } catch (IOException e) {
      LOGGER.severe(() -> "Cannot create file: " + file);
    }
  }
}
