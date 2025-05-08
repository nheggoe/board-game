package edu.ntnu.idi.bidata.boardgame.backend.io;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.common.io.FileUtil;
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

  @Test
  void testNullInputs() {
    assertThrows(
        IllegalArgumentException.class, () -> FileUtil.generateFilePath(null, "json", true));
    assertThrows(
        IllegalArgumentException.class, () -> FileUtil.generateFilePath("test", null, true));
    assertDoesNotThrow(() -> FileUtil.generateFilePath("test", "json", true));

    assertThrows(IllegalArgumentException.class, () -> FileUtil.ensureFileAndDirectoryExists(null));
  }

  @Test
  void testEnsureFileAndDirectoryExists() {
    var path = FileUtil.generateFilePath("test", "json", true);
    FileUtil.ensureFileAndDirectoryExists(path.toFile());
    assertTrue(path.toFile().exists());
    assertTrue(path.getParent().toFile().exists());
    assertTrue(path.getParent().getParent().toFile().exists());
    assertTrue(path.getParent().getParent().getParent().toFile().exists());

    // clean up
    path.toFile().delete();
    path.getParent().toFile().delete();
    path.getParent().getParent().toFile().delete();
    path.getParent().getParent().getParent().toFile().delete();
    assertFalse(path.toFile().exists());
    assertFalse(path.getParent().toFile().exists());
    assertFalse(path.getParent().getParent().toFile().exists());
  }
}
