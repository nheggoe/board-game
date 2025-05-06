package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board;

import edu.ntnu.idi.bidata.boardgame.backend.model.tile.Tile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.TileFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code BoardGameFactory} class is a factory class that creates different types of boards. It
 * also is able to read a board from a file.
 *
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.04.22
 */
public class BoardFactory {

  public static Board generateBoard(Layout layout) {
    return switch (layout) {
      case NORMAL, EASY, UNFAIR -> generateNormalBoard();
    };
  }

  private static Board generateNormalBoard() {
    var tmp = TileFactory.generateOwnableTiles();
    List<Tile> tiles = new ArrayList<>(tmp);
    tiles.addAll(TileFactory.generateCornerTiles());
    return new Board(tiles);
  }

  public enum Layout {
    NORMAL,
    UNFAIR,
    EASY
  }
}
