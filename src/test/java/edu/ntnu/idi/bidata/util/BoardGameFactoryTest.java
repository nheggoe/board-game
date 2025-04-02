package edu.ntnu.idi.bidata.util;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.backend.action.LadderAction;
import edu.ntnu.idi.bidata.boardgame.backend.action.SnakeAction;
import edu.ntnu.idi.bidata.boardgame.backend.core.Board;
import edu.ntnu.idi.bidata.boardgame.backend.util.BoardGameFactory;
import edu.ntnu.idi.bidata.boardgame.backend.util.json.JsonService;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class BoardGameFactoryTest {

  @Test
  void testCreateBoard() {
    Board board = BoardGameFactory.createBoard();
    assertNotNull(board);
    assertEquals(90, board.getNumberOfTiles());
  }

  @Test
  void testCreateUnfairBoard() {
    Board board = BoardGameFactory.createUnfairBoard();
    assertNotNull(board);
    assertEquals(90, board.getNumberOfTiles());
    assertInstanceOf(SnakeAction.class, board.getTile(5).getLandAction());
  }

  @Test
  void testCreateEasyBoard() {
    Board board = BoardGameFactory.createEasyBoard();
    assertNotNull(board);
    assertEquals(90, board.getNumberOfTiles());
    assertInstanceOf(LadderAction.class, board.getTile(5).getLandAction());
  }

  @Test
  void testSaveAndLoadBoardJson() {
    Board originalBoard = new Board();
    assertNotNull(originalBoard);
    JsonService<Board> boardService = new JsonService<>(Board.class);
    boardService.addItem(originalBoard);

    Stream<Board> loadedBoards = boardService.loadJsonAsStream();
    Board loadedBoard =
        loadedBoards.findFirst().orElseThrow(() -> new IllegalStateException("No board loaded"));

    assertNotNull(loadedBoard);
    assertEquals(originalBoard.getNumberOfTiles(), loadedBoard.getNumberOfTiles());
  }

  @Test
  void testGetTileOutOfBounds() {
    Board board = BoardGameFactory.createBoard();
    assertThrows(IllegalArgumentException.class, () -> board.getTile(-1));
    assertThrows(IllegalArgumentException.class, () -> board.getTile(1000));
  }
}
