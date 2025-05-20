package edu.ntnu.idi.bidata.boardgame.core;

import edu.ntnu.idi.bidata.boardgame.core.model.Game;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.core.model.Tile;
import java.util.List;
import java.util.Objects;

/**
 * @author Nick Heggø
 * @version 2025.05.09
 */
public record GameEngine<T extends Tile, P extends Player>(Game<T, P> game) {

  public GameEngine {
    Objects.requireNonNull(game, "Game cannot be null!");
  }

  public List<P> getPlayers() {
    return List.copyOf(game.getPlayers());
  }

  public List<T> getTiles() {
    return List.copyOf(game.getTiles());
  }

  public void nextTurn() {
    if (game.isEnded()) {
      return;
    }
    game.nextTurn();
  }

  public boolean isEnded() {
    return game.isEnded();
  }
}
