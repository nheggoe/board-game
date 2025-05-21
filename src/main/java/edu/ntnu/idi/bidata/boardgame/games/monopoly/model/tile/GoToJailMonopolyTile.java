package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile;

/**
 * {@code GoToJailTile} represents the tile that sends a player directly to jail when landed on.
 *
 * <p>Extends {@link CornerMonopolyTile}.
 *
 * @author Mihailo
 * @version 2025.04.25
 */
public final class GoToJailMonopolyTile extends CornerMonopolyTile {

  public GoToJailMonopolyTile(Position position) {
    super(position);
  }
}
