package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

import edu.ntnu.idi.bidata.boardgame.backend.model.property.Property;

/**
 * The {@link PropertyTile} class is a specialized type of {@link Tile} that represents a property
 * on the game board. A property tile includes a {@link Property} that can be owned or purchased by
 * players.
 *
 * <p>When a player lands on this tile, they may have the opportunity to purchase the associated
 * property.
 *
 * @author Nick Heggø
 * @version 2025.04.15
 */
public class PropertyTile extends Tile {

  private Property property;

  public PropertyTile(int position, String name) {
    super(
        position,
        name,
        (player -> {
          System.out.println("Do you want to buy this property for ");
        }));
  }
}
