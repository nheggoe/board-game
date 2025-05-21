package edu.ntnu.idi.bidata.boardgame.common.io.csv;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import edu.ntnu.idi.bidata.boardgame.common.io.FileUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CSVWriterTest {

  private File file;

  @BeforeEach
  void setUp() {
    file = FileUtil.generateFilePath("csvWriterTest", "csv", true).toFile();
    FileUtil.ensureFileAndDirectoryExists(file);
  }

  @AfterEach
  void tearDown() {
    file.delete();
    file.getParentFile().delete();
  }

  @DisplayName("Test valid CSV format")
  @ParameterizedTest
  @ValueSource(
      strings = {
        """
      Player, Figure
      Nick    ,        Hat
      Misha ,      BattleShip"""
      })
  void test_writeLines_with_validData(String csvData) throws IOException {
    List<String> lines = List.of(csvData.split("\n"));
    CSVWriter.writeLines(file, lines, false);
    String expectedOutput =
        """
        Player,Figure
        Nick,Hat
        Misha,BattleShip
        """;
    assertThat(file).content().isEqualTo(expectedOutput);
  }

  @DisplayName("Test invalid CSV format")
  @ParameterizedTest
  @ValueSource(
      strings = {
        """
      Player, Figure
      Nick    ,        Hat
      Misha ,      BattleShip""",
        """
      Player, Figure
        """
      })
  void test_writeLines_with_invalidData(String csvData) throws IOException {
    List<String> lines = List.of(csvData.split("\n"));
    CSVWriter.writeLines(file, lines, false);
  }
}
