package edu.ntnu.idi.bidata.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Utility class for handling file and directory operations. Provides methods to ensure that a
 * specified file and its parent directories exist, creating them if necessary.
 *
 * @author Nick Heggø
 * @version 2025.03.14
 */
public class FileUtil {

  private static final Logger LOGGER = Logger.getLogger(FileUtil.class.getName());

  private FileUtil() {}

  /**
   * Ensures that the specified file and its parent directories exist. If the directories do not
   * exist, they will be created. If the file does not exist, it will be created.
   *
   * @param file the file whose existence (and its parent directories) should be ensured; must not
   *     be null
   * @throws IOException if an error occurs during the creation of the file or directories
   */
  public static void ensureFileAndDirectoryExists(File file) throws IOException {
    createDirectory(file);
    createFile(file);
  }

  private static void createDirectory(File file) {
    File parentDir = file.getParentFile();
    if (parentDir.mkdirs()) {
      LOGGER.info("Created directory: " + parentDir.getAbsolutePath());
    }
  }

  private static void createFile(File file) throws IOException {
    if (file.createNewFile()) {
      LOGGER.info("Created file: " + file.getAbsolutePath());
    }
  }
}
