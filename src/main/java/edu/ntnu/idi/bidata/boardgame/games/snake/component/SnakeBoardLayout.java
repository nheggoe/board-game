package edu.ntnu.idi.bidata.boardgame.games.snake.component;

import javafx.geometry.Point2D;

/** Helper class for converting between tile numbers and grid coordinates in a snake board. */
public final class SnakeBoardLayout {

  private SnakeBoardLayout() {}

  /** Converts a 1-based tile number to a ({@code col}, {@code row}) pair. */
  public static Point2D toGrid(int tileNumber, int dimension) {
    int n = tileNumber - 1;

    int rowFromBottom = n / dimension;
    int row = dimension - 1 - rowFromBottom;

    boolean leftToRight = (rowFromBottom % 2 == 0);
    int colInRow = n % dimension;
    int col = leftToRight ? colInRow : dimension - 1 - colInRow;

    return new Point2D(col, row);
  }
}
