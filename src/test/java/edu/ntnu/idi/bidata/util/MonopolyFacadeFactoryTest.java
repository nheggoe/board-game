package edu.ntnu.idi.bidata.util;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.backend.action.LadderAction;
import edu.ntnu.idi.bidata.boardgame.backend.action.SnakeAction;
import edu.ntnu.idi.bidata.boardgame.backend.io.json.JsonService;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.BoardGameFactory;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class MonopolyFacadeFactoryTest {

  @Test
  void testCreateBoard() {
    Board board = BoardGameFactory.createBoard();
    assertNotNull(board);
    assertEquals(90, board.getNumberOfTiles());
  }

  @Test
  void testCreateUnfairBoard() {
    Board board = BoardGameFactory.generateUnfiarBoard();
    assertNotNull(board);
    assertEquals(90, board.getNumberOfTiles());
    assertInstanceOf(SnakeAction.class, board.getTile(5).getTileAction());
  }

  @Test
  void testCreateEasyBoard() {
    Board board = BoardGameFactory.generateEasyBoard();
    assertNotNull(board);
    assertEquals(90, board.getNumberOfTiles());
    assertInstanceOf(LadderAction.class, board.getTile(5).getTileAction());
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
