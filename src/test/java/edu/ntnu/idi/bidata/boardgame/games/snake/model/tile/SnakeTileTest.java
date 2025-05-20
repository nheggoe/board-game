package edu.ntnu.idi.bidata.boardgame.games.snake.model.tile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SnakeTileTest {

  @Test
  void test_basic() {
    var snakeTile = new SnakeTile(1);
    assertThat(snakeTile).hasToString("SnakeTile[tilesToSlideBack=1]");
  }

  @Test
  void test_invalid_data() {
    assertThatThrownBy(() -> new SnakeTile(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Tiles to slide back must be non-zero");

    assertThatThrownBy(() -> new SnakeTile(-1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Tiles to slide back must be non-negative");
  }
}
