package edu.ntnu.idi.bidata.boardgame.backend.io;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileUtilTest {

  @Test
  void testUnsupportedFileFormat() {
    assertThatThrownBy(() -> FileUtil.generateFilePath("test", "html", true))
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatCode(() -> FileUtil.generateFilePath("test", "json", true))
        .doesNotThrowAnyException();
    assertThatCode(() -> FileUtil.generateFilePath("test", "csv", true)).doesNotThrowAnyException();
  }

  @Test
  void testGeneratedFilPath() {
    assertThat(FileUtil.generateFilePath("test", "json", true))
        .isEqualTo(Path.of("src/test/resources/json/test.json"));

    assertThat(FileUtil.generateFilePath("test", "json", false))
        .isEqualTo(Path.of("data/json/test.json"));

    assertThat(FileUtil.generateFilePath("test", "csv", true))
        .isEqualTo(Path.of("src/test/resources/csv/test.csv"));

    assertThat(FileUtil.generateFilePath("test", "csv", false))
        .isEqualTo(Path.of("data/csv/test.csv"));
  }
}
