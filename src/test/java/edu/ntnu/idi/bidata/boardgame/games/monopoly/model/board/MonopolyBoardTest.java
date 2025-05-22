package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board;

import static org.assertj.core.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Property;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.CornerMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.FreeParkingMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.GoToJailMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.JailMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.MonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.OwnableMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.StartMonopolyTile;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MonopolyBoardTest {

  private MonopolyBoard board;

  @BeforeEach
  void setUp() {
    var tiles = new ArrayList<MonopolyTile>();
    tiles.add(new StartMonopolyTile(CornerMonopolyTile.Position.BOTTOM_RIGHT));
    tiles.add(new JailMonopolyTile(CornerMonopolyTile.Position.BOTTOM_LEFT));
    tiles.add(new FreeParkingMonopolyTile(CornerMonopolyTile.Position.TOP_LEFT));
    tiles.add(new GoToJailMonopolyTile(CornerMonopolyTile.Position.TOP_RIGHT));
    tiles.add(new OwnableMonopolyTile(new Property("Test property", Property.Color.DARK_BLUE, 20)));
    tiles.add(new OwnableMonopolyTile(new Property("Test property", Property.Color.BROWN, 40)));
    tiles.add(new OwnableMonopolyTile(new Property("Test property", Property.Color.RED, 60)));
    tiles.add(
        new OwnableMonopolyTile(new Property("Test property", Property.Color.LIGHT_BLUE, 80)));

    var copy = List.copyOf(tiles);
    board = new MonopolyBoard(tiles);

    assertThat(tiles).withFailMessage("Board should not change the original list").isEqualTo(copy);
  }

  @Test
  void test_basic() {
    assertThat(board.tiles()).hasSize(8);
  }
}
