package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

import static edu.ntnu.idi.bidata.boardgame.backend.util.OutputHandler.*;

/**
 * {@code GoToJailTile} represents the tile that sends a player directly to jail when landed on.
 *
 * <p>Extends {@link CornerTile}.
 *
 * @author Mihailo
 * @version 2025.04.25
 */
public final class GoToJailTile extends CornerTile {

  public GoToJailTile(Position position) {
    super(position);
  }

  // todo fix
  // public TileAction getAction() {
  //   return player -> {
  //     println(player.getName() + " has been sent directly to Jail!");
  //     GameEngine.getInstance().goToJail(player);
  //   };
  // }
}
