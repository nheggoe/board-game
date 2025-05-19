package edu.ntnu.idi.bidata.boardgame.common.io.csv;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

import edu.ntnu.idi.bidata.boardgame.common.io.FileUtil;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CSVHandlerTest {

  private CSVHandler csvHandler;
  private File file;

  @BeforeEach
  void setUp() {
    file = FileUtil.generateFilePath("csvHandlerTest", "csv", true).toFile();
    FileUtil.ensureFileAndDirectoryExists(file);
    requireNonNull(file);
    csvHandler = new CSVHandler(file);
  }

  @AfterEach
  void tearDown() {
    file.delete();
    file.getParentFile().delete();
  }

  @Test
  void test_initializeFile() throws IOException {
    assertThat(csvHandler.readCSV()).isNotNull().isEmpty();
    assertThat(file).exists().isFile().isEmpty();
  }
}
