package edu.ntnu.idi.bidata.util;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.action.LadderAction;
import edu.ntnu.idi.bidata.action.SnakeAction;
import edu.ntnu.idi.bidata.core.Board;
import edu.ntnu.idi.bidata.util.json.JsonService;
import java.io.IOException;
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
  void testSaveAndLoadBoardJson() throws IOException {
    Board originalBoard = new Board();
    assertNotNull(originalBoard);
    JsonService boardService = new JsonService(Board.class);
    boardService.writeCollection(Stream.of(originalBoard));

    Stream<Board> loadedBoards = boardService.loadJsonAsStream();
    Board loadedBoard = loadedBoards.findFirst().orElse(null);

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
