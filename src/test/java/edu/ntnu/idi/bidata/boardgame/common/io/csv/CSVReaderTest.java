package edu.ntnu.idi.bidata.boardgame.common.io.csv;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.common.io.FileUtil;
import java.io.File;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CSVReaderTest {

  private File testCSV;

  @BeforeEach
  void setUp() {
    testCSV = FileUtil.generateFilePath("testCSV", "csv", true).toFile();
  }

  @Test
  void test_readCSV() {
    assertThatThrownBy(() -> CSVReader.readLines(testCSV))
        .isInstanceOf(FileNotFoundException.class);
  }

  @Test
  void test_readCSV_with_empty_file() {
    FileUtil.ensureFileAndDirectoryExists(testCSV);
    assertThatCode(() -> CSVReader.readLines(testCSV)).doesNotThrowAnyException();
    testCSV.delete();
    testCSV.getParentFile().delete();
  }
}
