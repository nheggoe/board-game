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
public class GameEngine<P extends Player, T extends Tile> {

  private final Game<P, T> game;

  public GameEngine(Game<P, T> game) {
    this.game = Objects.requireNonNull(game, "Game cannot be null!");
  }

  public List<P> getPlayers() {
    return List.copyOf(game.getPlayers());
  }

  public List<T> getTiles() {
    return List.copyOf(game.getTiles());
  }

  public void nextTurn() {
    game.nextTrun();
  }
}
