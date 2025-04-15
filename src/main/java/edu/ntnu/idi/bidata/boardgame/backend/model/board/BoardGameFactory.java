package edu.ntnu.idi.bidata.boardgame.backend.model.board;

import edu.ntnu.idi.bidata.boardgame.backend.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.Tile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.TileAction;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code BoardGameFactory} class is a factory class that creates different types of boards. It
 * also is able to read a board from a file.
 *
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.04.15
 */
public class BoardGameFactory {

  // temporary for compatibility issues
  private static final TileAction snakeAction =
      player -> {
        player.move(-10);
        System.out.println(player.getName() + " is hit by snake action");
      };

  // temporary for compatibility issues
  private static final TileAction ladderAction =
      player -> {
        player.move(5);
        System.out.println(player.getName() + " is hit by ladder action");
      };

  // temporary for compatibility issues
  private static final TileAction resetAction =
      player -> {
        player.setCurrentTile(GameEngine.getInstance().getGame().getBoard().getStartingTile());
        System.out.println(player.getName() + " is hit by reset action");
      };

  public static Board generateBoard(Layout layout) {
    return switch (layout) {
      case NORMAL -> generateNormalBoard();
      case EASY -> generateEasyBoard();
      case UNFAIR -> generateUnfiarBoard();
    };
  }

  private static Board generateNormalBoard() {
    List<Tile> tmp = generateTiles(90);
    tmp.get(12).setTileAction(snakeAction);
    tmp.get(8).setTileAction(snakeAction);
    return new Board(tmp);
  }

  /**
   * Creates a board with snakes and reset actions to represent an unfair board.
   *
   * @return a new board with snakes and reset actions
   */
  private static Board generateUnfiarBoard() {
    List<Tile> tiles = generateTiles(88);
    tiles.get(5).setTileAction(snakeAction);
    tiles.get(15).setTileAction(snakeAction);
    tiles.get(18).setTileAction(snakeAction);
    tiles.get(40).setTileAction(snakeAction);
    tiles.get(45).setTileAction(snakeAction);
    tiles.get(50).setTileAction(resetAction);
    tiles.get(60).setTileAction(resetAction);
    tiles.get(65).setTileAction(resetAction);
    tiles.get(80).setTileAction(snakeAction);
    return new Board(tiles);
  }

  /**
   * Creates a board with ladders to represent an easy board.
   *
   * @return a new board with ladders
   */
  private static Board generateEasyBoard() {
    List<Tile> tiles = generateTiles(60);
    tiles.get(5).setTileAction(ladderAction);
    tiles.get(15).setTileAction(ladderAction);
    tiles.get(18).setTileAction(ladderAction);
    tiles.get(40).setTileAction(ladderAction);
    tiles.get(45).setTileAction(ladderAction);
    tiles.get(50).setTileAction(ladderAction);
    tiles.get(60).setTileAction(ladderAction);
    tiles.get(65).setTileAction(ladderAction);
    tiles.get(80).setTileAction(ladderAction);
    return new Board(tiles);
  }

  private static List<Tile> generateTiles(int numberOfTiles) {
    List<Tile> tmp = new ArrayList<>();
    for (int i = 1; i <= numberOfTiles - 2; i++) {
      final int finalI = i + 1;
      tmp.add(
          new Tile(
              i,
              "Tile",
              player -> System.out.println(player.getName() + " is safe on tile " + finalI)) {});
    }
    tmp.addFirst(
        new Tile(
            0,
            "Start",
            player -> System.out.println(player.getName() + " is on the first tile")) {});
    tmp.addLast(
        new Tile(
            tmp.size() - 1,
            "End",
            player -> System.out.println(player.getName() + " is on the last tile")) {});
    return tmp;
  }

  public enum Layout {
    NORMAL,
    UNFAIR,
    EASY
  }
}
